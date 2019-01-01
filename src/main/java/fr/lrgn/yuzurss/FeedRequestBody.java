package fr.lrgn.yuzurss;

import java.util.List;

public class FeedRequestBody
{
	private final List<String> urls;
	private final int limit;

	public FeedRequestBody(List<String> urls, int limit)
	{
		this.urls = urls;
		this.limit = limit;
	}

	public List<String> getUrls()
	{
		return urls;
	}

	public int getLimit()
	{
		return limit;
	}
}
