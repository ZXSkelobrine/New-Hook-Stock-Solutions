package com.github.ZXSkelobrine.stock;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.github.ZXSkelobrine.stock.errors.NotPreparedException;
import com.github.ZXSkelobrine.stock.functions.sql.SQLFunctions;
import com.github.ZXSkelobrine.stock.updates.UpdateManager;
import com.github.ZXSkelobrine.stock.windows.stock.StockOverview;

public class Launcher {

	public static final int MAJOR_VERSION = 1;
	public static final int EXPORT_VERSION = 2;
	public static final int MINOR_VERSION = 4;

	public static final boolean TESTING = false;

	public static final String VERSION = MAJOR_VERSION + "." + EXPORT_VERSION + "." + MINOR_VERSION;
	public static final String NAME = "New Hook Stock";
	public static final String BASE_SAVE_PATH = System.getProperty("user.home") + "/" + NAME;

	public static void main(String[] args) {
		if (TESTING) {
			System.out.println(BASE_SAVE_PATH + "/databases/stock/stock.db");
		} else {
			try {
				UpdateManager.checkForUpdate();
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
			if (UpdateManager.updateAvailable) {
				UpdateManager.processUpdate();
			} else {
				if (SQLFunctions.prepareDatabaseConnection(BASE_SAVE_PATH + "/databases/stock/stock.db", null, null)) {
					try {
						UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
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
		}
	}
}
