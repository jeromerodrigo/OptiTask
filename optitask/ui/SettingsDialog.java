package optitask.ui;

import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import optitask.AppController;
import optitask.store.AppPersistence;
import optitask.util.Settings;
import optitask.util.UIToolkit;

/**
 * SettingsDialog.java <br />
 * Purpose: Displays a dialog for users to edit the application settings.
 * @author Jerome Rodrigo
 * @since 0.8
 */

public class SettingsDialog extends JDialog {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 8299983443943142835L;

    /**
     * Stores a reference to the persistence module.
     * @see AppPersistence
     */
    private transient AppPersistence model;

    /**
     * Stores a reference to the application controller.
     * @see AppController
     */
    private transient AppController controller;

    /**
     * Allows the user to input the short break time.
     */
    private transient JSpinner shortBreakSpinner;

    /**
     * Allows the user to input the interval until a long break.
     */
    private transient JSpinner lBrkAfterSpinner;

    /**
     * Allows the user to input the pomodoro duration.
     */
    private transient JSpinner pomTimeSpinner;

    /**
     * Allows the user to input the long break time.
     */
    private transient JSpinner lBreakTimeSpinner;

    /**
     * Allows the user to input if a long break should occur
     * using a JCheckBox.
     */
    private transient JCheckBox wLBreakChkBox;

    /**
     * Displays the label for the long break.
     * @see #setEnabledIncrementSettings(boolean)
     */
    private transient JLabel lblLongBreak;

    /**
     * Displays the label for the long break after value.
     * @see #setEnabledIncrementSettings(boolean)
     */
    private transient JLabel lblLongBreakAfter;

    /**
     * Constant multiplier for conversion between milliseconds.
     */
    private static final int MILLI_MULT = 1000;

    /**
     * Constant for number of seconds in 1 minute.
     */
    private static final int MINUTE_IN_SECONDS = 60;

    /**
     * Creates the dialog.
     * <B>Not used</B>.
     */

    public SettingsDialog() {
        super();
        initialize();
    }

    /**
     * Creates and initialises the dialog.
     * @param mdl      the persistence module / model
     * @param cntrller the application controller
     */

    public SettingsDialog(final AppPersistence mdl,
            final AppController cntrller) {
        super();
        controller = cntrller;
        model = mdl;
        initialize();
        if (!mdl.getSettings().isWillLongBreak()) {
            setEnabledIncrementSettings(false);
        }
    }

    /**
     * Creates the user interface components.
     */

    private void initialize() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                SettingsDialog.class
                .getResource("/optitask/assests/settings.gif")));
        setSize(320, 300);
        setModal(true);
        setResizable(false);
        setTitle("Settings");
        getContentPane().setLayout(new MigLayout("",
                "[135px][53px][50px][64px]",
                "[26px][26px][151px][2px][39px]"));

        final JLabel lblShortBreak = new JLabel("Short Break Time:");
        getContentPane().add(lblShortBreak, "cell 0 0,grow");

        shortBreakSpinner = new JSpinner();
        lblShortBreak.setLabelFor(shortBreakSpinner);
        shortBreakSpinner.setModel(new SpinnerNumberModel(1, 1, 30, 1));
        UIToolkit.preventKeyboardInputJSpinner(shortBreakSpinner);
        getContentPane().add(shortBreakSpinner, "cell 2 0,grow");

        final JPanel lBrkTimePanel = new JPanel();
        lBrkTimePanel.setBorder(new TitledBorder(UIManager
                .getBorder("TitledBorder.border"), "Long Break Settings",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        getContentPane().add(lBrkTimePanel, "cell 0 2 4 1,grow");
        lBrkTimePanel.setLayout(new MigLayout("",
                "[137.00px][50px][69px]",
                "[26px][26px][25px]"));

        lblLongBreakAfter = new JLabel("Long Break After:");
        lBrkTimePanel.add(lblLongBreakAfter, "cell 0 0,grow");
        lblLongBreakAfter.setLabelFor(lBrkAfterSpinner);

        lBrkAfterSpinner = new JSpinner();
        lBrkTimePanel.add(lBrkAfterSpinner, "cell 1 0,grow");
        lBrkAfterSpinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
        UIToolkit.preventKeyboardInputJSpinner(lBrkAfterSpinner);

        lblLongBreak = new JLabel("Long Break Time:");
        lBrkTimePanel.add(lblLongBreak, "cell 0 1,grow");
        lblLongBreak.setLabelFor(lBreakTimeSpinner);

        lBreakTimeSpinner = new JSpinner();
        lBreakTimeSpinner.setModel(new SpinnerNumberModel(1, 1, 60, 1));
        lBrkTimePanel.add(lBreakTimeSpinner, "cell 1 1,grow");
        UIToolkit.preventKeyboardInputJSpinner(lBreakTimeSpinner);

        final JLabel lblToggleLBrk = new JLabel("Toggle Long Break:");
        lBrkTimePanel.add(lblToggleLBrk, "cell 0 2,grow");

        wLBreakChkBox = new JCheckBox("");
        wLBreakChkBox.setActionCommand("Toggle Increment");
        wLBreakChkBox.addActionListener(controller);
        lBrkTimePanel.add(wLBreakChkBox,
                "cell 2 2,growx,aligny bottom");

        final JLabel lblMinutes3 = new JLabel("minutes");
        lBrkTimePanel.add(lblMinutes3,
                "cell 2 1,alignx left,aligny center");

        final JLabel lblCycles = new JLabel("pomodoros");
        lBrkTimePanel.add(lblCycles, "cell 2 0,growx,aligny center");

        final JLabel lblTaskDuration = new JLabel("Pomodoro Time:");
        getContentPane().add(lblTaskDuration, "cell 0 1,grow");

        pomTimeSpinner = new JSpinner();
        lblTaskDuration.setLabelFor(pomTimeSpinner);
        pomTimeSpinner.setModel(new SpinnerNumberModel(1, 1, 60, 1));
        UIToolkit.preventKeyboardInputJSpinner(pomTimeSpinner);
        getContentPane().add(pomTimeSpinner, "cell 2 1,grow");

        final JButton btnDefault = new JButton("Default");
        btnDefault.setActionCommand("Default Settings");
        btnDefault.addActionListener(controller);
        getContentPane().add(btnDefault, "cell 2 4,alignx center,growy");

        final JButton btnSave = new JButton("Save");
        btnSave.setActionCommand("Save Settings");
        btnSave.addActionListener(controller);
        getContentPane().add(btnSave, "cell 3 4,alignx center,growy");

        final JSeparator separator = new JSeparator();
        getContentPane().add(separator, "cell 0 3 4 1,growx,aligny top");

        final JLabel lblMinutes1 = new JLabel("minutes");
        getContentPane().add(lblMinutes1, "cell 3 0,growx,aligny center");

        final JLabel lblMinutes2 = new JLabel("minutes");
        getContentPane().add(lblMinutes2, "cell 3 1,growx,aligny center");

        setSettings(model.getSettings());
    }

    /**
     * Gets the settings from the user interface components.
     * @return the {@link Settings} object
     */

    public final Settings getSettings() {
        Settings set = new Settings();
        set.setShortBreak(minutesToMilliseconds((Integer) shortBreakSpinner
                .getValue()));
        set.setIncrementInterval((Integer) lBrkAfterSpinner.getValue());
        set.setLongBreak(minutesToMilliseconds((Integer) lBreakTimeSpinner
                .getValue()));
        set.setPomodoroTime(minutesToMilliseconds((Integer) pomTimeSpinner
                .getValue()));
        set.setWillLongBreak(wLBreakChkBox.isSelected());

        // Long break value cannot be equal or less than short break
        if (set.getLongBreak() <= set.getShortBreak()) {
            set = new Settings();
        }
        
        return set;
    }

    /**
     * Sets the settings to be displayed.
     * @param settings the {@link Settings} to be set
     */

    public final void setSettings(final Settings settings) {
        shortBreakSpinner.setValue(millisecondsToMinutes(settings
                .getShortBreak()));
        lBrkAfterSpinner.setValue(settings.getIncrementInterval());
        pomTimeSpinner.setValue(millisecondsToMinutes(settings
                .getPomodoroTime()));
        lBreakTimeSpinner.setValue(millisecondsToMinutes(settings
                .getLongBreak()));
        wLBreakChkBox.setSelected(settings.isWillLongBreak());
    }

    /**
     * Sets the state whether the user interface components
     * related to the long break should be enabled or disabled.
     * @param bEnabled the flag to enabled/disable the components
     */

    public final void setEnabledIncrementSettings(final boolean bEnabled) {
        lBrkAfterSpinner.setEnabled(bEnabled);
        lBreakTimeSpinner.setEnabled(bEnabled);
        lblLongBreak.setEnabled(bEnabled);
        lblLongBreakAfter.setEnabled(bEnabled);
    }

    /**
     * Gets the state whether the long break settings are enabled or disabled.
     * @return <code>true</code> if the settings are enabled;
     *         <code>false</code> otherwise.
     */

    public final boolean isEnabledIncrementSettings() {
        return lBrkAfterSpinner.isEnabled()
                && lBreakTimeSpinner.isEnabled();
    }

    /**
     * Converts milliseconds to minutes.
     * @param milliseconds the value of time in milliseconds
     * @return value of time in minutes
     */

    private int millisecondsToMinutes(final long milliseconds) {
        return (int) (milliseconds / MILLI_MULT) / MINUTE_IN_SECONDS;
    }

    /**
     * Converts minutes to milliseconds.
     * @param minutes the value of time in minutes.
     * @return value of time in milliseconds
     */

    private long minutesToMilliseconds(final int minutes) {
        return (long) minutes * MILLI_MULT * MINUTE_IN_SECONDS;
    }

}
