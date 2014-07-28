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
	public static boolean PRINT_STACK_TRACES;
	public static final String VERSION = MAJOR_VERSION + "." + EXPORT_VERSION + "." + MINOR_VERSION;
	public static final String NAME = "New Hook Stock";
	public static final String BASE_SAVE_PATH = System.getProperty("user.home") + "\\" + NAME;

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			sb.append(args[i]);
			sb.append(" ");
		}
		String[] split = sb.toString().split("-");
		boolean testing = false;
		boolean updateOnly = false;
		boolean launchShop = false;
		int shopPort = -1;
		boolean applyWindowsLaf = false;
		boolean help = false;
		String addedHelp = "";
		boolean applyLaf = true;
		boolean showMang = true;
		for (String s : split) {
			if (s.contains("help")) {
				help = true;
				showMang = false;
			}
			if (s.contains("test")) {
				testing = true;
				showMang = false;
			}
			if (s.contains("checkForUpdate")) {
				updateOnly = true;
				showMang = false;
			}
			if (s.contains("shop")) {
				launchShop = true;
				showMang = false;
			}
			if (s.contains("customLaf")) {
				String[] clafSplit = s.split(":");
				if (clafSplit.length == 2) {
					if (clafSplit[1].contains("system")) {
						applyLaf = false;
					} else if (clafSplit[1].contains("window")) {
						applyLaf = false;
						applyWindowsLaf = true;
					} else if (clafSplit[1].contains("custom")) {
					} else {
						help = true;
						addedHelp = "-customLaf: <Look And Feel> - <Look And Feel> must equal one of the following: system, window, custom";
						showMang = false;
					}
				} else {
					help = true;
					addedHelp = "-customLaf: <Look And Feel> - Not enough arguments. Missing <Look and Feel>";
					showMang = false;
				}
			}
			if (s.contains("port")) {
				String[] sportSplit = s.split(":");
				if (sportSplit.length == 2) {
					String woSpaces = sportSplit[1].replaceAll("\\s+", "");
					try {
						shopPort = Integer.parseInt(woSpaces);
					} catch (NumberFormatException e) {
						help = true;
						addedHelp = "-port: <Serial Port> - <Serial Port> must be a valid integer.";
					}
				} else {
					help = true;
					addedHelp = "-port: <Serial Port> - Not enough arguments. Missing <Serial Port>";
				}
			}
			if (s.contains("shop")) {
				launchShop = true;
				showMang = false;
			}
			if (s.contains("showSTs")) {
				PRINT_STACK_TRACES = true;
			}
		}
		if (launchShop && shopPort == -1) {
			launchShop = false;
			help = true;
			addedHelp = "-shop - You must specify a serial port with -port: <Serial Port>";
		}
		calculateLaunch(testing, updateOnly, applyLaf, launchShop, shopPort, applyWindowsLaf, help, addedHelp, showMang);
	}

	private static void calculateLaunch(boolean testing, boolean updateOnly, boolean applyLaf, boolean launchShop, int shopPort, boolean applyWindowsLAF, boolean help, String addedHelp, boolean showManagement) {
		if (testing) {
			System.out.println(BASE_SAVE_PATH + "\\databases\\stock\\stock.db");
		}
		if (updateOnly) {
			try {
				if (!UpdateManager.checkForUpdate()) {
					System.out.println("[Updater]: No update is available");
				}
			} catch (Throwable e) {
				// Print a brief description of the error.
				System.out.println("[Calculate Launch]: Error updating(Throwable). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if
				// it is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
			}
		}
		if (applyLaf) {
			applyLaf();
		}
		if (applyWindowsLAF) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
				System.out.println("[Launcher]: Error setting look and feel. Contact the author or run this program with -showSTs to print the stack traces.");
				if (PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
			}
		}
		if (launchShop) {
			ShopWindow.launchWindow(shopPort);
		} else {
			if (showManagement) {
				run();
			}
		}
		if (help) {
			if (!addedHelp.equals("")) {
				System.out.println("There is an error in your launch flags: \n\t " + addedHelp);
			}
			System.out.println("New Hook Stock Solutions: Launch Flags:");
			System.out.println("\t *WARNING* These are case sensitive -showSts will not work. It must be -showSTs for example.");
			System.out.println("\t -help - Print this message");
			System.out.println("\t -test - Print the database location");
			System.out.println("\t -checkForUpdate - Checks for and update only");
			System.out.println("\t -shop - Launches the shop interface *BUGGY* (Used together with -port:<port>");
			System.out.println("\t -showSTs - Shows stack traces");
			System.out.println("\t -port: <Serial Port> - The port number (Eg. 5 == COM5)");
			System.out.println("\t -customLaf: <Look and Feel> - The look and feel to use. Must be either: system, window, custom");
		} else {
			System.out.println("[Help]: You can add -help to the command to see launch flags");
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
						// Print a brief description of the error.
						System.out.println("[Run]: Error closing database(Not Prepared Exception). Contact the author or run this program with -showSTs to print the stack traces.");
						// If it does not print the stack trace for error
						// logging if
						// it is enabled.
						if (Launcher.PRINT_STACK_TRACES) {
							e.printStackTrace();
						}
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
			// Print a brief description of the error.
			System.out.println("[Apply LAF]: Error setting SeaGlass LAF - Try running with '-customLaf: window' command line flags.(Throwable). Contact the author or run this program with -showSTs to print the stack traces.");
			// If it does not print the stack trace for error logging if
			// it is enabled.
			if (Launcher.PRINT_STACK_TRACES) {
				e1.printStackTrace();
			}
		}
	}
}
