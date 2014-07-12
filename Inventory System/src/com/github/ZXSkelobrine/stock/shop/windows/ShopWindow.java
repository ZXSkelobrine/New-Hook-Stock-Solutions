package com.github.ZXSkelobrine.stock.shop.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.github.ZXSkelobrine.stock.shop.listeners.SerialListener;
import com.github.ZXSkelobrine.stock.shop.misc.PriceObject;

public class ShopWindow extends JFrame {

	private static final long serialVersionUID = 6628856510226692387L;

	public static Font LIST_FONT;
	public static Dimension LIST_SIZE;

	private JPanel contentPane;
	private JPanel panel_1;
	private JPanel panel;
	private JPanel panel_2;

	private static JTextField txtTotal;
	private JTextField txtPleaseScanYour;

	private GridBagLayout gbl_panel_1;
	private GridBagLayout gbl_panel_2;
	private GridBagLayout gbl_panel;

	private GridBagConstraints gbc_txtPleaseScanYour;

	private static JList<PriceObject> list;

	private static List<PriceObject> pos = new ArrayList<PriceObject>();

	private JButton btnAddItem;

	private JScrollPane scrollPane;

	private static double currentTotal = 0.0;

	/**
	 * Launch the application.
	 */
	public static void launchWindow(final int port) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SerialListener.prepareConection(port);
					SerialListener.startListener();
					ShopWindow frame = new ShopWindow();
					GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
					frame.setVisible(true);
					frame.configureList();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ShopWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1233, 784);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 657, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 717, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		contentPane.add(panel_1, gbc_panel_1);

		gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 52, 0, 40, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		txtPleaseScanYour = new JTextField();
		txtPleaseScanYour.setForeground(new Color(51, 0, 0));
		txtPleaseScanYour.setFont(new Font("Verdana", Font.BOLD, 20));
		txtPleaseScanYour.setText("Please scan your items.");
		txtPleaseScanYour.setHorizontalAlignment(SwingConstants.CENTER);

		gbc_txtPleaseScanYour = new GridBagConstraints();
		gbc_txtPleaseScanYour.insets = new Insets(0, 0, 5, 0);
		gbc_txtPleaseScanYour.fill = GridBagConstraints.BOTH;
		gbc_txtPleaseScanYour.gridx = 0;
		gbc_txtPleaseScanYour.gridy = 0;
		panel_1.add(txtPleaseScanYour, gbc_txtPleaseScanYour);
		txtPleaseScanYour.setColumns(10);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_1.add(scrollPane, gbc_scrollPane);

		list = new JList<PriceObject>();
		scrollPane.setViewportView(list);

		txtTotal = new JTextField();
		txtTotal.setFont(new Font("Verdana", Font.BOLD, 20));
		txtTotal.setText("Total: \u00A30.00");
		txtTotal.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_txtTotal = new GridBagConstraints();
		gbc_txtTotal.fill = GridBagConstraints.BOTH;
		gbc_txtTotal.gridx = 0;
		gbc_txtTotal.gridy = 2;
		panel_1.add(txtTotal, gbc_txtTotal);
		txtTotal.setColumns(10);

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);

		gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		final Random random = new Random();

		btnAddItem = new JButton("Add Item");
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PriceObject po = new PriceObject(new DecimalFormat("##.##").format(random.nextDouble() + 3), "Item " + random.nextInt(), LIST_SIZE, list.getFont());
				addBoughtItem(po);
			}
		});
		GridBagConstraints gbc_btnAddItem = new GridBagConstraints();
		gbc_btnAddItem.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAddItem.gridx = 0;
		gbc_btnAddItem.gridy = 0;
		panel.add(btnAddItem, gbc_btnAddItem);

		panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridwidth = 2;
		gbc_panel_2.insets = new Insets(0, 0, 0, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		contentPane.add(panel_2, gbc_panel_2);

		gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0 };
		gbl_panel_2.rowHeights = new int[] { 0 };
		gbl_panel_2.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);
	}

	public void configureList() {
		LIST_SIZE = list.getBounds().getSize();
		LIST_FONT = list.getFont();
		PriceObject po = new PriceObject("10.00", "Thing", LIST_SIZE, list.getFont());
		addBoughtItem(po);
	}

	public static void addBoughtItem(PriceObject po) {
		pos.add(po);
		currentTotal += po.getPrice();
		updateList();
		updateTotal();
	}

	public static void updateTotal() {
		txtTotal.setText("Total: £" + currentTotal);
	}

	public static void updateList() {
		PriceObject[] posArray = new PriceObject[pos.size()];
		pos.toArray(posArray);
		list.setListData(posArray);
	}

}
