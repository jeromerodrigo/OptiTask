package optitask.ui;

import java.util.LinkedList;

import javax.swing.JPanel;

import optitask.store.AppPersistence;
import optitask.util.Task;

import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.SwingConstants;

public class CurrentTaskPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2030419465797237653L;
	private JLabel lblTask;
	private static LinkedList<Task> tasks;
	private static int currentIdx;
	private static AppPersistence model;
	private JLabel lblStatus;
	
	public static final int BREAK = 0;
	public static final int WORKING = 1;

	/**
	 * Create the panel.
	 */
	public CurrentTaskPanel() {
		initialize();
	}
	
	public CurrentTaskPanel(AppPersistence model) {
		tasks = model.getTasks();
		CurrentTaskPanel.model = model;
		currentIdx = 0;
		initialize();
		nextTask();
	}
	
	private void initialize() {
		setSize(320, 100);
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Current Task", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel.setBounds(9, 4, 302, 63);
		add(panel);
		panel.setLayout(null);
		
		lblTask = new JLabel("");
		lblTask.setBounds(20, 16, 272, 36);
		panel.add(lblTask);
		
		lblStatus = new JLabel("");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setBounds(10, 75, 300, 14);
		add(lblStatus);
	}
	
	public void nextTask() {
		if(currentIdx >= tasks.size()) {
			lblTask.setText("No tasks available...");
//			setVisible(false);
			return;
		}			
		
		if(tasks.get(currentIdx).isDone()) {
			currentIdx++;
			nextTask();
		} else {
			lblTask.setText(tasks.get(currentIdx).getTaskDesc());
//			setVisible(true);
		}
	}
	
	public void refresh() {
		tasks = model.getTasks();
		currentIdx = 0;
		nextTask();
	}
	
	public void markAsDone() {
		Task task = new Task();
		try {
			task = tasks.get(currentIdx);
		} catch (IndexOutOfBoundsException e) {
			return; //Do nothing
		}
		task.setDone(true);
		tasks.set(currentIdx, task);
		model.saveTasks(tasks);
	}
	
	public void setStatus(int status) {
		switch(status) {
		case BREAK:
			lblStatus.setText(getStatusString("green", "Break Time"));
			break;
		case WORKING:
			lblStatus.setText(getStatusString("red", "Working"));
			break;
			default:
				lblStatus.setText(null);
		}
	}
	
	private final String getStatusString(String color, String text) {		
		return "<html><b><font color=\"" + color + "\"> " + text + "</font></b></html>";
	}
}
