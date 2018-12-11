package fr.lrgn.yuzurss;

import java.util.Comparator;
import java.util.Date;

public class FeedEntry
{
	public static final Comparator<FeedEntry> COMPARATOR = (FeedEntry o1, FeedEntry o2) -> o2.getPublished().compareTo(o1.getPublished());

	private final String title;
	private final String link;
	private final Date published;
	private final String author;

	public FeedEntry(String title, String link, Date published, String author)
	{
		this.title = title;
		this.link = link;
		this.published = published;
		this.author = author;
	}

	public String getTitle()
	{
		return title;
	}

	public String getLink()
	{
		return link;
	}

	public Date getPublished()
	{
		return published;
	}

	public String getAuthor()
	{
		return author;
	}
}