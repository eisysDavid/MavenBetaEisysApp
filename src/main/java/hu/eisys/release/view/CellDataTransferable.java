package hu.eisys.release.view;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;


public class CellDataTransferable implements Transferable {
	public static final DataFlavor CELL_DATA_FLAVOR = createConstant(CellData.class, "application/x-java-celldata");
	private CellData cellData;

	public CellDataTransferable(CellData cellData) {;
		this.cellData = cellData;
	}

	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { CELL_DATA_FLAVOR };
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		boolean supported = false;
		for (DataFlavor available : getTransferDataFlavors()) {
			if (available.equals(flavor)) {
				supported = true;
			}
		}
		return supported;
	}

	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		return cellData;
	}

	@SuppressWarnings("rawtypes")
	static protected DataFlavor createConstant(Class clazz, String name) {
		try {
			return new DataFlavor(clazz, name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
