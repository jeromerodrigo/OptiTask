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

public class TasksDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8887671607735089782L;

	private LinkedList<Task> tasks;
	private JTable tasksTable;
	private AppController controller;

	private class TasksDataModel extends AbstractTableModel {

		private static final long serialVersionUID = -2642740868251152662L;
		private final String[] columnNames = { "Task", "Description", "Done" };

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		@Override
		public int getRowCount() {
			return tasks.size();
		}

		@Override
		public Class<?> getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			if (col > 0 && col < 3)
				return true;
			else
				return false;
		}

		@Override
		public Object getValueAt(int row, int col) {
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
		public void setValueAt(Object value, int row, int col) {
			assert (col > 0 && col < 3);
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

	public TasksDialog() {
		initialize();
	}

	public TasksDialog(AppPersistence model, AppController controller) {
		this.controller = controller;
		tasks = model.getTasks();
		initialize();
	}

	private void initialize() {
		setTitle("Tasks");
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
		setColumnWidths();

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

	private void setColumnWidths() {
		for (int i = 0; i < 3; i++) {
			if (i == 1)
				continue;
			tasksTable.getColumnModel().getColumn(i).setMinWidth(50);
			tasksTable.getColumnModel().getColumn(i).setPreferredWidth(55);
			tasksTable.getColumnModel().getColumn(i).setMaxWidth(60);
		}

	}

	public void addTask() {
		Task newTask = new Task();
		tasks.add(newTask);
		tasksTable.repaint();
	}

	public void deleteTask() {
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

	public LinkedList<Task> getTasks() {
		return tasks;
	}

	public void moveUp() {
		swapItems(tasksTable.getSelectedRow(), tasksTable.getSelectedRow() - 1);
	}

	public void moveDown() {
		swapItems(tasksTable.getSelectedRow(), tasksTable.getSelectedRow() + 1);
	}

	private void swapItems(int selectedIdx, int nextIdx) {
		Task temp = new Task();
		try {
			temp = tasks.get(selectedIdx); // Stores selected task
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
