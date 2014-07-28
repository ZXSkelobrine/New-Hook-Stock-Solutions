package com.github.ZXSkelobrine.stock.management.windows.errors;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import com.github.ZXSkelobrine.stock.Launcher;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ErrorWindow extends JFrame {

	private static final long serialVersionUID = -2745743349108640188L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void launchWindow(final String error) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ErrorWindow frame = new ErrorWindow(error);
					frame.setVisible(true);
				} catch (Exception e) {
					// Print a brief description of the error.
					System.out.println("[Launch Window (Error Window)]: Error initializing and showing frame(Exception). Contact the author or run this program with -showSTs to print the stack traces.");
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
	public ErrorWindow(String error) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 315, 135);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTextPane textPane = new JTextPane();
		textPane.setText(error);
		textPane.setEditable(false);
		contentPane.add(textPane, BorderLayout.CENTER);

		JLabel lblError = new JLabel("Error");
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblError, BorderLayout.NORTH);

		JButton btnDismiss = new JButton("Dismiss");
		btnDismiss.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(btnDismiss, BorderLayout.SOUTH);
	}

}
