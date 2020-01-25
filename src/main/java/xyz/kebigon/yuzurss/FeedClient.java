package xyz.kebigon.yuzurss;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FeedClient
{
	private final RestTemplate restTemplate = new RestTemplate(Arrays.asList(new AtomFeedHttpMessageConverter(), new RssChannelHttpMessageConverter()));
	private final SyndFeedInput input = new SyndFeedInput();

	@Cacheable("feeds")
	public SyndFeed getFeed(String url)
	{
		log.info("Downloading {}", url);

		return restTemplate.execute(url, HttpMethod.GET, null, response -> {
			try
			{
				return input.build(new XmlReader(response.getBody()));
			} catch (IllegalArgumentException | FeedException e)
			{
				throw new IOException(e);
			}
		});
	}
}