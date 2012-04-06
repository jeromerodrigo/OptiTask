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
 * TimerBar.java <br />
 * Purpose: Provides a controllable timer in a form of a {@link JProgressBar}.
 * @author Jerome
 * @version 0.8.1
 * @since 0.8
 */

public class TimerBar extends JProgressBar implements ActionListener {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -2250979200884046826L;

    /**
     * The application persistence module.
     * @see AppPersistence
     */
    private AppPersistence model;

    /**
     * The pomodoro time.
     * The timer will begin counting down, starting at this value.
     */
    private static long pomodoroTime = 0;

    /**
     * The break time.
     * The timer will begin counting up to this value.
     */
    private static long breakTime = 0;

    /**
     * The elapsed pomodoro time.
     */
    private static long ePomodoroTime = 0;

    /**
     * The elapsed break time.
     */
    private static long eBreakTime = 0;

    /**
     * This variable determines how many times the timer has iterated,
     * or completed its cycle.
     */
    private static int currentCycle = 0;

    /**
     * Flag whether the current cycle is a long break cycle.
     */
    private static boolean isLongBreakNow = false;

    /**
     * The swing timer.
     */
    private static Timer timer;

    /**
     * The current task panel.
     */
    private static CurrentTaskPanel taskPanel;

    /**
     * The constant for number of seconds in 1 hour.
     */
    private static final int HOUR_MULT = 3600;

    /**
     * The constant for number of seconds in 1 minute.
     */
    private static final int MIN_MULT = 60;

    /**
     * The constant for number of milliseconds in 1 second.
     */
    private static final int MILLI_MULT = 1000;

    /**
     * Default constructor.
     * <B>Not used</B>.
     */

    public TimerBar() {
    }

    /**
     * Creates the progress bar.
     * @param mdl     the persistence module
     * @param tskPnl the current task panel
     */

    public TimerBar(final AppPersistence mdl,
            final CurrentTaskPanel tskPnl) {
        super(SwingConstants.HORIZONTAL, 0, 0);
        taskPanel = tskPnl;
        model = mdl;
        timer = new Timer(MILLI_MULT, this);
    }

    /**
     * Starts the timer.
     */

    public final void start() {
        resetTimer();
        timer.start();
        setStringPainted(true);
        playSound();
    }

    /**
     * Stops the timer.
     */

    public final void stop() {
        setStringPainted(false);
        timer.stop();
        resetTimer();
        taskPanel.setStatus(CurrentTaskPanel.NULL);
    }

    /**
     * Method to reset the timer.
     * Refreshes all the timer constants from the data file.
     * @see #start()
     * @see #stop()
     */

    private void resetTimer() {
        TimerBar.pomodoroTime = model.getSettings()
                .getPomodoroTime() / MILLI_MULT;

        if (isLongBreakNow) {
            TimerBar.breakTime = model.getSettings()
                    .getLongBreak() / MILLI_MULT;
        } else {
            TimerBar.breakTime = model.getSettings()
                    .getShortBreak() / MILLI_MULT;
        }

        setValue((int) pomodoroTime);
        ePomodoroTime = pomodoroTime; // reset time
        eBreakTime = 0;
        setMaximum((int) pomodoroTime);
    }

    /**
     * Method to get a formatted time stamp.
     * @param elapsedTime the value of time in seconds
     * @return a {@link String} representation of the time
     */

    private String getElapsedTime(final long elapsedTime) {
        String format = String.format("%%0%dd", 2);
        return String.format(format, elapsedTime / HOUR_MULT) + ":"
        + String.format(format, (elapsedTime % HOUR_MULT) / MIN_MULT) + ":"
        + String.format(format, elapsedTime % MIN_MULT);
    }

    @Override
    public final void actionPerformed(final ActionEvent e) {
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
            if (isLongBreakNow) {
                taskPanel.setStatus(CurrentTaskPanel.LONG_BREAK);
            } else {
                taskPanel.setStatus(CurrentTaskPanel.SHORT_BREAK);
            }
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
                if (currentCycle >= model.getSettings()
                        .getIncrementInterval() - 1) {
                    isLongBreakNow = true;
                    currentCycle = 0; // Reset the cycle
                } else {
                    isLongBreakNow = false;
                    currentCycle++;
                }
            }
        }
    }

    /**
     * Plays a sound file.
     */

    private static synchronized void playSound() {
        final String url = "/optitask/assests/notify.wav";
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    //FIXME thread does not terminate after done playing
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
