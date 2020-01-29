package xyz.kebigon.yuzurss;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class FeedRequestBody
{
	public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

	public static final String DATE_FORMAT_TINEZONE = "UTC";

	private List<String> urls;

	private Integer limit;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
	private Date dateBefore;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
	private Date dateAfter;
}