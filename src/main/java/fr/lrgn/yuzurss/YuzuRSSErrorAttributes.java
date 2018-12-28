package fr.lrgn.yuzurss;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Component
public class YuzuRSSErrorAttributes extends DefaultErrorAttributes
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace)
	{
		log.error("Error while processsing request {}", request.uri(), getError(request));

		return super.getErrorAttributes(request, includeStackTrace);
	}
}
