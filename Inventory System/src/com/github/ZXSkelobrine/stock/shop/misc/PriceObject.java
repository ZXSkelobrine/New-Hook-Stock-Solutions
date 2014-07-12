package com.github.ZXSkelobrine.stock.shop.misc;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class PriceObject {

	private String stringPrice;
	private double price;
	private String name;
	private String breaker;

	public PriceObject(String price, String name, Dimension size, Font font) {
		this.setStringPrice(price);
		this.name = name;
		this.price = Double.parseDouble(price);
		BufferedImage test = new BufferedImage(10, 10, 1);
		FontMetrics fm = test.createGraphics().getFontMetrics(font);
		Rectangle2D textRec = fm.getStringBounds(name, test.getGraphics());
		Rectangle2D priceRec = fm.getStringBounds("£" + price, test.getGraphics());
		int textWidth = (int) textRec.getWidth();
		int priceWidth = (int) priceRec.getWidth();
		int totalWidth = textWidth + priceWidth;
		int inverseWidth = size.width - totalWidth;
		int spaceAmount = inverseWidth / 4;
		System.out.println(fm.getStringBounds(" ", test.getGraphics()).getWidth());
		// System.out.println("Text Width: " + textWidth);
		// System.out.println("Price Width: " + priceWidth);
		// System.out.println("Total Width: " + totalWidth);
		// System.out.println("Inverse Width: " + inverseWidth);
		// System.out.println("Space Amount: " + spaceAmount);
		breaker = "";
		for (int i = 0; i < spaceAmount + 1; i++) {
			breaker = breaker + " ";
		}
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name + breaker + "£" + price;
	}

	/**
	 * @return the stringPrice
	 */
	public String getStringPrice() {
		return stringPrice;
	}

	/**
	 * @param stringPrice the stringPrice to set
	 */
	public void setStringPrice(String stringPrice) {
		this.stringPrice = stringPrice;
	}

}
