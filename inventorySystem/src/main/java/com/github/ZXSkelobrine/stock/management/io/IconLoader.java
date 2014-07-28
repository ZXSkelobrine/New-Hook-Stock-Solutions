package com.github.ZXSkelobrine.stock.management.io;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.github.ZXSkelobrine.stock.management.windows.enums.Window;

public class IconLoader {

	private static String localPath;
	private static final String BASE_PATH = "/icons/";

	public static ImageIcon loadImageIcon(Window type) throws IOException {
		switch (type) {
		case INSERT:
			localPath = BASE_PATH + "add.png";
			return new ImageIcon(ImageIO.read(IconLoader.class.getResource(localPath)));
		case REMOVE:
			localPath = BASE_PATH + "remove.png";
			return new ImageIcon(ImageIO.read(IconLoader.class.getResource(localPath)));
		case LOOKUP:
			localPath = BASE_PATH + "lookup.png";
			return new ImageIcon(ImageIO.read(IconLoader.class.getResource(localPath)));
		default:
			localPath = BASE_PATH + "default.png";
			return new ImageIcon(ImageIO.read(IconLoader.class.getResource(localPath)));
		}
	}

}
