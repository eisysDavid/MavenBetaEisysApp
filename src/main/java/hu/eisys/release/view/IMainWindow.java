package hu.eisys.release.view;

import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;

public interface IMainWindow {

	public void displayViewFrame();

	public void setStringPath(String path);

	public void setTableMode(TableModel myTableModel);

	public void setTableCellRenderel(DefaultTableCellRenderer myTableRenderer);

	public void setTableSelection(int index);

	public void systemExit();

	public void expandTreeNodes(int start);

	public void collapseTreeNodes(int treePath);

	public void setTreePopup(JPopupMenu myPop);

	public void setTreeSelection(int select);

	public void setTreeModel(TreeModel myTreeModel);

	public void setTreeCellRenderel(DefaultTreeCellRenderer myTreeRenderer);
	
	// Event azonos�t�k
	final int KEY_PRESSED = 401;
	final int KEY_RELEASE = 402;
	final int ACTION_LISTENER = 1001;
	final int THROWINVALIDEVENTNUMBER = 989124412;
	final int CHECKBOX_NUMBER = 99;
	final int MOUSE_LISTENER = 501;

	// Event kezel�se
	String SET_TREEMODEL = "SetTreeModelEvent";
	String ON_UPLOADTABLE = "UploadTableEvent";
	String ON_EXIT = "OnExitPressedEvent";
	String ON_POPUP = "OnPopupEvent";
	String ON_REMOVE = "OnRemoveEvent";
	String ON_MODIFYENTER = "OnModifyEnterEvent";
	String ON_MODIFYDOUBLECLICK = "OnModifyDoubleClickEvent";
	String ON_SETCOMMENT = "OnSetCommentEvent";
	String ON_COMBOKEYMOVE = "OnComboKeyMoveEvent";
	String ON_DND = "OnDNDEvent";
	String ON_TREEPOPUP = "OnTreePopupEvent";
}
