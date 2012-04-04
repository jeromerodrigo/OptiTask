package optitask.util;

import java.io.Serializable;

/**
 * Task.java <br />
 * Purpose: Stores the details pertaining to a data unit of a Task.
 * @author Jerome
 * @version 0.8
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
     * Creates and initialises a Task with the default values.
     */

    public Task() {
        this("NULL", false);
    }

    /**
     * Creates a Task.
     * @param desc the description of a task
     * @param done a flag if the task is complete
     */

    public Task(final String desc, final boolean done) {
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

    @Override
    public String toString() {
        return "Description: " + taskDesc + "Is Done: " + isDone;
    }
}
