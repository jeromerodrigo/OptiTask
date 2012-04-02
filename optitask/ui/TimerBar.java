package optitask.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import optitask.store.AppPersistence;
import optitask.util.Settings;

/**
 * @author Jerome
 *
 */
public class TimerBar extends JProgressBar implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2250979200884046826L;
	private AppPersistence model;
	private static long pomodoroTime = 0;
	private static long breakTime = 0;
	private static long ePomodoroTime = 0; //Elapsed time for pomodoroTime
	private static long eBreakTime = 0; //Elapsed time for breakTime
	private static int currentCycle = 0; //Determine the current iteration before increment is added
	private Timer timer;
	private CurrentTaskPanel taskPanel;
	
	public TimerBar() {
		
	}
	
	public TimerBar(AppPersistence model, CurrentTaskPanel taskPanel) {
		super(JProgressBar.HORIZONTAL, 0, 0);
		this.taskPanel = taskPanel;
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
		taskPanel.setStatus(3);
	}
	
	private void resetTimer() {
		TimerBar.pomodoroTime = model.getSettings().getPomodoroTime() / 1000;
		TimerBar.breakTime = model.getSettings().getBreakTime() / 1000;
		
		setValue((int) pomodoroTime);
		ePomodoroTime = pomodoroTime; //reset time
		eBreakTime = 0;
		setMaximum((int) pomodoroTime);
	}
	
	private String getElapsedTime(long elapsedTime) {
		String format = String.format("%%0%dd", 2);
		return String.format(format, elapsedTime / 3600) + 
				":" + String.format(format, (elapsedTime % 3600) / 60) + 
				":" + String.format(format, elapsedTime % 60);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(ePomodoroTime == pomodoroTime) {
			taskPanel.setStatus(CurrentTaskPanel.WORKING);
		}
		
		if(eBreakTime == 0) {
			setString(getElapsedTime(ePomodoroTime));
			setValue((int) ePomodoroTime);
			ePomodoroTime--;
		}		
		
		if(ePomodoroTime == -1) {
			setString(getElapsedTime(eBreakTime));
			setValue((int) eBreakTime);
			eBreakTime++;
		}
		
		if(eBreakTime == 1) {
			playSound();
			setMaximum((int) breakTime);
			taskPanel.setStatus(CurrentTaskPanel.BREAK);
		}
		
		if(eBreakTime >= breakTime+1) {
			timer.stop();
			playSound();
			setString("Done!");
			taskPanel.markAsDone(); //Tell the task panel that the current task is completed
			taskPanel.setStatus(3);
			
			if(model.getSettings().isWillIncrement()) { //Increments the break time depending on settings
				currentCycle++;
				if(currentCycle >= model.getSettings().getIncrementInterval()) {
					currentCycle = 0;
					int incBrkTime = (int) (model.getSettings().getIncrementValue() / 1000);
					breakTime = breakTime + incBrkTime;
					updateBreakTime();
				}
			}			
		}
	}
	
	/**
	 * Updates the serialised breakTime with the current incremented breakTime
	 */
	
	private void updateBreakTime() {
		Settings set = model.getSettings();
		set.setBreakTime(breakTime * 1000); //Serialised as milliseconds
		model.saveSettings(set);
	}
	
	/*
	 * Java sound playing method provided by Pek
	 * Source: http://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
	 */
	
	private static synchronized void playSound() {
		final String url = "/optitask/assests/notify.wav";
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(TimerBar.class.getResourceAsStream(url));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}).start();
	}

}
