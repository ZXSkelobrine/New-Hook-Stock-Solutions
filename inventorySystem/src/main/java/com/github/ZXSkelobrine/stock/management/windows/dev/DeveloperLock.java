package com.github.ZXSkelobrine.stock.management.windows.dev;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.github.ZXSkelobrine.stock.Launcher;
import com.github.ZXSkelobrine.stock.management.functions.windows.WindowFunctions;
import com.github.ZXSkelobrine.stock.management.windows.enums.Window;

public class DeveloperLock extends JFrame {

	public static final String CORRECT_STRING = "4862";
	private static final long serialVersionUID = 8375595426237906841L;
	private JPanel contentPane;
	private JTextField txtStars;
	private int currentKey = 0;
	private int[] correctValues = generateValues();
	private int[] currentValues = new int[4];
	private JButton btnLock1;
	private JButton btnLock0;
	private JButton btnLock2;
	private JButton btnLock3;
	private JButton btnLock4;
	private JButton btnLock5;
	private JButton btnLock6;
	private JButton btnLock7;
	private JButton btnLock8;
	private JButton btnLock9;

	/**
	 * Launch the application.
	 */
	public static void launchWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeveloperLock frame = new DeveloperLock();
					frame.setVisible(true);
				} catch (Exception e) {
					// Print a brief description of the error.
					System.out.println("[Launch Window (DeveloperLock)]: Error initializing and showing frame(Exception). Contact the author or run this program with -showSTs to print the stack traces.");
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

	private int[] generateValues() {
		int[] vals = new int[4];
		for (int i = 0; i < vals.length; i++) {
			vals[i] = Integer.parseInt(Character.toString(CORRECT_STRING.charAt(i)));
		}
		return vals;
	}

	/**
	 * Create the frame.
	 */
	public DeveloperLock() {
		WindowFunctions.applyWindowSettings(Window.LOCK, this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 338, 484);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtStars = new JTextField();
		txtStars.requestFocus();
		txtStars.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (Character.isDigit(e.getKeyChar())) {
					int val = Integer.parseInt(Character.toString(e.getKeyChar()));
					if (currentKey < 3) {
						txtStars.setText(txtStars.getText() + "*");
						currentValues[currentKey] = val;
						currentKey++;
					} else {
						txtStars.setText(txtStars.getText() + "*");
						currentValues[currentKey] = val;
						currentKey++;
						validateKey();
					}
				}
			}
		});
		txtStars.setEditable(false);
		txtStars.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtStars.setHorizontalAlignment(SwingConstants.CENTER);
		txtStars.setBounds(90, 28, 141, 32);
		contentPane.add(txtStars);
		txtStars.setColumns(10);

		btnLock0 = new JButton("0");
		btnLock0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currentKey < 3) {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock0.getName().substring(btnLock0.getName().length() - 1));
					currentKey++;
				} else {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock0.getName().substring(btnLock0.getName().length() - 1));
					currentKey++;
					validateKey();
				}
			}
		});
		btnLock0.setName("btnLock0");
		btnLock0.setBounds(126, 329, 70, 70);
		contentPane.add(btnLock0);

		btnLock1 = new JButton("1");
		btnLock1.setName("btnLock1");
		btnLock1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentKey < 3) {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock1.getName().substring(btnLock1.getName().length() - 1));
					currentKey++;
				} else {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock1.getName().substring(btnLock1.getName().length() - 1));
					currentKey++;
					validateKey();
				}
			}
		});
		btnLock1.setBounds(46, 86, 70, 70);
		contentPane.add(btnLock1);

		btnLock2 = new JButton("2");
		btnLock2.setName("btnLock2");
		btnLock2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currentKey < 3) {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock2.getName().substring(btnLock2.getName().length() - 1));
					currentKey++;
				} else {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock2.getName().substring(btnLock2.getName().length() - 1));
					currentKey++;
					validateKey();
				}
			}
		});
		btnLock2.setBounds(126, 86, 70, 70);
		contentPane.add(btnLock2);

		btnLock3 = new JButton("3");
		btnLock3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currentKey < 3) {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock3.getName().substring(btnLock3.getName().length() - 1));
					currentKey++;
				} else {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock3.getName().substring(btnLock3.getName().length() - 1));
					currentKey++;
					validateKey();
				}
			}
		});
		btnLock3.setName("btnLock3");
		btnLock3.setBounds(206, 86, 70, 70);
		contentPane.add(btnLock3);

		btnLock4 = new JButton("4");
		btnLock4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currentKey < 3) {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock4.getName().substring(btnLock4.getName().length() - 1));
					currentKey++;
				} else {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock4.getName().substring(btnLock4.getName().length() - 1));
					currentKey++;
					validateKey();
				}
			}
		});
		btnLock4.setName("btnLock4");
		btnLock4.setBounds(46, 167, 70, 70);
		contentPane.add(btnLock4);

		btnLock5 = new JButton("5");
		btnLock5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currentKey < 3) {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock5.getName().substring(btnLock5.getName().length() - 1));
					currentKey++;
				} else {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock5.getName().substring(btnLock5.getName().length() - 1));
					currentKey++;
					validateKey();
				}
			}
		});
		btnLock5.setName("btnLock5");
		btnLock5.setBounds(126, 167, 70, 70);
		contentPane.add(btnLock5);

		btnLock6 = new JButton("6");
		btnLock6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currentKey < 3) {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock6.getName().substring(btnLock6.getName().length() - 1));
					currentKey++;
				} else {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock6.getName().substring(btnLock6.getName().length() - 1));
					currentKey++;
					validateKey();
				}
			}
		});
		btnLock6.setName("btnLock6");
		btnLock6.setBounds(206, 167, 70, 70);
		contentPane.add(btnLock6);

		btnLock7 = new JButton("7");
		btnLock7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currentKey < 3) {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock7.getName().substring(btnLock7.getName().length() - 1));
					currentKey++;
				} else {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock7.getName().substring(btnLock7.getName().length() - 1));
					currentKey++;
					validateKey();
				}
			}
		});
		btnLock7.setName("btnLock7");
		btnLock7.setBounds(46, 248, 70, 70);
		contentPane.add(btnLock7);

		btnLock8 = new JButton("8");
		btnLock8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currentKey < 3) {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock8.getName().substring(btnLock8.getName().length() - 1));
					currentKey++;
				} else {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock8.getName().substring(btnLock8.getName().length() - 1));
					currentKey++;
					validateKey();
				}
			}
		});
		btnLock8.setName("btnLock8");
		btnLock8.setBounds(126, 248, 70, 70);
		contentPane.add(btnLock8);

		btnLock9 = new JButton("9");
		btnLock9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currentKey < 3) {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock9.getName().substring(btnLock9.getName().length() - 1));
					currentKey++;
				} else {
					txtStars.setText(txtStars.getText() + "*");
					currentValues[currentKey] = Integer.parseInt(btnLock9.getName().substring(btnLock9.getName().length() - 1));
					currentKey++;
					validateKey();
				}
			}
		});
		btnLock9.setName("btnLock9");
		btnLock9.setBounds(206, 248, 70, 70);
		contentPane.add(btnLock9);
	}

	protected void validateKey() {
		if (currentValues[0] == correctValues[0] && currentValues[1] == correctValues[1] && currentValues[2] == correctValues[2] && currentValues[3] == correctValues[3]) {
			DeveloperControls.launchWindow();
			dispose();
		} else {
			txtStars.setText("");
			currentKey = 0;
			currentValues[0] = currentValues[1] = currentValues[2] = currentValues[3] = 0;
		}
	}
}
