package optitask.ui;

import javax.swing.JDialog;

/**
 * StatsDialog.java <br />
 * Purpose: Displays a dialog showing the statistics of the application.
 * @author Jerome Rodrigo
 */

public class StatsDialog extends JDialog {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -8894935203682875980L;

    /**
     * Creates the dialog.
     * @param model the persistence module
     */

    public StatsDialog(final optitask.store.AppPersistence model) {
        initialize();
    }

    /**
     * Creates the user interface components.
     */
    private void initialize() {

    }
}
