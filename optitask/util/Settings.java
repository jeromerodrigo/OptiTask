package optitask.util;

import java.io.Serializable;


public final class Settings implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7131951061036321184L;
	private long breakTime = 300000;
	private int incrementInterval = 4;
	private long pomodoroTime = 1500000;
	private long incrementValue = 30000;
	private boolean willIncrement = true;
	
	public Settings() {
		this(300000, 4, 1500000, 300000, true);
	}
	
	public Settings(long breakTime, int itrVal, long pomoTime, long incrVal, boolean increment) {
		this.breakTime = breakTime;
		incrementInterval = itrVal;
		pomodoroTime = pomoTime;
		incrementValue = incrVal;
		willIncrement = increment;
	}

	/**
	 * @return the willIncrement
	 */
	public boolean isWillIncrement() {
		return willIncrement;
	}

	/**
	 * @param willIncrement the willIncrement to set
	 */
	public void setWillIncrement(boolean willIncrement) {
		this.willIncrement = willIncrement;
	}

	/**
	 * @return the breakTime
	 */
	public long getBreakTime() {
		return breakTime;
	}

	/**
	 * @param breakTime the breakTime to set
	 */
	public void setBreakTime(long breakTime) {
		this.breakTime = breakTime;
	}

	/**
	 * @return the incrementInterval
	 */
	public int getIncrementInterval() {
		return incrementInterval;
	}

	/**
	 * @param incrementInterval the incrementInterval to set
	 */
	public void setIncrementInterval(int incrementInterval) {
		this.incrementInterval = incrementInterval;
	}

	/**
	 * @return the pomodoroTime
	 */
	public long getPomodoroTime() {
		return pomodoroTime;
	}

	/**
	 * @param pomodoroTime the pomodoroTime to set
	 */
	public void setPomodoroTime(long pomodoroTime) {
		this.pomodoroTime = pomodoroTime;
	}

	/**
	 * @return the incrementValue
	 */
	public long getIncrementValue() {
		return incrementValue;
	}

	/**
	 * @param incrementValue the incrementValue to set
	 */
	public void setIncrementValue(long incrementValue) {
		this.incrementValue = incrementValue;
	}
}
