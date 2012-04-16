package optitask.ui;

import java.awt.CardLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import optitask.AppController;

/**
 * InterruptDialog.java <br />
 * Purpose: Display a selection dialog when user is interrupted.
 * @author Jerome
 * @version 0.8.3
 * @since 0.8.3
 */

public class InterruptDialog extends JDialog {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -4306038905970650449L;

    private static AppController controller;

    /**
     * Create the dialog.
     */
    public InterruptDialog() {
        initialize();
    }

    public InterruptDialog(AppController cntrllr) {
        controller = cntrllr;
        initialize();
    }

    private void initialize() {
        setResizable(false);
        setTitle("Interrupt Task");
        setBounds(100, 100, 300, 170);
        getContentPane().setLayout(new MigLayout(
                "", "[grow][grow]", "[][grow]"));

        JLabel lblInterruptionType = new JLabel("Interruption Type:");
        getContentPane().add(lblInterruptionType, "cell 0 0,alignx leading");

        JComboBox<String> interruptTypeComboBox = new JComboBox<String>();
        interruptTypeComboBox.addActionListener(controller);
        interruptTypeComboBox.setModel(new DefaultComboBoxModel<String>(
                new String[] {"Internal", "External"}));
        getContentPane().add(interruptTypeComboBox, "cell 1 0,growx");

        JPanel interruptPanel = new JPanel();
        getContentPane().add(interruptPanel, "cell 0 1 2 1,grow");
        interruptPanel.setLayout(new CardLayout(0, 0));

        JPanel externalPanel = new JPanel();
        interruptPanel.add(externalPanel, "name_21028651393001");
        externalPanel.setLayout(new MigLayout("", "[]", "[]"));

        JButton btnStopCurrentTask = new JButton("Stop Current Task");
        btnStopCurrentTask.setActionCommand("Stop Current Task");
        btnStopCurrentTask.addActionListener(controller);
        externalPanel.add(btnStopCurrentTask, "cell 0 0");

        JPanel internalPanel = new JPanel();
        interruptPanel.add(internalPanel, "name_21043212372761");
        internalPanel.setLayout(new MigLayout("", "[]", "[][][]"));

        JButton btnAddNewTask = new JButton("Add New Task");
        btnAddNewTask.setActionCommand("Add New Task");
        btnAddNewTask.addActionListener(controller);
        internalPanel.add(btnAddNewTask, "cell 0 0,growx");

        JButton btnMoveTaskTo = new JButton("Move Task to Inventory");
        btnMoveTaskTo.setActionCommand("Move Task to Inventory");
        btnMoveTaskTo.addActionListener(controller);
        internalPanel.add(btnMoveTaskTo, "cell 0 1,growx");

        JButton btnContinueTaskLater = new JButton("Continue Task Later");
        btnContinueTaskLater.setActionCommand("Continue Task Later");
        btnContinueTaskLater.addActionListener(controller);
        internalPanel.add(btnContinueTaskLater, "cell 0 2,growx");

    }

}
