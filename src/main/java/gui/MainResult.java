package gui;

import java.awt.Color;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import model.Project;
import model.dimension.Dimension;
import model.task.Task;
import utils.Utils;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainResult extends JFrame {

	private JPanel contentPane;
	private Project project;
	private List<Dimension> dimensionList;
	private TaskNetPanel taskNet; 
	double width;
	double height;
	JPanel panel;
	

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainResult frame = new MainResult(project);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public MainResult(Project pj) {
		this.project = pj;
		this.dimensionList = pj.getTasks().get(0).getDimensionList();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth();
		height = screenSize.getHeight();
		setSize(screenSize);
		panel = new StatusBar(height,project);
		contentPane.add(panel);
		
		taskNet = new TaskNetPanel(pj);
		taskNet.setBounds(276, 0, (int) width - panel.getWidth(),panel.getHeight()-100);
		contentPane.add(taskNet);
		JPanel controls = new JPanel();
		controls.setBorder(new TitledBorder(null, "controls", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		controls.setLayout(null);
		controls.setBounds(panel.getWidth(), taskNet.getHeight(),(int)width-panel.getWidth(),(int) height-taskNet.getHeight());
		contentPane.add(controls);
		int count =0;
		for(Dimension d:dimensionList) {
			JButton dimension0 = new JButton("Dimension "+count);
			dimension0.setBounds((count+1)*100,100, 100, 23);
			dimension0.setFont(new Font("Arial", Font.PLAIN, 10));
			dimension0.setMargin(new Insets(0, 0, 0, 0));
			dimension0.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					// delete graph
					contentPane.remove(taskNet);
					contentPane.repaint();
					contentPane.revalidate();
					// update graph
					taskNet = new TaskNetPanel(d);
					taskNet.setBounds(276, 0, (int) width - panel.getWidth(),panel.getHeight()-100);
					contentPane.add(taskNet);	
					contentPane.repaint();
					contentPane.revalidate();
					// delete status
					contentPane.remove(panel);
					contentPane.repaint();
					contentPane.revalidate();
					//update
					panel = new StatusBar(height,d);
					contentPane.add(panel);
					contentPane.repaint();
					contentPane.revalidate();
					
				}
			});
			count ++;
			controls.add(dimension0);
		}
		
		

	}

	
}
