package hu.eisys.release.view;

import javax.swing.JTable;

public class CellData {
	
	private  JTable table;


	public CellData(JTable source) {
		this.table = source;
	}
	
	public CellData() {
	}

	public JTable getTable() {
		return table;
	}

	
}
