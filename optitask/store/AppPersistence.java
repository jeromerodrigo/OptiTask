package optitask.store;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

import optitask.util.Settings;
import optitask.util.Statistics;
import optitask.util.Task;

/**
 * AppPersistence.java <br />
 * Purpose: Manages operations between the application model
 * and the host file system.
 * @author Jerome Rodrigo
 * @since 0.8
 */

public final class AppPersistence {

    /**
     * Stores all application data in an ArrayList form.
     * @see #AppPersistence(String)
     */
    private ArrayList<Object> list;

    /**
     * Stores the constant index value for the tasks.
     * It is used in {@link #list}.
     */
    private static final int TODOLIST_INDEX = 0;

    /**
     * Stores the constant index value for the settings.
     * It is used in {@link #list}.
     */
    private static final int SETTINGS_INDEX = 1;

    /**
     * Stores the constant index value for the statistics.
     * It is used in {@link #list}.
     */
    private static final int STATS_INDEX = 2;

    /**
     * Stores the contant index value for the task inventory.
     * Is it used in {@link #list}.
     */
    private static final int TASKINVENTORY_INDEX = 3;

    /**
     * Stores one instance of the name of the data file.
     * @see #AppPersistence(String)
     */
    private String filename = "";

    /**
     * Creates an instance of the persistence module.
     * @param fName the name of the data file
     */
    public AppPersistence(final String fName) {
        filename = fName;

        list = new ArrayList<Object>();
        list.add(TODOLIST_INDEX, new LinkedList<Task>());
        list.add(SETTINGS_INDEX, new Settings());
        list.add(STATS_INDEX, new Statistics());
        list.add(TASKINVENTORY_INDEX, new LinkedList<Task>());
    }

    /**
     * <P>Reads the data file for any changes, and then updates
     * the {@link #list} with the changes.
     *
     * <P>If the data file does not exist, then a new file will
     * be created and will be initialised will the default values.
     */

    @SuppressWarnings("unchecked")
    private void readData() {

        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            writeObject(TODOLIST_INDEX, new LinkedList<Task>());
            writeObject(SETTINGS_INDEX, new Settings());
            writeObject(STATS_INDEX, new Statistics());
            writeObject(TASKINVENTORY_INDEX, new LinkedList<Task>());
        } else {
            try { // Initialise input stream
                InputStream inFile = new FileInputStream(filename);
                InputStream inBuffer = new BufferedInputStream(inFile);
                ObjectInput in = new ObjectInputStream(inBuffer);

                try {
                    list = (ArrayList<Object>) in.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    in.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds an object to the {@link #list}, and then writes it to the data file.
     *
     * @param index The identifier for a data object in the {@link #list},
     *              and possible values are either {@link #TODOLIST_INDEX},
     *              {@link #SETTINGS_INDEX} or {@link #STATS_INDEX}.
     * @param obj stores the data to be saved to the file
     * @return <code>true</code> if successfully written to file;
     *         <code>false</code> otherwise.
     */

    private boolean writeObject(final int index, final Object obj) {
        boolean isSaved = false;
        readData();
        list.set(index, obj);

        try { // Initialise output stream
            OutputStream outFile = new FileOutputStream(filename);
            OutputStream outBuffer = new BufferedOutputStream(outFile);
            ObjectOutput out = new ObjectOutputStream(outBuffer);

            try {
                out.writeObject(list);
                isSaved = true;
            } catch (IOException e) {
                e.printStackTrace();
                isSaved = false;
            } finally {
                out.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return isSaved;
    }

    /**
     * Gets an object from the {@link #list} at the index specified.
     *
     * @param index The identifier for a data object in the {@link #list},
     *              and possible values are either {@link #TODOLIST_INDEX},
     *              {@link #SETTINGS_INDEX}, {@link #TASKINVENTORY_INDEX}
     *              or {@link #STATS_INDEX}.
     * @return an object of type {@link Object}; Note the object has to be
     * casted to an usable type to be used.
     * @see #getSettings()
     * @see #getStats()
     * @see #getToDoList()
     * @see #getTaskInventory()
     */

    private Object getObject(final int index) {
        readData();
        return list.get(index);
    }

    /**
     * Saves the tasks to the data file.
     * @param tasks the list of tasks
     * @return <code>true</code> if successfully written to file;
     *         <code>false</code> otherwise.
     */

    public boolean saveToDoList(final LinkedList<Task> tasks) {
        return writeObject(TODOLIST_INDEX, tasks);
    }

    /**
     * Saves the settings to the data file.
     * @param settings the settings object
     * @return <code>true</code> if successfully written to file;
     *         <code>false</code> otherwise.
     */

    public boolean saveSettings(final Settings settings) {
        return writeObject(SETTINGS_INDEX, settings);
    }

    /**
     * Saves the statistics to the data file.
     * @param stats the statistics object
     * @return <code>true</code> if successfully written to file;
     *         <code>false</code> otherwise.
     */

    public boolean saveStats(final Statistics stats) {
        return writeObject(STATS_INDEX, stats);
    }

    /**
     * Saves the task inventory to the data file.
     * @param inv the task inventory list
     * @return <code>true</code> if successfully written to file;
     *         <code>false</code> otherwise.
     */
    public boolean saveTaskInventory(final LinkedList<Task> inv) {
        return writeObject(TASKINVENTORY_INDEX, inv);
    }

    /**
     * Gets the settings from the data file.
     * @return a settings object
     */

    public Settings getSettings() {
        return (Settings) getObject(SETTINGS_INDEX);
    }

    /**
     * Gets the list of tasks from the data file.
     * @return a list of tasks
     */

    @SuppressWarnings("unchecked")
    public LinkedList<Task> getToDoList() {
        return (LinkedList<Task>) getObject(TODOLIST_INDEX);
    }

    /**
     * Gets the statistics from the data file.
     * @return a statistics object
     */

    public Statistics getStats() {
        return (Statistics) getObject(STATS_INDEX);
    }

    /**
     * Gets the task inventory list from the data file.
     * @return a list of tasks object
     */
    
    @SuppressWarnings("unchecked")
    public LinkedList<Task> getTaskInventory() {
        return (LinkedList<Task>) getObject(TASKINVENTORY_INDEX);
    }

    /**
     * Deletes the data file.
     * <B>NOT USED.</B>
     * @return <code>true</code> if successfully deleted;
     *         <code>false</code> otherwise.
     */

    public boolean destroyFile() {
        File file = new File(filename);
        return file.delete();
    }
}
