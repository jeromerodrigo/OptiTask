package optitask.store;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

import optitask.util.Settings;
import optitask.util.Statistics;
import optitask.util.Task;

public final class AppPersistence {
	private ArrayList<Object> list;
	private static final int TASKS_INDEX = 0;
	private static final int SETTINGS_INDEX = 1;
	private static final int STATS_INDEX = 2;
	private static String filename = "";
	
	public AppPersistence(String filename) {
		AppPersistence.filename = filename;		
		
		list = new ArrayList<Object>();
		list.add(TASKS_INDEX, new LinkedList<Task>()); //Index 0 stores the list of tasks
		list.add(SETTINGS_INDEX, new Settings()); //Index 1 stores the settings
		list.add(STATS_INDEX, new Statistics()); //Index 2 stores the statistics
	}
	
	@SuppressWarnings("unchecked")
	private void readData() {
		
		File file = new File(filename);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			writeObject(TASKS_INDEX, new LinkedList<Task>());
			writeObject(SETTINGS_INDEX, new Settings());
			writeObject(STATS_INDEX, new Statistics());
		} else {
			try { //Initialise input stream
				InputStream inFile = new FileInputStream(filename);
				InputStream inBuffer = new BufferedInputStream(inFile);
				ObjectInput in = new ObjectInputStream(inBuffer);
				
				try {
					list = (ArrayList<Object>)in.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					in.close();
				}
				
			} catch (IOException e) {
			}
		}		
	}
	
	private boolean writeObject(int index, Object obj) {
		boolean isSaved = false;
		readData();
		list.set(index, obj);
		
		try { //Initialise output stream
			OutputStream outFile = new FileOutputStream(filename);
			OutputStream outBuffer = new BufferedOutputStream(outFile);
			ObjectOutput out = new ObjectOutputStream(outBuffer);
			
			try {
				out.writeObject(list);
				isSaved = true;
			} catch (IOException e) {
				e.printStackTrace();
				isSaved = false;
			} finally {
				out.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return isSaved;	
	}
	
	private Object getObject(int index) {
		readData();
		return list.get(index);
	}
	
	public boolean saveTasks(LinkedList<Task> tasks) {
		return writeObject(TASKS_INDEX, tasks);
	}
	
	public boolean saveSettings(Settings settings) {
		return writeObject(SETTINGS_INDEX, settings);	
	}
	
	public boolean saveStats(Statistics stats) {
		return writeObject(STATS_INDEX, stats);
	}
	
	public Settings getSettings() {
		return (Settings) getObject(SETTINGS_INDEX);
	}
	
	@SuppressWarnings("unchecked")
	public LinkedList<Task> getTasks() {
		return (LinkedList<Task>) getObject(TASKS_INDEX);
	}
	
	public Statistics getStats() {
		return (Statistics) getObject(STATS_INDEX);	
	}
	
	public boolean createFile() {
		return false;
	
	}
	
	public boolean destroyFile() {
		File file = new File(filename);
		return file.delete();	
	}
}
