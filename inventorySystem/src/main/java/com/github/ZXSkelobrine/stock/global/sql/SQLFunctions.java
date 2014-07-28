package com.github.ZXSkelobrine.stock.global.sql;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.github.ZXSkelobrine.stock.Launcher;
import com.github.ZXSkelobrine.stock.global.variables.Date;
import com.github.ZXSkelobrine.stock.global.variables.Stock;
import com.github.ZXSkelobrine.stock.management.errors.NotPreparedException;
import com.github.ZXSkelobrine.stock.management.errors.TableCreationError;

/**
 * This class handles all interactions with the SQLite database used to store
 * the product data.<br>
 * <br>
 * <b>Attributes</b>
 * <ul>
 * <li>Public</li>
 * </ul>
 * <br>
 * 
 * @author Ryan
 * 
 */
public class SQLFunctions {

	/**
	 * This variable holds whether a valid connection has been made to the
	 * database.<br>
	 * <br>
	 * <b>Attributes</b>
	 * <ul>
	 * <li>Private</li>
	 * <li>Static</li>
	 */
	private static boolean prepared = false;
	/**
	 * This is the connection to the database which the statement is created
	 * from.<br>
	 * <br>
	 * <b>Attributes</b>
	 * <ul>
	 * <li>Private</li>
	 * <li>Static</li>
	 */
	private static Connection connection;
	/**
	 * This is the statement that all commands are run through.<br>
	 * <br>
	 * <b>Attributes</b>
	 * <ul>
	 * <li>Private</li>
	 * <li>Static</li>
	 */
	private static Statement statement;
	/**
	 * This is the retry counter for creating tables<br>
	 * <br>
	 * <b>Attributes</b>
	 * <ul>
	 * <li>Private</li>
	 * <li>Static</li>
	 */
	private static int retryCount = 0;
	/**
	 * This is the retry maximum for creating tables.<br>
	 * <br>
	 * <b>Attributes</b>
	 * <ul>
	 * <li>Private</li>
	 * <li>Static</li>
	 * <li>Final</li>
	 */
	private static final int RETRY_MAX = 3;

	/**
	 * This prepares the sql connection to the specified path.
	 * 
	 * @param systemStockPath
	 *            - The local system path to the database file - allows for
	 *            switch outs.
	 * @param username
	 *            - The username for the database if needed.
	 * @param password
	 *            - The password for the database if needed.
	 * @return boolean - If the connection was completed successfully;
	 */
	public static boolean prepareDatabaseConnection(String systemStockPath, String username, String password) {
		try {
			// Create the file
			File file = new File(systemStockPath);
			// Check if the file exists
			if (!file.exists()) {
				// Check if the parent folders exist.
				if (!file.getParentFile().exists()) {
					// If is doesn't create the folders
					file.getParentFile().mkdirs();
				}
				// If it doesn't create it
				file.createNewFile();
			}
			// Initialise the database drivers.
			Class.forName("org.sqlite.JDBC");
			// Check whether to use usernames and passwords.
			if (username != null && password != null) {
				// If they are not null then use them in the connection
				connection = DriverManager.getConnection("jdbc:sqlite:" + systemStockPath, username, password);
			} else {
				// If they are null then do not use them in the connection.
				connection = DriverManager.getConnection("jdbc:sqlite:" + systemStockPath);
			}
			if (connection != null) {
				// Once the connection has been made, create the statement and
				// store
				// it,
				statement = connection.createStatement();

				// Test the tables.
				statement.executeQuery("SELECT * FROM PRODUCTS");
				// Once the connection has been opened, set prepared to true to
				// stop
				// NotPreparedExceptions.
				prepared = true;
				// Return true to show valid completion
				return true;
				// Check for any errors
			} else {
				return false;
			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// Check the error to see if the table needs to be created
			if (e.getMessage().contains("no such table: PRODUCTS")) {
				// If so try to create the tables
				try {
					prepareTables();
					// Then run the method again (if the retry is low enough)
					// and increment the count
					if (retryCount < RETRY_MAX) {
						if (prepareDatabaseConnection(systemStockPath, username, password)) {
							System.out.println("Worked");
							return true;
						}
					} else {
						// If not throw and error
						throw new TableCreationError(systemStockPath, retryCount);
					}
					retryCount++;
				} catch (NotPreparedException | TableCreationError e1) {
					// Print a brief description of the error.
					System.out.println("[Prepare Database Connection]: Error preparing tables(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
					// If it does not print the stack trace for error logging if
					// it
					// is enabled.
					if (Launcher.PRINT_STACK_TRACES) {
						e.printStackTrace();
					}
					// And return false to show that it failed
					return false;
				}

			} else {
				// Print a brief description of the error.
				System.out.println("[Prepare Database Connection]: Error preparing tables(ClassNotFoundException | SQLException | IOException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
				// And return false to show that it failed
				return false;
			}
		}
		return false;
	}

	/**
	 * This will create the necessary tables for the program to function in the
	 * database;
	 * 
	 * @return
	 * @throws NotPreparedException
	 */
	public static boolean prepareTables() throws NotPreparedException {
		try {
			// Try to create a new table in the database
			statement.executeUpdate("CREATE TABLE \"PRODUCTS\" (\"barcode\"  bigint,\"date\"  text,\"amount\"  int,\"name\"  text,\"price\"  decimal,\"category\"  text,\"manufacturer\"  text,\"restock\"  TEXT,\"restock_last\"  TEXT);");
			statement.executeUpdate("INSERT INTO \"main\".\"PRODUCTS\" (\"barcode\", \"date\", \"amount\", \"name\", \"price\", \"category\", \"manufacturer\", \"restock\", \"restock_last\", \"ROWID\") VALUES (7852143698245, '12/8/2015', 1, 'Engine', 125.99, 'Cars', 'Unknown', '1/9/2015', '5/7/2014', 1);");
			statement.executeUpdate("INSERT INTO \"main\".\"PRODUCTS\" (\"barcode\", \"date\", \"amount\", \"name\", \"price\", \"category\", \"manufacturer\", \"restock\", \"restock_last\", \"ROWID\") VALUES (5036905000123, '30/2/2015', 100, 'Pass The Pigs Deluxe', 5.49, 'Toys and Games', 'Winning Moves', '6/7/2014', '5/7/2014', 2);");
			// If this works then return true to signal that the process has
			// completed successfully.
			return true;
		} catch (SQLException e) {
			// Print a brief description of the error.
			System.out.println("[Prepare Tables]: Error preparing tables(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
			// If it does not print the stack trace for error logging if it
			// is enabled.
			if (Launcher.PRINT_STACK_TRACES) {
				e.printStackTrace();
			}
			// And return false to show that it failed
			return false;
		}
	}

	public static List<Stock> stockLookupByWhereClause(String whereClause) throws NotPreparedException {
		List<Stock> list = new ArrayList<Stock>();
		if (prepared) {
			try {
				ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCTS WHERE " + whereClause + ";");
				while (rs.next()) {
					Stock stock = new Stock(rs.getLong("barcode"), new Date(rs.getString("date")), rs.getInt("amount"), rs.getString("name"), rs.getDouble("price"), rs.getString("category"), rs.getString("manufacturer"), new Date(rs.getString("restock")), new Date(rs.getString("restock_last")));
					list.add(stock);
				}
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Stock Lookup By Where Clause]: Error looking up stock(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
			}
		} else {
			throw new NotPreparedException("SQLFunctions: stockLookUpByWhereClause(String)");
		}
		return list;
	}

	/**
	 * This looks up products in the database from the given barcode (
	 * <code>long</code>).
	 * 
	 * @param barcode
	 *            - The barcode to search for.
	 * @return A list of Stock items that match the criteria.
	 * @throws NotPreparedException
	 *             If the database connection has not been opened yet.
	 */
	public static List<Stock> stockLookUpByBarcode(long barcode) throws NotPreparedException {
		// Create a list to store the stock.
		List<Stock> list = new ArrayList<Stock>();
		// If the connection has been prepared
		if (prepared) {
			// Then try to
			try {
				// Exceute the lookup
				ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCTS WHERE barcode = " + barcode + ";");
				// While there are still results
				while (rs.next()) {
					// Create a new stock object
					Stock stock = new Stock(rs.getLong("barcode"), new Date(rs.getString("date")), rs.getInt("amount"), rs.getString("name"), rs.getDouble("price"), rs.getString("category"), rs.getString("manufacturer"), new Date(rs.getString("restock")), new Date(rs.getString("restock_last")));
					// And add it to the list
					list.add(stock);
				}
				// Catch any SQLExcections
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Stock Lookup By Barcode]: Error looking up stock(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
			}
			// If it is not prepared
		} else {
			// Then throw the exception.
			throw new NotPreparedException("SQLFunctions: stockLookUpByBarcode(long)");
		}
		// Finally return the list.
		return list;
	}

	/**
	 * This looks up products in the database from the given next restock date (
	 * <code>Date</code>).
	 * 
	 * @param restockDate
	 *            - The restockDate to search for.
	 * @return A list of Stock items that match the criteria.
	 * @throws NotPreparedException
	 *             If the database connection has not been opened yet.
	 */
	public static List<Stock> stockLookUpByNextRestockDate(Date restockDate) throws NotPreparedException {
		List<Stock> list = new ArrayList<Stock>();
		if (prepared) {
			try {
				ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCTS WHERE restock = " + restockDate.getFormatedDate() + ";");
				while (rs.next()) {
					Stock stock = new Stock(rs.getLong("barcode"), new Date(rs.getString("date")), rs.getInt("amount"), rs.getString("name"), rs.getDouble("price"), rs.getString("category"), rs.getString("manufacturer"), new Date(rs.getString("restock")), new Date(rs.getString("restock_last")));
					list.add(stock);
				}
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Stock Lookup By Next Restock]: Error looking up stock(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
			}
		} else {
			throw new NotPreparedException("SQLFunctions: stockLookUpByDate(Date)");
		}
		return list;
	}

	/**
	 * This looks up products in the database from the given last restock date (
	 * <code>Date</code>).
	 * 
	 * @param restockDate
	 *            - The restockDate to search for.
	 * @return A list of Stock items that match the criteria.
	 * @throws NotPreparedException
	 *             If the database connection has not been opened yet.
	 */
	public static List<Stock> stockLookUpByLastRestockDate(Date restockDate) throws NotPreparedException {
		List<Stock> list = new ArrayList<Stock>();
		if (prepared) {
			try {
				ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCTS WHERE restock_last = " + restockDate.getFormatedDate() + ";");
				while (rs.next()) {
					Stock stock = new Stock(rs.getLong("barcode"), new Date(rs.getString("date")), rs.getInt("amount"), rs.getString("name"), rs.getDouble("price"), rs.getString("category"), rs.getString("manufacturer"), new Date(rs.getString("restock")), new Date(rs.getString("restock_last")));
					list.add(stock);
				}
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Stock Lookup By Last Restock]: Error looking up stock(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
			}
		} else {
			throw new NotPreparedException("SQLFunctions: stockLookUpByDate(Date)");
		}
		return list;
	}

	/**
	 * This looks up products in the database from the given expiry date (
	 * <code>Date</code>).
	 * 
	 * @param expiry
	 *            - The expiry date to search for.
	 * @return A list of Stock items that match the criteria.
	 * @throws NotPreparedException
	 *             If the database connection has not been opened yet.
	 */
	public static List<Stock> stockLookUpByExpiry(Date expiry) throws NotPreparedException {
		List<Stock> list = new ArrayList<Stock>();
		if (prepared) {
			try {
				ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCTS WHERE date = " + expiry.getFormatedDate() + ";");
				while (rs.next()) {
					Stock stock = new Stock(rs.getLong("barcode"), new Date(rs.getString("date")), rs.getInt("amount"), rs.getString("name"), rs.getDouble("price"), rs.getString("category"), rs.getString("manufacturer"), new Date(rs.getString("restock")), new Date(rs.getString("restock_last")));
					list.add(stock);
				}
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Stock Lookup By Expiry]: Error looking up stock(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
			}
		} else {
			throw new NotPreparedException("SQLFunctions: stockLookUpByExpiry(Date)");
		}
		return list;
	}

	/**
	 * This looks up products in the database from the given amount (
	 * <code>int</code>).
	 * 
	 * @param amount
	 *            - The amount to search for.
	 * @return A list of Stock items that match the criteria.
	 * @throws NotPreparedException
	 *             If the database connection has not been opened yet.
	 */
	public static List<Stock> stockLookUpByAmount(int amount) throws NotPreparedException {
		List<Stock> list = new ArrayList<Stock>();
		if (prepared) {
			try {
				ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCTS WHERE amount = " + amount + ";");
				while (rs.next()) {
					Stock stock = new Stock(rs.getLong("barcode"), new Date(rs.getString("date")), rs.getInt("amount"), rs.getString("name"), rs.getDouble("price"), rs.getString("category"), rs.getString("manufacturer"), new Date(rs.getString("restock")), new Date(rs.getString("restock_last")));
					list.add(stock);
				}
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Stock Lookup By Amount]: Error looking up stock(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
			}
		} else {
			throw new NotPreparedException("SQLFunctions: stockLookUpByAmount(int)");
		}
		return list;
	}

	/**
	 * This looks up products in the database from the given name (
	 * <code>String</code>).
	 * 
	 * @param name
	 *            - The name to search for.
	 * @return A list of Stock items that match the criteria.
	 * @throws NotPreparedException
	 *             If the database connection has not been opened yet.
	 */
	public static List<Stock> stockLookUpByName(String name) throws NotPreparedException {
		List<Stock> list = new ArrayList<Stock>();
		if (prepared) {
			try {
				ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCTS WHERE name=\"" + name + "\";");
				while (rs.next()) {
					Stock stock = new Stock(rs.getLong("barcode"), new Date(rs.getString("date")), rs.getInt("amount"), rs.getString("name"), rs.getDouble("price"), rs.getString("category"), rs.getString("manufacturer"), new Date(rs.getString("restock")), new Date(rs.getString("restock_last")));
					list.add(stock);
				}
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Stock Lookup By Name]: Error looking up stock(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
			}
		} else {
			throw new NotPreparedException("SQLFunctions: stockLookUpByName(String)");
		}
		return list;
	}

	/**
	 * This looks up products in the database from the given price (
	 * <code>double</code>).
	 * 
	 * @param price
	 *            - The price to search for.
	 * @return A list of Stock items that match the criteria.
	 * @throws NotPreparedException
	 *             If the database connection has not been opened yet.
	 */
	public static List<Stock> stockLookUpByPrice(double price) throws NotPreparedException {
		List<Stock> list = new ArrayList<Stock>();
		if (prepared) {
			try {
				ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCTS WHERE price = " + price + ";");
				while (rs.next()) {
					Stock stock = new Stock(rs.getLong("barcode"), new Date(rs.getString("date")), rs.getInt("amount"), rs.getString("name"), rs.getDouble("price"), rs.getString("category"), rs.getString("manufacturer"), new Date(rs.getString("restock")), new Date(rs.getString("restock_last")));
					list.add(stock);
				}
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Stock Lookup By Price]: Error looking up stock(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
			}
		} else {
			throw new NotPreparedException("SQLFunctions: stockLookUpByPrice(double)");
		}
		return list;
	}

	/**
	 * This looks up products in the database from the given category (
	 * <code>String</code>).
	 * 
	 * @param category
	 *            - The category to search for.
	 * @return A list of Stock items that match the criteria.
	 * @throws NotPreparedException
	 *             If the database connection has not been opened yet.
	 */
	public static List<Stock> stockLookUpByCategory(String category) throws NotPreparedException {
		List<Stock> list = new ArrayList<Stock>();
		if (prepared) {
			try {
				ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCTS WHERE category = " + category + ";");
				while (rs.next()) {
					Stock stock = new Stock(rs.getLong("barcode"), new Date(rs.getString("date")), rs.getInt("amount"), rs.getString("name"), rs.getDouble("price"), rs.getString("category"), rs.getString("manufacturer"), new Date(rs.getString("restock")), new Date(rs.getString("restock_last")));
					list.add(stock);
				}
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Stock Lookup By Category]: Error looking up stock(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
			}
		} else {
			throw new NotPreparedException("SQLFunctions: stockLookUpByCategory(String)");
		}
		return list;
	}

	/**
	 * This looks up products in the database from the given manufacturer (
	 * <code>String</code>).
	 * 
	 * @param manufacturer
	 *            - The manufacturer to search for.
	 * @return A list of Stock items that match the criteria.
	 * @throws NotPreparedException
	 *             If the database connection has not been opened yet.
	 */
	public static List<Stock> stockLookUpByManufacturer(String manufacturer) throws NotPreparedException {
		List<Stock> list = new ArrayList<Stock>();
		if (prepared) {
			try {
				ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCTS WHERE manufacturer = " + manufacturer + ";");
				while (rs.next()) {
					Stock stock = new Stock(rs.getLong("barcode"), new Date(rs.getString("date")), rs.getInt("amount"), rs.getString("name"), rs.getDouble("price"), rs.getString("category"), rs.getString("manufacturer"), new Date(rs.getString("restock")), new Date(rs.getString("restock_last")));
					list.add(stock);
				}
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Stock Lookup By Manufacturer]: Error looking up stock(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
			}
		} else {
			throw new NotPreparedException("SQLFunctions: stockLookUpByManufacturer(String)");
		}
		return list;
	}

	/**
	 * This return all of the stock in the database
	 * 
	 * @return A list of all Stock items in the database
	 * @throws NotPreparedException
	 *             If the database connection has not been opened yet.
	 */
	public static List<Stock> getAllStock() throws NotPreparedException {
		List<Stock> list = new ArrayList<Stock>();
		if (prepared) {
			try {
				ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCTS;");
				while (rs.next()) {
					Stock stock = new Stock(rs.getLong("barcode"), new Date(rs.getString("date")), rs.getInt("amount"), rs.getString("name"), rs.getDouble("price"), rs.getString("category"), rs.getString("manufacturer"), new Date(rs.getString("restock")), new Date(rs.getString("restock_last")));
					list.add(stock);
				}
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Get All Stock]: Error getting all stock(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
				// And return false to show that it failed
			}
		} else {
			throw new NotPreparedException("SQLFunctions: getAllStock()");
		}
		return list;
	}

	/**
	 * This adds stock to the database.
	 * 
	 * @param stock
	 *            - The stock to add.
	 * @return whether the process completed successfully.
	 * @throws NotPreparedException
	 *             If the database connection has not been opened yet.
	 */
	public static boolean addStock(Stock stock) throws NotPreparedException {
		if (prepared) {
			try {
				statement.executeUpdate("INSERT INTO PRODUCTS VALUES(" + stock.getBarcode() + ", \"" + stock.getExpiry().getFormatedDate() + "\", " + stock.getAmount() + ", \"" + stock.getName() + "\", " + stock.getPrice() + ", \"" + stock.getCategory() + "\", \"" + stock.getManufacturer() + "\", \"" + stock.getNextRestock().getFormatedDate() + "\", \"" + stock.getLastRestock().getFormatedDate() + "\");");
				return true;
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Add Stock]: Error adding the stock (SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
				// And return false to show that it failed
				return false;
			}
		} else {
			throw new NotPreparedException("SQLFunctions: addStock(Stock)");
		}
	}

	/**
	 * This updates the barcode in the database
	 * 
	 * @param barcode
	 *            - The barcode.
	 * @param name
	 *            - The name of the item
	 * @return whether the process completed successfully.
	 * @throws NotPreparedException
	 *             If the database connection has not yet been opened.
	 */
	public static boolean updateBarcode(long barcode, String name) throws NotPreparedException {
		if (prepared) {
			try {
				statement.executeUpdate("UPDATE PRODUCTS SET barcode = " + barcode + " WHERE name = \"" + name + "\";");
				return true;
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Update Barcode]: Error updating barcodes (SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
				// And return false to show that it failed
				return false;
			}
		} else {
			throw new NotPreparedException("SQLFunctions: updateBarcode(long, String)");
		}
	}

	/**
	 * This updates the expiry in the database
	 * 
	 * @param expiry
	 *            - The date of the expiry.
	 * @param barcode
	 *            - The barcode of the item
	 * @return whether the process completed successfully.
	 * @throws NotPreparedException
	 *             If the database connection has not yet been opened.
	 */
	public static boolean updateExpiry(Date expiry, long barcode) throws NotPreparedException {
		if (prepared) {
			try {
				statement.executeUpdate("UPDATE PRODUCTS SET restock = \"" + expiry.getFormatedDate() + "\" WHERE barcode = " + barcode + ";");
				return true;
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Update Expiry]: Error updating the exipiry date (SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
				// And return false to show that it failed
				return false;
			}
		} else {
			throw new NotPreparedException("SQLFunctions: updateExpiry(Date, long)");
		}
	}

	/**
	 * This updates the amount in the database
	 * 
	 * @param amount
	 *            - The amount.
	 * @param barcode
	 *            - The barcode of the item
	 * @return whether the process completed successfully.
	 * @throws NotPreparedException
	 *             If the database connection has not yet been opened.
	 */
	public static boolean updateAmount(int amount, long barcode) throws NotPreparedException {
		if (prepared) {
			try {
				statement.executeUpdate("UPDATE PRODUCTS SET amount = " + amount + " WHERE barcode = " + barcode + ";");
				return true;
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Update Amount]: Error updating the amount (SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
				// And return false to show that it failed
				return false;
			}
		} else {
			throw new NotPreparedException("SQLFunctions: updateAmount(int, long)");
		}
	}

	/**
	 * This updates the name in the database
	 * 
	 * @param name
	 *            - The name.
	 * @param barcode
	 *            - The barcode of the item
	 * @return whether the process completed successfully.
	 * @throws NotPreparedException
	 *             If the database connection has not yet been opened.
	 */
	public static boolean updateName(String name, long barcode) throws NotPreparedException {
		if (prepared) {
			try {
				statement.executeUpdate("UPDATE PRODUCTS SET name = \"" + name + "\" WHERE barcode = " + barcode + ";");
				return true;
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Update Name]: Error updating the name(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
				// And return false to show that it failed
				return false;
			}
		} else {
			throw new NotPreparedException("SQLFunctions: updateName(String, long)");
		}
	}

	/**
	 * This updates the price in the database
	 * 
	 * @param price
	 *            - The price.
	 * @param barcode
	 *            - The barcode of the item
	 * @return whether the process completed successfully.
	 * @throws NotPreparedException
	 *             If the database connection has not yet been opened.
	 */
	public static boolean updatePrice(double price, long barcode) throws NotPreparedException {
		if (prepared) {
			try {
				statement.executeUpdate("UPDATE PRODUCTS SET price = " + price + " WHERE barcode = " + barcode + ";");
				return true;
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Update Price]: Error updating the price(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
				// And return false to show that it failed
				return false;
			}
		} else {
			throw new NotPreparedException("SQLFunctions: updatePrice(double, long)");
		}
	}

	/**
	 * This updates the category in the database
	 * 
	 * @param category
	 *            - The category.
	 * @param barcode
	 *            - The barcode of the item
	 * @return whether the process completed successfully.
	 * @throws NotPreparedException
	 *             If the database connection has not yet been opened.
	 */
	public static boolean updateCategory(String category, long barcode) throws NotPreparedException {
		if (prepared) {
			try {
				statement.executeUpdate("UPDATE PRODUCTS SET category = \"" + category + "\" WHERE barcode = " + barcode + ";");
				return true;
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Update Category]: Error updating the category(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
				// And return false to show that it failed
				return false;
			}
		} else {
			throw new NotPreparedException("SQLFunctions: updateCategory(String, long)");
		}
	}

	/**
	 * This updates the manufacturer in the database
	 * 
	 * @param manufacturer
	 *            - The manufacturer.
	 * @param barcode
	 *            - The barcode of the item
	 * @return whether the process completed successfully.
	 * @throws NotPreparedException
	 *             If the database connection has not yet been opened.
	 */
	public static boolean updateManufacturer(String manufacturer, long barcode) throws NotPreparedException {
		if (prepared) {
			try {
				statement.executeUpdate("UPDATE PRODUCTS SET manufacturer = \"" + manufacturer + "\" WHERE barcode = " + barcode + ";");
				return true;
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Update Manufacturer]: Error updating the manufacturer(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
				// And return false to show that it failed
				return false;
			}
		} else {
			throw new NotPreparedException("SQLFunctions: updateManufacturer(String, long)");
		}
	}

	/**
	 * This updates the next restock date in the database
	 * 
	 * @param nextDate
	 *            - The date of the next restock.
	 * @param barcode
	 *            - The barcode of the item
	 * @return whether the process completed successfully.
	 * @throws NotPreparedException
	 *             If the database connection has not yet been opened.
	 */
	public static boolean updateNextRestock(Date nextDate, long barcode) throws NotPreparedException {
		if (prepared) {
			try {
				statement.executeUpdate("UPDATE PRODUCTS SET restock = \"" + nextDate.getFormatedDate() + "\" WHERE barcode = " + barcode + ";");
				return true;
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Update Next Restock]: Error updating the next restock(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
				// And return false to show that it failed
				return false;
			}
		} else {
			throw new NotPreparedException("SQLFunctions: updateNextRestock(Date, long)");
		}
	}

	/**
	 * This updates the last restock date in the database
	 * 
	 * @param lastDate
	 *            - The date of the last restock.
	 * @param barcode
	 *            - The barcode of the item
	 * @return whether the process completed successfully.
	 * @throws NotPreparedException
	 *             If the database connection has not yet been opened.
	 */
	public static boolean updateLastRestock(Date lastDate, long barcode) throws NotPreparedException {
		if (prepared) {
			try {
				statement.executeUpdate("UPDATE PRODUCTS SET restock_last = \"" + lastDate.getFormatedDate() + "\" WHERE barcode = " + barcode + ";");
				return true;
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Update Last Restock]: Error updating the last restock(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
				// And return false to show that it failed
				return false;
			}
		} else {
			throw new NotPreparedException("SQLFunctions: updateLastRestock(Date, long)");
		}
	}

	/**
	 * This removes the given stock from the database.
	 * 
	 * @param s
	 *            - The stock to remove.
	 * @param amount
	 *            - The amount to remove.
	 * @param original
	 *            - The original amount.
	 * @return whether the process completed successfully.
	 * @throws NotPreparedException
	 *             If the database connection has not yet been opened.
	 */
	public static boolean removeStock(Stock s, int amount, int original) throws NotPreparedException {
		if (prepared) {
			try {
				boolean addNew = false;
				int addAmount = original - amount;
				if (addAmount > 0) addNew = true;
				if (!(addAmount < 0)) statement.executeUpdate("DELETE FROM PRODUCTS WHERE barcode=" + s.getBarcode() + " AND amount=" + original + ";");
				if (addNew) {
					Stock toAdd = new Stock(s, addAmount);
					addStock(toAdd);
				}
				return true;
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Remove Stock]: Error removing the stock(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
				// And return false to show that it failed
				return false;
			}
		} else {
			throw new NotPreparedException("SQLFunctions: removeStock(Stock, int, int)");
		}
	}

	/**
	 * This closes the database connection.
	 * 
	 * @return whether the process completed successfully.
	 * @throws NotPreparedException
	 *             If the database connection has not been opened yet.
	 */
	public static boolean closeDatabase() throws NotPreparedException {
		if (prepared) {
			try {
				statement.close();
				if (!connection.getAutoCommit()) connection.commit();
				connection.close();
				prepared = false;
				return true;
			} catch (SQLException e) {
				// Print a brief description of the error.
				System.out.println("[Close Database]: Error closing the databse(SQLException). Contact the author or run this program with -showSTs to print the stack traces.");
				// If it does not print the stack trace for error logging if it
				// is enabled.
				if (Launcher.PRINT_STACK_TRACES) {
					e.printStackTrace();
				}
				// And return false to show that it failed
				return false;
			}
		} else {
			throw new NotPreparedException("SQLFunctions: closeDatabase()");
		}
	}

}
