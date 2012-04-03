package optitask.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import optitask.store.AppPersistence;

/**
 * @author Jerome
 * 
 */
public class TimerBar extends JProgressBar implements ActionListener {

	private static final long serialVersionUID = -2250979200884046826L;
	private AppPersistence model;
	private static long pomodoroTime = 0;
	private static long breakTime = 0;
	private static long ePomodoroTime = 0; // Elapsed time for pomodoroTime
	private static long eBreakTime = 0; // Elapsed time for breakTime
	private static int currentCycle = 0; // Determine the current iteration
	// before increment is added
	private static boolean isLongBreakNow = false;
	private static Timer timer;
	private static CurrentTaskPanel taskPanel;

	public TimerBar() {
	}

	public TimerBar(AppPersistence model, CurrentTaskPanel taskPanel) {
		super(SwingConstants.HORIZONTAL, 0, 0);
		TimerBar.taskPanel = taskPanel;
		this.model = model;
		timer = new Timer(1000, this);
	}

	public void start() {
		resetTimer();
		timer.start();
		setStringPainted(true);
	}

	public void stop() {
		setStringPainted(false);
		timer.stop();
		resetTimer();
		taskPanel.setStatus(CurrentTaskPanel.NULL);
	}

	private void resetTimer() {
		TimerBar.pomodoroTime = model.getSettings().getPomodoroTime() / 1000;

		if(isLongBreakNow)
			TimerBar.breakTime = model.getSettings().getLongBreak() / 1000;
		else
			TimerBar.breakTime = model.getSettings().getShortBreak() / 1000;

		setValue((int) pomodoroTime);
		ePomodoroTime = pomodoroTime; // reset time
		eBreakTime = 0;
		setMaximum((int) pomodoroTime);
	}

	private String getElapsedTime(long elapsedTime) {
		String format = String.format("%%0%dd", 2);
		return String.format(format, elapsedTime / 3600) + ":"
		+ String.format(format, (elapsedTime % 3600) / 60) + ":"
		+ String.format(format, elapsedTime % 60);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (ePomodoroTime == pomodoroTime) {
			taskPanel.setStatus(CurrentTaskPanel.WORKING);
		}

		if (eBreakTime == 0) {
			setString(getElapsedTime(ePomodoroTime));
			setValue((int) ePomodoroTime);
			ePomodoroTime--;
		}

		if (ePomodoroTime == -1) {
			setString(getElapsedTime(eBreakTime));
			setValue((int) eBreakTime);
			eBreakTime++;
		}

		if (eBreakTime == 1) {
			playSound();
			setMaximum((int) breakTime);
			if(isLongBreakNow)
				taskPanel.setStatus(CurrentTaskPanel.LONGBREAK);
			else
				taskPanel.setStatus(CurrentTaskPanel.SHORTBREAK);
		}

		if (eBreakTime >= breakTime + 1) {
			timer.stop();
			playSound();
			setString("Done!");
			taskPanel.markAsDone(); // Tell the task panel that the current task
			// is completed
			taskPanel.setStatus(CurrentTaskPanel.NULL);

			// Enables a long break after an interval
			if (model.getSettings().isWillLongBreak()) {
				if (currentCycle >= model.getSettings().getIncrementInterval()-1) {
					isLongBreakNow = true;
					currentCycle = 0; // Reset the cycle
				} else {
					isLongBreakNow = false;
					currentCycle++;
				}
			}
		}
	}

	/*
	 * Java sound playing method provided by Pek Source:
	 * http://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
	 */

	private static synchronized void playSound() {
		final String url = "/optitask/assests/notify.wav";
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(TimerBar.class
									.getResourceAsStream(url));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}).start();
	}
}
