package fr.lrgn.yuzurss;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import reactor.core.publisher.Flux;

@Component
public class FeedClient
{
	private static final String ATOM_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX"; // 2018-11-03T18:12:15+00:00
	private static final String RSS_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z"; // Sun, 09 Dec 2018 09:22:00 +0000

	private final SimpleDateFormat atomDateFormat = new SimpleDateFormat(ATOM_DATE_FORMAT);
	private final SimpleDateFormat rssDateFormat = new SimpleDateFormat(RSS_DATE_FORMAT, Locale.ROOT);

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Cacheable("feeds")
	public Flux<FeedEntry> getFeed(URI uri)
	{
		final WebClient client = WebClient.create();
		final RequestHeadersSpec<?> request = client.get().uri(uri);
		final ResponseSpec response = request.retrieve();

		return response.bodyToMono(String.class).flatMapMany(xml -> {
			log.info("Downloaded {}", uri);

			final JSONObject root = XML.toJSONObject(xml);

			try
			{
				return isAtom(root) ? parseAtomFeed(root) : parseRSSFeed(root);
			}
			catch (final JSONException | ParseException e)
			{
				log.info("Exception while parsing {}", uri, e);
				return Flux.empty();
			}
		}).cache();
	}

	private static boolean isAtom(JSONObject root)
	{
		return root.has("feed");
	}

	private Flux<FeedEntry> parseAtomFeed(JSONObject root) throws JSONException, ParseException
	{
		Flux<FeedEntry> entries = Flux.empty();

		for (final Object entry : root.getJSONObject("feed").getJSONArray("entry"))
		{
			final String author = ((JSONObject) entry).getJSONObject("author").getString("name");
			final String link = ((JSONObject) entry).getJSONObject("link").getString("href");
			final String title = ((JSONObject) entry).getString("title");
			final Date published = atomDateFormat.parse(((JSONObject) entry).getString("published"));

			entries = entries.mergeWith(Flux.just(new FeedEntry(title, link, published, author)));
		}

		return entries;
	}

	private Flux<FeedEntry> parseRSSFeed(JSONObject root) throws JSONException, ParseException
	{
		Flux<FeedEntry> entries = Flux.empty();

		final JSONObject channel = root.getJSONObject("rss").getJSONObject("channel");
		final String author = channel.getString("title");

		for (final Object entry : channel.getJSONArray("item"))
		{
			final String link = ((JSONObject) entry).getString("link");
			final String title = ((JSONObject) entry).getString("title");
			final Date published = rssDateFormat.parse(((JSONObject) entry).getString("pubDate"));

			entries = entries.mergeWith(Flux.just(new FeedEntry(title, link, published, author)));
		}

		return entries;
	}
}