package optitask.ui;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import optitask.AppController;
import optitask.store.AppPersistence;
import optitask.util.Settings;

public class SettingsDialog extends JDialog {

	private static final long serialVersionUID = 8299983443943142835L;
	private AppPersistence model;
	private AppController controller;
	private JSpinner shortBreakTimeSpinner;
	private JSpinner longBreakAfterSpinner;
	private JSpinner pomodoroTimeSpinner;
	private JSpinner longBreakTimeSpinner;
	private JCheckBox willLongBreakCheckBox;
	private JLabel lblLongBreak;
	private JLabel lblLongBreakAfter;

	public SettingsDialog() {
		initialize();
	}

	public SettingsDialog(AppPersistence model, AppController controller) {
		this.controller = controller;
		this.model = model;
		initialize();
		if (!model.getSettings().isWillLongBreak())
			setEnabledIncrementSettings(false);
	}

	private void initialize() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				SettingsDialog.class
				.getResource("/optitask/assests/settings.gif")));
		setSize(320, 340);
		setModal(true);
		setResizable(false);
		setTitle("Settings");
		getContentPane().setLayout(null);

		JLabel lblShortBreak = new JLabel("Short Break Time:");
		lblShortBreak.setBounds(10, 13, 125, 25);
		getContentPane().add(lblShortBreak);

		shortBreakTimeSpinner = new JSpinner();
		lblShortBreak.setLabelFor(shortBreakTimeSpinner);
		shortBreakTimeSpinner.setModel(new SpinnerNumberModel(1, 1, 30, 1));
		shortBreakTimeSpinner.setBounds(188, 14, 50, 25);
		preventKeyboardInputJSpinner(shortBreakTimeSpinner);
		getContentPane().add(shortBreakTimeSpinner);

		JPanel longBreakTimePanel = new JPanel();
		longBreakTimePanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Long Break Settings",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		longBreakTimePanel.setBounds(6, 81, 296, 151);
		getContentPane().add(longBreakTimePanel);
		longBreakTimePanel.setLayout(null);

		lblLongBreakAfter = new JLabel("Long Break After:");
		lblLongBreakAfter.setBounds(25, 37, 116, 25);
		longBreakTimePanel.add(lblLongBreakAfter);
		lblLongBreakAfter.setLabelFor(longBreakAfterSpinner);

		longBreakAfterSpinner = new JSpinner();
		longBreakAfterSpinner.setBounds(153, 38, 50, 25);
		longBreakTimePanel.add(longBreakAfterSpinner);
		longBreakAfterSpinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		preventKeyboardInputJSpinner(longBreakAfterSpinner);

		lblLongBreak = new JLabel("Long Break Time:");
		lblLongBreak.setBounds(25, 75, 116, 25);
		longBreakTimePanel.add(lblLongBreak);
		lblLongBreak.setLabelFor(longBreakTimeSpinner);

		longBreakTimeSpinner = new JSpinner();
		longBreakTimeSpinner.setModel(new SpinnerNumberModel(1, 1, 60, 1));
		longBreakTimeSpinner.setBounds(153, 76, 50, 25);
		longBreakTimePanel.add(longBreakTimeSpinner);
		preventKeyboardInputJSpinner(longBreakTimeSpinner);

		JLabel lblToggleLongBreak = new JLabel("Toggle Long Break:");
		lblToggleLongBreak.setBounds(25, 113, 116, 25);
		longBreakTimePanel.add(lblToggleLongBreak);

		willLongBreakCheckBox = new JCheckBox("");
		willLongBreakCheckBox.setActionCommand("Toggle Increment");
		willLongBreakCheckBox.addActionListener(controller);
		willLongBreakCheckBox.setBounds(217, 115, 50, 23);
		longBreakTimePanel.add(willLongBreakCheckBox);

		JLabel lblMinutes3 = new JLabel("minutes");
		lblMinutes3.setBounds(215, 79, 52, 16);
		longBreakTimePanel.add(lblMinutes3);

		JLabel lblCycles = new JLabel("pomodoros");
		lblCycles.setBounds(215, 41, 69, 16);
		longBreakTimePanel.add(lblCycles);

		JLabel lblTaskDuration = new JLabel("Pomodoro Time:");
		lblTaskDuration.setBounds(10, 46, 125, 25);
		getContentPane().add(lblTaskDuration);

		pomodoroTimeSpinner = new JSpinner();
		lblTaskDuration.setLabelFor(pomodoroTimeSpinner);
		pomodoroTimeSpinner.setModel(new SpinnerNumberModel(1, 1, 60, 1));
		pomodoroTimeSpinner.setBounds(188, 47, 50, 25);
		preventKeyboardInputJSpinner(pomodoroTimeSpinner);
		getContentPane().add(pomodoroTimeSpinner);

		JButton btnDefault = new JButton("Default");
		btnDefault.setActionCommand("Default Settings");
		btnDefault.addActionListener(controller);
		btnDefault.setBounds(10, 260, 95, 39);
		getContentPane().add(btnDefault);

		JButton btnSave = new JButton("Save");
		btnSave.setActionCommand("Save Settings");
		btnSave.addActionListener(controller);
		btnSave.setBounds(207, 260, 95, 39);
		getContentPane().add(btnSave);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 245, 314, 2);
		getContentPane().add(separator);

		JLabel lblMinutes1 = new JLabel("minutes");
		lblMinutes1.setBounds(250, 17, 52, 16);
		getContentPane().add(lblMinutes1);

		JLabel lblMinutes2 = new JLabel("minutes");
		lblMinutes2.setBounds(250, 52, 52, 16);
		getContentPane().add(lblMinutes2);

		setSettings(model.getSettings());
	}

	public Settings getSettings() {
		Settings set = new Settings();
		set.setShortBreak(minutesToMilliseconds((Integer) shortBreakTimeSpinner
				.getValue()));
		set.setIncrementInterval((Integer) longBreakAfterSpinner.getValue());
		set.setLongBreak((minutesToMilliseconds((Integer) longBreakTimeSpinner
				.getValue())));
		set.setPomodoroTime(minutesToMilliseconds((Integer) pomodoroTimeSpinner
				.getValue()));
		set.setWillLongBreak(willLongBreakCheckBox.isSelected());
		return set;
	}

	public void setSettings(Settings settings) {
		shortBreakTimeSpinner.setValue(millisecondsToMinutes(settings
				.getShortBreak()));
		longBreakAfterSpinner.setValue(settings.getIncrementInterval());
		pomodoroTimeSpinner.setValue(millisecondsToMinutes(settings
				.getPomodoroTime()));
		longBreakTimeSpinner.setValue(millisecondsToMinutes(settings
				.getLongBreak()));
		willLongBreakCheckBox.setSelected(settings.isWillLongBreak());
	}

	public void setEnabledIncrementSettings(boolean bEnabled) {
		longBreakAfterSpinner.setEnabled(bEnabled);
		longBreakTimeSpinner.setEnabled(bEnabled);
		lblLongBreak.setEnabled(bEnabled);
		lblLongBreakAfter.setEnabled(bEnabled);
	}

	public boolean isEnabledIncrementSettings() {
		return longBreakAfterSpinner.isEnabled()
				&& longBreakTimeSpinner.isEnabled();
	}

	private int millisecondsToMinutes(long milliseconds) {
		return (int) (milliseconds / 1000) / 60;
	}

	private long minutesToMilliseconds(int minutes) {
		return (long) minutes * 1000 * 60;
	}

	private void preventKeyboardInputJSpinner(JSpinner spinner) {
		JFormattedTextField tf = ((JSpinner.DefaultEditor) spinner.getEditor())
				.getTextField();
		tf.setEditable(false);
		tf.setBackground(Color.WHITE);
	}
}
