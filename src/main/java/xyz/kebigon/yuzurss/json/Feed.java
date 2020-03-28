package xyz.kebigon.yuzurss.json;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Feed
{
	private final String version = "https://jsonfeed.org/version/1";
	private final String title = "YuzuRSS aggregated feed";
	private final Collection<Item> items;

	public Feed(Collection<Item> items)
	{
		this.items = items;
	}
}
