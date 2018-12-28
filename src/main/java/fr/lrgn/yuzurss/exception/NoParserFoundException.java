package fr.lrgn.yuzurss.exception;

import java.net.URI;

public class NoParserFoundException extends YuzuRSSException
{
	private static final long serialVersionUID = 1L;

	public NoParserFoundException(URI uri)
	{
		super("No parser found for feed " + uri);
	}
}
