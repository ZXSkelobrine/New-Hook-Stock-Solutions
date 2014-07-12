package com.github.ZXSkelobrine.stock.windows.notifications;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.github.ZXSkelobrine.stock.updates.UpdateManager;

import javax.swing.JScrollPane;

public class NotificationWindow extends JFrame {

	private static final long serialVersionUID = -1516594750840058499L;
	private JPanel contentPane;
	private ButtonTypes bt;
	private boolean useExternalListener = false;
	private ActionListener okay, yes, no;

	public enum ButtonTypes {
		OKAY_ONLY, YES_NO;
	}

	/**
	 * Launch the application.
	 */
	public static void launchWindow(final ButtonTypes buttons, final String info) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NotificationWindow frame = new NotificationWindow(info);
					frame.bt = buttons;
					frame.setupButtons();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Launch the application.
	 */
	public static void launchWindow(final ButtonTypes buttons, final String info, final ActionListener okay, final ActionListener yes, final ActionListener no) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NotificationWindow frame = new NotificationWindow(info);
					frame.okay = okay;
					frame.yes = yes;
					frame.no = no;
					frame.bt = buttons;
					frame.useExternalListener = true;
					frame.setupButtons();
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
	public NotificationWindow(String info) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 315, 135);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 22, 299, 57);
		contentPane.add(scrollPane);

		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setText(info);
		textPane.setEditable(false);

		JLabel lblError = new JLabel("Notification");
		lblError.setBounds(5, 5, 299, 17);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblError);

	}

	public void setupButtons() {
		switch (bt) {
		case OKAY_ONLY:
			JButton btnOkay = new JButton("Okay");
			if (useExternalListener) {
				btnOkay.addActionListener(okay);
			} else {
				btnOkay.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
			}
			btnOkay.setBounds(5, 84, 299, 23);
			contentPane.add(btnOkay);
			break;
		case YES_NO:
			JButton btnDismiss = new JButton("Yes");
			btnDismiss.setBounds(5, 79, 149, 23);
			if (useExternalListener) {
				btnDismiss.addActionListener(yes);
			} else {
				btnDismiss.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							UpdateManager.startUpdate();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
			}
			contentPane.add(btnDismiss);

			JButton btnNo = new JButton("No");
			if (useExternalListener) {
				btnNo.addActionListener(no);
			} else {
				btnNo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
			}
			btnNo.setBounds(155, 79, 149, 23);
			contentPane.add(btnNo);
			break;

		}
	}
}
