package fr.lrgn.yuzurss;

import java.net.URI;

import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import fr.lrgn.yuzurss.exception.NoParserFoundException;
import fr.lrgn.yuzurss.parser.AtomFeedParser;
import fr.lrgn.yuzurss.parser.FeedParser;
import fr.lrgn.yuzurss.parser.RSSFeedParser;
import reactor.core.publisher.Flux;

@Component
public class FeedClient
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final FeedParser[] parsers = new FeedParser[] { new AtomFeedParser(), new RSSFeedParser() };

	@Cacheable("feeds")
	public Flux<FeedEntry> getFeed(URI uri)
	{
		final WebClient client = WebClient.create();
		final RequestHeadersSpec<?> request = client.get().uri(uri);
		final ResponseSpec response = request.retrieve();

		log.info("Downloading {}", uri);

		return response.bodyToMono(String.class).flatMapMany(xml -> {
			log.info("Downloaded {}", uri);

			final JSONObject root = XML.toJSONObject(xml);

			try
			{
				return getFeedParser(uri, root).parseFeed(root);
			}
			catch (final Throwable e)
			{
				log.info("Exception while parsing {}", uri, e);
				return Flux.empty();
			}
		}).cache();
	}

	private FeedParser getFeedParser(URI uri, JSONObject root)
	{
		for (final FeedParser parser : parsers)
			if (parser.acceptFeed(root))
				return parser;

		throw new NoParserFoundException(uri);
	}
}
