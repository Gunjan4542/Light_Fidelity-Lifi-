import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class GUI_Transmitter implements Runnable {
	static JProgressBar progressBar;
	static File file = null;
	static JTextArea area = null;
	static boolean flag = false;
	static String message = "";
	public static void main(String[]args)throws Exception{
		final JFrame f = new JFrame("LIFI GUI");
		JButton selectFile = new JButton("Select File");
		final JFileChooser fc = new JFileChooser();
		progressBar = new JProgressBar(0,100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
//		progressBar.setIndeterminate(true);
	
		area = new JTextArea(300,300);
		final JTextField textField = new JTextField("Enter message to send.");
		JScrollPane scp = new JScrollPane(area);
		scp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		selectFile.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int val = fc.showOpenDialog(f);
				if (val == JFileChooser.APPROVE_OPTION) {
		            file = fc.getSelectedFile();
		            //This is where a real application would open the file.
		            System.out.println("Sending file "+file.getName());
		            flag = true;
		            area.setText("");
		            Thread thread = new Thread(new GUI_Transmitter());
		            thread.start();
				}
			}
		});
		
		BorderLayout layout = new BorderLayout();
		
		f.setLayout(layout);
		JPanel panel = new JPanel(new BorderLayout());
		panel.setSize(f.getWidth(),f.getHeight());
		panel.add(textField,BorderLayout.NORTH);
		JPanel p = new JPanel();
		p.add(selectFile);
		panel.add(p,BorderLayout.CENTER);
		
		f.add(progressBar,BorderLayout.SOUTH);
		f.add(area,BorderLayout.CENTER);
		f.add(panel,BorderLayout.NORTH);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		textField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(KeyEvent.VK_ENTER == arg0.getKeyCode()){
					flag = false;
					message = textField.getText();
					area.setText("");
					Thread thread = new Thread(new GUI_Transmitter());
		            thread.start();
				}
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		f.setSize(500,500);
		
		f.setVisible(true);
		
	}
	
	public void run(){
		Lifi_Transmitter lifi = new Lifi_Transmitter();
		try {
			if(flag)
			lifi.sendFile(file, progressBar,area);
			else{
				System.out.println("Sending Message...");
				lifi.sendMessage(message, progressBar, area);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
