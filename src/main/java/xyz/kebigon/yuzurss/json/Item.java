package xyz.kebigon.yuzurss.json;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rometools.rome.feed.synd.SyndEntry;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Item implements Comparable<Item>
{
	public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	public static final String DATE_FORMAT_TINEZONE = "UTC";

	private final String id;
	private String url;
	private final String title;

	@JsonProperty("content_html")
	private String contentHtml;
	private String summary;

	@JsonProperty("date_published")
	@JsonFormat(shape = Shape.STRING, pattern = DATE_FORMAT_PATTERN, timezone = DATE_FORMAT_TINEZONE)
	private Date datePublished;
	private Author author;

	public Item(SyndEntry entry)
	{
		this.id = entry.getUri();
		this.url = entry.getLink();
		this.title = sanitize(entry.getTitle());

		if (!entry.getContents().isEmpty())
			contentHtml = entry.getContents().get(0).getValue();
		if (entry.getDescription() != null)
			summary = sanitize(entry.getDescription().getValue());
		if (entry.getPublishedDate() != null)
			this.datePublished = entry.getPublishedDate();
		else if (entry.getUpdatedDate() != null)
			this.datePublished = entry.getUpdatedDate();
		if (!entry.getAuthor().isEmpty())
			this.author = new Author(entry.getAuthor());

		if (contentHtml == null && summary != null)
			contentHtml = summary;
	}

	@Override
	public int compareTo(Item other)
	{
		if (datePublished != null && other.datePublished != null)
			return other.datePublished.compareTo(datePublished);

		return 0;
	}

	public static String sanitize(String s)
	{
		return s.replace("\n", "").replace("\t", "");
	}
}