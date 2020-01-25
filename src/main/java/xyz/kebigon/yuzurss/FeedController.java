package xyz.kebigon.yuzurss;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import xyz.kebigon.yuzurss.json.Feed;
import xyz.kebigon.yuzurss.json.Item;

@RestController
@CrossOrigin
public class FeedController
{
	@Autowired
	private FeedClient client;

	@Value("${feeds.youtube.replaceByInvidious}")
	private boolean replaceYoutubeByInvidious;
	@Value("${invidious.url}")
	private String invidiousUrl;

	@PostMapping
	public Feed getFeeds(@RequestBody FeedRequestBody body)
	{
		List<Item> items = body.getUrls().parallelStream() //
				.flatMap(this::getItems) //
				.sorted() //
				.collect(Collectors.toList());

		if (body.getLimit() < items.size())
			items = items.subList(0, body.getLimit());

		if (replaceYoutubeByInvidious)
			items.parallelStream().filter(item -> item.getUrl().contains("https://www.youtube.com"))
					.forEach(item -> item.setUrl(item.getUrl().replace("https://www.youtube.com", invidiousUrl)));

		return new Feed(items);
	}

	private Stream<Item> getItems(String url)
	{
		return client.getFeed(url).getEntries().stream().map(entry -> new Item(entry));
	}
}