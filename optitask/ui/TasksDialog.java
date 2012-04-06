package optitask.ui;

import java.awt.Color;
import java.awt.Toolkit;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

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
        private final String[] columnNames = { "Task", "Description", "Done" };

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
            return col > 0 && col < columnNames.length;
        }

        @Override
        public Object getValueAt(final int row, final int col) {
            switch (col) {
            case 0:
                return row + 1;
            case 1:
                return tasks.get(row).getTaskDesc();
            case 2:
                return tasks.get(row).isDone();
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
            case 2:
                task.setDone((Boolean) value);
                break;
            default:
                return;
            }

            tasks.set(row, task);
            fireTableCellUpdated(row, col);
        }

    };

    /**
     * Constant for the width of the first column.
     */
    private static final int COL1_WIDTH = 50;

    /**
     * Constant for the width of the second column.
     */
    private static final int COL2_WIDTH = 70;

    /**
     * Constant for the width of the third column.
     */
    private static final int COL3_WIDTH = 50;

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

        // Set the column widths
        setColumnWidths(COL1_WIDTH, COL2_WIDTH, COL3_WIDTH);

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
     * @param col1 the first column
     * @param col2 the second column
     * @param col3 the third column
     */

    private void setColumnWidths(final int col1,
            final int col2, final int col3) {
        for (int i = 0; i < tasksTable.getColumnCount(); i++) {
            if (i == 1) {
                continue;
            }
            tasksTable.getColumnModel().getColumn(i).setMinWidth(col1);
            tasksTable.getColumnModel().getColumn(i).setPreferredWidth(col2);
            tasksTable.getColumnModel().getColumn(i).setMaxWidth(col3);
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
            //FIXME the way this is done should be avoided if possible
            JOptionPane.showMessageDialog(getParent(), "No row to move to!",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        tasksTable.changeSelection(nextIdx, 0, false, false);
    }
}
