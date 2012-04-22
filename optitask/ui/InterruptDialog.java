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

    private AppController controller;

    public static final String INT_PANEL = "intPanel";

    public static final String EXT_PANEL = "extPanel";

    private JPanel interruptPanel;

    /**
     * Create the dialog.
     */
    public InterruptDialog() {
        initialize();
    }

    public InterruptDialog(AppController cntrllr) {
        controller = cntrllr;
        initialize();
        switchCard("blank");
    }

    private void initialize() {
        setResizable(false);
        setTitle("Interrupt Task");
        setModal(true);
        setBounds(100, 100, 300, 170);
        getContentPane().setLayout(new MigLayout(
                "", "[grow][grow]", "[][grow]"));

        JLabel lblInterruptionType = new JLabel("Interruption Type:");
        getContentPane().add(lblInterruptionType, "cell 0 0,alignx leading");

        JComboBox<String> interruptTypeComboBox = new JComboBox<String>();
        interruptTypeComboBox.setActionCommand("Change Interruption Type");
        interruptTypeComboBox.addActionListener(controller);
        interruptTypeComboBox.setModel(new DefaultComboBoxModel<String>(
                new String[] {"Internal", "External"}));
        interruptTypeComboBox.setSelectedIndex(-1);
        getContentPane().add(interruptTypeComboBox, "cell 1 0,growx");

        interruptPanel = new JPanel();
        getContentPane().add(interruptPanel, "cell 0 1 2 1,grow");
        interruptPanel.setLayout(new CardLayout(0, 0));

        JPanel externalPanel = new JPanel();
        interruptPanel.add(externalPanel, EXT_PANEL);
        externalPanel.setLayout(new MigLayout("", "[grow]", "[]"));

        JButton btnStopCurrentTask = new JButton("Stop Current Task");
        btnStopCurrentTask.setActionCommand("Stop");
        btnStopCurrentTask.addActionListener(controller);
        externalPanel.add(btnStopCurrentTask, "cell 0 0,alignx center");

        JPanel internalPanel = new JPanel();
        interruptPanel.add(internalPanel, INT_PANEL);
        internalPanel.setLayout(new MigLayout("", "[grow]", "[][][]"));

        JButton btnAddNewTask = new JButton("Add New Task");
        btnAddNewTask.setActionCommand("Add New Task");
        btnAddNewTask.addActionListener(controller);
        internalPanel.add(btnAddNewTask, "cell 0 0,alignx center");

        JButton btnMoveTaskTo = new JButton("Move Task to Inventory");
        btnMoveTaskTo.setActionCommand("Move Task to Inventory");
        btnMoveTaskTo.addActionListener(controller);
        internalPanel.add(btnMoveTaskTo, "cell 0 1,alignx center");

        JButton btnContinueTaskLater = new JButton("Continue Task Later");
        btnContinueTaskLater.setActionCommand("Continue Task Later");
        btnContinueTaskLater.addActionListener(controller);
        internalPanel.add(btnContinueTaskLater, "cell 0 2,alignx center");

        JPanel blankPanel = new JPanel();
        interruptPanel.add(blankPanel, "blank");

    }

    public void switchCard(String card) {
        CardLayout cl = (CardLayout) interruptPanel.getLayout();
        cl.show(interruptPanel, card);
    }

}
