package com.github.ZXSkelobrine.stock;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.github.ZXSkelobrine.stock.global.sql.SQLFunctions;
import com.github.ZXSkelobrine.stock.global.updates.UpdateManager;
import com.github.ZXSkelobrine.stock.management.errors.NotPreparedException;
import com.github.ZXSkelobrine.stock.management.windows.stock.StockOverview;
import com.github.ZXSkelobrine.stock.shop.windows.ShopWindow;

public class Launcher {

	public static final int MAJOR_VERSION = 1;
	public static final int EXPORT_VERSION = 3;
	public static final int MINOR_VERSION = 4;

	public static final boolean TESTING = false;

	public static final String VERSION = MAJOR_VERSION + "." + EXPORT_VERSION + "." + MINOR_VERSION;
	public static final String NAME = "New Hook Stock";
	public static final String BASE_SAVE_PATH = System.getProperty("user.home") + "/" + NAME;

	public static void main(String[] args) {
		if (TESTING) {
			System.out.println(BASE_SAVE_PATH + "/databases/stock/stock.db");
		} else {
			// if (UpdateManager.updateAvailable) {
			// UpdateManager.processUpdate();
			// }
			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("wolaf")) {
					try {
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
					run();
				}
				if (args[0].equalsIgnoreCase("wilaf")) {
					// applyLaf();
					run();
				}
				if (args[0].equalsIgnoreCase("shop")) {
					if (args.length == 2) {
						ShopWindow.launchWindow(Integer.parseInt(args[1]));
					}
				}
			} else {
				// applyLaf();
				run();
			}
		}
	}

	private static void run() {
		if (SQLFunctions.prepareDatabaseConnection(BASE_SAVE_PATH + "/databases/stock/stock.db", null, null)) {
			StockOverview.launchWindow();
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					try {
						SQLFunctions.closeDatabase();
					} catch (NotPreparedException e) {
						e.printStackTrace();
					}
				}
			});
		} else {
		}
	}

	private static void applyLaf() {
		try {
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
			UpdateManager.checkForUpdate();
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
	}
}
