package optitask.util;

import java.io.Serializable;

/**
 * Settings.java <br />
 * Purpose: Stores the settings of the application.
 * @author Jerome Rodrigo
 * @since 0.8
 */

public final class Settings implements Serializable {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 7131951061036321184L;

    /**
     * Stores the duration of a short break in milliseconds.
     * @serial
     */
    private long shortBreak;

    /**
     * Stores the duration of a long break in milliseconds.
     * @serial
     */
    private long longBreak;

    /**
     * Stores the value of the interval between long breaks.
     * @serial
     */
    private int incrementInterval;

    /**
     * Stores the duration of a pomodoro in milliseconds.
     * @serial
     */
    private long pomodoroTime;

    /**
     * Stores a flag whether a long break will occur.
     * @serial
     */
    private boolean willLongBreak;

    /**
     * The default constant for a short break.
     */
    private static final long SHORT_BREAK_TIME = 300000;

    /**
     * The default constant for a long break.
     */
    private static final long LONG_BREAK_TIME = 900000;

    /**
     * The default constant for the increment interval.
     */
    private static final int INC_INTERVAL = 4;

    /**
     * The default constant for a pomodoro duration.
     */
    private static final long POMODORO_TIME = 1500000;

    /**
     * The default constant for if a long break shall occur.
     */
    private static final boolean WILL_LONG_BREAK = true;

    /**
     * Creates and initialises the settings object with some default values.
     */

    public Settings() {
        this(SHORT_BREAK_TIME, LONG_BREAK_TIME, INC_INTERVAL,
                POMODORO_TIME, WILL_LONG_BREAK);
    }

    /**
     * Creates the settings object.
     * @param shrtBrk       duration of a short break
     * @param lngBrk        duration of a long break
     * @param itrVal        value of cycles until a long break
     * @param pomoTime      duration of a pomodoro
     * @param willLngBrk    a flag whether a long break will occur
     */

    public Settings(final long shrtBrk, final long lngBrk,
            final int itrVal, final long pomoTime,
            final boolean willLngBrk) {
        shortBreak = shrtBrk;
        longBreak = lngBrk;
        incrementInterval = itrVal;
        pomodoroTime = pomoTime;
        willLongBreak = willLngBrk;
    }

    /**
     * @return the willIncrement
     */
    public boolean isWillLongBreak() {
        return willLongBreak;
    }

    /**
     * @param willLngBrk the willLongBreak to set
     */
    public void setWillLongBreak(final boolean willLngBrk) {
        willLongBreak = willLngBrk;
    }

    /**
     * @return the breakTime
     */
    public long getShortBreak() {
        return shortBreak;
    }

    /**
     * @param breakTime the breakTime to set
     */
    public void setShortBreak(final long breakTime) {
        shortBreak = breakTime;
    }

    /**
     * @return the incrementInterval
     */
    public int getIncrementInterval() {
        return incrementInterval;
    }

    /**
     * @param incInt the incrementInterval to set
     */
    public void setIncrementInterval(final int incInt) {
        incrementInterval = incInt;
    }

    /**
     * @return the pomodoroTime
     */
    public long getPomodoroTime() {
        return pomodoroTime;
    }

    /**
     * @param pomTime the pomodoroTime to set
     */
    public void setPomodoroTime(final long pomTime) {
        pomodoroTime = pomTime;
    }

    /**
     * @return the longBreak
     */
    public long getLongBreak() {
        return longBreak;
    }

    /**
     * @param lngBrk the longBreak to set
     */
    public void setLongBreak(final long lngBrk) {
        longBreak = lngBrk;
    }
}
