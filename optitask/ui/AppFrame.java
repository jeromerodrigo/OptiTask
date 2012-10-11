package optitask.ui;

import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import net.miginfocom.swing.MigLayout;
import optitask.AppController;
import optitask.store.AppPersistence;
import optitask.util.UIToolkit;

/**
 * AppFrame.java <br />
 * Purpose: Displays the top-level window for components.
 * @author Jerome Rodrigo
 * @since 0.8
 */

public class AppFrame extends JFrame {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -1255058389795622257L;

    /**
     * Stores the reference to the persistence module.
     * @see #AppFrame(optitask.store.AppPersistence)
     * @see AppPersistence
     */
    private AppPersistence model;

    /**
     * Stores an instance of the controller.
     * It is instantiated in {@link #AppFrame(optitask.store.AppPersistence)}.
     * @see AppController
     */
    private AppController controller;

    /**
     * Component of the frame which displays the timer and its controls.
     * @see TimerPanel
     */
    private TimerPanel timerPanel;

    /**
     * Creates the frame.
     * <B>Not Used.</B>
     */

    public AppFrame() {
        setNimbusLookAndFeel();
        initialize();
    }

    /**
     * Creates and initialises the view.
     * @param mdl an instance of the persistence module
     */

    public AppFrame(final AppPersistence mdl) {
        model = mdl;
        controller = new AppController(mdl, this);
        setNimbusLookAndFeel();
        initialize();
    }

    /**
     * Creates the user interface.
     */

    private void initialize() {
        setTitle("OptiTask");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 270);
        setResizable(false);
        UIToolkit.centerThisFrame(this);
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                AppFrame.class.getResource("/optitask/assests/appIcon.gif")));
        getContentPane().setLayout(new MigLayout(
                "", "[40px][][][]", "[185px][][30px]"));

        timerPanel = new TimerPanel(model, controller);
        getContentPane().add(timerPanel, "cell 0 0 4 1,grow");

        JSeparator separator = new JSeparator();
        getContentPane().add(separator, "cell 0 1 4 1,growx,aligny center");

        JButton btnManageTasks = new JButton("To Do List");
        btnManageTasks.setIcon(new ImageIcon(AppFrame.class
                .getResource("/optitask/assests/pencil.gif")));
        btnManageTasks.setActionCommand("Open Manage Tasks");
        btnManageTasks.addActionListener(controller);
        getContentPane().add(btnManageTasks, "cell 0 2,alignx left,growy");

        JButton btnTaskInventory = new JButton("Task Inventory");
        btnTaskInventory.setActionCommand("Open Task Inventory");
        btnTaskInventory.addActionListener(controller);
        getContentPane().add(btnTaskInventory, "cell 1 2,grow");

        JButton btnSettings = new JButton("");
        btnSettings.setIcon(new ImageIcon(AppFrame.class
                .getResource("/optitask/assests/settings.gif")));
        btnSettings.setActionCommand("Open Settings");
        btnSettings.addActionListener(controller);
        getContentPane().add(btnSettings, "cell 2 2,grow");

        JButton btnAbout = new JButton("");
        btnAbout.setActionCommand("Open About");
        btnAbout.addActionListener(controller);
        btnAbout.setIcon(new ImageIcon(AppFrame.class
                .getResource("/optitask/assests/star.gif")));
        getContentPane().add(btnAbout, "cell 3 2,grow");
    }

    /**
     * Starts the timer.
     * @see TimerPanel
     */

    public final void startTimer() {
        timerPanel.startTimer();
    }

    /**
     * Stops the timer.
     * @see TimerPanel
     */

    public final void stopTimer() {
        timerPanel.stopTimer();
    }

    /**
     * Resets the current cycle.
     * @see TimerPanel
     */

    public final void resetCycle() {
        timerPanel.resetCycle();
    }
    
    /**
     * Resets the button to the 'Start' state.
     * @see TimerPanel
     */
    
    public final void resetButtonState() {
        timerPanel.resetButtonState();
    }

    /**
     * Sets the Nimbus look and feel for the user interface.
     */

    private void setNimbusLookAndFeel() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equalsIgnoreCase(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look
            // and feel.
            e.printStackTrace();
        }
    }
}
