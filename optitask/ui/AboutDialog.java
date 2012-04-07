package optitask.ui;

import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

/**
 * AboutDialog.java <br />
 * Purpose: Displays some information about the application.
 * @author Jerome
 * @version 0.8.1
 * @since 0.8
 */

public class AboutDialog extends JDialog {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 5686802784165414690L;

    /**
     * The current version number.
     */
    private static final String VERSION_NUMBER = "0.8.1";

    /**
     * Create the dialog.
     */
    public AboutDialog() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                AboutDialog.class.getResource("/optitask/assests/star.gif")));
        setModal(true);
        setResizable(false);
        setTitle("About OptiTask");
        setSize(350, 210);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new MigLayout("", "[324px]",
                "[28px][14px][14px][50px]"));

        JLabel lblTitle = new JLabel("<html><h1>OptiTask Pomodoro Timer</h1>"
                + "<hr /></html>");
        getContentPane().add(lblTitle, "cell 0 0,grow");

        JLabel lblCopy = new JLabel(
                "<html>\r\nCopyright &copy; 2012 "
                        + "by Jerome Rodrigo.\r\n</html>");
        getContentPane().add(lblCopy, "cell 0 1,growx,aligny top");

        JLabel lblVersion = new JLabel(
                "<html>\r\n<b>Version:</b> " + VERSION_NUMBER + "\r\n</html>");
        getContentPane().add(lblVersion, "cell 0 2,growx,aligny top");

        JLabel lblCredit = new JLabel(
                "<html>\r\nDeveloped based on the Pomodoro Technique &reg;"
                        + " <br />\r\nby Francesco Cirillo <br />"
                        + "\r\n<a href=\"http://www.pomodorotechnique.com/\">"
                        + "http://www.pomodorotechnique.com/</a>\r\n</html>");
        getContentPane().add(lblCredit, "cell 0 3,grow");

    }
}
