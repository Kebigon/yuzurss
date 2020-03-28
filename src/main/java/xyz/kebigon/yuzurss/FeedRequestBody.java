package xyz.kebigon.yuzurss;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class FeedRequestBody
{
	private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
	private static final String DATE_FORMAT_TINEZONE = "UTC";

	private List<String> urls;
	private Integer limit;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_PATTERN, timezone = DATE_FORMAT_TINEZONE)
	private Date dateBefore;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_PATTERN, timezone = DATE_FORMAT_TINEZONE)
	private Date dateAfter;
}
