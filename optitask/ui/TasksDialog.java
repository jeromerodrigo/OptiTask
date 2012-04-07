package optitask.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.util.LinkedList;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;

import net.miginfocom.swing.MigLayout;
import optitask.AppController;
import optitask.store.AppPersistence;
import optitask.util.Task;

/**
 * TasksDialog.java <br />
 * Purpose: Displays a dialog for the user to manage the tasks.
 * @author Jerome
 * @version 0.8.2
 * @since 0.8
 */

public class TasksDialog extends JDialog {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 8887671607735089782L;

    /**
     * The list of tasks.
     * @see Task
     */
    private LinkedList<Task> tasks;

    /**
     * The tasks table.
     */
    private JTable tasksTable;

    /**
     * The application controller.
     * @see AppController
     */
    private AppController controller;

    /**
     * The data model for the tasks table.
     * @author Jerome
     * @since 0.8
     * @see TasksDialog#tasksTable
     */

    private class TasksDataModel extends AbstractTableModel {

        /**
         * The serial version UID.
         */
        private static final long serialVersionUID = -2642740868251152662L;

        /**
         * The names for each column.
         */
        private final String[] columnNames = { "Task", "Description",
                "Current", "Assigned", "Done" };

        /**
         * The limit where assigned pomodoros is considered as 'too many'.
         */
        private static final int TOO_MANY_POMS = 6;

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(final int col) {
            return columnNames[col];
        }

        @Override
        public int getRowCount() {
            return tasks.size();
        }

        @Override
        public Class<?> getColumnClass(final int c) {
            return getValueAt(0, c).getClass();
        }

        @Override
        public boolean isCellEditable(final int row, final int col) {
            // If the task is done, the disable changing the assigned pomodoros
            if (tasks.get(row).isDone() && col == 3) {
                return false;
            }
            return col > 0 && col < columnNames.length && col != 2;
        }

        @Override
        public Object getValueAt(final int row, final int col) {
            switch (col) {
            case 0:
                return row + 1;
            case 1:
                return tasks.get(row).getTaskDesc();
            case 4:
                return tasks.get(row).isDone();
            case 2:
                return tasks.get(row).getCurrentPomodoro();
            case 3:
                return tasks.get(row).getAssignedPomodoros();
            default:
                return null;
            }
        }

        @Override
        public void setValueAt(final Object value,
                final int row, final int col) {
            assert (col > 0 && col < columnNames.length);
            Task task = tasks.get(row);

            switch (col) {
            case 1:
                task.setTaskDesc((String) value);
                break;
            case 4:
                task.setDone((Boolean) value);

                // If a task is 'undone' then reset the current pomodoros
                if (!(Boolean) value) {
                    task.setCurrentPomodoro(0);
                    tasksTable.repaint(); // Refresh the table
                }

                break;
            case 3:
                task.setAssignedPomodoros((Integer) value);

                if ((Integer) value > TOO_MANY_POMS) {
                    JOptionPane.showMessageDialog(getParent(),
                            "Too many pomodoros assigned.\n"
                                    + "Consider breaking down your tasks!",
                                    "Warning", JOptionPane.WARNING_MESSAGE);
                }

                break;
            default:
                return;
            }

            tasks.set(row, task);
            fireTableCellUpdated(row, col);
        }

    };

    /**
     * Pomodoro Number Editor class. <br />
     * Purpose: Provides a {@link JSpinner} based
     * number editor for a  table cell.
     * @author Jerome
     * @since 0.8.2
     */

    private static class PomNumberEditor extends AbstractCellEditor
    implements TableCellEditor {

        /**
         * The serial version UID.
         */
        private static final long serialVersionUID = -871596321699148434L;

        /**
         * The JSpinner.
         */
        private final JSpinner spinner = new JSpinner();

        /**
         * The maximum value for the JSpinner.
         */
        private static final int MAX = 15;

        /**
         * Creates the number editor.
         */
        public PomNumberEditor() {
            spinner.setModel(new SpinnerNumberModel(1, 1, MAX, 1));
            preventKeyboardInputJSpinner(spinner);
        }

        @Override
        public Object getCellEditorValue() {
            return spinner.getValue();
        }

        @Override
        public Component getTableCellEditorComponent(final JTable table,
                final Object value, final boolean isSelected,
                final int row, final int column) {
            spinner.setValue(value);
            return spinner;
        }

        /**
         * Prevents keyboard input for a {@link JSpinner}.
         * @param spinr the JSpinner
         */

        private void preventKeyboardInputJSpinner(final JSpinner spinr) {
            JFormattedTextField tf = (
                    (JSpinner.DefaultEditor) spinr.getEditor())
                    .getTextField();
            tf.setEditable(false);
            tf.setBackground(Color.WHITE);
        }

    }

    /**
     * Constant for the width of the first column.
     */
    private static final int MIN_WIDTH = 60;

    /**
     * Constant for the width of the second column.
     */
    private static final int PREF_WIDTH = 70;

    /**
     * Constant for the width of the third column.
     */
    private static final int MAX_WIDTH = 100;

    /**
     * Creates the dialog.
     * <B>Not used</B>.
     */

    public TasksDialog() {
        initialize();
    }

    /**
     * Creates and initialises the dialog.
     * @param model      the persistence module
     * @param cntrller   the application controller
     */

    public TasksDialog(final AppPersistence model,
            final AppController cntrller) {
        controller = cntrller;
        tasks = model.getTasks();
        initialize();
    }

    /**
     * Creates the user interface components.
     */

    private void initialize() {
        setTitle("To Do List");
        setSize(515, 300);
        setModal(true);
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                TasksDialog.class.getResource("/optitask/assests/pencil.gif")));
        getContentPane().setLayout(new MigLayout("",
                "[][10px][][][10px][236px][40px]",
                "[51px][11px][165px][23px]"));

        JScrollPane scrollPane = new JScrollPane();
        getContentPane().add(scrollPane, "cell 0 0 6 3,grow");

        tasksTable = new JTable(new TasksDataModel());

        // Configure jTable parameters
        tasksTable.setFillsViewportHeight(true);
        tasksTable.setRowSelectionAllowed(true);
        tasksTable.setColumnSelectionAllowed(false);
        tasksTable
        .setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        //Set the appropriate editor for the assigned pomodoros
        tasksTable.getColumnModel().getColumn(3)
        .setCellEditor(new PomNumberEditor());

        //Set the row height
        tasksTable.setRowHeight(30);

        // Set the column widths
        setColumnWidths(MIN_WIDTH, PREF_WIDTH, MAX_WIDTH);

        // Configure jTable selection colours
        tasksTable.setSelectionBackground(Color.ORANGE);
        tasksTable.setSelectionForeground(Color.WHITE);

        scrollPane.setViewportView(tasksTable);

        JButton btnAdd = new JButton("Add");
        btnAdd.setActionCommand("Add Task");
        btnAdd.addActionListener(controller);
        getContentPane().add(btnAdd, "cell 0 3,growx,aligny top");

        JButton btnDelete = new JButton("Delete");
        btnDelete.setActionCommand("Delete Task");
        btnDelete.addActionListener(controller);
        getContentPane().add(btnDelete, "cell 2 3,growx,aligny top");

        JButton btnSave = new JButton("Save");
        btnSave.setActionCommand("Save Tasks");
        btnSave.addActionListener(controller);
        getContentPane().add(btnSave, "cell 3 3,growx,aligny top");

        JButton btnMoveUp = new JButton("");
        btnMoveUp.setIcon(new ImageIcon(TasksDialog.class
                .getResource("/optitask/assests/upArrow.gif")));
        btnMoveUp.setActionCommand("Move Up");
        btnMoveUp.addActionListener(controller);
        getContentPane().add(btnMoveUp, "cell 6 0,growx,aligny bottom");

        JButton btnMoveDown = new JButton("");
        btnMoveDown.setIcon(new ImageIcon(TasksDialog.class
                .getResource("/optitask/assests/downArrow.gif")));
        btnMoveDown.setActionCommand("Move Down");
        btnMoveDown.addActionListener(controller);
        getContentPane().add(btnMoveDown, "cell 6 2,growx,aligny top");

    }

    /**
     * Sets the widths of the columns.
     * @param min the minimum width
     * @param pref the preferred width
     * @param max the maximum width
     */

    private void setColumnWidths(final int min,
            final int pref, final int max) {
        for (int i = 0; i < tasksTable.getColumnCount(); i++) {
            if (i == 1) {
                continue;
            }
            tasksTable.getColumnModel().getColumn(i).setMinWidth(min);
            tasksTable.getColumnModel().getColumn(i).setPreferredWidth(pref);
            tasksTable.getColumnModel().getColumn(i).setMaxWidth(max);
        }

    }

    /**
     * Adds a new task with default values.
     */

    public final void addTask() {
        Task newTask = new Task();
        tasks.add(newTask);
        tasksTable.repaint();
    }

    /**
     * Deletes the selected task from the table.
     */

    public final void deleteTask() {
        int row = -1;
        try {
            row = tasksTable.getSelectedRow();
            tasks.remove(row);
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(tasksTable, "Please select a task!",
                    "No Task", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        tasksTable.repaint();
    }

    /**
     * Gets the list of tasks.
     * @return list of {@link Task}s
     */

    public final LinkedList<Task> getTasks() {
        return tasks;
    }

    /**
     * Moves a table item upwards.
     */

    public final void moveUp() {
        swapItems(tasksTable.getSelectedRow(), tasksTable.getSelectedRow() - 1);
    }

    /**
     * Moves a table item downwards.
     */

    public final void moveDown() {
        swapItems(tasksTable.getSelectedRow(), tasksTable.getSelectedRow() + 1);
    }

    /**
     * Method to swap two task items in the {@link #tasks} list.
     * @param selectedIdx the index of the selected item
     * @param nextIdx     the index of the item to be swapped
     */

    private void swapItems(final int selectedIdx, final int nextIdx) {
        Task temp = new Task();
        try {
            temp = tasks.get(selectedIdx); // Temporarily stores selected task
            tasks.set(selectedIdx, tasks.get(nextIdx));
            tasks.set(nextIdx, temp);
            tasksTable.repaint();
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(getParent(), "No row to move to!",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        tasksTable.changeSelection(nextIdx, 0, false, false);
    }
}
