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

    /**
     * The application controller.
     */
    private transient AppController controller;
    
    /**
     * Text field for the new task.
     */
    private transient JTextField taskTextField;

    /**
     * Used to select which list to move the task to.
     */
    private transient JComboBox moveToComboBox;

    /**
     * Used to select number of pomodoros for the new task.
     */
    private transient JSpinner numPomsSpinner;

    /**
     * Create the dialog.
     */
    public InterruptDialog() {
        super();
        initialize();
    }

    /**
     * Constructs an InterruptDialog.
     * @param cntrllr the application controller
     */
    
    public InterruptDialog(final AppController cntrllr) {
        super();
        controller = cntrllr;
        initialize();
    }

    /**
     * Initializes the UI components.
     */
    
    private void initialize() {
        setResizable(false);
        setTitle("Interrupt Task");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(true);
        setBounds(100, 100, 400, 120);
        getContentPane().setLayout(new CardLayout(0, 0));
        
        final JPanel newTaskPanel = new JPanel();
        getContentPane().add(newTaskPanel, "newTaskPanel");
        newTaskPanel.setLayout(new MigLayout("", 
                "[90px,grow][53px,grow][116px,grow]", "[16px][22px][22px]"));

        final JLabel lblTaskDesc = new JLabel("Task Description:");
        newTaskPanel.add(lblTaskDesc, 
                "cell 0 0,alignx left,aligny center");

        final JLabel lblPomsAssigned = new JLabel("Pomodoros Assigned:");
        newTaskPanel.add(lblPomsAssigned, 
                "cell 2 0,alignx left,aligny center");

        taskTextField = new JTextField();
        newTaskPanel.add(taskTextField, "cell 0 1 2 1,grow");
        taskTextField.setColumns(10);

        numPomsSpinner = new JSpinner();
        newTaskPanel.add(numPomsSpinner, "cell 2 1,grow");
        numPomsSpinner.setModel(new SpinnerNumberModel(1, 1, 15, 1));

        final JLabel lblWhenToDo = new JLabel("Do Task:");
        newTaskPanel.add(lblWhenToDo, 
                "flowx,cell 0 2,alignx left,aligny center");
        
        final JButton btnConfirm = new JButton("Confirm");
        btnConfirm.setActionCommand("Add New Task");
        btnConfirm.addActionListener(controller);
        newTaskPanel.add(btnConfirm, "cell 2 2,alignx center,growy");
        
        moveToComboBox = new JComboBox();
        newTaskPanel.add(moveToComboBox, "cell 0 2,grow");
        moveToComboBox.setModel(new DefaultComboBoxModel(
                new String[] {"Now", "Later"}));
        
        final JPanel actionPanel = new JPanel();
        getContentPane().add(actionPanel, "actionPanel");
        actionPanel.setLayout(new MigLayout(
                "", "[101px,grow][127px,grow]", "[25px,grow]"));
        
        final JButton btnStopTask = new JButton("Stop Current Task");
        actionPanel.add(btnStopTask, "cell 0 0,alignx center,growy");
        btnStopTask.setActionCommand("Stop");
        
        final JButton btnDoNewTask = new JButton("Do New Task");
        actionPanel.add(btnDoNewTask, "cell 1 0,alignx center,growy");
        btnDoNewTask.setActionCommand("Do New Task");
        btnDoNewTask.addActionListener(controller);
        btnStopTask.addActionListener(controller);
        
        changeCard("actionPanel");

    }
    
    /**
     * Changes the cardLayout to display the specified card.
     * @param card the name of the card
     */
    
    private void changeCard(final String card) {
        final CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), card);
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
            
        Task newTask;
        
        if (taskTextField.getText().isEmpty()) {
            newTask = new Task();
        } else {
            newTask = new Task(taskTextField.getText(), false, 
                    Integer.parseInt(numPomsSpinner.getValue().toString()), 0);
        }
        
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
