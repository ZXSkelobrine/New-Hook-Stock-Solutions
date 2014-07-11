package com.github.ZXSkelobrine.stock.functions.errors;

import java.util.ArrayList;
import java.util.List;

import com.github.ZXSkelobrine.stock.interfaces.AddUpdate;
import com.github.ZXSkelobrine.stock.variables.ErrorLogItem;

public class ErrorLogFunctions {

	private static List<ErrorLogItem> items = new ArrayList<ErrorLogItem>();
	private static boolean isPersistantErrorEnabled = false;
	private static boolean isErrorLogActive = false;
	private static AddUpdate update = null;

	public static void setUpdateListener(AddUpdate listener) {
		update = listener;
	}

	public static synchronized void addItem(ErrorLogItem item) {
		if (isPersistantErrorEnabled || isErrorLogActive) {
			synchronized (items) {
				items.add(item);
				if (update != null) update.update();
			}
		}
	}

	/**
	 * @return the isPersistantErrorEnabled
	 */
	public static boolean isPersistantErrorEnabled() {
		return isPersistantErrorEnabled;
	}

	/**
	 * @param isPersistantErrorEnabled
	 *            the isPersistantErrorEnabled to set
	 */
	public static void setPersistantErrorEnabled(boolean isPersistantErrorEnabled) {
		ErrorLogFunctions.isPersistantErrorEnabled = isPersistantErrorEnabled;
	}

	/**
	 * @return the isErrorLogActive
	 */
	public static boolean isErrorLogActive() {
		return isErrorLogActive;
	}

	/**
	 * @param isErrorLogActive
	 *            the isErrorLogActive to set
	 */
	public static void setErrorLogActive(boolean isErrorLogActive) {
		ErrorLogFunctions.isErrorLogActive = isErrorLogActive;
	}

	/**
	 * @return the items
	 */
	public static List<ErrorLogItem> getItems() {
		return items;
	}

}
