package optitask;

import optitask.store.AppPersistence;
import optitask.ui.AppFrame;


public class Main {
	
	private static AppPersistence model;
	private static AppFrame view;
	
	
	public static void main(String[] args) {
		model = new AppPersistence("optitask.dat");
		view = new AppFrame(model);
		view.setVisible(true);
	}
}
