package optitask;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import optitask.ui.AboutDialog;
import optitask.ui.SettingsDialog;
import optitask.ui.TasksDialog;
import optitask.util.Settings;
import optitask.util.Statistics;
import optitask.util.Task;

public class AppController implements ActionListener {
	private optitask.store.AppPersistence model;
	private optitask.ui.AppFrame view;
	private SettingsDialog settingsDialog;
	private TasksDialog tasksDialog;
	private AboutDialog aboutDialog;

	public AppController(optitask.store.AppPersistence model,
			optitask.ui.AppFrame view) {
		this.model = model;
		this.view = view;
	}

	private void startTimer() {
		view.startTimer();
	}

	private void stopTimer() {
		view.stopTimer();
	}

	public Statistics getStats() {
		return model.getStats();
	}

	public Settings getSettings() {
		return model.getSettings();
	}

	private boolean saveSettings(optitask.util.Settings settings) {
		return model.saveSettings(settings);
	}

	@SuppressWarnings("unused")
	private boolean saveStats(optitask.util.Statistics stats) {
		return model.saveStats(stats);
	}

	private boolean saveTasks(LinkedList<Task> tasks) {
		return model.saveTasks(tasks);
	}

	private void openSettings() {
		settingsDialog = new SettingsDialog(model, this);
		settingsDialog.setLocationRelativeTo(view);
		settingsDialog.setVisible(true);
	}

	private void openManageTasks() {
		tasksDialog = new TasksDialog(model, this);
		tasksDialog.setLocationRelativeTo(view);
		tasksDialog.setVisible(true);
	}

	private void openAbout() {
		aboutDialog = new AboutDialog();
		aboutDialog.setLocationRelativeTo(view);
		aboutDialog.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		if (actionCommand.equalsIgnoreCase("Start")) {
			startTimer();
		} else if (actionCommand.equalsIgnoreCase("Stop")) {

			stopTimer();

		} else if (actionCommand.equalsIgnoreCase("Open Settings")) {

			openSettings();

		} else if (actionCommand.equalsIgnoreCase("Save Settings")) {

			saveSettings(settingsDialog.getSettings());
			JOptionPane.showMessageDialog(settingsDialog, "Settings saved!",
					"Notification", JOptionPane.INFORMATION_MESSAGE);

		} else if (actionCommand.equalsIgnoreCase("Default Settings")) {

			settingsDialog.setSettings(new Settings());

		} else if (actionCommand.equalsIgnoreCase("Toggle Increment")) {

			settingsDialog.setEnabledIncrementSettings(!settingsDialog
					.isEnabledIncrementSettings());

		} else if (actionCommand.equalsIgnoreCase("Open Manage Tasks")) {

			openManageTasks();

		} else if (actionCommand.equalsIgnoreCase("Add Task")) {

			tasksDialog.addTask();

		} else if (actionCommand.equalsIgnoreCase("Delete Task")) {

			tasksDialog.deleteTask();

		} else if (actionCommand.equalsIgnoreCase("Save Tasks")) {

			saveTasks(tasksDialog.getTasks());
			JOptionPane.showMessageDialog(tasksDialog, "Tasks saved!",
					"Notification", JOptionPane.INFORMATION_MESSAGE);

		} else if (actionCommand.equalsIgnoreCase("Move Up")) {

			tasksDialog.moveUp();

		} else if (actionCommand.equalsIgnoreCase("Move Down")) {

			tasksDialog.moveDown();

		} else if (actionCommand.equalsIgnoreCase("Open About")) {

			openAbout();

		}
	}
}
