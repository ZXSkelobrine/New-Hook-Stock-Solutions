package com.github.ZXSkelobrine.stock.windows.stock;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import com.github.ZXSkelobrine.stock.errors.NotPreparedException;
import com.github.ZXSkelobrine.stock.functions.sql.SQLFunctions;
import com.github.ZXSkelobrine.stock.functions.windows.WindowFunctions;
import com.github.ZXSkelobrine.stock.variables.Date;
import com.github.ZXSkelobrine.stock.variables.Stock;
import com.github.ZXSkelobrine.stock.windows.enums.Window;

public class StockInsert extends JFrame {

	private static final long serialVersionUID = 8696925481230366616L;

	private JPanel contentPane;

	private JFormattedTextField txt_Barcode;
	private JFormattedTextField txt_Expiry;
	private JFormattedTextField txt_Amount;
	private JFormattedTextField txt_Price;

	private JTextField txt_Name;
	private JTextField txt_Category;
	private JTextField txt_Manufacturer;

	private MaskFormatter barcodeFormatter;
	private MaskFormatter expiryFormatter;
	private MaskFormatter amountFormatter;
	private MaskFormatter priceFormatter;

	private StockOverview parent;

	private JLabel lblError;

	/**
	 * Launch the frame.
	 */
	public static void launchWindow(final StockOverview parent) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StockInsert frame = new StockInsert();
					frame.parent = parent;
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
	public StockInsert() {
		WindowFunctions.applyWindowSettings(Window.INSERT, this);
		setResizable(false);
		setBounds(10, 10, 350, 500);

		try {
			barcodeFormatter = new MaskFormatter("#############");
			barcodeFormatter.setPlaceholderCharacter('-');
			expiryFormatter = new MaskFormatter("##/##/####");
			expiryFormatter.setPlaceholderCharacter('-');
			amountFormatter = new MaskFormatter("####");
			amountFormatter.setPlaceholderCharacter('-');
			priceFormatter = new MaskFormatter("####.##");
			priceFormatter.setPlaceholderCharacter('-');
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txt_Barcode = new JFormattedTextField(barcodeFormatter);
		txt_Barcode.setBounds(109, 17, 225, 25);
		contentPane.add(txt_Barcode);
		txt_Barcode.setColumns(10);

		txt_Expiry = new JFormattedTextField(expiryFormatter);
		txt_Expiry.setBounds(109, 67, 225, 25);
		contentPane.add(txt_Expiry);
		txt_Expiry.setColumns(10);

		txt_Amount = new JFormattedTextField(amountFormatter);
		txt_Amount.setBounds(109, 114, 225, 25);
		contentPane.add(txt_Amount);
		txt_Amount.setColumns(10);

		txt_Name = new JTextField();
		txt_Name.setBounds(109, 161, 225, 25);
		contentPane.add(txt_Name);
		txt_Name.setColumns(10);

		txt_Price = new JFormattedTextField(priceFormatter);
		txt_Price.setBounds(109, 208, 225, 25);
		contentPane.add(txt_Price);
		txt_Price.setColumns(10);

		txt_Category = new JTextField();
		txt_Category.setBounds(109, 255, 225, 25);
		contentPane.add(txt_Category);
		txt_Category.setColumns(10);

		txt_Manufacturer = new JTextField();
		txt_Manufacturer.setBounds(109, 302, 225, 25);
		contentPane.add(txt_Manufacturer);
		txt_Manufacturer.setColumns(10);

		JLabel lblBarcode = new JLabel("Barcode");
		lblBarcode.setBounds(10, 22, 46, 14);
		contentPane.add(lblBarcode);

		JLabel lblExpiry = new JLabel("Expiry");
		lblExpiry.setBounds(10, 72, 46, 14);
		contentPane.add(lblExpiry);

		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setBounds(10, 119, 46, 14);
		contentPane.add(lblAmount);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 166, 46, 14);
		contentPane.add(lblName);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(10, 213, 46, 14);
		contentPane.add(lblPrice);

		JLabel lblCategory = new JLabel("Category");
		lblCategory.setBounds(10, 260, 46, 14);
		contentPane.add(lblCategory);

		JLabel lblManufacturer = new JLabel("Manufacturer");
		lblManufacturer.setBounds(10, 307, 65, 14);
		contentPane.add(lblManufacturer);

		JButton btnAdd = new JButton("Insert");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calendar c = Calendar.getInstance();
				Stock s = new Stock(Long.parseLong(txt_Barcode.getText()), new Date(txt_Expiry.getText()), Integer.parseInt(txt_Amount.getText()), txt_Name.getText(), Double.parseDouble(txt_Price.getText()), txt_Category.getText(), txt_Manufacturer.getText(), new Date("00/00/0000"), new Date(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR)));
				try {
					SQLFunctions.addStock(s);
					dispose();
				} catch (NotPreparedException e1) {
					lblError.setText("Error: " + e1.getLocalizedMessage() + "\tSee console for full error.");
					lblError.setVisible(true);
					e1.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(94, 375, 155, 49);
		contentPane.add(btnAdd);

		lblError = new JLabel("New label");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(10, 430, 324, 14);
		lblError.setVisible(false);
		contentPane.add(lblError);
	}

	@Override
	public void dispose() {
		if (parent != null) parent.refreshStockList();
		super.dispose();
	}

}
