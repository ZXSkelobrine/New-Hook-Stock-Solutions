package com.github.ZXSkelobrine.stock.windows.stock;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.github.ZXSkelobrine.stock.functions.windows.WindowFunctions;
import com.github.ZXSkelobrine.stock.variables.Date;
import com.github.ZXSkelobrine.stock.variables.Stock;
import com.github.ZXSkelobrine.stock.windows.enums.Window;

public class StockControl extends JFrame {

	private static final long serialVersionUID = 4343075877579859135L;
	private JPanel contentPane;

	private JList<String> list;

	private GridBagLayout gbl_contentPane;

	private JPanel panel;

	private JLabel lblValue;
	private JLabel lblError;

	private JTextField txtValue;

	private JButton btnApply;

	private Stock stock;

	private StockOverview owner;

	/**
	 * Launch the frame.
	 */
	public static void launchWindow(final Stock stock, final StockOverview owner) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StockControl frame = new StockControl();
					frame.stock = stock;
					frame.owner = owner;
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
	public StockControl() {
		WindowFunctions.applyWindowSettings(Window.CONTROL, this);
		setBounds(100, 100, 543, 382);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 168, 0, 361, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		list = new JList<String>();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (list.getSelectedIndex() == -1) {
					txtValue.setEnabled(false);
					btnApply.setEnabled(false);
				} else {
					txtValue.setEnabled(true);
					btnApply.setEnabled(true);
				}
			}
		});
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 0, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 0;
		contentPane.add(list, gbc_list);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);

		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		lblValue = new JLabel("Value:");
		GridBagConstraints gbc_lblValue = new GridBagConstraints();
		gbc_lblValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblValue.anchor = GridBagConstraints.EAST;
		gbc_lblValue.gridx = 0;
		gbc_lblValue.gridy = 1;
		panel.add(lblValue, gbc_lblValue);

		txtValue = new JTextField();
		GridBagConstraints gbc_txtValue = new GridBagConstraints();
		gbc_txtValue.insets = new Insets(0, 0, 5, 0);
		gbc_txtValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValue.gridx = 1;
		gbc_txtValue.gridy = 1;
		panel.add(txtValue, gbc_txtValue);
		txtValue.setColumns(10);

		btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean success = false;
				switch (list.getSelectedValue()) {
				case "Barcode":
					if (!canParse(txtValue.getText())) {
						lblError.setText("Error: Only numbers are allowed in this field");
						lblError.setVisible(true);
					} else {
						stock.setBarcode(Long.parseLong(txtValue.getText()));
						success = true;
					}
					break;
				case "Expiry":
					if (!canParseDate(txtValue.getText())) {
						lblError.setText("Error: Only valid dates are allowed in this field (DD/MM/YYYY)");
						lblError.setVisible(true);
					} else {
						stock.setExpiry(new Date(txtValue.getText()));
						success = true;
					}
					break;
				case "Amount":
					if (!canParse(txtValue.getText())) {
						lblError.setText("Error: Only numbers are allowed in this field");
						lblError.setVisible(true);
					} else {
						stock.setAmount(Integer.parseInt(txtValue.getText()));
						success = true;
					}
					break;
				case "Name":
					stock.setName(txtValue.getText());
					success = true;
					break;
				case "Price":
					if (!canParsePrice(txtValue.getText())) {
						lblError.setText("Error: Only decimals are allowed in this field");
						lblError.setVisible(true);
					} else {
						stock.setPrice(Double.parseDouble(txtValue.getText()));
						success = true;
					}
					break;
				case "Category":
					stock.setCategory(txtValue.getText());
					success = true;
					break;
				case "Manufacturer":
					stock.setManufacturer(txtValue.getText());
					success = true;
					break;
				case "Next Restock":
					if (!canParseDate(txtValue.getText())) {
						lblError.setText("Error: Only valid dates are allowed in this field (DD/MM/YYYY)");
						lblError.setVisible(true);
					} else {
						stock.setNextRestock(new Date(txtValue.getText()));
						success = true;
					}
					break;
				case "Last Restock":
					if (!canParseDate(txtValue.getText())) {
						lblError.setText("Error: Only valid dates are allowed in this field (DD/MM/YYYY)");
						lblError.setVisible(true);
					} else {
						stock.setLastRestock(new Date(txtValue.getText()));
						success = true;
					}
					break;
				}
				if (success) closeWindow();
			}
		});
		GridBagConstraints gbc_btnApply = new GridBagConstraints();
		gbc_btnApply.insets = new Insets(0, 0, 5, 0);
		gbc_btnApply.gridx = 1;
		gbc_btnApply.gridy = 3;
		panel.add(btnApply, gbc_btnApply);

		lblError = new JLabel("Error:");
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.gridx = 1;
		gbc_lblError.gridy = 4;
		panel.add(lblError, gbc_lblError);
		lblError.setVisible(false);

		String[] values = new String[] { "Barcode", "Expiry", "Amount", "Name", "Price", "Category", "Manufacturer", "Next Restock", "Last Restock" };
		list.setListData(values);

		txtValue.setEnabled(false);
		btnApply.setEnabled(false);
	}

	protected boolean canParsePrice(String text) {
		try {
			Double.parseDouble(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	protected boolean canParseDate(String text) {
		return text.split("/").length == 3;
	}

	protected boolean canParse(String text) {
		try {
			Long.parseLong(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	protected void closeWindow() {
		if (owner != null) owner.refreshStockList();
		dispose();
	}

}
