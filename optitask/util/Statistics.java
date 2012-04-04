package optitask.util;

import java.io.Serializable;

/**
 * Statistics.java <br />
 * Purpose: Stores the statistics of the application.
 * @author Jerome
 * @version 0.8
 * @since 0.8
 */

public final class Statistics implements Serializable {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 647827585331411770L;

    /**
     * The total pomodoros done throughout the usage of the application.
     * @serial
     */
    private int pomodorosDone;

    /**
     * The total usage time.
     * @serial
     */
    private long totalUsageTime;

    /**
     * The time wasted by not doing anything while the application is open.
     * @serial
     */
    private long timeWasted;

    /**
     * The total break time accumulated when using the application.
     * @serial
     */
    private long totalBreakTime;

    /**
     * The total number of interruptions occurred.
     * @serial
     */
    private int numInterrupts;

    /**
     * Creates and initialises the statistics object to zero.
     */

    public Statistics() {
        this(0, 0, 0, 0, 0);
    }

    /**
     * Creates the statistics object.
     * @param pomoDone    number of pomodoros completed
     * @param usageTime   total usage time of the application
     * @param wasted      total time wasted
     * @param breakTime   total time used on breaks
     * @param interrupts  total number of interruptions
     */

    public Statistics(final int pomoDone, final long usageTime,
            final long wasted, final long breakTime,
            final int interrupts) {
        pomodorosDone = pomoDone;
        totalUsageTime = usageTime;
        timeWasted = wasted;
        totalBreakTime = breakTime;
        numInterrupts = interrupts;
    }

    /**
     * @return the pomodorosDone
     */
    public int getPomodorosDone() {
        return pomodorosDone;
    }

    /**
     * @param pomDone the pomodorosDone to set
     */
    public void setPomodorosDone(final int pomDone) {
        pomodorosDone = pomDone;
    }

    /**
     * @return the totalUsageTime
     */
    public long getTotalUsageTime() {
        return totalUsageTime;
    }

    /**
     * @param totUseTime the totalUsageTime to set
     */
    public void setTotalUsageTime(final long totUseTime) {
        totalUsageTime = totUseTime;
    }

    /**
     * @return the timeWasted
     */
    public long getTimeWasted() {
        return timeWasted;
    }

    /**
     * @param timeWstd the timeWasted to set
     */
    public void setTimeWasted(final long timeWstd) {
        timeWasted = timeWstd;
    }

    /**
     * @return the totalBreakTime
     */
    public long getTotalBreakTime() {
        return totalBreakTime;
    }

    /**
     * @param totBrkTime the totalBreakTime to set
     */
    public void setTotalBreakTime(final long totBrkTime) {
        totalBreakTime = totBrkTime;
    }

    /**
     * @return the numInterrupts
     */
    public int getNumInterrupts() {
        return numInterrupts;
    }

    /**
     * @param numInts the numInterrupts to set
     */
    public void setNumInterrupts(final int numInts) {
        this.numInterrupts = numInts;
    }
}
