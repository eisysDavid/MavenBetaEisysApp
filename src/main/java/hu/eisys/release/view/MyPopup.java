package hu.eisys.release.view;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.JTextComponent;

import hu.eisys.release.model.StepNames;
import hu.eisys.release.presenter.*;

public class MyPopup {

	private IBundle res;
	
	public MyPopup(IBundle r) {
		res = r;
		menuItems = new JMenuItem[]{
				new JMenuItem(res.getString("dataBase")),
				new JMenuItem(res.getString("adhock")),
				new JMenuItem(res.getString("remove")),
				new JMenuItem(res.getString("modify"))
				};
	}
	
	private JPopupMenu popUp;
	JMenuItem[] menuItems;

	public void display(Container cont, IMyPopup ipopUp, StepNames stepType) {
		popUp = new JPopupMenu();
		popUp.add(menuItems[0]);
		popUp.add(menuItems[1]);
		popUp.add(menuItems[2]);

		if (stepType.equals(StepNames.DBCHANGE) || stepType.equals(StepNames.ADHOCK)) {
			popUp.add(menuItems[3]);
		}

		menuItems[0].addActionListener(new MyDBChangeListener());
		menuItems[1].addActionListener(new MyAdhockListener());
		menuItems[2].addActionListener(new MyRemoveListener());
		menuItems[3].addActionListener(new MyModifyListener());
		this.ipopUp = ipopUp;
		this.stepType = stepType;
		popUp.show(cont, cont.getX(), cont.getY());
	}

	private class MyDBChangeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			frame = new JFrame(res.getString("frameDb"));
			dbNameL = new JLabel(res.getString("dbNameL"));
			userNameL = new JLabel(res.getString("userNameL"));
			passwordL = new JLabel(res.getString("passwordL"));
			dbName = new JTextField(15);
			userName = new JTextField(15);
			password = new JTextField(15);
			save = new JButton("Save");

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
			frame.getContentPane().add(save, grid);

			frame.pack();

			save.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if ((!dbName.getText().equals("") && (!userName.getText().equals("")))) {
						ipopUp.dbChangeEvent(dbName.getText(), userName.getText(), password.getText());
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
		}
	}

	private class MyRemoveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ipopUp.removeElementEvent();
		}
	}

	private class MyAdhockListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			frame = new JFrame(res.getString("frameAd"));
			scriptName = new JTextField(20);
			myScript = new JTextPane();
			myScript.setFont(new Font("Consolas", Font.PLAIN, 14));
			JScrollPane scrollPane = new JScrollPane(myScript);
			scriptNameL = new JLabel(res.getString("scriptNameL"));
			myScriptL = new JLabel(res.getString("myScriptL"));
			save = new JButton(res.getString("save"));
			frame.setVisible(true);
			frame.setSize(500, 500);
			frame.setLocationRelativeTo(null);

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
			frame.add(save, grid);

			save.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (((!scriptName.getText().equals("")) && (!myScript.getText().equals("")))) {
						ipopUp.addScriptEvent(scriptName.getText(), myScript.getText());
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

	private class MyModifyListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new ModifyScript().onlyModify(new IModifyScript() {

				public void modifyScriptEvent(String scriptName, String myScript) {
					ipopUp.modifyScriptEvent(scriptName, myScript);
				}

				public Object modifyEvent() {
					return ipopUp.modifyEvent();
				}

				public void modifyDbChangeEvent(String dbName, String userName, String password) {
					ipopUp.modifyDbChangeEvent(dbName, userName, password);
				}

				public void removeElementEvent() {
					ipopUp.removeElementEvent();
				}
			}, stepType, res);
		}
	}

	private JFrame frame;
	private JTextField dbName, userName, password, scriptName;
	private JTextComponent myScript;
	private JLabel dbNameL, userNameL, passwordL, scriptNameL, myScriptL;
	private JButton save;
	private GridBagConstraints grid;
	private IMyPopup ipopUp;
	private Insets insets;
	private StepNames stepType;
}
