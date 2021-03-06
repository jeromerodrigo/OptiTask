/**
 * 
 */
package optitask.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
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
import optitask.util.UIToolkit;

/**
 * @author Jerome Rodrigo
 * @since 0.9.0
 */
public abstract class AbstractTaskManager extends JDialog
implements TaskManagerActions {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 7500146889668176022L;

    /**
     * The list of tasks.
     * @see Task
     */
    private List<Task> tasks;

    /**
     * The tasks table.
     */
    private transient JTable tasksTable;

    /**
     * The application controller.
     * @see AppController
     */
    private transient AppController controller;

    
    /**
     * The persistence module.
     * @see AppPersistence
     */
    private static AppPersistence model;

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
        private final transient JSpinner spinner = new JSpinner();

        /**
         * The maximum value for the JSpinner.
         */
        private static final int MAX = 15;

        /**
         * Creates the number editor.
         */
        public PomNumberEditor() {
            super();
            spinner.setModel(new SpinnerNumberModel(1, 1, MAX, 1));
            UIToolkit.preventKeyboardInputJSpinner(spinner);
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
     * Button that allows user to move a task to another list.
     */
    private transient JButton btnMoveTo;

    /**
     * 
     */
    public AbstractTaskManager() {
        super();
        initialize();
    }

    /**
     * Constructs an abstract TaskManager.
     * @param mdl the persistence module
     * @param cntrller the application controller
     */
    
    public AbstractTaskManager(final AppPersistence mdl,
            final AppController cntrller) {
        super();
        controller = cntrller;
        setModel(mdl);
        tasks = (LinkedList<Task>) getTasksModel();
        initialize();

        // Runtime initialisation of 'move to' button
        btnMoveTo.setText(getMoveToMessage());
        btnMoveTo.setActionCommand(getMoveToMessage());
    }

    /**
     * Initializes the UI components.
     */
    
    private void initialize() {
        setTitle(getWindowTitle());
        setSize(515, 300);
        setModal(true);
        setResizable(false);
        setIconImage(getIconImage());
        getContentPane().setLayout(new MigLayout("",
                "[][10px][][][10px][236px][40px]",
                "[51px][11px][165px][23px]"));

        final JScrollPane scrollPane = new JScrollPane();
        getContentPane().add(scrollPane, "cell 0 0 6 3,grow");

        tasksTable = new JTable(getTableModel());

        // Configure jTable parameters
        tasksTable.setFillsViewportHeight(true);
        tasksTable.setRowSelectionAllowed(true);
        tasksTable.setColumnSelectionAllowed(false);
        tasksTable
        .setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        //Set the appropriate editor for the assigned pomodoros
        tasksTable.getColumnModel().getColumn(getPomNumberEditorColumn())
        .setCellEditor(new PomNumberEditor());

        //Set the row height
        tasksTable.setRowHeight(30);

        // Set the column widths
        setColumnWidths(MIN_WIDTH, PREF_WIDTH, MAX_WIDTH);

        // Configure jTable selection colours
        tasksTable.setSelectionBackground(Color.ORANGE);
        tasksTable.setSelectionForeground(Color.WHITE);

        // Add table model listener to the task table
        tasksTable.getModel().addTableModelListener(controller);

        scrollPane.setViewportView(tasksTable);

        final JButton btnAdd = new JButton("Add");
        btnAdd.setActionCommand(getAddMessage());
        btnAdd.addActionListener(controller);
        getContentPane().add(btnAdd, "cell 0 3,growx,aligny top");

        final JButton btnDelete = new JButton("Delete");
        btnDelete.setActionCommand(getDeleteMessage());
        btnDelete.addActionListener(controller);
        getContentPane().add(btnDelete, "cell 1 3,growx,aligny top");

        btnMoveTo = new JButton("Move To");
        btnMoveTo.setActionCommand("Move To");
        btnMoveTo.addActionListener(controller);
        getContentPane().add(btnMoveTo, "cell 2 3, growx, aligny top");

        final JButton btnMoveUp = new JButton("");
        btnMoveUp.setIcon(new ImageIcon(AbstractTaskManager.class
                .getResource("/optitask/assests/upArrow.gif")));
        btnMoveUp.setActionCommand(getMoveUpMessage());
        btnMoveUp.addActionListener(controller);
        getContentPane().add(btnMoveUp, "cell 6 0,growx,aligny bottom");

        final JButton btnMoveDown = new JButton("");
        btnMoveDown.setIcon(new ImageIcon(AbstractTaskManager.class
                .getResource("/optitask/assests/downArrow.gif")));
        btnMoveDown.setActionCommand(getMoveDownMessage());
        btnMoveDown.addActionListener(controller);
        getContentPane().add(btnMoveDown, "cell 6 2,growx,aligny top");
    }

    /**
     * @param tsks the tasks to set
     */
    public final void setTasks(final List<Task> tsks) {
        tasks = tsks;
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

    /* (non-Javadoc)
     * @see optitask.ui.TaskManagerActions#addTask()
     */
    @Override
    public final void addTask() {
        final Task newTask = new Task();
        tasks.add(newTask);
        tasksTable.repaint();

    }

    /* (non-Javadoc)
     * @see optitask.ui.TaskManagerActions#deleteTask()
     */
    @Override
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

    /* (non-Javadoc)
     * @see optitask.ui.TaskManagerActions#getTasks()
     */
    @Override
    public final List<Task> getTasks() {
        return tasks;
    }

    /**
     * @return the model
     */
    public static AppPersistence getModel() {
        return model;
    }

    /**
     * @param mdl the model to set
     */
    public static void setModel(final AppPersistence mdl) {
        AbstractTaskManager.model = mdl;
    }

    /* (non-Javadoc)
     * @see optitask.ui.TaskManagerActions#swapItems(int, int)
     */
    @Override
    public final void swapItems(final int selectedIdx, final int nextIdx) {
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

    /* (non-Javadoc)
     * @see optitask.ui.TaskManagerActions#moveUp()
     */
    @Override
    public final void moveUp() {
        swapItems(tasksTable.getSelectedRow(), tasksTable.getSelectedRow() - 1);
    }

    /* (non-Javadoc)
     * @see optitask.ui.TaskManagerActions#moveDown()
     */
    @Override
    public final void moveDown() {
        swapItems(tasksTable.getSelectedRow(), tasksTable.getSelectedRow() + 1);
    }

    @Override
    public final Task getSelectedTask() {
        Task task = new Task();

        try {
            task = tasks.get(tasksTable.getSelectedRow());
        } catch (IndexOutOfBoundsException e) {
            Logger.getAnonymousLogger().log(Level.WARNING, 
                    "No more tasks to select!");
        }

        return task; 
    }
    
    /**
     * Gets the table model.
     * @return the table model
     */

    protected abstract AbstractTableModel getTableModel();

    /**
     * Gets the title string for the current window.
     * @return the window title string
     */
    
    protected abstract String getWindowTitle();

    /**
     * Gets the icon for the current window.
     * @return window icon
     */
    
    protected abstract Image getIconImage();

    /**
     * Gets the appropriate action event message for moving up a task.
     * @return the move up message
     */
    
    protected abstract String getMoveUpMessage();

    /**
     * Gets the appropriate action event message for moving down a task.
     * @return the move down message
     */
    
    protected abstract String getMoveDownMessage();

    /**
     * Gets the adding a task action event message.
     * @return the add task message
     */
    
    protected abstract String getAddMessage();

    /**
     * Gets the delete a task action event message.
     * @return the delete task message
     */
    
    protected abstract String getDeleteMessage();

    /**
     * Gets the tasks model.
     * @return list of tasks
     */
    
    protected abstract List<Task> getTasksModel();

    /**
     * Gets the column to initialize the pomodoro editor.
     * @return the column number
     */
    
    protected abstract int getPomNumberEditorColumn();

    /**
     * Gets the appropriate action event message for moving a task to
     * another list.
     * @return the move task message
     */
    
    protected abstract String getMoveToMessage();

}
