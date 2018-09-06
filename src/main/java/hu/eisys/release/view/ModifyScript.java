package hu.eisys.release.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.JTextComponent;

import hu.eisys.release.model.*;
import hu.eisys.release.presenter.IBundle;
import hu.eisys.release.presenter.IConstans;

public class ModifyScript {

	public void onlyModify(IModifyScript ipopUpModify, StepNames stepType, IBundle res) {
		this.ipopUpModify = ipopUpModify;
		if (stepType.equals(StepNames.DBCHANGE) || stepType.equals(StepNames.ADHOCK)) {
			onlyModify(res);
		} else {
			ipopUpModify.removeElementEvent();
		}

	}

	public void onlyModify(IBundle res) {

		if (ipopUpModify.modifyEvent() instanceof DbChange) {
			DbChange myDbChange = (DbChange) ipopUpModify.modifyEvent();
			frame = new JFrame(res.getString("frameDb"));
			dbNameL = new JLabel(res.getString("dbNameL"));
			userNameL = new JLabel(res.getString("userNameL"));
			passwordL = new JLabel(res.getString("passwordL"));
			dbName = new JTextField(20);
			userName = new JTextField(20);
			password = new JTextField(20);
			modify = new JButton(res.getString("modify"));
			dbName.setText(myDbChange.getDbName());
			userName.setText(myDbChange.getUserName());
			password.setText(myDbChange.getPassword());

			frame.setVisible(true);
			frame.setSize(500, 500);
			frame.setLocationRelativeTo(null);
			frame.setLayout(new GridBagLayout());

			Insets insets = new Insets(10, 10, 10, 10);
			grid = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, insets, 0, 0);
			frame.getContentPane().add(dbNameL, grid);

			grid = new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, insets, 0, 0);
			frame.getContentPane().add(dbName, grid);

			grid = new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, insets, 0, 0);
			frame.getContentPane().add(userNameL, grid);

			grid = new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, insets, 0, 0);
			frame.getContentPane().add(userName, grid);

			grid = new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, insets, 0, 0);
			frame.getContentPane().add(passwordL, grid);

			grid = new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, insets, 0, 0);
			frame.getContentPane().add(password, grid);

			grid = new GridBagConstraints(0, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, insets, 0, 0);
			frame.getContentPane().add(modify, grid);
			frame.pack();
			modify.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if ((!dbName.getText().equals(IConstans.EMPTY_STRING)
							&& (!userName.getText().equals(IConstans.EMPTY_STRING)))) {
						ipopUpModify.modifyDbChangeEvent(dbName.getText(), userName.getText(), password.getText());
						frame.dispose();
					}

					else {
						int dialogButton = JOptionPane.showConfirmDialog(null,
								res.getString("noDB"),
								res.getString("noDBChange"), JOptionPane.OK_CANCEL_OPTION);
						if (dialogButton == JOptionPane.YES_OPTION) {
							frame.dispose();
						}
					}
				}
			});

		} else {
			Adhock myAdhock = (Adhock) ipopUpModify.modifyEvent();
			frame = new JFrame(res.getString("frameAd"));
			scriptName = new JTextField(20);
			myScript = new JTextPane();
			myScript.setFont(new Font("Consolas", Font.PLAIN, 14));
			JScrollPane scrollPane = new JScrollPane(myScript);
			scriptNameL = new JLabel(res.getString("scriptNameL"));
			myScriptL = new JLabel(res.getString("myScriptL"));
			modify = new JButton(res.getString("modify"));
			frame.setVisible(true);
			frame.setSize(500, 500);
			frame.setLocationRelativeTo(null);
			myScript.setText(myAdhock.getScript());
			scriptName.setText(myAdhock.getScriptName());
			insets = new Insets(5, 5, 5, 5);
			frame.setLayout(new GridBagLayout());
			grid = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
					insets, 0, 0);

			frame.add(scriptNameL, grid);

			grid = new GridBagConstraints(1, 0, 1, 1, 0.5, 0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
					insets, 0, 0);
			frame.add(scriptName, grid);

			grid = new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
					insets, 0, 0);
			frame.add(myScriptL, grid);

			grid = new GridBagConstraints(0, 2, 2, 2, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					insets, 0, 0);
			frame.add(scrollPane, grid);

			grid = new GridBagConstraints(0, 4, 2, 2, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
					insets, 5, 6);
			frame.add(modify, grid);

			modify.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (((!scriptName.getText().equals(IConstans.EMPTY_STRING))
							&& (!myScript.getText().equals(IConstans.EMPTY_STRING)))) {
						ipopUpModify.modifyScriptEvent(scriptName.getText(), myScript.getText());
						frame.dispose();
					}

					else {
						int dialogButton = JOptionPane.showConfirmDialog(null,
								res.getString("noScript"),
								res.getString("noScriptChange"), JOptionPane.OK_CANCEL_OPTION);
						if (dialogButton == JOptionPane.YES_OPTION) {
							frame.dispose();
						}
					}
				}
			});
		}
	}

	private JFrame frame;
	private JTextField dbName, userName, password, scriptName;
	private JTextComponent myScript;
	private JLabel dbNameL, userNameL, passwordL, scriptNameL, myScriptL;
	private JButton modify;
	private GridBagConstraints grid;
	private IModifyScript ipopUpModify;
	private Insets insets;

}
