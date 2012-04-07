package optitask.util;

import java.io.Serializable;

/**
 * Task.java <br />
 * Purpose: Stores the details pertaining to a data unit of a Task.
 * @author Jerome
 * @version 0.8.2
 * @since 0.8
 */

public final class Task implements Serializable {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -5664685099098657046L;

    /**
     * Stores the description of a task.
     */
    private String taskDesc;

    /**
     * A flag to determine if the task is done, or not.
     */
    private boolean isDone;

    /**
     * The total number of pomodoros needed to complete the task.
     */
    private int assignedPomodoros;

    /**
     * The current pomodoro.
     */
    private int currentPomodoro;

    /**
     * Creates and initialises a Task with the default values.
     */

    public Task() {
        this("NULL", false, 1, 0);
    }

    /**
     * Creates a Task.
     * @param desc  the description of a task
     * @param done  a flag if the task is complete
     * @param aPoms the assigned pomodoros
     * @param cPom  the current pomodoro
     */

    public Task(final String desc, final boolean done,
            final int aPoms, final int cPom) {
        taskDesc = desc;
        isDone = done;
        assignedPomodoros = aPoms;
        currentPomodoro = cPom;
    }

    /**
     * @return the isDone
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * @param done the isDone to set
     */
    public void setDone(final boolean done) {
        isDone = done;
    }

    /**
     * @return the taskDesc
     */
    public String getTaskDesc() {
        return taskDesc;
    }

    /**
     * @param tskDsc the taskDesc to set
     */
    public void setTaskDesc(final String tskDsc) {
        taskDesc = tskDsc;
    }

    /**
     * @return the assignedPomodoros
     */
    public int getAssignedPomodoros() {
        return assignedPomodoros;
    }

    /**
     * @param poms the assignedPomodoros to set
     */
    public void setAssignedPomodoros(final int poms) {
        this.assignedPomodoros = poms;
    }

    /**
     * @return the currentPomodoro
     */
    public int getCurrentPomodoro() {
        return currentPomodoro;
    }

    /**
     * @param poms the currentPomodoro to set
     */
    public void setCurrentPomodoro(final int poms) {
        this.currentPomodoro = poms;
    }

    @Override
    public String toString() {
        return "Description: " + taskDesc
                + " Is Done: " + isDone
                + " Assigned Pomodoros: " + assignedPomodoros
                + " Current: " + currentPomodoro;
    }
}
