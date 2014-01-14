package exercise;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;



public class View extends JFrame {
	private Controller controller;
	private JFileChooser fileChooser;
	private JTextArea textAreaStatus;
	private JTextArea textAreaFormat;
	private JButton buttonPlay;
	private JButton buttonStop;
	private JButton buttonPause;
	private JMenuItem menuItemOpen;
	
	public View(Controller c){
		this.controller = c;
		fileChooser = new JFileChooser();
		
		Box verticalBox = Box.createVerticalBox();
		add(verticalBox, BorderLayout.CENTER);
		textAreaStatus = new JTextArea(1, 50);
		textAreaStatus.setEditable(false);
		verticalBox.add(textAreaStatus);
		textAreaFormat = new JTextArea(4, 50);
		verticalBox.add(textAreaFormat);
		textAreaFormat.setEditable(false);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		buttonPlay = new JButton("Play");
		panel.add(buttonPlay);
		buttonPlay.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				controller.start();
			}
		});
		
		buttonStop = new JButton("Stop");
		panel.add(buttonStop);
		buttonStop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				controller.stop();
			}
		});
		
		buttonPause = new JButton("Pause");
		panel.add(buttonPause);
		buttonPause.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				controller.pause();
			}
		});

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);
		menuItemOpen = new JMenuItem("Open");
		menuFile.add(menuItemOpen);
		menuItemOpen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onOpen();
			}
		});
		
		setSize(400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void onOpen(){
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".wav files", "wav", "mp3");
	    fileChooser.setFileFilter(filter);
		if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
			File file = fileChooser.getSelectedFile();
			controller.open(file);
		}
	}

	public void showStatus(final String string) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				textAreaStatus.setText(string);
				textAreaStatus.repaint();
			}
		});
	}
	public void showAudioFormat() {
		AudioFormat audioFormat = controller.getModel().getFormat();
		
		textAreaFormat.setText(audioFormat.getEncoding() + "\n"
						  + audioFormat.getChannels() + " channels\n"
						  + audioFormat.getSampleRate() + " Hz\n"
						  + audioFormat.getSampleSizeInBits() + " Bits\n"
		);
		textAreaFormat.repaint();

	}
	public void clearAudioFormat(){
		textAreaFormat.setText("");
	}

	public void setPlayEnabled(boolean enabled){
		this.buttonPlay.setEnabled(enabled);
	}
	
	public void setStopEnabled(boolean enabled){
		this.buttonStop.setEnabled(enabled);
	}
	public void setPauseEnabled(boolean enabled){
		this.buttonPause.setEnabled(enabled);
	}
	public void setOpenEnabled(boolean enabled){
		this.menuItemOpen.setEnabled(enabled);
	}
}
