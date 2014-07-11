package com.github.ZXSkelobrine.stock.functions.commands;

import java.util.Arrays;

import com.github.ZXSkelobrine.stock.functions.errors.ErrorLogFunctions;
import com.github.ZXSkelobrine.stock.variables.ErrorLogItem;
import com.github.ZXSkelobrine.stock.windows.dev.DeveloperControls;
import com.github.ZXSkelobrine.stock.windows.dev.DeveloperLock;
import com.github.ZXSkelobrine.stock.windows.errors.ErrorWindow;
import com.github.ZXSkelobrine.stock.windows.stock.StockInsert;
import com.github.ZXSkelobrine.stock.windows.stock.StockLookup;
import com.github.ZXSkelobrine.stock.windows.stock.StockOverview;

public class CommandFunctions {

	public static void runCommand(String command) {
		if (command.startsWith("/")) command = command.substring(1);
		String realCommand = getCommand(command);
		String[] args = getArgs(command);
		switch (realCommand) {
		case "launch":
			launchWindow(args);
		}
	}

	private static String[] getArgs(String command) {
		String[] initialSplit = command.split("\\s+");
		return Arrays.copyOfRange(initialSplit, 1, initialSplit.length);
	}

	private static String getCommand(String command) {
		return command.split("\\s+")[0];
	}

	public static void launchWindow(String[] args) {
		switch (args[0]) {
		case "dev":
			if (args.length >= 2) {
				switch (args[1]) {
				case "controls":
					if (args.length == 3) {
						if (args[2].equals(DeveloperLock.CORRECT_STRING)) {
							DeveloperControls.launchWindow();
						} else {
							ErrorWindow.launchWindow("Incorrect passcode");
							ErrorLogFunctions.addItem(new ErrorLogItem("CommandFunctions", "launchWindow", "", "Incorrect passcode", 42, new StackTraceElement[] {}));
						}
					} else {
						ErrorWindow.launchWindow("Invalid arguments: /launch dev controls <passcode>");
						ErrorLogFunctions.addItem(new ErrorLogItem("CommandFunctions", "launchWindow", "", "Invalid arguments", 41, new StackTraceElement[] {}));
					}
					break;
				case "lock":
					System.out.println("Lock");
					DeveloperLock.launchWindow();
					break;
				}
			}
			break;
		case "stock":
			if (args.length >= 2) {
				switch (args[1]) {
				case "insert":
					StockInsert.launchWindow(null);
					break;
				case "lookup":
					StockLookup.launchWindow();
					break;
				case "overview":
					StockOverview.launchWindow();
					break;
				}
			} else {
				System.out.println("logged");
				ErrorLogFunctions.addItem(new ErrorLogItem("CommandFunctions", "launchWindow", "", "Invalid arguments", 61, new StackTraceElement[] {}));
			}
			break;
		case "error":
			if (args.length == 2) {
				ErrorWindow.launchWindow(args[1]);
			} else {
				ErrorLogFunctions.addItem(new ErrorLogItem("CommandFunctions", "launchWindow", "", "Invalid arguments", 78, new StackTraceElement[] {}));
			}
			break;
		}
	}

}
