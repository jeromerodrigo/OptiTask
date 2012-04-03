package optitask.ui;

import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import optitask.store.AppPersistence;
import optitask.util.Task;

public class CurrentTaskPanel extends JPanel {

	private static final long serialVersionUID = -2030419465797237653L;
	private JLabel lblTask;
	private static LinkedList<Task> tasks;
	private static int currentIdx;
	private static AppPersistence model;
	private JLabel lblStatus;

	public static final int NULL = 0;
	public static final int SHORTBREAK = 1;
	public static final int WORKING = 2;
	public static final int LONGBREAK = 3;

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
		panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Current Task",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));
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
		if (currentIdx >= tasks.size()) {
			lblTask.setText("No tasks available...");
			return;
		}

		if (tasks.get(currentIdx).isDone()) {
			currentIdx++;
			nextTask();
		} else {
			lblTask.setText(tasks.get(currentIdx).getTaskDesc());
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
			return; // Do nothing
		}
		task.setDone(true);
		tasks.set(currentIdx, task);
		model.saveTasks(tasks);

		lblTask.setText(lblTask.getText() + " [DONE]");
	}

	public void setStatus(int status) {
		switch (status) {
		case NULL:
			lblStatus.setText(getHtmlColoredString("black", "Idle"));
			break;
		case SHORTBREAK:
			lblStatus.setText(getHtmlColoredString("green", "Short Break"));
			break;
		case LONGBREAK:
			lblStatus.setText(getHtmlColoredString("green", "Long Break"));
			break;
		case WORKING:
			lblStatus.setText(getHtmlColoredString("red", "Working"));
			break;
		default:
			lblStatus.setText(null);
		}
	}

	private final String getHtmlColoredString(String color, String text) {
		return "<html><b><font color=\"" + color + "\"> " + text
				+ "</font></b></html>";
	}
}
