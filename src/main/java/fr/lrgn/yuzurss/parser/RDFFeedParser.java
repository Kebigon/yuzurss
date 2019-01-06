package fr.lrgn.yuzurss.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import fr.lrgn.yuzurss.FeedEntry;
import fr.lrgn.yuzurss.exception.DateParseException;
import reactor.core.publisher.Flux;

public class RDFFeedParser extends FeedParser
{
	private static final String RDF_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX"; // 2018-11-03T18:12:15+00:00

	private static final ThreadLocal<SimpleDateFormat> rdfDateFormat = new ThreadLocal<SimpleDateFormat>()
	{
		@Override
		protected SimpleDateFormat initialValue()
		{
			return new SimpleDateFormat(RDF_DATE_FORMAT);
		};
	};

	@Override
	public boolean acceptFeed(JSONObject root)
	{
		return root.has("rdf:RDF");
	}

	@Override
	public Flux<FeedEntry> parseFeed(JSONObject root)
	{
		Flux<FeedEntry> entries = Flux.empty();

		final JSONObject channel = root.getJSONObject("rdf:RDF").getJSONObject("channel");
		final String author = channel.getString("title");

		for (final Object entry : root.getJSONObject("rdf:RDF").getJSONArray("item"))
		{
			log.debug("Parsing entry {}", entry);

			final String link = ((JSONObject) entry).getString("link");
			final String title = ((JSONObject) entry).getString("title");
			final Date published = parseDate(((JSONObject) entry).getString("dc:date"));

			entries = entries.mergeWith(Flux.just(new FeedEntry(title, link, published, author)));
		}

		return entries;
	}

	private Date parseDate(String date)
	{
		try
		{
			return rdfDateFormat.get().parse(date);
		}
		catch (final ParseException ex)
		{
			throw new DateParseException(date, RDF_DATE_FORMAT, ex);
		}
	}
}
