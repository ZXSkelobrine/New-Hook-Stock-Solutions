package com.github.ZXSkelobrine.stock.management.windows.stock;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import com.github.ZXSkelobrine.stock.Launcher;
import com.github.ZXSkelobrine.stock.global.sql.SQLFunctions;
import com.github.ZXSkelobrine.stock.global.variables.Stock;
import com.github.ZXSkelobrine.stock.management.errors.NotPreparedException;
import com.github.ZXSkelobrine.stock.management.functions.windows.WindowFunctions;
import com.github.ZXSkelobrine.stock.management.windows.enums.Window;

public class StockRemove extends JFrame {

	private static final long serialVersionUID = 2419034097791696395L;

	private JPanel contentPane;

	private JTextField txtBarcode;
	private JTextField txtExpiry;
	private JTextField txtAmount;
	private JTextField txtName;
	private JTextField txtPrice;
	private JTextField txtCategory;
	private JTextField txtManufacturer;
	private JTextField txtNext;
	private JTextField txtLast;

	private Stock removed;

	private StockOverview parent;

	private JLabel lblError;

	/**
	 * Launch the application.
	 */
	public static void launchWindow(final Stock stock, final StockOverview owner) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StockRemove frame = new StockRemove(stock);
					frame.parent = owner;
					frame.setVisible(true);
				} catch (Exception e) {
					// Print a brief description of the error.
					System.out.println("[Launch Window (Stock Remove)]: Error initializing and showing frame(Exception). Contact the author or run this program with -showSTs to print the stack traces.");
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
	public StockRemove(Stock stock) {
		WindowFunctions.applyWindowSettings(Window.REMOVE, this);
		this.removed = stock;
		setBounds(100, 100, 423, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel label = new JLabel("Barcode:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.HORIZONTAL;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		panel.add(label, gbc_label);

		txtBarcode = new JTextField();
		txtBarcode.setEditable(false);
		txtBarcode.setColumns(10);
		txtBarcode.setText(removed.getBarcode() + "");
		GridBagConstraints gbc_txtBarcode = new GridBagConstraints();
		gbc_txtBarcode.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBarcode.insets = new Insets(0, 0, 5, 5);
		gbc_txtBarcode.gridx = 2;
		gbc_txtBarcode.gridy = 1;
		panel.add(txtBarcode, gbc_txtBarcode);

		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 2;
		gbc_separator.gridy = 2;
		panel.add(separator, gbc_separator);

		JLabel label_1 = new JLabel("Expiry Date:");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 3;
		panel.add(label_1, gbc_label_1);

		txtExpiry = new JTextField();
		txtExpiry.setEditable(false);
		txtExpiry.setColumns(10);
		txtExpiry.setText(removed.getExpiry().getFormatedDate());
		GridBagConstraints gbc_txtExpiry = new GridBagConstraints();
		gbc_txtExpiry.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtExpiry.insets = new Insets(0, 0, 5, 5);
		gbc_txtExpiry.gridx = 2;
		gbc_txtExpiry.gridy = 3;
		panel.add(txtExpiry, gbc_txtExpiry);

		JSeparator separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.insets = new Insets(0, 0, 5, 5);
		gbc_separator_1.gridx = 2;
		gbc_separator_1.gridy = 4;
		panel.add(separator_1, gbc_separator_1);

		JLabel label_2 = new JLabel("Quantity:");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 5;
		panel.add(label_2, gbc_label_2);

		MaskFormatter mf = new MaskFormatter();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < String.valueOf(removed.getAmount()).length(); i++) {
			sb.append("#");
		}
		sb.append("/" + String.valueOf(removed.getAmount()));
		try {
			mf.setMask(sb.toString());
			mf.setPlaceholderCharacter('-');
		} catch (ParseException e1) {
			// Print a brief description of the error.
			System.out.println("[Constuctor (Stock Remove)]: Error setting masks(ParseException). Contact the author or run this program with -showSTs to print the stack traces.");
			// If it does not print the stack trace for error logging if
			// it is enabled.
			if (Launcher.PRINT_STACK_TRACES) {
				e1.printStackTrace();
			}
		}

		txtAmount = new JFormattedTextField(mf);
		txtAmount.setColumns(10);
		GridBagConstraints gbc_txtAmount = new GridBagConstraints();
		gbc_txtAmount.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAmount.insets = new Insets(0, 0, 5, 5);
		gbc_txtAmount.gridx = 2;
		gbc_txtAmount.gridy = 5;
		panel.add(txtAmount, gbc_txtAmount);

		JSeparator separator_2 = new JSeparator();
		GridBagConstraints gbc_separator_2 = new GridBagConstraints();
		gbc_separator_2.insets = new Insets(0, 0, 5, 5);
		gbc_separator_2.gridx = 2;
		gbc_separator_2.gridy = 6;
		panel.add(separator_2, gbc_separator_2);

		JLabel label_3 = new JLabel("Name:");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 0;
		gbc_label_3.gridy = 7;
		panel.add(label_3, gbc_label_3);

		txtName = new JTextField();
		txtName.setEditable(false);
		txtName.setColumns(10);
		txtName.setText(removed.getName());
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.insets = new Insets(0, 0, 5, 5);
		gbc_txtName.gridx = 2;
		gbc_txtName.gridy = 7;
		panel.add(txtName, gbc_txtName);

		JSeparator separator_3 = new JSeparator();
		GridBagConstraints gbc_separator_3 = new GridBagConstraints();
		gbc_separator_3.insets = new Insets(0, 0, 5, 5);
		gbc_separator_3.gridx = 2;
		gbc_separator_3.gridy = 8;
		panel.add(separator_3, gbc_separator_3);

		JLabel label_4 = new JLabel("Price:");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 0;
		gbc_label_4.gridy = 9;
		panel.add(label_4, gbc_label_4);

		txtPrice = new JTextField();
		txtPrice.setEditable(false);
		txtPrice.setColumns(10);
		txtPrice.setText(removed.getPrice() + "");
		GridBagConstraints gbc_txtPrice = new GridBagConstraints();
		gbc_txtPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrice.insets = new Insets(0, 0, 5, 5);
		gbc_txtPrice.gridx = 2;
		gbc_txtPrice.gridy = 9;
		panel.add(txtPrice, gbc_txtPrice);

		JSeparator separator_4 = new JSeparator();
		GridBagConstraints gbc_separator_4 = new GridBagConstraints();
		gbc_separator_4.insets = new Insets(0, 0, 5, 5);
		gbc_separator_4.gridx = 2;
		gbc_separator_4.gridy = 10;
		panel.add(separator_4, gbc_separator_4);

		JLabel label_5 = new JLabel("Category:");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		gbc_label_5.gridx = 0;
		gbc_label_5.gridy = 11;
		panel.add(label_5, gbc_label_5);

		txtCategory = new JTextField();
		txtCategory.setEditable(false);
		txtCategory.setColumns(10);
		txtCategory.setText(removed.getCategory());
		GridBagConstraints gbc_txtCategory = new GridBagConstraints();
		gbc_txtCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCategory.insets = new Insets(0, 0, 5, 5);
		gbc_txtCategory.gridx = 2;
		gbc_txtCategory.gridy = 11;
		panel.add(txtCategory, gbc_txtCategory);

		JSeparator separator_5 = new JSeparator();
		GridBagConstraints gbc_separator_5 = new GridBagConstraints();
		gbc_separator_5.insets = new Insets(0, 0, 5, 5);
		gbc_separator_5.gridx = 2;
		gbc_separator_5.gridy = 12;
		panel.add(separator_5, gbc_separator_5);

		JLabel label_6 = new JLabel("Manufacturer:");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_6.insets = new Insets(0, 0, 5, 5);
		gbc_label_6.gridx = 0;
		gbc_label_6.gridy = 13;
		panel.add(label_6, gbc_label_6);

		txtManufacturer = new JTextField();
		txtManufacturer.setEditable(false);
		txtManufacturer.setColumns(10);
		txtManufacturer.setText(removed.getManufacturer());
		GridBagConstraints gbc_txtManufacturer = new GridBagConstraints();
		gbc_txtManufacturer.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtManufacturer.insets = new Insets(0, 0, 5, 5);
		gbc_txtManufacturer.gridx = 2;
		gbc_txtManufacturer.gridy = 13;
		panel.add(txtManufacturer, gbc_txtManufacturer);

		JSeparator separator_6 = new JSeparator();
		GridBagConstraints gbc_separator_6 = new GridBagConstraints();
		gbc_separator_6.insets = new Insets(0, 0, 5, 5);
		gbc_separator_6.gridx = 2;
		gbc_separator_6.gridy = 14;
		panel.add(separator_6, gbc_separator_6);

		JLabel label_7 = new JLabel("Next Restock:");
		GridBagConstraints gbc_label_7 = new GridBagConstraints();
		gbc_label_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_7.insets = new Insets(0, 0, 5, 5);
		gbc_label_7.gridx = 0;
		gbc_label_7.gridy = 15;
		panel.add(label_7, gbc_label_7);

		txtNext = new JTextField();
		txtNext.setEditable(false);
		txtNext.setColumns(10);
		txtNext.setText(removed.getNextRestock().getFormatedDate());
		GridBagConstraints gbc_txtNext = new GridBagConstraints();
		gbc_txtNext.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNext.anchor = GridBagConstraints.NORTH;
		gbc_txtNext.insets = new Insets(0, 0, 5, 5);
		gbc_txtNext.gridx = 2;
		gbc_txtNext.gridy = 15;
		panel.add(txtNext, gbc_txtNext);

		JSeparator separator_7 = new JSeparator();
		GridBagConstraints gbc_separator_7 = new GridBagConstraints();
		gbc_separator_7.insets = new Insets(0, 0, 5, 5);
		gbc_separator_7.gridx = 2;
		gbc_separator_7.gridy = 16;
		panel.add(separator_7, gbc_separator_7);

		JLabel label_8 = new JLabel("Last Restock:");
		GridBagConstraints gbc_label_8 = new GridBagConstraints();
		gbc_label_8.insets = new Insets(0, 0, 5, 5);
		gbc_label_8.gridx = 0;
		gbc_label_8.gridy = 17;
		panel.add(label_8, gbc_label_8);

		txtLast = new JTextField();
		txtLast.setEditable(false);
		txtLast.setColumns(10);
		txtLast.setText(removed.getLastRestock().getFormatedDate());
		GridBagConstraints gbc_txtLast = new GridBagConstraints();
		gbc_txtLast.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLast.anchor = GridBagConstraints.NORTH;
		gbc_txtLast.insets = new Insets(0, 0, 5, 5);
		gbc_txtLast.gridx = 2;
		gbc_txtLast.gridy = 17;
		panel.add(txtLast, gbc_txtLast);

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SQLFunctions.removeStock(removed, Integer.parseInt(txtAmount.getText().split("/")[0]), Integer.parseInt(txtAmount.getText().split("/")[1]));
				} catch (NumberFormatException e1) {
					lblError.setText("Error: Please enter a valid amount.");
					lblError.setVisible(true);
				} catch (NotPreparedException e1) {
					// Print a brief description of the error.
					System.out.println("[Button Press (Stock Remove)]: Error removing stock(Not Prepared Exception). Contact the author or run this program with -showSTs to print the stack traces.");
					// If it does not print the stack trace for error logging if
					// it is enabled.
					if (Launcher.PRINT_STACK_TRACES) {
						e1.printStackTrace();
					}
				} finally {
					parent.refreshStockList();
					dispose();
				}
			}
		});
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemove.gridx = 2;
		gbc_btnRemove.gridy = 18;
		panel.add(btnRemove, gbc_btnRemove);

		lblError = new JLabel("Error");
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.insets = new Insets(0, 0, 0, 5);
		gbc_lblError.gridx = 2;
		gbc_lblError.gridy = 19;
		panel.add(lblError, gbc_lblError);
		lblError.setVisible(false);
		txtAmount.requestFocusInWindow();
	}
}
