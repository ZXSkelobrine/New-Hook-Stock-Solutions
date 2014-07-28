package com.github.ZXSkelobrine.stock.global.variables;

public class Date {

	/**
	 * This is the presentable version of the date to be displayed in text
	 * fields.
	 */
	private String formatedDate;
	/**
	 * This is the year
	 */
	private int year;
	/**
	 * This is the month
	 */
	private int month;
	/**
	 * This is the day
	 */
	private int day;

	/**
	 * This is the default constructor for creating a Date object
	 * 
	 * @param day
	 *            - The day
	 * @param month
	 *            - The month
	 * @param year
	 *            - The year
	 */
	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
		formatedDate = day + "/" + month + "/" + year;
	}

	public Date(String date) {
		this(Integer.parseInt(date.split("/")[0]), Integer.parseInt(date.split("/")[1]), Integer.parseInt(date.split("/")[2]));
	}

	/**
	 * @return the formatedDate
	 */
	public String getFormatedDate() {
		return formatedDate;
	}

	/**
	 * @param formatedDate
	 *            the formatedDate to set
	 */
	public void setFormatedDate(String formatedDate) {
		this.formatedDate = formatedDate;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @param month
	 *            the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @param day
	 *            the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}
}
