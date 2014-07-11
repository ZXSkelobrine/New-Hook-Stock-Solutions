package com.github.ZXSkelobrine.stock.windows.dev;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.ZXSkelobrine.stock.Launcher;
import com.github.ZXSkelobrine.stock.functions.commands.CommandFunctions;
import com.github.ZXSkelobrine.stock.functions.errors.ErrorLogFunctions;
import com.github.ZXSkelobrine.stock.functions.windows.WindowFunctions;
import com.github.ZXSkelobrine.stock.interfaces.AddUpdate;
import com.github.ZXSkelobrine.stock.variables.ErrorLogItem;
import com.github.ZXSkelobrine.stock.windows.enums.Window;

public class DeveloperControls extends JFrame {

	private static final long serialVersionUID = -4234776206932000778L;
	private JPanel contentPane;
	private JTextField txtCommand;
	private JCheckBox chckbxKeepErrorLog;
	private JCheckBox chckbxDiagnoseSqlStatements;
	private JTextPane txtpnCompletelog;
	private JScrollPane scrollPane;
	private JButton btnRunCommand;
	private JScrollPane scrollPane_1;
	private JTextPane txtpnErrorLog;

	/**
	 * Launch the application.
	 */
	public static void launchWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeveloperControls frame = new DeveloperControls();
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
	public DeveloperControls() {
		WindowFunctions.applyWindowSettings(Window.CONTROLS, this);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 509);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ErrorLogFunctions.setUpdateListener(new AddUpdate() {

			@Override
			public void update() {
				updateErrorLog();
			}
		});
		ErrorLogFunctions.setErrorLogActive(true);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 324, 132);
		contentPane.add(scrollPane);

		txtpnCompletelog = new JTextPane();
		txtpnCompletelog.setEditable(false);
		scrollPane.setViewportView(txtpnCompletelog);

		chckbxDiagnoseSqlStatements = new JCheckBox("Manual SQL Repair");
		chckbxDiagnoseSqlStatements.setBounds(8, 295, 328, 23);
		contentPane.add(chckbxDiagnoseSqlStatements);

		chckbxKeepErrorLog = new JCheckBox("Keep error log active");
		chckbxKeepErrorLog.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ErrorLogFunctions.setPersistantErrorEnabled(chckbxKeepErrorLog.isSelected());
			}
		});
		chckbxKeepErrorLog.setBounds(8, 321, 328, 23);
		contentPane.add(chckbxKeepErrorLog);

		JButton btnDumbErrors = new JButton("Dump Errors");
		btnDumbErrors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calendar c = Calendar.getInstance();
				File output = new File(Launcher.BASE_SAVE_PATH + "/logs/error/log--" + new SimpleDateFormat("dd.MM.yy--HH.mm.ss").format(c.getTime()) + ".txt");
				try {
					if (!output.getParentFile().exists()) output.getParentFile().mkdirs();
					if (!output.exists()) output.createNewFile();
					FileOutputStream fos;
					fos = new FileOutputStream(output, true);
					PrintWriter pw = new PrintWriter(fos);
					for (ErrorLogItem item : ErrorLogFunctions.getItems()) {
						pw.append(item.getCompleteOutput(true));
					}
					pw.close();
					fos.close();
				} catch (IOException e1) {
					ErrorLogFunctions.addItem(new ErrorLogItem("DeveloperControl", "ActionPerformed", "Output error", e1.getMessage(), 100, e1.getStackTrace()));
				}
			}
		});
		btnDumbErrors.setBounds(8, 351, 121, 31);
		contentPane.add(btnDumbErrors);
		txtCommand = new JTextField();
		txtCommand.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtCommand.setBounds(8, 393, 328, 31);
		contentPane.add(txtCommand);
		txtCommand.setColumns(10);

		btnRunCommand = new JButton("Run Command");
		btnRunCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CommandFunctions.runCommand(txtCommand.getText());
				txtCommand.setText("");
			}
		});
		btnRunCommand.setBounds(8, 435, 121, 31);
		contentPane.add(btnRunCommand);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 154, 322, 130);
		contentPane.add(scrollPane_1);

		txtpnErrorLog = new JTextPane();
		scrollPane_1.setViewportView(txtpnErrorLog);
		txtpnErrorLog.setEditable(false);
	}

	@Override
	public void dispose() {
		ErrorLogFunctions.setErrorLogActive(false);
		super.dispose();
	}

	public void updateErrorLog() {
		StringBuilder sb = new StringBuilder();
		for (ErrorLogItem s : ErrorLogFunctions.getItems()) {
			sb.append(s.getCompleteOutput(false) + "\n");
		}
		txtpnCompletelog.setText(sb.toString());
	}
}
