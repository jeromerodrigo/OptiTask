package optitask.ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import optitask.AppController;
import optitask.store.AppPersistence;
import optitask.util.Task;

/**
 * ToDoListDialog.java <br />
 * Purpose: Displays a dialog for the user to manage the tasks.
 * @author Jerome Rodrigo
 * @since 0.8
 */

public class ToDoListDialog extends AbstractTaskManager {

    /**
     * 
     */
    private static final long serialVersionUID = 2408587468175238769L;

    /**
     * The data model for the to do list table.
     * @author Jerome
     * @since 0.8
     */

    private class TasksDataModel extends AbstractTableModel {

        /**
         * The serial version UID.
         */
        private static final long serialVersionUID = -2642740868251152662L;

        /**
         * The names for each column.
         */
        private final transient String[] columnNames = { "Task", "Description",
                "Current", "Assigned", "Done" };

        /**
         * The limit where assigned pomodoros is considered as 'too many'.
         */
        private static final int TOO_MANY_POMS = 6;

        /**
         * The list of tasks.
         */
        private final transient List<Task> tasks;
        
        /**
         * Constructor for the TasksDataModel.
         * @param list the list of tasks
         */
        public TasksDataModel(final List<Task> list) {
            super();
            tasks = list;
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
        public Class<?> getColumnClass(final int col) {
            return getValueAt(0, col).getClass();
        }

        @Override
        public boolean isCellEditable(final int row, final int col) {
            boolean editable;
            
            // If the task is done, the disable changing the assigned pomodoros
            if (tasks.get(row).isDone() 
                    && 
                    (col == 3 || col == 2 || col == 1)) {
                editable = false;
            } else {
                editable = col > 0 && col < columnNames.length && col != 2;
            }
            
            return editable;
        }

        @Override
        public Object getValueAt(final int row, final int col) {
            Object obj;
            
            switch (col) {
            case 0:
                obj = row + 1;
                break;
            case 1:
                obj = tasks.get(row).getTaskDesc();
                break;
            case 4:
                obj = tasks.get(row).isDone();
                break;
            case 2:
                obj = tasks.get(row).getCurrentPomodoro();
                break;
            case 3:
                obj = tasks.get(row).getAssignedPomodoros();
                break;
            default:
                obj = new Object();
            }
            
            return obj;
        }

        @Override
        public void setValueAt(final Object value,
                final int row, final int col) {
            assert (col > 0 && col < columnNames.length);
            final Task task = tasks.get(row);

            switch (col) {
            case 1:
                task.setTaskDesc((String) value);
                break;
            case 4:
                task.setTaskDone((Boolean) value);

                // If a task is 'undone' then reset the current pomodoros
                if (!(Boolean) value) {
                    task.setCurrentPomodoro(0);
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
     * Title of the window.
     */
    private static final String WINDOW_TITLE = "To Do List";

    /**
     * The column which holds the pomodoro editor in the JTable.
     */
    private static final int POM_EDITOR_COLUMN = 3;

    /**
     * Creates the dialog.
     * <B>Not used</B>.
     */

    public ToDoListDialog() {
        super();
    }

    /**
     * Creates and initialises the dialog.
     * @param mdl      the persistence module
     * @param cntrller   the application controller
     */

    public ToDoListDialog(final AppPersistence mdl,
            final AppController cntrller) {
        super(mdl, cntrller);
    }

    @Override
    public final AbstractTableModel getTableModel() {
        return new TasksDataModel(getTasks());
    }

    @Override
    protected final String getWindowTitle() {
        return WINDOW_TITLE;
    }

    @Override
    protected final Image getIconImage() {
        return Toolkit.getDefaultToolkit().getImage(
                ToDoListDialog.class.
                getResource("/optitask/assests/pencil.gif"));
    }

    @Override
    protected final String getMoveUpMessage() {
        return "Move Up To Do List";
    }

    @Override
    protected final String getMoveDownMessage() {
        return "Move Down To Do List";
    }

    @Override
    protected final String getAddMessage() {
        return "Add Task To Do List";
    }

    @Override
    protected final String getDeleteMessage() {
        return "Delete Task To Do List";
    }

    @Override
    protected final List<Task> getTasksModel() {
        return (List<Task>) getModel().getToDoList();
    }

    @Override
    protected final int getPomNumberEditorColumn() {
        return POM_EDITOR_COLUMN;
    }

    @Override
    protected final String getMoveToMessage() {
        return "Move To Task Inventory";
    }


}
