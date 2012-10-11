package optitask;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import optitask.store.AppPersistence;
import optitask.ui.AboutDialog;
import optitask.ui.AppFrame;
import optitask.ui.InterruptDialog;
import optitask.ui.SettingsDialog;
import optitask.ui.TaskInventoryDialog;
import optitask.ui.ToDoListDialog;
import optitask.util.Settings;
import optitask.util.Statistics;
import optitask.util.Task;

/**
 * AppController.java <br />
 * Purpose: Listens to all events generated by the user interface.
 * @author Jerome Rodrigo
 * @since 0.8
 */

public class AppController implements ActionListener, TableModelListener,
ChangeListener {

    /** Stores the reference to the application persistence module. */
    private final AppPersistence model;

    /** Stores the reference to the main user interface. */
    private final AppFrame view;

    /** The settings dialog user interface object. */
    private SettingsDialog settingsDialog;

    /** The tasks dialog user interface object. */
    private ToDoListDialog toDoListDialog;

    /** The about dialog user interface object. */
    private AboutDialog aboutDialog;

    /** The interruption dialog object. */
    private InterruptDialog interruptDialog;

    /** The task inventory dialog user interface object. */
    private TaskInventoryDialog taskInventoryDialog;

    /**
     * Creates the controller object.
     * @param persistence the persistence module
     * @param frame the user interface
     */

    public AppController(final AppPersistence persistence,
            final AppFrame frame) {
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

    private boolean saveToDoList(final LinkedList<Task> tasks) {
        return model.saveToDoList(tasks);
    }

    /**
     * Saves the tasks to the task inventory file.
     * @param inv list of tasks
     * @return <code>true</code> if settings successfully saved;
     *         <code>false</code>, if otherwise.
     */
    private boolean saveTaskInventory(final LinkedList<Task> inv) {
        return model.saveTaskInventory(inv);
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

    private void openToDoList() {
        toDoListDialog = new ToDoListDialog(model, this);
        toDoListDialog.setLocationRelativeTo(view);
        toDoListDialog.setVisible(true);
    }

    /**
     * Opens the about dialog.
     */

    private void openAbout() {
        aboutDialog = new AboutDialog();
        aboutDialog.setLocationRelativeTo(view);
        aboutDialog.setVisible(true);
    }

    /**
     * Opens the interrupt dialog.
     */
    
    private void openInterrupt() {
        interruptDialog = new InterruptDialog(this);
        interruptDialog.setLocationRelativeTo(view);
        interruptDialog.setVisible(true);
    }
    
    /**
     * Opens the task inventory dialog.
     */

    private void openTaskInventory() {
        taskInventoryDialog = new TaskInventoryDialog(model, this);
        taskInventoryDialog.setLocationRelativeTo(view);
        taskInventoryDialog.setVisible(true);
    }

    @Override
    public final void actionPerformed(final ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (actionCommand.equalsIgnoreCase("Start")) {
            startTimer();
        } else if (actionCommand.equalsIgnoreCase("Stop")) {
            stopTimer();
            if (interruptDialog.isVisible()) {
                interruptDialog.dispose();
            }
        } else if (actionCommand.equalsIgnoreCase("Do New Task")) {
            interruptDialog.viewNewTaskForm();
        } else if (actionCommand.equalsIgnoreCase("Add New Task")) {            
            stopTimer();
            
            LinkedList<Task> tasks;
            
            if (interruptDialog.getNewTaskLocation().equalsIgnoreCase("Now")) {
                tasks = model.getToDoList();
                tasks.addFirst(interruptDialog.getNewTask());
                model.saveToDoList(tasks);
            } else {
                tasks = model.getTaskInventory();
                tasks.addFirst(interruptDialog.getNewTask());
                model.saveTaskInventory(tasks);
            }
            
            if (interruptDialog.isVisible()) {
                interruptDialog.dispose();
            }            
            
        } else if (actionCommand.equalsIgnoreCase("Open Settings")) {
            openSettings();
        } else if (actionCommand.equalsIgnoreCase("Save Settings")) {

            if (settingsDialog.getSettings() == null) {
                JOptionPane.showMessageDialog(settingsDialog,
                        "Long break must not be equal "
                                + "or less than short break!",
                                "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                saveSettings(settingsDialog.getSettings());
                JOptionPane.showMessageDialog(settingsDialog, "Settings saved!",
                        "Notification", JOptionPane.INFORMATION_MESSAGE);
            }

        } else if (actionCommand.equalsIgnoreCase("Default Settings")) {
            settingsDialog.setSettings(new Settings());
        } else if (actionCommand.equalsIgnoreCase("Toggle Increment")) {
            settingsDialog.setEnabledIncrementSettings(!settingsDialog
                    .isEnabledIncrementSettings());
        } else if (actionCommand.equalsIgnoreCase("Open Manage Tasks")) {
            openToDoList();
        } else if (actionCommand.equalsIgnoreCase("Add Task To Do List")) {
            toDoListDialog.addTask();
            saveToDoList(toDoListDialog.getTasks());
        } else if (actionCommand.equalsIgnoreCase("Delete Task To Do List")) {
            toDoListDialog.deleteTask();
            saveToDoList(toDoListDialog.getTasks());
            view.resetCycle();
        } else if (actionCommand.equalsIgnoreCase("Move Up To Do List")) {
            toDoListDialog.moveUp();
            saveToDoList(toDoListDialog.getTasks());
            view.resetCycle();
        } else if (actionCommand.equalsIgnoreCase("Move Down To Do List")) {
            toDoListDialog.moveDown();
            saveToDoList(toDoListDialog.getTasks());
            view.resetCycle();
        } else if (actionCommand.equalsIgnoreCase("Open About")) {
            openAbout();
        } else if (actionCommand.equalsIgnoreCase("Open Interrupt Dialog")) {
            openInterrupt();
        } else if (actionCommand.equalsIgnoreCase("Open Task Inventory")) {
            openTaskInventory();
        } else if (actionCommand.equalsIgnoreCase("Add Task Task Inventory")) {
            taskInventoryDialog.addTask();
            saveTaskInventory(taskInventoryDialog.getTasks());
        } else if (actionCommand.equalsIgnoreCase(
                "Delete Task Task Inventory")) {
            taskInventoryDialog.deleteTask();
            saveTaskInventory(taskInventoryDialog.getTasks());
        } else if (actionCommand.equalsIgnoreCase("Move Up Task Inventory")) {
            taskInventoryDialog.moveUp();
            saveTaskInventory(taskInventoryDialog.getTasks());
            view.resetCycle();
        } else if (actionCommand.equalsIgnoreCase("Move Down Task Inventory")) {
            taskInventoryDialog.moveDown();
            saveTaskInventory(taskInventoryDialog.getTasks());
        } else if (actionCommand.equalsIgnoreCase("Move To To Do List")) {
            Task temp = new Task();
            temp = taskInventoryDialog.getSelectedTask();

            LinkedList<Task> tempList = model.getToDoList();
            tempList.add(temp);

            saveToDoList(tempList);

            taskInventoryDialog.deleteTask();
            saveTaskInventory(taskInventoryDialog.getTasks());

        } else if (actionCommand.equalsIgnoreCase("Move To Task Inventory")) {

            Task temp = new Task();
            temp = toDoListDialog.getSelectedTask();

            final LinkedList<Task> tempList = model.getTaskInventory();
            tempList.add(temp);

            saveTaskInventory(tempList);

            toDoListDialog.deleteTask();
            saveToDoList(toDoListDialog.getTasks());
            view.resetCycle();

        }
    }

    @Override
    public final void tableChanged(final TableModelEvent evt) {
        
        if (toDoListDialog != null) {
            saveToDoList(toDoListDialog.getTasks());
        } else if (taskInventoryDialog != null) {
            saveTaskInventory(taskInventoryDialog.getTasks());
        }

    }

    @Override
    public final void stateChanged(final ChangeEvent e) {
        Object source = e.getSource();
        if (source instanceof JProgressBar) {
            JProgressBar timerBar = (JProgressBar) source;
            if (timerBar.getPercentComplete() >= 1.0) {
                view.resetButtonState();
            }
        }
        
    }
}
