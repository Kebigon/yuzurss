package fr.lrgn.yuzurss;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/feed")
public class FeedController
{
	@Autowired
	private FeedClient client;

	@GetMapping("/{urls}")
	Flux<FeedEntry> getFeeds(@PathVariable String[] urls)
	{
		return Flux.fromArray(urls).map(url -> URI.create(url)).flatMap(uri -> client.getFeed(uri)).sort(FeedEntry.COMPARATOR);
	}
}