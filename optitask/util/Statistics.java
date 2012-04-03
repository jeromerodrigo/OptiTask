package optitask.util;

import java.io.Serializable;

public final class Statistics implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 647827585331411770L;
	private int pomodorosDone = 0;
	private long totalUsageTime = 0;
	private long timeWasted = 0;
	private long totalBreakTime = 0;
	private int numInterrupts = 0;

	public Statistics() {
		this(0, 0, 0, 0, 0);
	}

	public Statistics(int pomoDone, long usageTime, long wasted,
			long breakTime, int interrupts) {
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
	 * @param pomodorosDone
	 *            the pomodorosDone to set
	 */
	public void setPomodorosDone(int pomodorosDone) {
		this.pomodorosDone = pomodorosDone;
	}

	/**
	 * @return the totalUsageTime
	 */
	public long getTotalUsageTime() {
		return totalUsageTime;
	}

	/**
	 * @param totalUsageTime
	 *            the totalUsageTime to set
	 */
	public void setTotalUsageTime(long totalUsageTime) {
		this.totalUsageTime = totalUsageTime;
	}

	/**
	 * @return the timeWasted
	 */
	public long getTimeWasted() {
		return timeWasted;
	}

	/**
	 * @param timeWasted
	 *            the timeWasted to set
	 */
	public void setTimeWasted(long timeWasted) {
		this.timeWasted = timeWasted;
	}

	/**
	 * @return the totalBreakTime
	 */
	public long getTotalBreakTime() {
		return totalBreakTime;
	}

	/**
	 * @param totalBreakTime
	 *            the totalBreakTime to set
	 */
	public void setTotalBreakTime(long totalBreakTime) {
		this.totalBreakTime = totalBreakTime;
	}

	/**
	 * @return the numInterrupts
	 */
	public int getNumInterrupts() {
		return numInterrupts;
	}

	/**
	 * @param numInterrupts
	 *            the numInterrupts to set
	 */
	public void setNumInterrupts(int numInterrupts) {
		this.numInterrupts = numInterrupts;
	}
}
