package optitask.util;

import java.io.Serializable;


public final class Task implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5664685099098657046L;
	private String taskDesc = null;
	private boolean isDone = false;
	public Task() {
		this("NULL", false);
	}
	
	public Task(String desc, boolean done) {
		taskDesc = desc;
		isDone = done;
	}

	/**
	 * @return the isDone
	 */
	public boolean isDone() {
		return isDone;
	}

	/**
	 * @param isDone the isDone to set
	 */
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	/**
	 * @return the taskDesc
	 */
	public String getTaskDesc() {
		return taskDesc;
	}

	/**
	 * @param taskDesc the taskDesc to set
	 */
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	
	@Override
	public String toString() {
		return "Description: " + taskDesc + "Is Done: " + isDone;
	}
}
