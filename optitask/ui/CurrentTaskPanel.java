package optitask.ui;

import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import optitask.store.AppPersistence;
import optitask.util.Task;

/**
 * CurrentTaskPanel.java <br />
 * Purpose: Displays the current task, and status of the task.
 * @author Jerome
 * @version 0.8
 * @since 0.8
 */

public class CurrentTaskPanel extends JPanel {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -2030419465797237653L;

    /**
     * Label to display the current task.
     */
    private JLabel lblTask;

    /**
     * The list of tasks.
     * @see Task
     */
    private static LinkedList<Task> tasks;

    /**
     * Stores the current index. Used in {@link #tasks}.
     */
    private static int currentIdx;

    /**
     * Stores a reference to the persistence module.
     * @see AppPersistence
     */
    private static AppPersistence model;

    /**
     * Label to display the status of the task.
     */
    private JLabel lblStatus;

    /**
     * A constant used to set the status label to display nothing.
     * @see #setStatus(int)
     */
    public static final int NULL = 0;

    /**
     * A constant used to set the status label to display short break message.
     * @see #setStatus(int)
     */
    public static final int SHORT_BREAK = 1;

    /**
     * A constant used to set the status label to display the working message.
     * @see #setStatus(int)
     */
    public static final int WORKING = 2;

    /**
     * A constant used to set the status label to display long break message.
     * @see #setStatus(int)
     */
    public static final int LONG_BREAK = 3;

    /**
     * Create the panel.
     * <B>Not used</B>.
     */
    public CurrentTaskPanel() {
        initialize();
    }

    /**
     * Creates the panel and initialises the tasks.
     * @param mdl the persistence module
     */

    public CurrentTaskPanel(final AppPersistence mdl) {
        tasks = mdl.getTasks();
        model = mdl;
        currentIdx = 0;
        initialize();
        nextTask();
    }

    /**
     * Creates the user interface components.
     */

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

    /**
     * A recursive method to iterate to the next available task
     * and display it on the {@link #lblTask}.
     */

    public final void nextTask() {
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

    /**
     * Updates the {@link #tasks} with data from the file.
     * Also sets the {@link #currentIdx} to 0.
     */

    public final void refresh() {
        tasks = model.getTasks();
        currentIdx = 0;
        nextTask();
    }

    /**
     * Gets the current {@link Task} and sets its
     * state to 'done'.
     */

    public final void markAsDone() {
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

    /**
     * Sets the status of the current task to be displayed.
     * @param status the status constant
     */

    public final void setStatus(final int status) {
        switch (status) {
        case NULL:
            lblStatus.setText(getHtmlColoredString("black", "Idle"));
            break;
        case SHORT_BREAK:
            lblStatus.setText(getHtmlColoredString("green", "Short Break"));
            break;
        case LONG_BREAK:
            lblStatus.setText(getHtmlColoredString("green", "Long Break"));
            break;
        case WORKING:
            lblStatus.setText(getHtmlColoredString("red", "Working"));
            break;
        default:
            lblStatus.setText(null);
        }
    }

    /**
     * Returns a HTML marked-up string.
     * @param colour the colour of the text
     * @param text  the text string
     * @return a HTML marked-up string
     * @see #setStatus(int)
     */

    private String getHtmlColoredString(final String colour,
            final String text) {
        return "<html><b><font color=\"" + colour + "\"> " + text
                + "</font></b></html>";
    }
}
