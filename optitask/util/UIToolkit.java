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
    
    /**
     * Prevents instantiation of this class.
     */
    
    private UIToolkit() {
        
    }
    
    /**
     * Prevents keyboard input for a {@link JSpinner}.
     * @param spinner the JSpinner
     */

    public static void preventKeyboardInputJSpinner(final JSpinner spinner) {
        final JFormattedTextField textField = ((JSpinner.DefaultEditor) spinner.getEditor())
                .getTextField();
        textField.setEditable(false);
        textField.setBackground(Color.WHITE);
    }
    
    /**
     * Centering frame method provided by Vipin Kumar Rajput, 2010.
     * <a href="http://www.esblog.in/2010/09/
     * centering-a-swing-window-on-screen/">
     * Centering a Window</a>
     * @param window an instance of a window frame
     */

    public static void centerThisFrame(final Window window) {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(screenSize.width / 2 - (window.getWidth() / 2),
                screenSize.height / 2 - (window.getHeight() / 2));
    }
}
