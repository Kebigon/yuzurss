package fr.lrgn.yuzurss.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;

import fr.lrgn.yuzurss.FeedEntry;
import fr.lrgn.yuzurss.exception.DateParseException;
import reactor.core.publisher.Flux;

public class RSSFeedParser extends FeedParser
{
	private static final String RSS_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z"; // Sun, 09 Dec 2018 09:22:00 +0000

	private static final ThreadLocal<SimpleDateFormat> rssDateFormat = new ThreadLocal<SimpleDateFormat>()
	{
		@Override
		protected SimpleDateFormat initialValue()
		{
			return new SimpleDateFormat(RSS_DATE_FORMAT, Locale.ROOT);
		};
	};

	@Override
	public boolean acceptFeed(JSONObject root)
	{
		return root.has("rss");
	}

	@Override
	public Flux<FeedEntry> parseFeed(JSONObject root)
	{
		Flux<FeedEntry> entries = Flux.empty();
		final JSONObject channel = root.getJSONObject("rss").getJSONObject("channel");

		if (channel.has("item"))
		{
			final String author = channel.getString("title");

			for (final Object entry : channel.getJSONArray("item"))
			{
				log.debug("Parsing entry {}", entry);

				final String link = ((JSONObject) entry).getString("link");
				final String title = ((JSONObject) entry).getString("title");
				final Date published = parseDate(((JSONObject) entry).getString("pubDate"));

				entries = entries.mergeWith(Flux.just(new FeedEntry(title, link, published, author)));
			}
		}

		return entries;
	}

	public Date parseDate(String date)
	{
		try
		{
			return rssDateFormat.get().parse(date);
		}
		catch (final ParseException ex)
		{
			throw new DateParseException(date, RSS_DATE_FORMAT, ex);
		}
	}
}
