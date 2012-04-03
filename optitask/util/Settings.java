package optitask.util;

import java.io.Serializable;

public final class Settings implements Serializable {

	private static final long serialVersionUID = 7131951061036321184L;
	private long shortBreak;
	private long longBreak;
	private int incrementInterval;
	private long pomodoroTime;
	private boolean willLongBreak;

	public Settings() {
		this(300000, 900000, 4, 1500000, true);
	}

	public Settings(long shrtBrk, long lngBrk, int itrVal, long pomoTime,
			boolean willLongBreak) {
		shortBreak = shrtBrk;
		longBreak = lngBrk;
		incrementInterval = itrVal;
		pomodoroTime = pomoTime;
		this.willLongBreak = willLongBreak;
	}

	/**
	 * @return the willIncrement
	 */
	public boolean isWillLongBreak() {
		return willLongBreak;
	}

	/**
	 * @param willIncrement
	 *            the willIncrement to set
	 */
	public void setWillLongBreak(boolean willLongBreak) {
		this.willLongBreak = willLongBreak;
	}

	/**
	 * @return the breakTime
	 */
	public long getShortBreak() {
		return shortBreak;
	}

	/**
	 * @param breakTime
	 *            the breakTime to set
	 */
	public void setShortBreak(long breakTime) {
		this.shortBreak = breakTime;
	}

	/**
	 * @return the incrementInterval
	 */
	public int getIncrementInterval() {
		return incrementInterval;
	}

	/**
	 * @param incrementInterval
	 *            the incrementInterval to set
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
	 * @param pomodoroTime
	 *            the pomodoroTime to set
	 */
	public void setPomodoroTime(long pomodoroTime) {
		this.pomodoroTime = pomodoroTime;
	}

	/**
	 * @return the longBreak
	 */
	public long getLongBreak() {
		return longBreak;
	}

	/**
	 * @param longBreak
	 *            the longBreak to set
	 */
	public void setLongBreak(long longBreak) {
		this.longBreak = longBreak;
	}
}
