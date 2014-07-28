package com.github.ZXSkelobrine.stock.management.prints;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.github.ZXSkelobrine.stock.global.variables.Stock;

public class Printer implements Printable {

	private static void print() {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new Printer());
		boolean doPrint = job.printDialog();
		if (doPrint) {
			try {
				job.print();
			} catch (PrinterException e) {
				e.printStackTrace();
			}
		}
	}

	public static void printStock(List<Stock> p1, String title) {
		try {
			if (p1.size() > 0) {
				if (p1.size() > 108) {
					List<Stock> p11 = new ArrayList<>();
					List<Stock> p12 = new ArrayList<>();
					List<Stock> p13 = new ArrayList<>();
					List<Stock> p14 = new ArrayList<>();
					for (int i = 0; i < 36; i++) {
						p11.add(p1.get(i));
					}
					for (int i = 36; i < 72; i++) {
						p12.add(p1.get(i));
					}
					for (int i = 72; i < 108; i++) {
						p13.add(p1.get(i));
					}
					for (int i = 108; i < p1.size(); i++) {
						p14.add(p1.get(i));
					}
					Printer.setTitle(title);
					printArrays(p11, p12, p13, p14);
				} else if (p1.size() > 72) {
					List<Stock> p11 = new ArrayList<>();
					List<Stock> p12 = new ArrayList<>();
					List<Stock> p13 = new ArrayList<>();
					for (int i = 0; i < 36; i++) {
						p11.add(p1.get(i));
					}
					for (int i = 36; i < 72; i++) {
						p12.add(p1.get(i));
					}
					for (int i = 72; i < p1.size(); i++) {
						p13.add(p1.get(i));
					}
					Printer.setTitle(title);
					printArrays(p11, p12, p13);
				} else if (p1.size() > 36) {
					List<Stock> p11 = new ArrayList<>();
					List<Stock> p12 = new ArrayList<>();
					for (int i = 0; i < 36; i++) {
						p11.add(p1.get(i));
					}
					for (int i = 36; i < p1.size(); i++) {
						p12.add(p1.get(i));
					}
					Printer.setTitle(title);
					printArrays(p11, p12);
				} else {
					Printer.setTitle(title);
					printArrays(p1);
				}
			}
		} catch (PrinterException e) {
			e.printStackTrace();
		}

	}

	@SafeVarargs
	private static void printArrays(List<Stock>... lists) throws PrinterException {
		for (List<Stock> products : lists) {
			Printer.setStocks(products);
			Printer.print();
			Printer.reset();
		}
	}

	private static Stock[] productsArray;
	private static List<Stock> productsList;
	private static String title;

	private static void setStocks(List<Stock> products) {
		Printer.productsList = products;
	}

	private static void setTitle(String title) {
		Printer.title = title;
	}

	int name = 60;
	int barcode = 125;
	int expiry = 205;
	int buy = 265;
	int nextRestock = 310;
	int lastRestock = 380;
	int amount = 450;
	int category = 500;
	static int currentRecord = 65;

	@Override
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
		if (page > 0) {
			return NO_SUCH_PAGE;
		}
		currentRecord = 65;
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		Rectangle b = g.getClipBounds();
		{
			g.setFont(new Font(Font.DIALOG, Font.TRUETYPE_FONT, 8));
			titleDate(g);
			drawRectangle(b, g);
			drawTable(b, g);
			if (productsArray != null) {
				drawRecords(productsArray, g);
			} else {
				drawRecords(productsList, g2d);
			}
		}
		return PAGE_EXISTS;
	}

	private void drawTable(Rectangle b, Graphics g) {
		int bottom = b.height - 50;
		g.drawLine(barcode - 10, 50, barcode - 10, bottom);
		g.drawLine(expiry - 10, 50, expiry - 10, bottom);
		g.drawLine(buy - 10, 50, buy - 10, bottom);
		g.drawLine(nextRestock - 10, 50, nextRestock - 10, bottom);
		g.drawLine(lastRestock - 10, 50, lastRestock - 10, bottom);
		g.drawLine(amount - 10, 50, amount - 10, bottom);
		g.drawLine(category - 10, 50, category - 10, bottom);

		g.setFont(new Font(Font.DIALOG, Font.BOLD, 8));
		g.drawString("Name", name, currentRecord);
		g.drawString("Barcode" + "", barcode, currentRecord);
		g.drawString("Expiry Date", expiry, currentRecord);
		g.drawString("Price" + "", buy, currentRecord);
		g.drawString("Next Restock" + "", nextRestock, currentRecord);
		g.drawString("Last Restock", lastRestock, currentRecord);
		g.drawString("Amount", amount, currentRecord);
		g.drawString("Category", category, currentRecord);
		g.drawLine(50, currentRecord + 5, g.getClipBounds().width - 50, currentRecord + 5);
		currentRecord += 20;
		g.setFont(new Font(Font.DIALOG, Font.TRUETYPE_FONT, 8));
	}

	private void drawRectangle(Rectangle b, Graphics g) {
		g.drawLine(50, 50, b.width - 50, 50);
		g.drawLine(50, 50, 50, b.height - 50);
		g.drawLine(b.width - 50, 50, b.width - 50, b.height - 50);
		g.drawLine(50, b.height - 50, b.width - 50, b.height - 50);
	}

	private void titleDate(Graphics g) {
		g.drawString(new SimpleDateFormat().format(Calendar.getInstance().getTime()) + " - " + title, 50, 40);
	}

	private void drawRecord(Stock product, Graphics g) {
		String nameString;
		String categoryString;
		if (product.getName().length() > 13) {
			nameString = product.getName().substring(0, 10) + "...";
		} else {
			nameString = product.getName();
		}
		if (product.getCategory().length() > 10) {
			categoryString = product.getCategory().substring(0, 7) + "...";
		} else {
			categoryString = product.getCategory();
		}
		g.drawString(nameString, name, currentRecord);
		g.drawString(product.getBarcode() + "", barcode, currentRecord);
		g.drawString(product.getExpiry().getFormatedDate(), expiry, currentRecord);
		g.drawString(product.getPrice() + "", buy, currentRecord);
		g.drawString(product.getNextRestock().getFormatedDate() + "", nextRestock, currentRecord);
		g.drawString(product.getLastRestock().getFormatedDate(), lastRestock, currentRecord);
		g.drawString(product.getAmount() + "", amount, currentRecord);
		g.drawString(categoryString, category, currentRecord);
		g.drawLine(50, currentRecord + 5, g.getClipBounds().width - 50, currentRecord + 5);
		currentRecord += 20;
	}

	private void drawRecords(Stock[] products, Graphics g) {
		for (Stock p : products) {
			if (p.getName() != null && !p.getName().equals("")) {
				drawRecord(p, g);
			}
		}
	}

	private void drawRecords(List<Stock> products, Graphics g) {
		for (Stock p : products) {
			if (p.getName() != null && !p.getName().equals("")) {
				drawRecord(p, g);
			}
		}
	}

	private static void reset() {
		currentRecord = 65;
		productsArray = null;
		productsList = null;
	}
}
