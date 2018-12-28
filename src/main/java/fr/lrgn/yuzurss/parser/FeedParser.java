package fr.lrgn.yuzurss.parser;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lrgn.yuzurss.FeedEntry;
import reactor.core.publisher.Flux;

public abstract class FeedParser
{
	protected final Logger log = LoggerFactory.getLogger(getClass());

	public abstract boolean acceptFeed(JSONObject root);

	public abstract Flux<FeedEntry> parseFeed(JSONObject root);
}
