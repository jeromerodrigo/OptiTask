package optitask.ui;

import javax.swing.JDialog;
import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Font;

public class AboutDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5686802784165414690L;

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutDialog.class.getResource("/optitask/assests/star.gif")));
		setModal(true);
		setResizable(false);
		setTitle("About OptiTask");
		setBounds(100, 100, 350, 200);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel("OptiTask Pomodoro Timer");
		lblTitle.setFont(new Font("Times New Roman", lblTitle.getFont().getStyle() | Font.BOLD, lblTitle.getFont().getSize() + 4));
		lblTitle.setBounds(10, 11, 324, 28);
		getContentPane().add(lblTitle);
		
		JLabel lblCopy = new JLabel("<html>\r\nCopyright &copy; 2012 by Jerome Rodrigo.\r\n</html>");
		lblCopy.setBounds(10, 61, 324, 14);
		getContentPane().add(lblCopy);
		
		JLabel lblVersion = new JLabel("<html>\r\n<b>Version:</b> 0.8\r\n</html>");
		lblVersion.setBounds(10, 86, 324, 14);
		getContentPane().add(lblVersion);
		
		JLabel lblCredit = new JLabel("<html>\r\nDeveloped based on the Pomodoro Technique &reg; <br />\r\nCredit to: Francesco Cirillo <br />\r\n<a href=\"http://www.pomodorotechnique.com/\">http://www.pomodorotechnique.com/</a>\r\n</html>");
		lblCredit.setBounds(10, 111, 324, 50);
		getContentPane().add(lblCredit);

	}
}
