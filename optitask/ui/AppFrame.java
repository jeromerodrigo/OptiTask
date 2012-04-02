package optitask.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JFrame;

import optitask.AppController;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


public class AppFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1255058389795622257L;
	private optitask.store.AppPersistence model;
	private AppController controller;
	private TimerPanel timerPanel;
	
	public AppFrame() {
		setNimbusLookAndFeel();
		initialize();
	}
	public AppFrame(optitask.store.AppPersistence model) {
		this.model = model;
		controller = new AppController(model, this);
		setNimbusLookAndFeel();
		initialize();
	}
	
	private void initialize() {
		getContentPane().setLayout(null);
		setTitle("OptiTask");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(350, 265);
		setResizable(false);
		centerThisFrame(this);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AppFrame.class.getResource("/optitask/assests/appIcon.gif")));
		
		timerPanel = new TimerPanel(model, controller);
		timerPanel.setBounds(0, 0, 344, 185);
		getContentPane().add(timerPanel);
		
		JButton btnSettings = new JButton("");
		btnSettings.setIcon(new ImageIcon(AppFrame.class.getResource("/optitask/assests/settings.gif")));
		btnSettings.setActionCommand("Open Settings");
		btnSettings.addActionListener(controller);
		btnSettings.setBounds(10, 196, 30, 30);
		getContentPane().add(btnSettings);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 70, 300, 2);
		getContentPane().add(separator);
		
		JButton btnManageTasks = new JButton("Manage Tasks");
		btnManageTasks.setIcon(new ImageIcon(AppFrame.class.getResource("/optitask/assests/pencil.gif")));
		btnManageTasks.setActionCommand("Open Manage Tasks");
		btnManageTasks.addActionListener(controller);
		btnManageTasks.setBounds(90, 196, 160, 30);
		getContentPane().add(btnManageTasks);
		
		JButton btnAbout = new JButton("");
		btnAbout.setActionCommand("Open About");
		btnAbout.addActionListener(controller);
		btnAbout.setIcon(new ImageIcon(AppFrame.class.getResource("/optitask/assests/star.gif")));
		btnAbout.setBounds(50, 196, 30, 30);
		getContentPane().add(btnAbout);
	}
	
	public void startTimer() {
		timerPanel.startTimer();
	}
	
	public void stopTimer() {
		timerPanel.stopTimer();
	}
	
	/**
     * Centering frame method provided by Vipin Kumar Rajput, 2010
     * URL: http://www.esblog.in/2010/09/centering-a-swing-window-on-screen/
     */
	
	private void centerThisFrame(Window w) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        w.setLocation(screenSize.width / 2 - (w.getWidth() / 2),
                screenSize.height / 2 - (w.getHeight() / 2));
    }
	
	private void setNimbusLookAndFeel() {
		try {
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if("Nimbus".equalsIgnoreCase(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}
	}
}
