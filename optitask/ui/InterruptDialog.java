package optitask.ui;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import optitask.AppController;
import optitask.util.Task;

import javax.swing.SpinnerNumberModel;
import javax.swing.JPanel;
import java.awt.CardLayout;

/**
 * InterruptDialog.java <br />
 * Purpose: Display a selection dialog when user is interrupted.
 * @author Jerome Rodrigo
 * @since 0.9.0
 */

public class InterruptDialog extends JDialog {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -4306038905970650449L;

    private AppController controller;
    
    private JTextField taskTextField;

    private JPanel newTaskPanel;

    private JButton btnConfirm;

    private JComboBox moveToComboBox;

    private JSpinner numPomsSpinner;

    /**
     * Create the dialog.
     */
    public InterruptDialog() {
        initialize();
    }

    public InterruptDialog(final AppController cntrllr) {
        controller = cntrllr;
        initialize();
    }

    private void initialize() {
        setResizable(false);
        setTitle("Interrupt Task");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(true);
        setBounds(100, 100, 400, 120);
        getContentPane().setLayout(new CardLayout(0, 0));
        
        newTaskPanel = new JPanel();
        getContentPane().add(newTaskPanel, "newTaskPanel");
        newTaskPanel.setLayout(new MigLayout("", 
                "[90px,grow][53px,grow][116px,grow]", "[16px][22px][22px]"));

        JLabel lblTaskDescription = new JLabel("Task Description:");
        newTaskPanel.add(lblTaskDescription, 
                "cell 0 0,alignx left,aligny center");

        JLabel lblPomodorosAssigned = new JLabel("Pomodoros Assigned:");
        newTaskPanel.add(lblPomodorosAssigned, 
                "cell 2 0,alignx left,aligny center");

        taskTextField = new JTextField();
        newTaskPanel.add(taskTextField, "cell 0 1 2 1,grow");
        taskTextField.setColumns(10);

        numPomsSpinner = new JSpinner();
        newTaskPanel.add(numPomsSpinner, "cell 2 1,grow");
        numPomsSpinner.setModel(new SpinnerNumberModel(1, 1, 15, 1));

        JLabel lblWhenToDo = new JLabel("Do Task:");
        newTaskPanel.add(lblWhenToDo, 
                "flowx,cell 0 2,alignx left,aligny center");
        
        btnConfirm = new JButton("Confirm");
        btnConfirm.setActionCommand("Add New Task");
        btnConfirm.addActionListener(controller);
        newTaskPanel.add(btnConfirm, "cell 2 2,alignx center,growy");
        
        moveToComboBox = new JComboBox();
        newTaskPanel.add(moveToComboBox, "cell 0 2,grow");
        moveToComboBox.setModel(new DefaultComboBoxModel(
                new String[] {"Now", "Later"}));
        
        JPanel actionPanel = new JPanel();
        getContentPane().add(actionPanel, "actionPanel");
        actionPanel.setLayout(new MigLayout(
                "", "[101px,grow][127px,grow]", "[25px,grow]"));
        
        JButton btnStopCurrentTask = new JButton("Stop Current Task");
        actionPanel.add(btnStopCurrentTask, "cell 0 0,alignx center,growy");
        btnStopCurrentTask.setActionCommand("Stop");
        
        JButton btnDoNewTask = new JButton("Do New Task");
        actionPanel.add(btnDoNewTask, "cell 1 0,alignx center,growy");
        btnDoNewTask.setActionCommand("Do New Task");
        btnDoNewTask.addActionListener(controller);
        btnStopCurrentTask.addActionListener(controller);
        
        changeCard("actionPanel");

    }
    
    private void changeCard(final String card) {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), card);
    }
    
    /**
     * Sets the interruptDialog to display the NewTaskPanel form.
     */
    
    public final void viewNewTaskForm() {
        changeCard("newTaskPanel");
    }
    
    /**
     * Gets the new task input from the NewTaskPanel form.
     * @return an instance of {@link #optitask.util.Task}
     */
    
    public final Task getNewTask() {
        if (taskTextField.getText().isEmpty()) {
            return new Task();
        }
            
        Task newTask = new Task(taskTextField.getText(), false, 
                Integer.parseInt(numPomsSpinner.getValue().toString()), 0);
        return newTask;
    }
    
    /**
     * Gets the designated location for the new task to be saved. 
     * Can be either the To Do List or Task Inventory.
     * @return string representation of the new task save location
     */
    
    public final String getNewTaskLocation() {
        return moveToComboBox.getSelectedItem().toString();
    }

}
