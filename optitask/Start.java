package optitask;

import optitask.store.AppPersistence;
import optitask.ui.AppFrame;

/**
 * Start.java <br />
 * Purpose: Acts as a starting point for the application.
 * @author Jerome Rodrigo
 * @since 0.8
 */

public final class Start {

    /** Stores a single instance of the application persistence module. */
    private static AppPersistence model;

    /** Stores a single instance of the main user interface. */
    private static AppFrame view;

    /**
     * Default constructor for the Start class.
     * Not Used.
     */
    private Start() {
    }

    /**
     * This is the entry point of the program.
     * @param args not used
     */
    public static void main(final String[] args) {
        model = new AppPersistence("optitask.dat");
        view = new AppFrame(model);
        view.setVisible(true);
    }
}
