package com.github.ZXSkelobrine.stock;

import javax.swing.UIManager;

import com.github.ZXSkelobrine.stock.global.sql.SQLFunctions;
import com.github.ZXSkelobrine.stock.global.updates.UpdateManager;
import com.github.ZXSkelobrine.stock.management.errors.NotPreparedException;
import com.github.ZXSkelobrine.stock.management.windows.stock.StockOverview;

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
			if (args.length == 0) {
				try {
					UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
					UpdateManager.checkForUpdate();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
				if (UpdateManager.updateAvailable) {
					UpdateManager.processUpdate();
				} else {
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
			} else if (args.length == 1) {
				switch (args[0]) {
				case "shop":

					break;
				}
			}
		}
	}
}
