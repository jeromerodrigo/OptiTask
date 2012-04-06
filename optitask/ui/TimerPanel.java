package optitask.ui;

import javax.swing.JButton;
import javax.swing.JPanel;

import optitask.AppController;
import optitask.store.AppPersistence;

/**
 * TimerPanel.java <br />
 * Purpose: Displays the timer-related components and allows the user to
 * control it.
 * @author Jerome
 * @version 0.8
 * @since 0.8
 */

public class TimerPanel extends JPanel {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -1094232436805661102L;

    /**
     * The application persistence module.
     * @see AppPersistence
     */
    private AppPersistence model;

    /**
     * The application controller.
     * @see AppController
     */
    private AppController controller;

    /**
     * The start button.
     */
    private JButton btnStart;

    /**
     * The timer bar displays the timer and its controls.
     * @see TimerBar
     */
    private TimerBar timer;

    /**
     * The current task panel displays the current task and status information.
     * @see CurrentTaskPanel
     */
    private CurrentTaskPanel currentTaskPanel;

    /**
     * Creates the panel.
     * <B>Not used</B>.
     */

    public TimerPanel() {
        initialize();
    }

    /**
     * Creates and initialises the panel.
     * @param mdl      the persistence module
     * @param cntrller the application controller
     */

    public TimerPanel(final AppPersistence mdl, final AppController cntrller) {
        controller = cntrller;
        model = mdl;
        initialize();
    }

    /**
     * Creates the user interface components.
     */

    private void initialize() {
        setLayout(null);
        setSize(340, 185);

        btnStart = new JButton("Start");
        btnStart.setActionCommand("Start");
        btnStart.setBounds(10, 10, 70, 50);
        btnStart.addActionListener(controller);
        add(btnStart);

        currentTaskPanel = new CurrentTaskPanel(model);
        currentTaskPanel.setBounds(10, 71, 320, 110);
        currentTaskPanel.setStatus(CurrentTaskPanel.NULL);
        add(currentTaskPanel);

        timer = new TimerBar(model, currentTaskPanel);
        timer.setSize(240, 50);
        timer.setLocation(90, 10);
        add(timer);

    }

    /**
     * Starts the timer.
     */

    public final void startTimer() {
        timer.start();
        btnStart.setText("Stop");
        btnStart.setActionCommand("Stop");
        currentTaskPanel.refresh();
        currentTaskPanel.nextTask();
    }

    /**
     * Stops the timer.
     */

    public final void stopTimer() {
        timer.stop();
        btnStart.setText("Start");
        btnStart.setActionCommand("Start");
        currentTaskPanel.refresh();
    }

    /**
     * Resets the current cycle to zero.
     * @see TimerBar
     */

    public final void resetCycle() {
        timer.resetCycle();
    }
}
