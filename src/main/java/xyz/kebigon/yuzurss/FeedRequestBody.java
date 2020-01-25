package xyz.kebigon.yuzurss;

import java.util.List;

import lombok.Data;

@Data
public class FeedRequestBody
{
	private final List<String> urls;
	private final int limit;
}