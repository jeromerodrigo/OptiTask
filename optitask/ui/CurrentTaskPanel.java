package optitask.ui;

import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import optitask.store.AppPersistence;
import optitask.util.Task;

/**
 * CurrentTaskPanel.java <br />
 * Purpose: Displays the current task, and status of the task.
 * @author Jerome Rodrigo
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
    private LinkedList<Task> tasks;

    /**
     * Stores the current index. Used in {@link #tasks}.
     */
    private int currentIdx;

    /**
     * Stores a reference to the persistence module.
     * @see AppPersistence
     */
    private AppPersistence model;

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
     * A constant used to set the status label to display the working message
     * before a short break.
     * @see #setStatus(int)
     */
    public static final int WORKING_THEN_SBRK = 2;

    /**
     * A constant used to set the status label to display the working message
     * before a long break.
     * @see #setStatus(int)
     */
    public static final int WORKING_THEN_LBRK = 4;

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
        tasks = mdl.getToDoList();
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
        setLayout(new MigLayout("", "[302px]", "[63px][14px]"));

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(UIManager
                .getBorder("TitledBorder.border"), "Current Task",
                TitledBorder.CENTER, TitledBorder.TOP, null, null));
        add(panel, "cell 0 0,grow");
        panel.setLayout(new MigLayout("", "[272px]", "[36px]"));

        lblTask = new JLabel("");
        panel.add(lblTask, "cell 0 0,grow");

        lblStatus = new JLabel("");
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblStatus, "cell 0 1,grow");
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
        tasks = model.getToDoList();
        currentIdx = 0;
        nextTask();
    }

    /**
     * Gets the current {@link Task} and sets its
     * state to 'done'.
     */

    private void markAsDone() {
        Task task = new Task();
        try {
            task = tasks.get(currentIdx);
        } catch (IndexOutOfBoundsException e) {
            return; // Do nothing
        }
        task.setIsDone(true);
        tasks.set(currentIdx, task);
        model.saveToDoList(tasks);

        lblTask.setText(lblTask.getText() + " [DONE]");
    }

    /**
     * Increments the pomodoro number of the current task.
     * If the current pomodoro matches the assigned pomodoros,
     * then the task is marked as done.
     */

    public final void incrementPomodoro() {
        Task task = new Task();
        try {
            task = tasks.get(currentIdx);
        } catch (IndexOutOfBoundsException e) {
            return; // Do nothing
        }
        task.setCurrentPomodoro(task.getCurrentPomodoro() + 1);
        tasks.set(currentIdx, task);
        model.saveToDoList(tasks);

        if (task.getCurrentPomodoro() == task.getAssignedPomodoros()) {
            markAsDone();
        }
    }

    /**
     * Sets the status of the current task to be displayed.
     * @param status the status constant
     */

    public final void setStatus(final int status) {
        switch (status) {
        case NULL:
            lblStatus.setText(wrapWithHtml(
                    getHtmlColoredString("Black", "Idle")));
            break;
        case SHORT_BREAK:
            lblStatus.setText(wrapWithHtml(
                    getHtmlColoredString("Green", "Short Break")));
            break;
        case LONG_BREAK:
            lblStatus.setText(wrapWithHtml(
                    getHtmlColoredString("Green", "Long Break")));
            break;
        case WORKING_THEN_SBRK:
            lblStatus.setText(wrapWithHtml(
                    getHtmlColoredString("red", "Working") + " --> "
                            + getHtmlColoredString("Green", "Short Break")));
            break;
        case WORKING_THEN_LBRK:
            lblStatus.setText(wrapWithHtml(
                    getHtmlColoredString("red", "Working") + " --> "
                            + getHtmlColoredString("Green", "Long Break")));
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
        return "<b><font color=\"" + colour + "\"> " + text
                + "</font></b>";
    }

    /**
     * Wraps a string with HTML tags.
     * @param str the string
     * @return a string wrapped with HTML tags
     */

    private String wrapWithHtml(final String str) {
        return "<html>" + str + "</html>";
    }
}
