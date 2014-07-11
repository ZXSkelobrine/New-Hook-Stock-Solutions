package com.github.ZXSkelobrine.stock.errors;

public class TableCreationError extends Throwable {

	private static final long serialVersionUID = -3264490123182432992L;

	public TableCreationError(String location, int amount) {
		super("Table creation failed on database: " + location.replace('\\', '/') + " after " + amount + " retry attempts.");
	}
}
