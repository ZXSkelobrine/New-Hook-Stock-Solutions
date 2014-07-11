package com.github.ZXSkelobrine.stock;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.github.ZXSkelobrine.stock.errors.NotPreparedException;
import com.github.ZXSkelobrine.stock.functions.sql.SQLFunctions;
import com.github.ZXSkelobrine.stock.windows.stock.StockOverview;

public class Launcher {

	public static final boolean TESTING = false;
	public static final String VERSION = "1.0.1";
	public static final String NAME = "New Hook Stock";
	public static final String BASE_SAVE_PATH = System.getProperty("user.home") + "/" + NAME;

	public static void main(String[] args) {
		if (TESTING) {
			System.out.println(BASE_SAVE_PATH + "/databases/stock/stock.db");
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
