package optitask.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import optitask.AppController;
import optitask.store.AppPersistence;

/**
 * AppFrame.java <br />
 * Purpose: Displays the top-level window for components.
 * @author Jerome
 * @version 0.8
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
        getContentPane().setLayout(null);
        setTitle("OptiTask");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 265);
        setResizable(false);
        centerThisFrame(this);
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                AppFrame.class.getResource("/optitask/assests/appIcon.gif")));

        timerPanel = new TimerPanel(model, controller);
        timerPanel.setBounds(0, 0, 344, 185);
        getContentPane().add(timerPanel);

        JButton btnSettings = new JButton("");
        btnSettings.setIcon(new ImageIcon(AppFrame.class
                .getResource("/optitask/assests/settings.gif")));
        btnSettings.setActionCommand("Open Settings");
        btnSettings.addActionListener(controller);
        btnSettings.setBounds(10, 196, 30, 30);
        getContentPane().add(btnSettings);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 70, 300, 2);
        getContentPane().add(separator);

        JButton btnManageTasks = new JButton("Manage Tasks");
        btnManageTasks.setIcon(new ImageIcon(AppFrame.class
                .getResource("/optitask/assests/pencil.gif")));
        btnManageTasks.setActionCommand("Open Manage Tasks");
        btnManageTasks.addActionListener(controller);
        btnManageTasks.setBounds(90, 196, 160, 30);
        getContentPane().add(btnManageTasks);

        JButton btnAbout = new JButton("");
        btnAbout.setActionCommand("Open About");
        btnAbout.addActionListener(controller);
        btnAbout.setIcon(new ImageIcon(AppFrame.class
                .getResource("/optitask/assests/star.gif")));
        btnAbout.setBounds(50, 196, 30, 30);
        getContentPane().add(btnAbout);
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
     * Centering frame method provided by Vipin Kumar Rajput, 2010.
     * <a href="http://www.esblog.in/2010/09/
     * centering-a-swing-window-on-screen/">
     * Centering a Window</a>
     * @param w an instance of a window frame
     */

    private void centerThisFrame(final Window w) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        w.setLocation(screenSize.width / 2 - (w.getWidth() / 2),
                screenSize.height / 2 - (w.getHeight() / 2));
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
