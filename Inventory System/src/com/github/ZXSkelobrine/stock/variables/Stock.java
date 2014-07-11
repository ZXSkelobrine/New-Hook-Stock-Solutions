package com.github.ZXSkelobrine.stock.variables;

import com.github.ZXSkelobrine.stock.errors.NotPreparedException;
import com.github.ZXSkelobrine.stock.functions.sql.SQLFunctions;

public class Stock {

	/**
	 * This is the products barcode.
	 */
	private long barcode;
	/**
	 * This is when the product with expire
	 */
	private Date expiry;
	/**
	 * This is the amount of stock in the record
	 */
	private int amount;
	/**
	 * This is the name of the product
	 */
	private String name;
	/**
	 * This is the price for the product
	 */
	private double price;
	/**
	 * This is the category the product is in
	 */
	private String category;
	/**
	 * This is the manufacturer of the product
	 */
	private String manufacturer;
	/**
	 * This is the date of the next restock.
	 */
	private Date nextRestock;
	/**
	 * This is the date of the last restock.
	 */
	private Date lastRestock;

	/**
	 * This is the default constructor to create a new Stock object;
	 * 
	 * @param barcode
	 *            - The products barcode
	 * @param expiry
	 *            - The products expiry date
	 * @param amount
	 *            - The amount of the product
	 * @param name
	 *            - The products names
	 * @param price
	 *            - The products price
	 * @param category
	 *            - The products category
	 * @param manufacturer
	 *            - The products manufacturer
	 */
	public Stock(long barcode, Date expiry, int amount, String name, double price, String category, String manufacturer, Date restock, Date lastRestock) {
		this.barcode = barcode;
		this.expiry = expiry;
		this.amount = amount;
		this.name = name;
		this.price = price;
		this.category = category;
		this.manufacturer = manufacturer;
		this.nextRestock = restock;
		this.lastRestock = lastRestock;
	}

	/**
	 * This is a constructor that creates a duplicated Stock object from the one
	 * given.
	 * 
	 * @param stock
	 *            - The stock to duplicate.
	 */
	public Stock(Stock stock, int amount) {
		this(stock.getBarcode(), stock.getExpiry(), amount, stock.getName(), stock.getPrice(), stock.getCategory(), stock.getManufacturer(), stock.getNextRestock(), stock.getLastRestock());
	}

	/**
	 * @return the barcode
	 */
	public long getBarcode() {
		return barcode;
	}

	/**
	 * @param barcode
	 *            the barcode to set
	 */
	public void setBarcode(long barcode) {
		this.barcode = barcode;
		try {
			SQLFunctions.updateBarcode(barcode, name);
		} catch (NotPreparedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the expiry
	 */
	public Date getExpiry() {
		return expiry;
	}

	/**
	 * @param expiry
	 *            the expiry to set
	 */
	public void setExpiry(Date expiry) {
		this.expiry = expiry;
		try {
			SQLFunctions.updateExpiry(expiry, barcode);
		} catch (NotPreparedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
		try {
			SQLFunctions.updateAmount(amount, barcode);
		} catch (NotPreparedException e) {
			e.printStackTrace();
		}
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
		try {
			SQLFunctions.updateName(name, barcode);
		} catch (NotPreparedException e) {
			e.printStackTrace();
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
		try {
			SQLFunctions.updatePrice(price, barcode);
		} catch (NotPreparedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
		try {
			SQLFunctions.updateCategory(category, barcode);
		} catch (NotPreparedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param manufacturer
	 *            the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
		try {
			SQLFunctions.updateManufacturer(manufacturer, barcode);
		} catch (NotPreparedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the nextRestock
	 */
	public Date getNextRestock() {
		return nextRestock;
	}

	/**
	 * @param nextRestock
	 *            the nextRestock to set
	 */
	public void setNextRestock(Date nextRestock) {
		this.nextRestock = nextRestock;
		try {
			SQLFunctions.updateNextRestock(nextRestock, barcode);
		} catch (NotPreparedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the lastRestock
	 */
	public Date getLastRestock() {
		return lastRestock;
	}

	/**
	 * @param lastRestock
	 *            the lastRestock to set
	 */
	public void setLastRestock(Date lastRestock) {
		this.lastRestock = lastRestock;
		try {
			SQLFunctions.updateLastRestock(lastRestock, barcode);
		} catch (NotPreparedException e) {
			e.printStackTrace();
		}
	}

}
