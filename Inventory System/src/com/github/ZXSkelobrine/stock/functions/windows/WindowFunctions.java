package com.github.ZXSkelobrine.stock.functions.windows;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.github.ZXSkelobrine.stock.Launcher;
import com.github.ZXSkelobrine.stock.io.IconLoader;
import com.github.ZXSkelobrine.stock.windows.enums.Window;

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
			e.printStackTrace();
		}
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
