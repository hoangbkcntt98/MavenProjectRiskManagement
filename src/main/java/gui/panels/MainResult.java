package gui.panels;

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

import algorithms.pert.Pert;
import model.Project;
import model.dimension.Dimension;
import model.task.Task;
import utils.Utils;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class MainResult extends JFrame {

	private JPanel contentPane;
	private Project project;
	private List<Dimension> dimensionList;
	private TaskNetPanel taskNet;
	double width;
	double height;
	private JPanel panel;
	private JTextField deadline;
	private JButton[] dimensionButtonList;

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
		panel = new StatusBar(height, project);
		contentPane.add(panel);

		taskNet = new TaskNetPanel(pj);
		taskNet.setBounds(276, 0, (int) width - panel.getWidth(), panel.getHeight() - 100);
		contentPane.add(taskNet);
		JPanel controls = new JPanel();
		controls.setBorder(new TitledBorder(null, "controls", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		controls.setLayout(null);
		controls.setBounds(panel.getWidth(), taskNet.getHeight(), (int) width - panel.getWidth(),
				(int) height - taskNet.getHeight());
		contentPane.add(controls);

		dimensionButtonList = new JButton[dimensionList.size()];
		int count = 0;
		for (Dimension d : dimensionList) {
			Pert.showCriticalPath(d.getCriticalPath());
			dimensionButtonList[count] = new JButton(d.getName());
			dimensionButtonList[count].setBounds((count + 1) * 100, 100, 100, 23);
			dimensionButtonList[count].setFont(new Font("Arial", Font.PLAIN, 10));
			dimensionButtonList[count].setMargin(new Insets(0, 0, 0, 0));
			dimensionButtonList[count].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateGUI(d);
				}
			});

			controls.add(dimensionButtonList[count]);
			count++;
		}

		JButton projectButton = new JButton("Project ");
		projectButton.setBounds((count + 1) * 100, 100, 100, 23);
		projectButton.setFont(new Font("Arial", Font.PLAIN, 10));
		projectButton.setMargin(new Insets(0, 0, 0, 0));
		projectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateGUI(project);

			}
		});

		controls.add(projectButton);
		
		
		deadline = new JTextField(Utils.round(project.getDeadline()));
		deadline.setBounds(80, 50, 86, 20);
		controls.add(deadline);
		deadline.setColumns(10);
		JLabel deadlineLable = new JLabel("Deadline");
		deadlineLable.setBounds(10, 50, 80, 20);
		controls.add(deadlineLable);
		
		JButton deadlineButton = new JButton("Set");
		deadlineButton.setBounds(169, 50, 40, 20);
		deadlineButton.setFont(new Font("Arial", Font.PLAIN, 10));
		deadlineButton.setMargin(new Insets(0, 0, 0, 0));
		
		deadlineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				project.setDeadline(Double.parseDouble(deadline.getText()));
				project.update();
				project.calcProb();
				dimensionList = project.getTasks().get(0).getDimensionList();
				for (int count = 0; count < dimensionButtonList.length; count++) {
					
					Dimension d = dimensionList.get(count);
					controls.remove(dimensionButtonList[count]);
					controls.repaint();
					controls.revalidate();
					dimensionButtonList[count] = new JButton(d.getName());
					dimensionButtonList[count].setBounds((count + 1) * 100, 100, 100, 23);
					dimensionButtonList[count].setFont(new Font("Arial", Font.PLAIN, 10));
					dimensionButtonList[count].setMargin(new Insets(0, 0, 0, 0));
					dimensionButtonList[count].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							updateGUI(d);
						}
					});
					controls.add(dimensionButtonList[count]);
					count++;
				}

				updateGUI(project);
			}
		});
		controls.add(deadlineButton);
		
		
		
	}

	public void updateGUI(Project d) {
		// delete graph
		contentPane.remove(taskNet);
		contentPane.repaint();
		contentPane.revalidate();
		// update graph
		taskNet = new TaskNetPanel(d);
		taskNet.setBounds(276, 0, (int) width - panel.getWidth(), panel.getHeight() - 100);
		contentPane.add(taskNet);
		contentPane.repaint();
		contentPane.revalidate();
		// delete status
		contentPane.remove(panel);
		contentPane.repaint();
		contentPane.revalidate();
		// update
		panel = new StatusBar(height, d);
		contentPane.add(panel);
		contentPane.repaint();
		contentPane.revalidate();
	}
}
