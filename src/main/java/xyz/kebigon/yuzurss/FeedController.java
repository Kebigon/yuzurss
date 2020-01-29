package xyz.kebigon.yuzurss;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import xyz.kebigon.yuzurss.json.Feed;
import xyz.kebigon.yuzurss.json.Item;

@RestController
@CrossOrigin
@Slf4j
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
		Stream<Item> itemsStream = body.getUrls().parallelStream().flatMap(this::getItems);

		// Date filters
		if (body.getDateBefore() != null)
			itemsStream = itemsStream.filter(i -> !i.getDatePublished().toInstant().truncatedTo(ChronoUnit.DAYS).isAfter(body.getDateBefore().toInstant()));
		if (body.getDateAfter() != null)
			itemsStream = itemsStream.filter(i -> !i.getDatePublished().toInstant().truncatedTo(ChronoUnit.DAYS).isBefore(body.getDateAfter().toInstant()));

		// Sorting the items before applying the limit
		itemsStream = itemsStream.sequential().sorted();

		if (body.getLimit() != null)
			itemsStream = itemsStream.limit(body.getLimit().intValue());

		final List<Item> items = itemsStream.collect(Collectors.toList());

		if (this.replaceYoutubeByInvidious)
			items.parallelStream().filter(item -> item.getUrl().contains("https://www.youtube.com"))
					.forEach(item -> item.setUrl(item.getUrl().replace("https://www.youtube.com", this.invidiousUrl)));

		return new Feed(items);
	}

	private Stream<Item> getItems(String url)
	{
		return client.getFeed(url).getEntries().stream().map(entry -> new Item(entry));
	}
}