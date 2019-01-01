package fr.lrgn.yuzurss;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/feed")
@CrossOrigin
public class FeedController
{
	@Autowired
	private FeedClient client;

	@PostMapping()
	public Flux<FeedEntry> getFeeds(@RequestBody FeedRequestBody body)
	{
		return Flux.fromIterable(body.getUrls()).flatMap(url -> client.getFeed(URI.create(url))).sort(FeedEntry.COMPARATOR)
				.take(body.getLimit());
	}
}
