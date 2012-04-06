package optitask.ui;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

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
        setBounds(100, 100, 350, 200);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("OptiTask Pomodoro Timer");
        lblTitle.setFont(new Font("Times New Roman", lblTitle.getFont()
                .getStyle() | Font.BOLD, lblTitle.getFont().getSize() + 4));
        lblTitle.setBounds(10, 11, 324, 28);
        getContentPane().add(lblTitle);

        JLabel lblCopy = new JLabel(
                "<html>\r\nCopyright &copy; 2012 "
                        + "by Jerome Rodrigo.\r\n</html>");
        lblCopy.setBounds(10, 61, 324, 14);
        getContentPane().add(lblCopy);

        JLabel lblVersion = new JLabel(
                "<html>\r\n<b>Version:</b> " + VERSION_NUMBER + "\r\n</html>");
        lblVersion.setBounds(10, 86, 324, 14);
        getContentPane().add(lblVersion);

        JLabel lblCredit = new JLabel(
                "<html>\r\nDeveloped based on the Pomodoro Technique &reg;"
                        + " <br />\r\nby Francesco Cirillo <br />"
                        + "\r\n<a href=\"http://www.pomodorotechnique.com/\">"
                        + "http://www.pomodorotechnique.com/</a>\r\n</html>");
        lblCredit.setBounds(10, 111, 324, 50);
        getContentPane().add(lblCredit);

    }
}
