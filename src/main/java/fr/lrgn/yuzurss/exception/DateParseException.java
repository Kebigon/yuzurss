package fr.lrgn.yuzurss.exception;

import java.text.ParseException;

public class DateParseException extends YuzuRSSException
{
	private static final long serialVersionUID = 1L;

	public DateParseException(String date, String format, ParseException cause)
	{
		super("Unable to parse date " + date + " with format " + format, cause);
	}
}
