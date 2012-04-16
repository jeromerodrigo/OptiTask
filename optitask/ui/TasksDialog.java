package optitask.ui;

import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

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

public class TasksDialog extends TaskManager {

    /**
     * 
     */
    private static final long serialVersionUID = 2408587468175238769L;

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

        private final LinkedList<Task> tasks;

        private final JTable tasksTable;

        public TasksDataModel(LinkedList<Task> tsks, JTable tskTbl) {
            tasks = tsks;
            tasksTable = tskTbl;
        }

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
     * Creates the dialog.
     * <B>Not used</B>.
     */

    public TasksDialog() {
    }

    /**
     * Creates and initialises the dialog.
     * @param model      the persistence module
     * @param cntrller   the application controller
     */

    public TasksDialog(final AppPersistence model,
            final AppController cntrller) {
        super(model, cntrller);
    }

    @Override
    public AbstractTableModel getTableModel() {
        return new TasksDataModel(tasks, tasksTable);
    }


}
