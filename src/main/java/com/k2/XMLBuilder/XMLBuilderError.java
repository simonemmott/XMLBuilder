package com.k2.XMLBuilder;

public class XMLBuilderError extends Error {

	public XMLBuilderError() {
	}

	public XMLBuilderError(String message) {
		super(message);
	}

	public XMLBuilderError(Throwable cause) {
		super(cause);
	}

	public XMLBuilderError(String message, Throwable cause) {
		super(message, cause);
	}

	public XMLBuilderError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
