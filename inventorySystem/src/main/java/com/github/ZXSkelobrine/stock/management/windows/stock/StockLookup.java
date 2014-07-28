package com.github.ZXSkelobrine.stock.management.windows.stock;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

import com.github.ZXSkelobrine.stock.Launcher;
import com.github.ZXSkelobrine.stock.global.sql.SQLFunctions;
import com.github.ZXSkelobrine.stock.global.variables.Stock;
import com.github.ZXSkelobrine.stock.management.errors.NotPreparedException;
import com.github.ZXSkelobrine.stock.management.functions.windows.WindowFunctions;
import com.github.ZXSkelobrine.stock.management.windows.enums.Window;

public class StockLookup extends JFrame {

	private static final long serialVersionUID = -3713195038979056259L;

	private JPanel contentPane;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel;

	private JTextField txtName;
	private JTextField txtBarcode;
	private JTextField txtExpiry;
	private JTextField txtPrice;
	private JTextField txtNext;
	private JTextField txtLast;
	private JTextField txtAmount;
	private JTextField txtCategory;
	private JTextField txtManufacturer;
	private JTextField txt_v_last;
	private JTextField txt_v_barcode;
	private JTextField txt_v_expiry;
	private JTextField txt_v_quantity;
	private JTextField txt_v_name;
	private JTextField txt_v_price;
	private JTextField txt_v_category;
	private JTextField txt_v_manufacturer;
	private JTextField txt_v_next;

	private JList<String> list;

	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;

	private GridBagConstraints gbc_btnSearch;

	private GridBagLayout gbl_panel_1;
	private GridBagLayout gbl_contentPane;
	private GridBagLayout gbl_panel;

	private JButton btnSearch;

	private JSeparator sprManufacturer;
	private JSeparator sptCategory;
	private JSeparator sprAmount;
	private JSeparator sprLast;
	private JSeparator sprNext;
	private JSeparator separator_1;
	private JSeparator sprBarcode;
	private JSeparator separator_3;
	private JSeparator separator_6;
	private JSeparator sprPrice;
	private JSeparator separator_5;
	private JSeparator separator_4;
	private JSeparator separator_7;
	private JSeparator separator;
	private JSeparator separator_2;
	private JSeparator sprExpiry;
	private JSeparator sprName;

	private JLabel lblManufacturer;
	private JLabel lblCategory;
	private JLabel lblAmount;
	private JLabel lblLastRestock;
	private JLabel lblNextRestock;
	private JLabel lblPrice;
	private JLabel lblExpiryDate;
	private JLabel lblBarcode;
	private JLabel lblName;
	private JLabel lbl_v_quantity;
	private JLabel lbl_v_expiry;
	private JLabel lbl_v_name;
	private JLabel lbl_v_price;
	private JLabel lbl_v_last;
	private JLabel lbl_v_manufacturer;
	private JLabel lbl_v_category;
	private JLabel lbl_v_barcode;
	private JLabel lbl_v_next;

	private List<Stock> stock;
	private List<String> names;

	/**
	 * Launch the application.
	 */
	public static void launchWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StockLookup frame = new StockLookup();
					frame.setVisible(true);
				} catch (Exception e) {
					// Print a brief description of the error.
					System.out.println("[Launch Window (Stock Lookup)]: Error initializing and showing frame(Exception). Contact the author or run this program with -showSTs to print the stack traces.");
					// If it does not print the stack trace for error logging if it
					// is enabled.
					if (Launcher.PRINT_STACK_TRACES) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StockLookup() {
		WindowFunctions.applyWindowSettings(Window.LOOKUP, this);
		setBounds(100, 100, 806, 561);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 179, 517, 0 };
		gbl_contentPane.rowHeights = new int[] { 208, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 2;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);

		gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		lblName = new JLabel("Name:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		panel.add(lblName, gbc_lblName);

		txtName = new JTextField();
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.insets = new Insets(0, 0, 5, 0);
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 0;
		panel.add(txtName, gbc_txtName);
		txtName.setColumns(10);

		sprName = new JSeparator();
		GridBagConstraints gbc_sprName = new GridBagConstraints();
		gbc_sprName.gridwidth = 2;
		gbc_sprName.insets = new Insets(0, 0, 5, 5);
		gbc_sprName.gridx = 0;
		gbc_sprName.gridy = 1;
		panel.add(sprName, gbc_sprName);

		lblBarcode = new JLabel("Barcode:");
		GridBagConstraints gbc_lblBarcode = new GridBagConstraints();
		gbc_lblBarcode.anchor = GridBagConstraints.EAST;
		gbc_lblBarcode.insets = new Insets(0, 0, 5, 5);
		gbc_lblBarcode.gridx = 0;
		gbc_lblBarcode.gridy = 2;
		panel.add(lblBarcode, gbc_lblBarcode);

		txtBarcode = new JTextField();
		GridBagConstraints gbc_txtBarcode = new GridBagConstraints();
		gbc_txtBarcode.insets = new Insets(0, 0, 5, 0);
		gbc_txtBarcode.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBarcode.gridx = 1;
		gbc_txtBarcode.gridy = 2;
		panel.add(txtBarcode, gbc_txtBarcode);
		txtBarcode.setColumns(10);

		sprBarcode = new JSeparator();
		GridBagConstraints gbc_sprBarcode = new GridBagConstraints();
		gbc_sprBarcode.gridwidth = 2;
		gbc_sprBarcode.insets = new Insets(0, 0, 5, 5);
		gbc_sprBarcode.gridx = 0;
		gbc_sprBarcode.gridy = 3;
		panel.add(sprBarcode, gbc_sprBarcode);

		lblExpiryDate = new JLabel("Expiry Date:");
		GridBagConstraints gbc_lblExpiryDate = new GridBagConstraints();
		gbc_lblExpiryDate.anchor = GridBagConstraints.EAST;
		gbc_lblExpiryDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblExpiryDate.gridx = 0;
		gbc_lblExpiryDate.gridy = 4;
		panel.add(lblExpiryDate, gbc_lblExpiryDate);

		txtExpiry = new JTextField();
		GridBagConstraints gbc_txtExpiry = new GridBagConstraints();
		gbc_txtExpiry.insets = new Insets(0, 0, 5, 0);
		gbc_txtExpiry.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtExpiry.gridx = 1;
		gbc_txtExpiry.gridy = 4;
		panel.add(txtExpiry, gbc_txtExpiry);
		txtExpiry.setColumns(10);

		sprExpiry = new JSeparator();
		GridBagConstraints gbc_sprExpiry = new GridBagConstraints();
		gbc_sprExpiry.gridwidth = 2;
		gbc_sprExpiry.insets = new Insets(0, 0, 5, 5);
		gbc_sprExpiry.gridx = 0;
		gbc_sprExpiry.gridy = 5;
		panel.add(sprExpiry, gbc_sprExpiry);

		lblPrice = new JLabel("Price:");
		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.anchor = GridBagConstraints.EAST;
		gbc_lblPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrice.gridx = 0;
		gbc_lblPrice.gridy = 6;
		panel.add(lblPrice, gbc_lblPrice);

		txtPrice = new JTextField();
		GridBagConstraints gbc_txtPrice = new GridBagConstraints();
		gbc_txtPrice.insets = new Insets(0, 0, 5, 0);
		gbc_txtPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrice.gridx = 1;
		gbc_txtPrice.gridy = 6;
		panel.add(txtPrice, gbc_txtPrice);
		txtPrice.setColumns(10);

		sprPrice = new JSeparator();
		GridBagConstraints gbc_sprPrice = new GridBagConstraints();
		gbc_sprPrice.gridwidth = 2;
		gbc_sprPrice.insets = new Insets(0, 0, 5, 5);
		gbc_sprPrice.gridx = 0;
		gbc_sprPrice.gridy = 7;
		panel.add(sprPrice, gbc_sprPrice);

		lblNextRestock = new JLabel("Next Restock:");
		GridBagConstraints gbc_lblNextRestock = new GridBagConstraints();
		gbc_lblNextRestock.anchor = GridBagConstraints.EAST;
		gbc_lblNextRestock.insets = new Insets(0, 0, 5, 5);
		gbc_lblNextRestock.gridx = 0;
		gbc_lblNextRestock.gridy = 8;
		panel.add(lblNextRestock, gbc_lblNextRestock);

		txtNext = new JTextField();
		GridBagConstraints gbc_txtNext = new GridBagConstraints();
		gbc_txtNext.insets = new Insets(0, 0, 5, 0);
		gbc_txtNext.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNext.gridx = 1;
		gbc_txtNext.gridy = 8;
		panel.add(txtNext, gbc_txtNext);
		txtNext.setColumns(10);

		sprNext = new JSeparator();
		GridBagConstraints gbc_sprNext = new GridBagConstraints();
		gbc_sprNext.gridwidth = 2;
		gbc_sprNext.insets = new Insets(0, 0, 5, 5);
		gbc_sprNext.gridx = 0;
		gbc_sprNext.gridy = 9;
		panel.add(sprNext, gbc_sprNext);

		lblLastRestock = new JLabel("Last Restock:");
		GridBagConstraints gbc_lblLastRestock = new GridBagConstraints();
		gbc_lblLastRestock.anchor = GridBagConstraints.EAST;
		gbc_lblLastRestock.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastRestock.gridx = 0;
		gbc_lblLastRestock.gridy = 10;
		panel.add(lblLastRestock, gbc_lblLastRestock);

		txtLast = new JTextField();
		GridBagConstraints gbc_txtLast = new GridBagConstraints();
		gbc_txtLast.insets = new Insets(0, 0, 5, 0);
		gbc_txtLast.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLast.gridx = 1;
		gbc_txtLast.gridy = 10;
		panel.add(txtLast, gbc_txtLast);
		txtLast.setColumns(10);

		sprLast = new JSeparator();
		GridBagConstraints gbc_sprLast = new GridBagConstraints();
		gbc_sprLast.gridwidth = 2;
		gbc_sprLast.insets = new Insets(0, 0, 5, 5);
		gbc_sprLast.gridx = 0;
		gbc_sprLast.gridy = 11;
		panel.add(sprLast, gbc_sprLast);

		lblAmount = new JLabel("Amount:");
		GridBagConstraints gbc_lblAmount = new GridBagConstraints();
		gbc_lblAmount.anchor = GridBagConstraints.EAST;
		gbc_lblAmount.insets = new Insets(0, 0, 5, 5);
		gbc_lblAmount.gridx = 0;
		gbc_lblAmount.gridy = 12;
		panel.add(lblAmount, gbc_lblAmount);

		txtAmount = new JTextField();
		GridBagConstraints gbc_txtAmount = new GridBagConstraints();
		gbc_txtAmount.insets = new Insets(0, 0, 5, 0);
		gbc_txtAmount.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAmount.gridx = 1;
		gbc_txtAmount.gridy = 12;
		panel.add(txtAmount, gbc_txtAmount);
		txtAmount.setColumns(10);

		sprAmount = new JSeparator();
		GridBagConstraints gbc_sprAmount = new GridBagConstraints();
		gbc_sprAmount.gridwidth = 2;
		gbc_sprAmount.insets = new Insets(0, 0, 5, 5);
		gbc_sprAmount.gridx = 0;
		gbc_sprAmount.gridy = 13;
		panel.add(sprAmount, gbc_sprAmount);

		lblCategory = new JLabel("Category:");
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.anchor = GridBagConstraints.EAST;
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.gridx = 0;
		gbc_lblCategory.gridy = 14;
		panel.add(lblCategory, gbc_lblCategory);

		txtCategory = new JTextField();
		GridBagConstraints gbc_txtCategory = new GridBagConstraints();
		gbc_txtCategory.insets = new Insets(0, 0, 5, 0);
		gbc_txtCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCategory.gridx = 1;
		gbc_txtCategory.gridy = 14;
		panel.add(txtCategory, gbc_txtCategory);
		txtCategory.setColumns(10);

		sptCategory = new JSeparator();
		GridBagConstraints gbc_sptCategory = new GridBagConstraints();
		gbc_sptCategory.gridwidth = 2;
		gbc_sptCategory.insets = new Insets(0, 0, 5, 5);
		gbc_sptCategory.gridx = 0;
		gbc_sptCategory.gridy = 15;
		panel.add(sptCategory, gbc_sptCategory);

		lblManufacturer = new JLabel("Manufacturer:");
		GridBagConstraints gbc_lblManufacturer = new GridBagConstraints();
		gbc_lblManufacturer.anchor = GridBagConstraints.EAST;
		gbc_lblManufacturer.insets = new Insets(0, 0, 5, 5);
		gbc_lblManufacturer.gridx = 0;
		gbc_lblManufacturer.gridy = 16;
		panel.add(lblManufacturer, gbc_lblManufacturer);

		txtManufacturer = new JTextField();
		GridBagConstraints gbc_txtManufacturer = new GridBagConstraints();
		gbc_txtManufacturer.insets = new Insets(0, 0, 5, 0);
		gbc_txtManufacturer.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtManufacturer.gridx = 1;
		gbc_txtManufacturer.gridy = 16;
		panel.add(txtManufacturer, gbc_txtManufacturer);
		txtManufacturer.setColumns(10);

		sprManufacturer = new JSeparator();
		GridBagConstraints gbc_sprManufacturer = new GridBagConstraints();
		gbc_sprManufacturer.gridwidth = 2;
		gbc_sprManufacturer.insets = new Insets(0, 0, 5, 0);
		gbc_sprManufacturer.gridx = 0;
		gbc_sprManufacturer.gridy = 17;
		panel.add(sprManufacturer, gbc_sprManufacturer);

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringBuilder sb = new StringBuilder();
				if (txtAmount.getText() != null && !txtAmount.getText().equals("")) {
					sb.append("amount=" + txtAmount.getText() + " AND ");
				}
				if (txtBarcode.getText() != null && !txtBarcode.getText().equals("")) {
					sb.append("barcode=" + txtBarcode.getText() + " AND ");

				}
				if (txtCategory.getText() != null && !txtCategory.getText().equals("")) {
					sb.append("category=\"" + txtCategory.getText() + "\" AND ");

				}
				if (txtExpiry.getText() != null && !txtExpiry.getText().equals("")) {
					sb.append("date=\"" + txtExpiry.getText() + "\" AND ");

				}
				if (txtLast.getText() != null && !txtLast.getText().equals("")) {
					sb.append("restock_last=\"" + txtLast.getText() + "\" AND ");

				}
				if (txtManufacturer.getText() != null && !txtManufacturer.getText().equals("")) {
					sb.append("manufacturer=\"" + txtManufacturer.getText() + "\" AND ");

				}
				if (txtName.getText() != null && !txtName.getText().equals("")) {
					sb.append("name=\"" + txtName.getText() + "\" AND ");

				}
				if (txtNext.getText() != null && !txtNext.getText().equals("")) {
					sb.append("restock=\"" + txtNext.getText() + "\" AND ");

				}
				if (txtPrice.getText() != null && !txtPrice.getText().equals("")) {
					sb.append("price=" + txtPrice.getText() + " AND ");

				}
				try {
					String where = sb.toString();
					if (where.endsWith("AND ")) where = where.substring(0, where.length() - 4);
					System.out.println("Where: " + where);
					stock = SQLFunctions.stockLookupByWhereClause(where);
					names = new ArrayList<String>();
					for (Stock s : stock) {
						names.add(s.getName());
					}
					String[] namesArray = new String[names.size()];
					names.toArray(namesArray);
					list.setListData(namesArray);
				} catch (NotPreparedException e1) {
					// Print a brief description of the error.
					System.out.println("[Button Press (Stock Lookup)]: Error looking up stock(Not Prepared Exception). Contact the author or run this program with -showSTs to print the stack traces.");
					// If it does not print the stack trace for error logging if
					// it is enabled.
					if (Launcher.PRINT_STACK_TRACES) {
						e1.printStackTrace();
					}
				}
			}
		});
		gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 18;
		panel.add(btnSearch, gbc_btnSearch);

		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		contentPane.add(panel_1, gbc_panel_1);

		gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		scrollPane = new JScrollPane();
		WindowFunctions.applyScrollSettings(scrollPane);
		;
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel_1.add(scrollPane, gbc_scrollPane);

		list = new JList<String>();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (list.getSelectedIndex() != -1) {
					Stock stockItem = stock.get(names.indexOf(list.getSelectedValue()));
					txt_v_barcode.setText(stockItem.getBarcode() + "");
					txt_v_category.setText(stockItem.getCategory());
					txt_v_expiry.setText(stockItem.getExpiry().getFormatedDate());
					txt_v_last.setText(stockItem.getLastRestock().getFormatedDate());
					txt_v_manufacturer.setText(stockItem.getManufacturer());
					txt_v_name.setText(stockItem.getName());
					txt_v_next.setText(stockItem.getNextRestock().getFormatedDate());
					txt_v_price.setText(stockItem.getPrice() + "");
					txt_v_quantity.setText(stockItem.getAmount() + "");
				} else {
					txt_v_barcode.setText("");
					txt_v_category.setText("");
					txt_v_expiry.setText("");
					txt_v_last.setText("");
					txt_v_manufacturer.setText("");
					txt_v_name.setText("");
					txt_v_next.setText("");
					txt_v_price.setText("");
					txt_v_quantity.setText("");
				}
			}
		});
		scrollPane.setViewportView(list);

		scrollPane_1 = new JScrollPane();
		WindowFunctions.applyScrollSettings(scrollPane_1);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 1;
		contentPane.add(scrollPane_1, gbc_scrollPane_1);

		panel_2 = new JPanel();
		scrollPane_1.setViewportView(panel_2);
		panel_2.setBackground(Color.WHITE);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		lbl_v_barcode = new JLabel("Barcode:");
		GridBagConstraints gbc_lbl_v_barcode = new GridBagConstraints();
		gbc_lbl_v_barcode.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbl_v_barcode.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_v_barcode.gridx = 0;
		gbc_lbl_v_barcode.gridy = 0;
		panel_2.add(lbl_v_barcode, gbc_lbl_v_barcode);

		txt_v_barcode = new JTextField();
		txt_v_barcode.setEditable(false);
		txt_v_barcode.setColumns(10);
		GridBagConstraints gbc_txt_v_barcode = new GridBagConstraints();
		gbc_txt_v_barcode.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_v_barcode.insets = new Insets(0, 0, 5, 5);
		gbc_txt_v_barcode.gridx = 2;
		gbc_txt_v_barcode.gridy = 0;
		panel_2.add(txt_v_barcode, gbc_txt_v_barcode);

		separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 2;
		gbc_separator.gridy = 1;
		panel_2.add(separator, gbc_separator);

		lbl_v_expiry = new JLabel("Expiry Date:");
		GridBagConstraints gbc_lbl_v_expiry = new GridBagConstraints();
		gbc_lbl_v_expiry.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbl_v_expiry.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_v_expiry.gridx = 0;
		gbc_lbl_v_expiry.gridy = 2;
		panel_2.add(lbl_v_expiry, gbc_lbl_v_expiry);

		txt_v_expiry = new JTextField();
		txt_v_expiry.setEditable(false);
		txt_v_expiry.setColumns(10);
		GridBagConstraints gbc_txt_v_expiry = new GridBagConstraints();
		gbc_txt_v_expiry.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_v_expiry.insets = new Insets(0, 0, 5, 5);
		gbc_txt_v_expiry.gridx = 2;
		gbc_txt_v_expiry.gridy = 2;
		panel_2.add(txt_v_expiry, gbc_txt_v_expiry);

		separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.insets = new Insets(0, 0, 5, 5);
		gbc_separator_1.gridx = 2;
		gbc_separator_1.gridy = 3;
		panel_2.add(separator_1, gbc_separator_1);

		lbl_v_quantity = new JLabel("Quantity:");
		GridBagConstraints gbc_lbl_v_quantity = new GridBagConstraints();
		gbc_lbl_v_quantity.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbl_v_quantity.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_v_quantity.gridx = 0;
		gbc_lbl_v_quantity.gridy = 4;
		panel_2.add(lbl_v_quantity, gbc_lbl_v_quantity);

		txt_v_quantity = new JTextField();
		txt_v_quantity.setEditable(false);
		txt_v_quantity.setColumns(10);
		GridBagConstraints gbc_txt_v_quantity = new GridBagConstraints();
		gbc_txt_v_quantity.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_v_quantity.insets = new Insets(0, 0, 5, 5);
		gbc_txt_v_quantity.gridx = 2;
		gbc_txt_v_quantity.gridy = 4;
		panel_2.add(txt_v_quantity, gbc_txt_v_quantity);

		separator_2 = new JSeparator();
		GridBagConstraints gbc_separator_2 = new GridBagConstraints();
		gbc_separator_2.insets = new Insets(0, 0, 5, 5);
		gbc_separator_2.gridx = 2;
		gbc_separator_2.gridy = 5;
		panel_2.add(separator_2, gbc_separator_2);

		lbl_v_name = new JLabel("Name:");
		GridBagConstraints gbc_lbl_v_name = new GridBagConstraints();
		gbc_lbl_v_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbl_v_name.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_v_name.gridx = 0;
		gbc_lbl_v_name.gridy = 6;
		panel_2.add(lbl_v_name, gbc_lbl_v_name);

		txt_v_name = new JTextField();
		txt_v_name.setEditable(false);
		txt_v_name.setColumns(10);
		GridBagConstraints gbc_txt_v_name = new GridBagConstraints();
		gbc_txt_v_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_v_name.insets = new Insets(0, 0, 5, 5);
		gbc_txt_v_name.gridx = 2;
		gbc_txt_v_name.gridy = 6;
		panel_2.add(txt_v_name, gbc_txt_v_name);

		separator_3 = new JSeparator();
		GridBagConstraints gbc_separator_3 = new GridBagConstraints();
		gbc_separator_3.insets = new Insets(0, 0, 5, 5);
		gbc_separator_3.gridx = 2;
		gbc_separator_3.gridy = 7;
		panel_2.add(separator_3, gbc_separator_3);

		lbl_v_price = new JLabel("Price:");
		GridBagConstraints gbc_lbl_v_price = new GridBagConstraints();
		gbc_lbl_v_price.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbl_v_price.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_v_price.gridx = 0;
		gbc_lbl_v_price.gridy = 8;
		panel_2.add(lbl_v_price, gbc_lbl_v_price);

		txt_v_price = new JTextField();
		txt_v_price.setEditable(false);
		txt_v_price.setColumns(10);
		GridBagConstraints gbc_txt_v_price = new GridBagConstraints();
		gbc_txt_v_price.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_v_price.insets = new Insets(0, 0, 5, 5);
		gbc_txt_v_price.gridx = 2;
		gbc_txt_v_price.gridy = 8;
		panel_2.add(txt_v_price, gbc_txt_v_price);

		separator_4 = new JSeparator();
		GridBagConstraints gbc_separator_4 = new GridBagConstraints();
		gbc_separator_4.insets = new Insets(0, 0, 5, 5);
		gbc_separator_4.gridx = 2;
		gbc_separator_4.gridy = 9;
		panel_2.add(separator_4, gbc_separator_4);

		lbl_v_category = new JLabel("Category:");
		GridBagConstraints gbc_lbl_v_category = new GridBagConstraints();
		gbc_lbl_v_category.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbl_v_category.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_v_category.gridx = 0;
		gbc_lbl_v_category.gridy = 10;
		panel_2.add(lbl_v_category, gbc_lbl_v_category);

		txt_v_category = new JTextField();
		txt_v_category.setEditable(false);
		txt_v_category.setColumns(10);
		GridBagConstraints gbc_txt_v_category = new GridBagConstraints();
		gbc_txt_v_category.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_v_category.insets = new Insets(0, 0, 5, 5);
		gbc_txt_v_category.gridx = 2;
		gbc_txt_v_category.gridy = 10;
		panel_2.add(txt_v_category, gbc_txt_v_category);

		separator_5 = new JSeparator();
		GridBagConstraints gbc_separator_5 = new GridBagConstraints();
		gbc_separator_5.insets = new Insets(0, 0, 5, 5);
		gbc_separator_5.gridx = 2;
		gbc_separator_5.gridy = 11;
		panel_2.add(separator_5, gbc_separator_5);

		lbl_v_manufacturer = new JLabel("Manufacturer:");
		GridBagConstraints gbc_lbl_v_manufacturer = new GridBagConstraints();
		gbc_lbl_v_manufacturer.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbl_v_manufacturer.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_v_manufacturer.gridx = 0;
		gbc_lbl_v_manufacturer.gridy = 12;
		panel_2.add(lbl_v_manufacturer, gbc_lbl_v_manufacturer);

		txt_v_manufacturer = new JTextField();
		txt_v_manufacturer.setEditable(false);
		txt_v_manufacturer.setColumns(10);
		GridBagConstraints gbc_txt_v_manufacturer = new GridBagConstraints();
		gbc_txt_v_manufacturer.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_v_manufacturer.insets = new Insets(0, 0, 5, 5);
		gbc_txt_v_manufacturer.gridx = 2;
		gbc_txt_v_manufacturer.gridy = 12;
		panel_2.add(txt_v_manufacturer, gbc_txt_v_manufacturer);

		separator_6 = new JSeparator();
		GridBagConstraints gbc_separator_6 = new GridBagConstraints();
		gbc_separator_6.insets = new Insets(0, 0, 5, 5);
		gbc_separator_6.gridx = 2;
		gbc_separator_6.gridy = 13;
		panel_2.add(separator_6, gbc_separator_6);

		lbl_v_next = new JLabel("Next Restock:");
		GridBagConstraints gbc_lbl_v_next = new GridBagConstraints();
		gbc_lbl_v_next.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbl_v_next.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_v_next.gridx = 0;
		gbc_lbl_v_next.gridy = 14;
		panel_2.add(lbl_v_next, gbc_lbl_v_next);

		txt_v_next = new JTextField();
		txt_v_next.setEditable(false);
		txt_v_next.setColumns(10);
		GridBagConstraints gbc_txt_v_next = new GridBagConstraints();
		gbc_txt_v_next.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_v_next.anchor = GridBagConstraints.NORTH;
		gbc_txt_v_next.insets = new Insets(0, 0, 5, 5);
		gbc_txt_v_next.gridx = 2;
		gbc_txt_v_next.gridy = 14;
		panel_2.add(txt_v_next, gbc_txt_v_next);

		separator_7 = new JSeparator();
		GridBagConstraints gbc_separator_7 = new GridBagConstraints();
		gbc_separator_7.insets = new Insets(0, 0, 5, 5);
		gbc_separator_7.gridx = 2;
		gbc_separator_7.gridy = 15;
		panel_2.add(separator_7, gbc_separator_7);

		lbl_v_last = new JLabel("Last Restock:");
		GridBagConstraints gbc_lbl_v_last = new GridBagConstraints();
		gbc_lbl_v_last.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_v_last.gridx = 0;
		gbc_lbl_v_last.gridy = 16;
		panel_2.add(lbl_v_last, gbc_lbl_v_last);

		txt_v_last = new JTextField();
		txt_v_last.setEditable(false);
		txt_v_last.setColumns(10);
		GridBagConstraints gbc_txt_v_last = new GridBagConstraints();
		gbc_txt_v_last.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_v_last.anchor = GridBagConstraints.NORTH;
		gbc_txt_v_last.insets = new Insets(0, 0, 0, 5);
		gbc_txt_v_last.gridx = 2;
		gbc_txt_v_last.gridy = 16;
		panel_2.add(txt_v_last, gbc_txt_v_last);
	}

	public List<Stock> compare(List<Stock> l1, List<Stock> l2) {
		List<Stock> common = new ArrayList<Stock>(l1);
		common.retainAll(l2);
		return common;
	}
}
