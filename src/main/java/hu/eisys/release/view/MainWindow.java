package hu.eisys.release.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.inject.Inject;
import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.eisys.release.presenter.IBundle;
import hu.eisys.release.presenter.ViewComponent;


@SuppressWarnings("serial")
public class MainWindow extends ViewComponent implements IMainWindow {

	private final Logger LOGGER = LoggerFactory.getLogger(MainWindow.class);

	@Inject
	private IBundle res;

	public void displayViewFrame() {
		initcomponent();
		actions();
		setFont();
		setVisible(true);
		LOGGER.info("Frame has started");
	}

	public String getPath() {
		return stringPath.getText();
	}

	public boolean isChchboxSelected() {
		return checkBox.isSelected();
	}

	public String getTreeElement() {
		return treeView.getSelectionPath() != null ? treeView.getSelectionPath().toString() : null;
	}

	public int getTableRow() {
		return tableView.getSelectedRow();
	}

	public int getTableColumn() {
		return tableView.getSelectedColumn();
	}

	public int[] getMultyTableRow() {
		return tableView.getSelectedRows();
	}

	// Inicializ�l�s
	public void initcomponent() {
		// p�ld�nyos�t�s
		panel = new JPanel();
		basicPanel = new JPanel();
		topLeft = new JPanel();
		topRight = new JPanel();
		pathWindow = new JButton(res.getString("pathWindow"));
		generateJar = new JButton(res.getString("generateJar"));
		stringPath = new JTextField(20);
		tableView = new JTable();
		treeView = new JTree(new DefaultMutableTreeNode(null));
		treePanel = new JScrollPane(treeView);
		tablePanel = new JScrollPane(tableView);
		checkBox = new JCheckBox(res.getString("checkBox"));
		selectPath = new JLabel(res.getString("selectPath"));
		tableView.setDragEnabled(true);
		tableView.setDropMode(DropMode.ON);
		tableView.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableView.setCellSelectionEnabled(true);
		tableView.setTransferHandler(new MyTransferHandler(new IMyTransferHandler() {
			public void updateArrayList(int dropRow) {
				fireViewEvent(ON_DND, getMultyTableRow(), dropRow);
			}
		}));

		// kin�zet fel�p�t�se
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(1000, 600);
		setLocationRelativeTo(null);

		// Panelek be�ll�t�sa
		// Top left Panel
		Insets ins = new Insets(5, 0, 5, 5);
		GridBagConstraints grid = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, ins, 0, 0);
		topLeft.setLayout(new GridBagLayout());
		topLeft.setBorder(BorderFactory.createTitledBorder(BorderFactory.createDashedBorder(Color.DARK_GRAY, 3, 3),
				res.getString("treeControler")));
		topLeft.add(selectPath, grid);
		grid = new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, ins, 0,
				0);
		topLeft.add(pathWindow, grid);
		grid = new GridBagConstraints(2, 0, 2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, ins, 0,
				0);
		topLeft.add(stringPath, grid);
		grid = new GridBagConstraints(4, 0, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, ins, 0,
				0);
		topLeft.add(checkBox, grid);

		// Top right Panel
		topRight.setLayout(new GridBagLayout());
		topRight.setBorder(BorderFactory.createTitledBorder(BorderFactory.createDashedBorder(Color.DARK_GRAY, 3, 3),
				res.getString("tableControler")));
		ins = new Insets(10, 10, 10, 10);
		grid = new GridBagConstraints(0, 0, 1, 1, .5, .5, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, ins,
				0, 0);
		topRight.add(generateJar, grid);

		// Basic panel be�ll�t�sa
		GroupLayout basicLayout = new GroupLayout(basicPanel);
		basicLayout.setAutoCreateGaps(true);
		basicLayout.setAutoCreateContainerGaps(true);
		basicPanel.setLayout(basicLayout);
		basicLayout.setHorizontalGroup(basicLayout.createSequentialGroup()

				.addGroup(basicLayout.createParallelGroup().addComponent(topLeft).addComponent(treePanel))
				.addGroup(basicLayout.createParallelGroup().addComponent(topRight).addComponent(tablePanel)));

		basicLayout.setVerticalGroup(basicLayout.createParallelGroup()
				.addGroup(basicLayout.createSequentialGroup().addComponent(topLeft).addComponent(treePanel))
				.addGroup(basicLayout.createSequentialGroup().addComponent(topRight).addComponent(tablePanel)));

		basicLayout.linkSize(SwingConstants.HORIZONTAL, topLeft, topRight);
		basicLayout.linkSize(SwingConstants.VERTICAL, topLeft, topRight);
		panel.setLayout(new BorderLayout());
		panel.add(basicPanel);
		getContentPane().add(panel);

	}

	public void setFont() {
		pathWindow.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		generateJar.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		stringPath.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		checkBox.setFont(new Font("Times New Roman", Font.BOLD, 16));
		selectPath.setFont(new Font("Times New Roman", Font.BOLD, 16));
		pack();
	}

	public void actions() {

		// Set treeModel
		stringPath.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				fireViewEvent(SET_TREEMODEL, getPath(), isChchboxSelected(), stringPath, e.getKeyCode(), e.getID());
			}
		});

		pathWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireViewEvent(SET_TREEMODEL, null, isChchboxSelected(), stringPath, THROWINVALIDEVENTNUMBER, e.getID());
			}
		});

		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireViewEvent(SET_TREEMODEL, getPath(), isChchboxSelected(), stringPath, CHECKBOX_NUMBER, e.getID());
			}
		});

		// Add element to the table
		treeView.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				try {
					fireViewEvent(ON_UPLOADTABLE, e.getKeyCode(), e.getID(), getTreeElement(), getTableRow(),
							treeView.getLeadSelectionRow());
				} catch (Exception ex) {
					LOGGER.error("No args \n", ex);
				}
			}
		});

		treeView.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				fireViewEvent(ON_TREEPOPUP, e.getModifiers(), InputEvent.BUTTON3_MASK, treeView.getLeadSelectionRow());
			}
		});

		treeView.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				fireViewEvent(ON_UPLOADTABLE, e.getClickCount(), e.getID(), getTreeElement(), getTableRow(),
						treeView.getLeadSelectionRow());
			}
		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				fireViewEvent(ON_EXIT, null);
			}
		});

		tablePanel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				fireViewEvent(ON_POPUP, e.getModifiers(), InputEvent.BUTTON3_MASK, tableView, getTableRow(),
						getMultyTableRow());
			}
		});

		tableView.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				fireViewEvent(ON_POPUP, e.getModifiers(), InputEvent.BUTTON3_MASK, tableView, getTableRow(),
						getMultyTableRow());
			}
		});

		tableView.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				fireViewEvent(ON_REMOVE, e.getKeyCode(), getMultyTableRow());
			}
		});

		tableView.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				fireViewEvent(ON_MODIFYENTER, e.getKeyCode(), getTableRow(), getTableColumn());
			}
		});

		tableView.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				fireViewEvent(ON_MODIFYDOUBLECLICK, e.getClickCount(), e.getID(), getTableRow(), getTableColumn(),
						getMultyTableRow());
			}
		});

		tableView.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				fireViewEvent(ON_COMBOKEYMOVE, e.getKeyCode(), getMultyTableRow(), getTableColumn(), e.getID());
			}

			public void keyReleased(KeyEvent e) {
				fireViewEvent(ON_COMBOKEYMOVE, e.getKeyCode(), getMultyTableRow(), getTableColumn(), e.getID());
			}

		});

		tableView.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					tableView.getModel().addTableModelListener(new TableModelListener() {
						public void tableChanged(TableModelEvent e) {
							try {
								fireViewEvent(ON_SETCOMMENT,
										tableView.getValueAt(getTableRow(), getTableColumn()).toString(), getTableRow(),
										getTableColumn());
							} catch (Exception exc) {
								// LOGGER.error("Hib�s param�ter",exc);
							}
						}
					});
				}
			}
		});

	}

	public void setStringPath(String path) {
		stringPath.setText(path);
	}

	public void setTreeModel(TreeModel treeModel) {
		treeView.setModel(treeModel);
	}

	public void setTreeCellRenderel(DefaultTreeCellRenderer myRenderer) {
		treeView.setCellRenderer(myRenderer);
	}

	public void setTableMode(TableModel myTableModel) {
		tableView.setModel(myTableModel);
	}

	public void setTableCellRenderel(DefaultTableCellRenderer myTableRenderer) {
		tableView.setDefaultRenderer(JLabel.class, myTableRenderer);
	}

	public void systemExit() {
		System.exit(0);
	}

	public void setTableSelection(int index) {
		tableView.changeSelection(index, 1, true, false);
	}

	public void expandTreeNodes(int startingIndex) {
		for (int i = startingIndex; i < treeView.getRowCount(); ++i) {
			treeView.expandRow(i);
		}

		if (treeView.getRowCount() != treeView.getRowCount()) {
			expandTreeNodes(treeView.getRowCount());
		}
	}

	public void collapseTreeNodes(int treePath) {
		for (int i = treePath; i < treeView.getRowCount(); ++i) {
			treeView.collapseRow(i);
		}

		if (treeView.getRowCount() != treeView.getRowCount()) {
			collapseTreeNodes(treeView.getRowCount());
		}
	}

	public void setTreePopup(JPopupMenu myPop) {
		treeView.setComponentPopupMenu(myPop);
	}

	public void setTreeSelection(int position) {
		treeView.setSelectionRow(position);
	}

	private JButton pathWindow, generateJar;
	private JTextField stringPath;
	private JTable tableView;
	private JTree treeView;
	private JScrollPane treePanel, tablePanel;
	private JCheckBox checkBox;
	private JLabel selectPath;
	private JPanel panel, basicPanel, topLeft, topRight;
}

