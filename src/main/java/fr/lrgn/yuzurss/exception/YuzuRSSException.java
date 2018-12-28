package fr.lrgn.yuzurss.exception;

abstract class YuzuRSSException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	protected YuzuRSSException(String message)
	{
		super(message);
	}

	protected YuzuRSSException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
