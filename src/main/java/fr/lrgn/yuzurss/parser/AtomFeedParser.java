package fr.lrgn.yuzurss.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import fr.lrgn.yuzurss.FeedEntry;
import fr.lrgn.yuzurss.exception.DateParseException;
import reactor.core.publisher.Flux;

public class AtomFeedParser extends FeedParser
{
	private static final String ATOM_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX"; // 2018-11-03T18:12:15+00:00

	private static final ThreadLocal<SimpleDateFormat> atomDateFormat = new ThreadLocal<SimpleDateFormat>()
	{
		@Override
		protected SimpleDateFormat initialValue()
		{
			return new SimpleDateFormat(ATOM_DATE_FORMAT);
		};
	};

	@Override
	public boolean acceptFeed(JSONObject root)
	{
		return root.has("feed");
	}

	@Override
	public Flux<FeedEntry> parseFeed(JSONObject root)
	{
		Flux<FeedEntry> entries = Flux.empty();

		for (final Object entry : root.getJSONObject("feed").getJSONArray("entry"))
		{
			log.debug("Parsing entry {}", entry);

			final String author = ((JSONObject) entry).getJSONObject("author").getString("name");
			final String link = ((JSONObject) entry).getJSONObject("link").getString("href");
			final String title = ((JSONObject) entry).getString("title");
			final Date published = parseDate(((JSONObject) entry).getString("published"));

			entries = entries.mergeWith(Flux.just(new FeedEntry(title, link, published, author)));
		}

		return entries;
	}

	public Date parseDate(String date)
	{
		try
		{
			return atomDateFormat.get().parse(date);
		}
		catch (final ParseException ex)
		{
			throw new DateParseException(date, ATOM_DATE_FORMAT, ex);
		}
	}
}
