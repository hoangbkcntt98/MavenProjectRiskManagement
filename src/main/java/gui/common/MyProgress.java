package gui.common;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import gui.frame.Home;
 
public class MyProgress{
 
    private JDialog mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private String text;
 
    public MyProgress(){
       prepareGUI();
       
    }
 
 
    private void prepareGUI() {
        mainFrame = new JDialog(Home.it);
        mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
       
        mainFrame.setUndecorated(true);
        mainFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        mainFrame.setBounds(400,500,200,100);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setSize(200, 100);
        mainFrame.setLayout(new GridLayout(3, 1));
        mainFrame.setResizable(false);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        headerLabel = new JLabel("", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.ITALIC,10));
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }
 
    private JProgressBar progressBar;
    private Task task;
//    private JButton startButton;
    private JTextArea outputTextArea;
 
    public void showProgressBarDemo(String name) {
    	text = name;
        headerLabel.setText(name);
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setForeground(Color.green);
//        outputTextArea = new JTextArea("", 5, 20);
        task = new Task();
        task.start();
        controlPanel.add(progressBar);
        mainFrame.setVisible(true);
//        Home.it.setEnabled(true);
    }
 
    private class Task extends Thread {
        public Task() {
        }
 
        public void run() {
            for (int i = 0; i <= 100; i += 10) {
                final int progress = i;
           
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        progressBar.setValue(progress);
                        if(text=="Reading Resources ...")
                        {
                        	if(progress==30) {
                            	headerLabel.setText("Importing Resources....");
                            }
                        	else if(progress == 60) {
                            	headerLabel.setText("Generating Project ...");
                            }
                        	
                        }
                        if(text=="Generating GUI ...") {
                        	if(progress==30) {
                            	headerLabel.setText("Loading Data....");
                            }
                        	else if(progress == 60) {
                            	headerLabel.setText("Generating Network ...");
                            }
                        }
                        
                        
                    }
                });
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }
            }
            mainFrame.setVisible(false);
        }
    }
}

