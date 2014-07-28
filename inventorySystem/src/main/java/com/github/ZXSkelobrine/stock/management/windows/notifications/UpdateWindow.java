package com.github.ZXSkelobrine.stock.management.windows.notifications;

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

import com.github.ZXSkelobrine.stock.Launcher;
import com.github.ZXSkelobrine.stock.global.updates.UpdateManager;

public class UpdateWindow extends JFrame {

	private static final long serialVersionUID = -2745743349108640188L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void launchWindow(final String info) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateWindow frame = new UpdateWindow(info);
					frame.setVisible(true);
				} catch (Exception e) {
					// Print a brief description of the error.
					System.out.println("[Launch Window (Update Window)]: Error initializing and showing frame(Exception). Contact the author or run this program with -showSTs to print the stack traces.");
					// If it does not print the stack trace for error logging if
					// it
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
	public UpdateWindow(String info) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 315, 135);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextPane textPane = new JTextPane();
		textPane.setBounds(5, 22, 299, 57);
		textPane.setText(info);
		textPane.setEditable(false);
		contentPane.add(textPane);

		JLabel lblError = new JLabel("Update");
		lblError.setBounds(5, 5, 299, 17);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblError);

		JButton btnDismiss = new JButton("Yes");
		btnDismiss.setBounds(5, 79, 149, 23);
		btnDismiss.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					UpdateManager.startUpdate();
				} catch (IOException e1) {
					// Print a brief description of the error.
					System.out.println("[Button Press (Update Window)]: Error updating(IOException). Contact the author or run this program with -showSTs to print the stack traces.");
					// If it does not print the stack trace for error logging if
					// it
					// is enabled.
					if (Launcher.PRINT_STACK_TRACES) {
						e1.printStackTrace();
					}
				}
			}
		});
		contentPane.add(btnDismiss);

		JButton btnNo = new JButton("No");
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnNo.setBounds(155, 79, 149, 23);
		contentPane.add(btnNo);
	}
}
