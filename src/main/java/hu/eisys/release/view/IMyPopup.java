package hu.eisys.release.view;



public interface IMyPopup extends IModifyScript{
	public void dbChangeEvent(String dbName,String userName,String password);
	public void addScriptEvent(String scriptName,String myScript);
}
