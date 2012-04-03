package optitask.ui;

import javax.swing.JButton;
import javax.swing.JPanel;

import optitask.AppController;
import optitask.store.AppPersistence;

public class TimerPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1094232436805661102L;
	private AppPersistence model;
	private AppController controller;
	private JButton btnStart;
	private TimerBar timer;
	private CurrentTaskPanel currentTaskPanel;

	public TimerPanel() {
		initialize();
	}

	public TimerPanel(AppPersistence model, AppController controller) {
		this.controller = controller;
		this.model = model;
		initialize();
	}

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

	public void startTimer() {
		timer.start();
		btnStart.setText("Stop");
		btnStart.setActionCommand("Stop");
		currentTaskPanel.refresh();
		currentTaskPanel.nextTask();
	}

	public void stopTimer() {
		timer.stop();
		btnStart.setText("Start");
		btnStart.setActionCommand("Start");
		currentTaskPanel.refresh();
	}
}
