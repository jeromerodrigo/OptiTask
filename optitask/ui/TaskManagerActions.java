package optitask.ui;

import java.util.LinkedList;

import optitask.util.Task;

/**
 * Interface for task manager actions.
 * @author Jerome
 * @version 0.8.3
 */

public interface TaskManagerActions {

    /**
     * Adds a task.
     */
    void addTask();

    /**
     * Deletes the selected task.
     */
    void deleteTask();

    /**
     * Gets the list of tasks.
     * @return the list of tasks
     */
    LinkedList<Task> getTasks();

    /**
     * Switches two task items in the list.
     * @param selectedIdx the index of the selected task
     * @param nextIdx     the index of the task to be switched
     */
    void swapItems(final int selectedIdx, final int nextIdx);

    /**
     * Implements the {@link #swapItems(int, int)}, by switching the
     * selected task with the preceding task item.
     */
    void moveUp();

    /**
     * Implements the {@link #swapItems(int, int)}, by switching the
     * selected task with the following task item.
     */
    void moveDown();

}
