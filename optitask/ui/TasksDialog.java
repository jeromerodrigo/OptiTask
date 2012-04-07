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

import optitask.AppController;
import optitask.store.AppPersistence;
import optitask.util.Task;

/**
 * TasksDialog.java <br />
 * Purpose: Displays a dialog for the user to manage the tasks.
 * @author Jerome
 * @version 0.8
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
        private final String[] columnNames = { "Task", "Description", "Current", "Assigned", "Done" };

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
                break;
            case 3:
                task.setAssignedPomodoros((Integer) value);
                break;
            default:
                return;
            }

            tasks.set(row, task);
            fireTableCellUpdated(row, col);
        }

    };

    private static class PomNumberEditor extends AbstractCellEditor implements TableCellEditor {

        /**
         * 
         */
        private static final long serialVersionUID = -871596321699148434L;
        private final JSpinner spinner = new JSpinner();
        private final int MAX = 15;

        public PomNumberEditor() {
            spinner.setModel(new SpinnerNumberModel(1, 1, MAX, 1));
            preventKeyboardInputJSpinner(spinner);
        }

        @Override
        public Object getCellEditorValue() {
            return spinner.getValue();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table,
                Object value, boolean isSelected, int row, int column) {
            spinner.setValue(value);
            return spinner;
        }

        /**
         * Prevents keyboard input for a {@link JSpinner}.
         * @param spinner the JSpinner
         */

        private void preventKeyboardInputJSpinner(final JSpinner spinner) {
            JFormattedTextField tf = ((JSpinner.DefaultEditor) spinner.getEditor())
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
        getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 444, 227);
        getContentPane().add(scrollPane);

        tasksTable = new JTable(new TasksDataModel());

        // Configure jTable parameters
        tasksTable.setFillsViewportHeight(true);
        tasksTable.setRowSelectionAllowed(true);
        tasksTable.setColumnSelectionAllowed(false);
        tasksTable
        .setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        //Set the appropriate editor for the assigned pomodoros
        tasksTable.getColumnModel().getColumn(3).setCellEditor(new PomNumberEditor());

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
        btnAdd.setBounds(10, 238, 89, 23);
        getContentPane().add(btnAdd);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setActionCommand("Delete Task");
        btnDelete.addActionListener(controller);
        btnDelete.setBounds(109, 238, 89, 23);
        getContentPane().add(btnDelete);

        JButton btnSave = new JButton("Save");
        btnSave.setActionCommand("Save Tasks");
        btnSave.addActionListener(controller);
        btnSave.setBounds(208, 238, 89, 23);
        getContentPane().add(btnSave);

        JButton btnMoveUp = new JButton("");
        btnMoveUp.setIcon(new ImageIcon(TasksDialog.class
                .getResource("/optitask/assests/upArrow.gif")));
        btnMoveUp.setActionCommand("Move Up");
        btnMoveUp.addActionListener(controller);
        btnMoveUp.setBounds(454, 11, 40, 40);
        getContentPane().add(btnMoveUp);

        JButton btnMoveDown = new JButton("");
        btnMoveDown.setIcon(new ImageIcon(TasksDialog.class
                .getResource("/optitask/assests/downArrow.gif")));
        btnMoveDown.setActionCommand("Move Down");
        btnMoveDown.addActionListener(controller);
        btnMoveDown.setBounds(454, 62, 40, 40);
        getContentPane().add(btnMoveDown);

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
