package com.github.ZXSkelobrine.stock.windows.stock;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.github.ZXSkelobrine.stock.errors.NotPreparedException;
import com.github.ZXSkelobrine.stock.functions.sql.SQLFunctions;
import com.github.ZXSkelobrine.stock.functions.windows.WindowFunctions;
import com.github.ZXSkelobrine.stock.prints.Printer;
import com.github.ZXSkelobrine.stock.variables.Date;
import com.github.ZXSkelobrine.stock.variables.Stock;
import com.github.ZXSkelobrine.stock.windows.dev.DeveloperLock;
import com.github.ZXSkelobrine.stock.windows.enums.Window;

public class StockOverview extends JFrame {

	private static final long serialVersionUID = 6142543341186746194L;
	private JPanel contentPane;
	private JPanel panel;
	private JPanel panel_1;

	private JTextField txtBarcode;
	private JTextField txtExpiry;
	private JTextField txtQuantity;
	private JTextField txtName;
	private JTextField txtPrice;
	private JTextField txtCategory;
	private JTextField txtManufacturer;
	private JTextField txtRestock;
	private JTextField txtLastRestock;

	private List<Stock> stock;
	private List<String> names;

	private JList<String> lstStock;

	private GridBagLayout gbl_panel;
	private GridBagLayout gbl_contentPane;
	private GridBagConstraints gbc_lstStock;

	private JLabel lblBarcode;
	private JLabel lblExpiryDate;
	private JLabel lblQuantity;
	private JLabel lblName;
	private JLabel lblPrice;
	private JLabel lblCategory;
	private JLabel lblManufacturer;
	private JLabel lblNextRestock;
	private JLabel lblLastRestock;

	private JSeparator separator;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JSeparator separator_3;
	private JSeparator separator_4;
	private JSeparator separator_5;
	private JSeparator separator_6;
	private JSeparator separator_7;

	private JButton btnAddNewStock;
	private JButton btnEditExistingStock;
	private JButton btnRemoveStock;
	private JButton btnRestockSelectedStock;
	private JButton btnRestockAllStock;
	private JButton btnRemoveAllStock;
	private JButton btnSave;

	private JScrollPane scrollPane;

	private boolean isLocked = false;
	private JButton btnPrintStockList;
	private JButton btnLookupStock;
	private JButton btnRefreshList;
	private JButton btnOpenDeveloperConsole;

	/**
	 * Launch the frame.
	 */
	public static void launchWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StockOverview frame = new StockOverview();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StockOverview() {
		WindowFunctions.applyWindowSettings(Window.OVERVIEW, this);
		setBounds(100, 100, 857, 527);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 191, 607, 0 };
		gbl_contentPane.rowHeights = new int[] { 289, 168, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		lstStock = new JList<String>();
		lstStock.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (lstStock.getSelectedIndex() != -1) {
					Stock selected = stock.get(lstStock.getSelectedIndex());
					txtBarcode.setText(selected.getBarcode() + "");
					txtCategory.setText(selected.getCategory());
					txtExpiry.setText(selected.getExpiry().getFormatedDate());
					txtManufacturer.setText(selected.getManufacturer());
					txtName.setText(selected.getName());
					txtPrice.setText(selected.getPrice() + "");
					txtQuantity.setText(selected.getAmount() + "");
					txtRestock.setText(selected.getNextRestock().getFormatedDate());
					txtLastRestock.setText(selected.getLastRestock().getFormatedDate());
				} else {
					txtBarcode.setText("");
					txtCategory.setText("");
					txtExpiry.setText("");
					txtManufacturer.setText("");
					txtName.setText("");
					txtPrice.setText("");
					txtQuantity.setText("");
					txtRestock.setText("");
					txtLastRestock.setText("");
				}
			}
		});
		gbc_lstStock = new GridBagConstraints();
		gbc_lstStock.insets = new Insets(0, 0, 5, 5);
		gbc_lstStock.fill = GridBagConstraints.BOTH;
		gbc_lstStock.gridx = 0;
		gbc_lstStock.gridy = 0;
		contentPane.add(lstStock, gbc_lstStock);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 2;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);

		gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		lblBarcode = new JLabel("Barcode:");
		GridBagConstraints gbc_lblBarcode = new GridBagConstraints();
		gbc_lblBarcode.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblBarcode.insets = new Insets(0, 0, 5, 5);
		gbc_lblBarcode.gridx = 0;
		gbc_lblBarcode.gridy = 1;
		panel.add(lblBarcode, gbc_lblBarcode);

		txtBarcode = new JTextField();
		txtBarcode.setEditable(false);
		GridBagConstraints gbc_txtBarcode = new GridBagConstraints();
		gbc_txtBarcode.insets = new Insets(0, 0, 5, 5);
		gbc_txtBarcode.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBarcode.gridx = 2;
		gbc_txtBarcode.gridy = 1;
		panel.add(txtBarcode, gbc_txtBarcode);
		txtBarcode.setColumns(10);

		separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 2;
		gbc_separator.gridy = 2;
		panel.add(separator, gbc_separator);

		lblExpiryDate = new JLabel("Expiry Date:");
		GridBagConstraints gbc_lblExpiryDate = new GridBagConstraints();
		gbc_lblExpiryDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblExpiryDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblExpiryDate.gridx = 0;
		gbc_lblExpiryDate.gridy = 3;
		panel.add(lblExpiryDate, gbc_lblExpiryDate);

		txtExpiry = new JTextField();
		txtExpiry.setEditable(false);
		GridBagConstraints gbc_txtExpiry = new GridBagConstraints();
		gbc_txtExpiry.insets = new Insets(0, 0, 5, 5);
		gbc_txtExpiry.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtExpiry.gridx = 2;
		gbc_txtExpiry.gridy = 3;
		panel.add(txtExpiry, gbc_txtExpiry);
		txtExpiry.setColumns(10);

		separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.insets = new Insets(0, 0, 5, 5);
		gbc_separator_1.gridx = 2;
		gbc_separator_1.gridy = 4;
		panel.add(separator_1, gbc_separator_1);

		lblQuantity = new JLabel("Quantity:");
		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblQuantity.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantity.gridx = 0;
		gbc_lblQuantity.gridy = 5;
		panel.add(lblQuantity, gbc_lblQuantity);

		txtQuantity = new JTextField();
		txtQuantity.setEditable(false);
		GridBagConstraints gbc_txtQuantity = new GridBagConstraints();
		gbc_txtQuantity.insets = new Insets(0, 0, 5, 5);
		gbc_txtQuantity.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtQuantity.gridx = 2;
		gbc_txtQuantity.gridy = 5;
		panel.add(txtQuantity, gbc_txtQuantity);
		txtQuantity.setColumns(10);

		separator_2 = new JSeparator();
		GridBagConstraints gbc_separator_2 = new GridBagConstraints();
		gbc_separator_2.insets = new Insets(0, 0, 5, 5);
		gbc_separator_2.gridx = 2;
		gbc_separator_2.gridy = 6;
		panel.add(separator_2, gbc_separator_2);

		lblName = new JLabel("Name:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 7;
		panel.add(lblName, gbc_lblName);

		txtName = new JTextField();
		txtName.setEditable(false);
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.insets = new Insets(0, 0, 5, 5);
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.gridx = 2;
		gbc_txtName.gridy = 7;
		panel.add(txtName, gbc_txtName);
		txtName.setColumns(10);

		separator_3 = new JSeparator();
		GridBagConstraints gbc_separator_3 = new GridBagConstraints();
		gbc_separator_3.insets = new Insets(0, 0, 5, 5);
		gbc_separator_3.gridx = 2;
		gbc_separator_3.gridy = 8;
		panel.add(separator_3, gbc_separator_3);

		lblPrice = new JLabel("Price:");
		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrice.gridx = 0;
		gbc_lblPrice.gridy = 9;
		panel.add(lblPrice, gbc_lblPrice);

		txtPrice = new JTextField();
		txtPrice.setEditable(false);
		GridBagConstraints gbc_txtPrice = new GridBagConstraints();
		gbc_txtPrice.insets = new Insets(0, 0, 5, 5);
		gbc_txtPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrice.gridx = 2;
		gbc_txtPrice.gridy = 9;
		panel.add(txtPrice, gbc_txtPrice);
		txtPrice.setColumns(10);

		separator_4 = new JSeparator();
		GridBagConstraints gbc_separator_4 = new GridBagConstraints();
		gbc_separator_4.insets = new Insets(0, 0, 5, 5);
		gbc_separator_4.gridx = 2;
		gbc_separator_4.gridy = 10;
		panel.add(separator_4, gbc_separator_4);

		lblCategory = new JLabel("Category:");
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.gridx = 0;
		gbc_lblCategory.gridy = 11;
		panel.add(lblCategory, gbc_lblCategory);

		txtCategory = new JTextField();
		txtCategory.setEditable(false);
		GridBagConstraints gbc_txtCategory = new GridBagConstraints();
		gbc_txtCategory.insets = new Insets(0, 0, 5, 5);
		gbc_txtCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCategory.gridx = 2;
		gbc_txtCategory.gridy = 11;
		panel.add(txtCategory, gbc_txtCategory);
		txtCategory.setColumns(10);

		separator_5 = new JSeparator();
		GridBagConstraints gbc_separator_5 = new GridBagConstraints();
		gbc_separator_5.insets = new Insets(0, 0, 5, 5);
		gbc_separator_5.gridx = 2;
		gbc_separator_5.gridy = 12;
		panel.add(separator_5, gbc_separator_5);

		lblManufacturer = new JLabel("Manufacturer:");
		GridBagConstraints gbc_lblManufacturer = new GridBagConstraints();
		gbc_lblManufacturer.insets = new Insets(0, 0, 5, 5);
		gbc_lblManufacturer.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblManufacturer.gridx = 0;
		gbc_lblManufacturer.gridy = 13;
		panel.add(lblManufacturer, gbc_lblManufacturer);

		txtManufacturer = new JTextField();
		txtManufacturer.setEditable(false);
		GridBagConstraints gbc_txtManufacturer = new GridBagConstraints();
		gbc_txtManufacturer.insets = new Insets(0, 0, 5, 5);
		gbc_txtManufacturer.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtManufacturer.gridx = 2;
		gbc_txtManufacturer.gridy = 13;
		panel.add(txtManufacturer, gbc_txtManufacturer);
		txtManufacturer.setColumns(10);

		separator_6 = new JSeparator();
		GridBagConstraints gbc_separator_6 = new GridBagConstraints();
		gbc_separator_6.insets = new Insets(0, 0, 5, 5);
		gbc_separator_6.gridx = 2;
		gbc_separator_6.gridy = 14;
		panel.add(separator_6, gbc_separator_6);

		lblNextRestock = new JLabel("Next Restock:");
		GridBagConstraints gbc_lblNextRestock = new GridBagConstraints();
		gbc_lblNextRestock.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNextRestock.insets = new Insets(0, 0, 5, 5);
		gbc_lblNextRestock.gridx = 0;
		gbc_lblNextRestock.gridy = 15;
		panel.add(lblNextRestock, gbc_lblNextRestock);

		txtRestock = new JTextField();
		txtRestock.setEditable(false);
		GridBagConstraints gbc_txtRestock = new GridBagConstraints();
		gbc_txtRestock.anchor = GridBagConstraints.NORTH;
		gbc_txtRestock.insets = new Insets(0, 0, 5, 5);
		gbc_txtRestock.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRestock.gridx = 2;
		gbc_txtRestock.gridy = 15;
		panel.add(txtRestock, gbc_txtRestock);
		txtRestock.setColumns(10);

		separator_7 = new JSeparator();
		GridBagConstraints gbc_separator_7 = new GridBagConstraints();
		gbc_separator_7.insets = new Insets(0, 0, 5, 5);
		gbc_separator_7.gridx = 2;
		gbc_separator_7.gridy = 16;
		panel.add(separator_7, gbc_separator_7);

		lblLastRestock = new JLabel("Last Restock:");
		GridBagConstraints gbc_lblLastRestock = new GridBagConstraints();
		gbc_lblLastRestock.insets = new Insets(0, 0, 0, 5);
		gbc_lblLastRestock.gridx = 0;
		gbc_lblLastRestock.gridy = 17;
		panel.add(lblLastRestock, gbc_lblLastRestock);

		txtLastRestock = new JTextField();
		txtLastRestock.setEditable(false);
		GridBagConstraints gbc_txtLastRestock = new GridBagConstraints();
		gbc_txtLastRestock.anchor = GridBagConstraints.NORTH;
		gbc_txtLastRestock.insets = new Insets(0, 0, 0, 5);
		gbc_txtLastRestock.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLastRestock.gridx = 2;
		gbc_txtLastRestock.gridy = 17;
		panel.add(txtLastRestock, gbc_txtLastRestock);
		txtLastRestock.setColumns(10);

		scrollPane = new JScrollPane();
		WindowFunctions.applyScrollSettings(scrollPane);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);

		panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setBackground(Color.WHITE);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		btnAddNewStock = new JButton("Add New Stock");
		btnAddNewStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StockInsert.launchWindow(StockOverview.this);
				isLocked = true;
				lockControlUpdate();
			}
		});
		GridBagConstraints gbc_btnAddNewStock = new GridBagConstraints();
		gbc_btnAddNewStock.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddNewStock.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAddNewStock.gridx = 0;
		gbc_btnAddNewStock.gridy = 0;
		panel_1.add(btnAddNewStock, gbc_btnAddNewStock);

		btnEditExistingStock = new JButton("Edit Selected Stock");
		btnEditExistingStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StockControl.launchWindow(stock.get(names.indexOf(lstStock.getSelectedValue())), StockOverview.this);
			}
		});
		GridBagConstraints gbc_btnEditExistingStock = new GridBagConstraints();
		gbc_btnEditExistingStock.insets = new Insets(0, 0, 5, 0);
		gbc_btnEditExistingStock.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEditExistingStock.gridx = 0;
		gbc_btnEditExistingStock.gridy = 1;
		panel_1.add(btnEditExistingStock, gbc_btnEditExistingStock);

		btnRemoveStock = new JButton("Remove Selected Stock");
		btnRemoveStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lstStock.getSelectedIndex() != -1) {
					StockRemove.launchWindow(stock.get(names.indexOf(lstStock.getSelectedValue())), StockOverview.this);
				}
			}
		});

		btnOpenDeveloperConsole = new JButton("Open Developer Console");
		btnOpenDeveloperConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeveloperLock.launchWindow();
			}
		});
		GridBagConstraints gbc_btnOpenDeveloperConsole = new GridBagConstraints();
		gbc_btnOpenDeveloperConsole.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOpenDeveloperConsole.insets = new Insets(0, 0, 5, 0);
		gbc_btnOpenDeveloperConsole.gridx = 0;
		gbc_btnOpenDeveloperConsole.gridy = 2;
		panel_1.add(btnOpenDeveloperConsole, gbc_btnOpenDeveloperConsole);
		GridBagConstraints gbc_btnRemoveStock = new GridBagConstraints();
		gbc_btnRemoveStock.insets = new Insets(0, 0, 5, 0);
		gbc_btnRemoveStock.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemoveStock.gridx = 0;
		gbc_btnRemoveStock.gridy = 3;
		panel_1.add(btnRemoveStock, gbc_btnRemoveStock);

		btnRestockSelectedStock = new JButton("Restock Selected Stock");
		GridBagConstraints gbc_btnRestockSelectedStock = new GridBagConstraints();
		gbc_btnRestockSelectedStock.insets = new Insets(0, 0, 5, 0);
		gbc_btnRestockSelectedStock.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRestockSelectedStock.gridx = 0;
		gbc_btnRestockSelectedStock.gridy = 4;
		panel_1.add(btnRestockSelectedStock, gbc_btnRestockSelectedStock);

		btnRestockAllStock = new JButton("Restock All Stock");
		btnRestockAllStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Stock s : stock) {
					Calendar cal = Calendar.getInstance();
					s.setLastRestock(new Date(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR)));
				}
				refreshStockList();
			}
		});
		GridBagConstraints gbc_btnRestockAllStock = new GridBagConstraints();
		gbc_btnRestockAllStock.insets = new Insets(0, 0, 5, 0);
		gbc_btnRestockAllStock.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRestockAllStock.gridx = 0;
		gbc_btnRestockAllStock.gridy = 5;
		panel_1.add(btnRestockAllStock, gbc_btnRestockAllStock);

		btnRefreshList = new JButton("Refresh List");
		btnRefreshList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshStockList();
			}
		});
		GridBagConstraints gbc_btnRefreshList = new GridBagConstraints();
		gbc_btnRefreshList.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRefreshList.insets = new Insets(0, 0, 5, 0);
		gbc_btnRefreshList.gridx = 0;
		gbc_btnRefreshList.gridy = 6;
		panel_1.add(btnRefreshList, gbc_btnRefreshList);

		btnRemoveAllStock = new JButton("Remove All Stock");
		btnRemoveAllStock.setBackground(new Color(153, 0, 0));
		GridBagConstraints gbc_btnRemoveAllStock = new GridBagConstraints();
		gbc_btnRemoveAllStock.insets = new Insets(0, 0, 5, 0);
		gbc_btnRemoveAllStock.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemoveAllStock.gridx = 0;
		gbc_btnRemoveAllStock.gridy = 7;
		panel_1.add(btnRemoveAllStock, gbc_btnRemoveAllStock);

		btnSave = new JButton("Save and close");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					SQLFunctions.closeDatabase();
				} catch (NotPreparedException e) {
					e.printStackTrace();
				}
			}
		});

		btnPrintStockList = new JButton("Print Stock List");
		btnPrintStockList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Printer.printStock(stock, "Stock Overview");
			}
		});

		btnLookupStock = new JButton("Lookup Stock");
		btnLookupStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StockLookup.launchWindow();
			}
		});
		GridBagConstraints gbc_btnLookupStock = new GridBagConstraints();
		gbc_btnLookupStock.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLookupStock.insets = new Insets(0, 0, 5, 0);
		gbc_btnLookupStock.gridx = 0;
		gbc_btnLookupStock.gridy = 8;
		panel_1.add(btnLookupStock, gbc_btnLookupStock);
		GridBagConstraints gbc_btnPrintStockList = new GridBagConstraints();
		gbc_btnPrintStockList.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPrintStockList.insets = new Insets(0, 0, 5, 0);
		gbc_btnPrintStockList.gridx = 0;
		gbc_btnPrintStockList.gridy = 9;
		panel_1.add(btnPrintStockList, gbc_btnPrintStockList);
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 10;
		panel_1.add(btnSave, gbc_btnSave);

		refreshStockList();

	}

	protected void lockControlUpdate() {
		if (isLocked) {
			btnAddNewStock.setEnabled(false);
			btnEditExistingStock.setEnabled(false);
			btnRemoveAllStock.setEnabled(false);
			btnRemoveStock.setEnabled(false);
			btnRestockAllStock.setEnabled(false);
			btnRestockSelectedStock.setEnabled(false);
			btnSave.setEnabled(false);
		} else {
			btnAddNewStock.setEnabled(true);
			btnEditExistingStock.setEnabled(true);
			btnRemoveAllStock.setEnabled(true);
			btnRemoveStock.setEnabled(true);
			btnRestockAllStock.setEnabled(true);
			btnRestockSelectedStock.setEnabled(true);
			btnSave.setEnabled(true);
		}
	}

	public void refreshStockList() {
		try {
			stock = SQLFunctions.getAllStock();
			names = new ArrayList<String>();
			for (Stock s : stock) {
				names.add(s.getName());
			}
			String[] nameArray = new String[names.size()];
			names.toArray(nameArray);
			lstStock.setListData(nameArray);
			isLocked = false;
			lockControlUpdate();
		} catch (NotPreparedException e) {
			System.out.println("Not prepared");
		}
	}
}
