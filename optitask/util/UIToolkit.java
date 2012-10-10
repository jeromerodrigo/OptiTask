/**
 * 
 */
package optitask.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;

/**
 * Provides shared UI tools for multiple purposes.
 * @author Jerome Rodrigo
 * @since 0.9.0
 */
public final class UIToolkit {
    
    public UIToolkit() {
        
    }
    
    /**
     * Prevents keyboard input for a {@link JSpinner}.
     * @param spinner the JSpinner
     */

    public static void preventKeyboardInputJSpinner(final JSpinner spinner) {
        JFormattedTextField tf = ((JSpinner.DefaultEditor) spinner.getEditor())
                .getTextField();
        tf.setEditable(false);
        tf.setBackground(Color.WHITE);
    }
    
    /**
     * Centering frame method provided by Vipin Kumar Rajput, 2010.
     * <a href="http://www.esblog.in/2010/09/
     * centering-a-swing-window-on-screen/">
     * Centering a Window</a>
     * @param w an instance of a window frame
     */

    public static void centerThisFrame(final Window w) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        w.setLocation(screenSize.width / 2 - (w.getWidth() / 2),
                screenSize.height / 2 - (w.getHeight() / 2));
    }
}
