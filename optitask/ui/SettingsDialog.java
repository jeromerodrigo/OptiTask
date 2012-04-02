package optitask.ui;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JSeparator;

import optitask.AppController;
import optitask.store.AppPersistence;
import optitask.util.Settings;
import java.awt.Toolkit;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;


public class SettingsDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8299983443943142835L;
	private AppPersistence model;
	private AppController controller;
	private JSpinner breakTimeSpinner;
	private JSpinner incIntSpinner;
	private JSpinner taskDurationSpinner;
	private JSpinner incValSpinner;
	private JCheckBox incCheckBox;
	private JLabel lblIncrementValue;
	private JLabel lblIncrementInterval;

	public SettingsDialog() {		
		initialize();
	}
	
	public SettingsDialog(AppPersistence model, AppController controller) {
		this.controller = controller;
		this.model = model;
		initialize();
		if(!model.getSettings().isWillIncrement())
			setEnabledIncrementSettings(false);
	}
	
	private void initialize() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(SettingsDialog.class.getResource("/optitask/assests/settings.gif")));		
		setSize(300, 320);
		setModal(true);
		setResizable(false);
		setTitle("Settings");
		getContentPane().setLayout(null);
		
		JLabel lblBreakTime = new JLabel("Break Time (min):");
		lblBreakTime.setBounds(10, 17, 125, 14);
		getContentPane().add(lblBreakTime);
		
		breakTimeSpinner = new JSpinner();
		lblBreakTime.setLabelFor(breakTimeSpinner);
		breakTimeSpinner.setModel(new SpinnerNumberModel(0, 0, 30, 1));
		breakTimeSpinner.setBounds(234, 12, 50, 25);
		preventKeyboardInputJSpinner(breakTimeSpinner); //Prevents keyboard input
		getContentPane().add(breakTimeSpinner);
		
		JPanel breakTimePanel = new JPanel();
		breakTimePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Break Time Increments", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		breakTimePanel.setBounds(6, 81, 278, 120);
		getContentPane().add(breakTimePanel);
		breakTimePanel.setLayout(null);
		
		lblIncrementInterval = new JLabel("Increment Interval:");
		lblIncrementInterval.setBounds(25, 21, 149, 14);
		breakTimePanel.add(lblIncrementInterval);
		lblIncrementInterval.setLabelFor(incIntSpinner);
		
		incIntSpinner = new JSpinner();
		incIntSpinner.setBounds(218, 16, 50, 25);
		breakTimePanel.add(incIntSpinner);
		incIntSpinner.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		preventKeyboardInputJSpinner(incIntSpinner);
		
		lblIncrementValue = new JLabel("Increment Value (min):");
		lblIncrementValue.setBounds(25, 57, 149, 14);
		breakTimePanel.add(lblIncrementValue);
		lblIncrementValue.setLabelFor(incValSpinner);
		
		incValSpinner = new JSpinner();
		incValSpinner.setBounds(218, 52, 50, 25);
		breakTimePanel.add(incValSpinner);
		preventKeyboardInputJSpinner(incValSpinner);
		
		JLabel lblToggleBreakTime = new JLabel("Toggle Increments:");
		lblToggleBreakTime.setBounds(25, 88, 149, 14);
		breakTimePanel.add(lblToggleBreakTime);
		
		incCheckBox = new JCheckBox("");
		incCheckBox.setActionCommand("Toggle Increment");
		incCheckBox.addActionListener(controller);
		incCheckBox.setBounds(218, 84, 50, 23);
		breakTimePanel.add(incCheckBox);
		
		JLabel lblTaskDuration = new JLabel("Task Duration (min):");
		lblTaskDuration.setBounds(10, 50, 125, 14);
		getContentPane().add(lblTaskDuration);
		
		taskDurationSpinner = new JSpinner();
		lblTaskDuration.setLabelFor(taskDurationSpinner);
		taskDurationSpinner.setModel(new SpinnerNumberModel(0, 0, 60, 1));
		taskDurationSpinner.setBounds(234, 45, 50, 25);
		preventKeyboardInputJSpinner(taskDurationSpinner); //Prevents keyboard input
		getContentPane().add(taskDurationSpinner);
		
		JButton btnDefault = new JButton("Default");
		btnDefault.setActionCommand("Default Settings");
		btnDefault.addActionListener(controller);
		btnDefault.setBounds(10, 242, 95, 39);
		getContentPane().add(btnDefault);
		
		JButton btnSave = new JButton("Save");
		btnSave.setActionCommand("Save Settings");
		btnSave.addActionListener(controller);
		btnSave.setBounds(189, 242, 95, 39);
		getContentPane().add(btnSave);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 229, 294, 2);
		getContentPane().add(separator);
		
		setSettings(model.getSettings());
	}
	
	public Settings getSettings() {
		Settings set = new Settings();
		set.setBreakTime(minutesToMilliseconds((Integer) breakTimeSpinner.getValue()));
		set.setIncrementInterval((Integer) incIntSpinner.getValue());
		set.setIncrementValue((minutesToMilliseconds((Integer) incValSpinner.getValue())));
		set.setPomodoroTime(minutesToMilliseconds((Integer) taskDurationSpinner.getValue()));
		set.setWillIncrement(incCheckBox.isSelected());
		return set;
	}
	
	public void setSettings(Settings settings) {
		breakTimeSpinner.setValue(millisecondsToMinutes(settings.getBreakTime()));
		incIntSpinner.setValue(settings.getIncrementInterval());
		taskDurationSpinner.setValue(millisecondsToMinutes(settings.getPomodoroTime()));
		incValSpinner.setValue(millisecondsToMinutes(settings.getIncrementValue()));
		incCheckBox.setSelected(settings.isWillIncrement());
	}
	
	public void setEnabledIncrementSettings(boolean bEnabled) {
		incIntSpinner.setEnabled(bEnabled);
		incValSpinner.setEnabled(bEnabled);
		lblIncrementValue.setEnabled(bEnabled);
		lblIncrementInterval.setEnabled(bEnabled);
	}
	
	public boolean isEnabledIncrementSettings() {
		return incIntSpinner.isEnabled() && incValSpinner.isEnabled();
	}
	
	private int millisecondsToMinutes(long milliseconds) {
		return (int) (milliseconds / 1000) / 60;
	}
	
	private long minutesToMilliseconds(int minutes) {
		return (long) minutes * 1000 * 60;
	}
	
	private void preventKeyboardInputJSpinner(JSpinner spinner) {
		JFormattedTextField tf = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
		tf.setEditable(false);
		tf.setBackground(Color.WHITE);
	}
}
