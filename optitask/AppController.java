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

/**
 * AppController.java <br />
 * Purpose: Listens to all events generated by the user interface.
 * @author Jerome
 * @version 0.8
 * @since 0.8
 */

public class AppController implements ActionListener {

    /** Stores the reference to the application persistence module. */
    private final optitask.store.AppPersistence model;

    /** Stores the reference to the main user interface. */
    private final optitask.ui.AppFrame view;

    /** The settings dialog user interface object. */
    private SettingsDialog settingsDialog;

    /** The tasks dialog user interface object. */
    private TasksDialog tasksDialog;

    /** The about dialog user interface object. */
    private AboutDialog aboutDialog;

    /**
     * Creates the controller object.
     * @param persistence the persistence module
     * @param frame the user interface
     */

    public AppController(final optitask.store.AppPersistence persistence,
            final optitask.ui.AppFrame frame) {
        model = persistence;
        view = frame;
    }

    /**
     * Invokes the user interface to start the timer.
     */

    private void startTimer() {
        view.startTimer();
    }

    /**
     * Invokes the user interface to stop the timer.
     */

    private void stopTimer() {
        view.stopTimer();
    }

    /**
     * Gets the statistics from file.
     * @return the statistics object
     */

    public final Statistics getStats() {
        return model.getStats();
    }

    /**
     * Gets the settings from file.
     * @return the settings object
     */

    public final Settings getSettings() {
        return model.getSettings();
    }

    /**
     * Saves the settings to the file.
     * @param settings the settings object
     * @return <code>true</code> if settings successfully saved;
     *         <code>false</code>, if otherwise.
     */

    private boolean saveSettings(final optitask.util.Settings settings) {
        return model.saveSettings(settings);
    }

    /**
     * Saves the statistics to the file.
     * @param stats the statistics object
     * @return <code>true</code> if settings successfully saved;
     *         <code>false</code>, if otherwise.
     */

    @SuppressWarnings("unused")
    private boolean saveStats(final optitask.util.Statistics stats) {
        return model.saveStats(stats);
    }

    /**
     * Saves the tasks to the file.
     * @param tasks a list of tasks to be saved
     * @return <code>true</code> if settings successfully saved;
     *         <code>false</code>, if otherwise.
     */

    private boolean saveTasks(final LinkedList<Task> tasks) {
        return model.saveTasks(tasks);
    }

    /**
     * Opens the settings dialog.
     */

    private void openSettings() {
        settingsDialog = new SettingsDialog(model, this);
        settingsDialog.setLocationRelativeTo(view);
        settingsDialog.setVisible(true);
    }

    /**
     * Opens the tasks management dialog.
     */

    private void openManageTasks() {
        tasksDialog = new TasksDialog(model, this);
        tasksDialog.setLocationRelativeTo(view);
        tasksDialog.setVisible(true);
    }

    /**
     * Opens the about dialog.
     */

    private void openAbout() {
        aboutDialog = new AboutDialog();
        aboutDialog.setLocationRelativeTo(view);
        aboutDialog.setVisible(true);
    }

    @Override
    public final void actionPerformed(final ActionEvent e) {
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
            view.resetCycle();

        } else if (actionCommand.equalsIgnoreCase("Save Tasks")) {

            saveTasks(tasksDialog.getTasks());
            JOptionPane.showMessageDialog(tasksDialog, "Tasks saved!",
                    "Notification", JOptionPane.INFORMATION_MESSAGE);

        } else if (actionCommand.equalsIgnoreCase("Move Up")) {

            tasksDialog.moveUp();
            view.resetCycle();

        } else if (actionCommand.equalsIgnoreCase("Move Down")) {

            tasksDialog.moveDown();
            view.resetCycle();

        } else if (actionCommand.equalsIgnoreCase("Open About")) {

            openAbout();

        }
    }
}
