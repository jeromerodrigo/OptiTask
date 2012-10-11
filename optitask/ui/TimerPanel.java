package optitask.ui;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import optitask.AppController;
import optitask.store.AppPersistence;

/**
 * TimerPanel.java <br />
 * Purpose: Displays the timer-related components and allows the user to
 * control it.
 * @author Jerome Rodrigo
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
    private transient AppPersistence model;

    /**
     * The application controller.
     * @see AppController
     */
    private transient AppController controller;

    /**
     * The start button.
     */
    private transient JButton btnStart;

    /**
     * The timer bar displays the timer and its controls.
     * @see TimerBar
     */
    private transient TimerBar timer;

    /**
     * The current task panel displays the current task and status information.
     * @see CurrentTaskPanel
     */
    private transient CurrentTaskPanel currentTaskPanel;
    
    /**
     * String constant for the literal 'Start'.
     */
    private static final String START = "Start";

    /**
     * Creates the panel.
     * <B>Not used</B>.
     */

    public TimerPanel() {
        super();
        initialize();
    }

    /**
     * Creates and initialises the panel.
     * @param mdl      the persistence module
     * @param cntrller the application controller
     */

    public TimerPanel(final AppPersistence mdl, final AppController cntrller) {
        super();
        controller = cntrller;
        model = mdl;
        initialize();
    }

    /**
     * Creates the user interface components.
     */

    private void initialize() {
        setSize(330, 180);

        btnStart = new JButton(START);
        btnStart.setActionCommand(START);
        btnStart.addActionListener(controller);
        setLayout(new MigLayout("", "[70px][240px]", "[50px][110px]"));
        add(btnStart, "cell 0 0,grow");

        currentTaskPanel = new CurrentTaskPanel(model);
        currentTaskPanel.setStatus(CurrentTaskPanel.NULL);
        add(currentTaskPanel, "cell 0 1 2 1,grow");

        timer = new TimerBar(model, currentTaskPanel);
        timer.addChangeListener(controller);
        add(timer, "cell 1 0,grow");

    }

    /**
     * Starts the timer.
     */

    public final void startTimer() {
        timer.start();
        btnStart.setText("Interrupt");
        btnStart.setActionCommand("Open Interrupt Dialog");
        currentTaskPanel.refresh();
        currentTaskPanel.nextTask();
    }

    /**
     * Stops the timer.
     */

    public final void stopTimer() {
        timer.stop();
        resetButtonState();
        currentTaskPanel.refresh();
    }
    
    /**
     * Resets the button to the 'Start' state.
     */
    
    public final void resetButtonState() {
        btnStart.setText(START);
        btnStart.setActionCommand(START);
    }

    /**
     * Resets the current cycle to zero.
     * @see TimerBar
     */

    public final void resetCycle() {
        timer.resetCycle();
    }
}
