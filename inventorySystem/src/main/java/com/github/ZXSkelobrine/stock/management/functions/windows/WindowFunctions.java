package com.github.ZXSkelobrine.stock.management.functions.windows;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.github.ZXSkelobrine.stock.Launcher;
import com.github.ZXSkelobrine.stock.management.io.IconLoader;
import com.github.ZXSkelobrine.stock.management.windows.enums.Window;

public class WindowFunctions {

	public static String formatTitle(String windowName) {
		return Launcher.NAME + " (" + Launcher.VERSION + ") - " + windowName;
	}

	public static void applyScrollSettings(JScrollPane pane) {
		pane.getVerticalScrollBar().setUnitIncrement(10);
	}

	public static void applyWindowSettings(Window window, JFrame frame) {
		String title = window.toString().substring(0, 1).toUpperCase() + window.toString().toLowerCase().substring(1);
		frame.setTitle(formatTitle(title));
		try {
			frame.setIconImage(IconLoader.loadImageIcon(window).getImage());
		} catch (IOException e) {
			// Print a brief description of the error.
			System.out.println("[Apply Window Settings]: Error updating the amount(IOException). Contact the author or run this program with -showSTs to print the stack traces.");
			// If it does not print the stack trace for error logging if it
			// is enabled.
			if (Launcher.PRINT_STACK_TRACES) {
				e.printStackTrace();
			}
		}
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
