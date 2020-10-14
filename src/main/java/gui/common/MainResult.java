package gui.common;

import java.awt.Color;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import algorithms.pert.Pert;
import gui.panels.DescriptionPanel;
import gui.panels.StatusBar;
import gui.panels.TaskNetPanel;
import model.Project;
import model.dimension.Dimension;
import model.task.Task;
import utils.Utils;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

public class MainResult extends MyFrame {

	private JPanel contentPane;
	private Project project;
	private Project projectTemp;
	private List<Dimension> dimensionList;
	private TaskNetPanel taskNet;
	double width;
	double height;
	private JPanel panel;
	private JTextField deadline;
	private JPanel controls;
	private int option;

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
		this.projectTemp = pj;
		this.dimensionList = pj.getTasks().get(0).getDimensionList();

		setTitle("Risk measurement");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth();
		height = screenSize.getHeight();
		setBounds(0, 0,(int)width-10,(int)(height*9.5/10));
		setLocationRelativeTo(null);

		panel = new StatusBar(height, project);
		contentPane.add(panel);

		taskNet = new TaskNetPanel(pj);
		taskNet.setBounds(276, 0, (int) width - panel.getWidth(), (int) getHeight() * 7 / 10);
		contentPane.add(taskNet);

		// add controls panel
		controls = new JPanel();
		controls.setBorder(new TitledBorder(null, "controls", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		controls.setLayout(null);
		controls.setBounds(panel.getWidth(), taskNet.getHeight(), (int) width - panel.getWidth(),
				(int) height - taskNet.getHeight() - 90);
		contentPane.add(controls);
		// add task setting panel
		JPanel taskSetting = new JPanel();
		taskSetting
				.setBorder(new TitledBorder(null, "Task setting", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		taskSetting.setLayout(null);
		taskSetting.setBounds(404, 10, 559, 120);
		controls.add(taskSetting);
		JCheckBox critical = new JCheckBox("only show critical path");
		critical.setFont(new Font("Tahoma", Font.PLAIN, 9));
		// add button group
		JLabel taskLevel = new JLabel("Task successfull level");
		taskLevel.setFont(new Font("Tahoma", Font.PLAIN, 9));
		taskLevel.setBounds(10, 20, 111, 16);
		taskSetting.add(taskLevel);

		JRadioButton optional = new JRadioButton("optional");
		optional.setFont(new Font("Tahoma", Font.PLAIN, 9));
		optional.setSelected(true);
		optional.setBounds(10, 40, 79, 16);
		optional.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (optional.isSelected()) {
					option = 0;
					updateGUI(projectTemp, critical.isSelected(), option);
				}

			}
		});
		taskSetting.add(optional);

		JRadioButton high = new JRadioButton("high", false);
		high.setFont(new Font("Tahoma", Font.PLAIN, 9));
		high.setBounds(10, 59, 65, 16);
		taskSetting.add(high);
		high.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (high.isSelected()) {
					option = 1;
					updateGUI(projectTemp, critical.isSelected(), option);

				}

			}
		});

		JRadioButton medium = new JRadioButton("medium", false);
		medium.setFont(new Font("Tahoma", Font.PLAIN, 9));
		medium.setBounds(10, 78, 79, 16);
		medium.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (medium.isSelected()) {
					option = 2;
					updateGUI(projectTemp, critical.isSelected(), option);
				}

			}
		});
		taskSetting.add(medium);

		JRadioButton low = new JRadioButton("low", false);
		low.setFont(new Font("Tahoma", Font.PLAIN, 9));
		low.setBounds(10, 97, 79, 16);
		low.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (low.isSelected()) {
					option = 3;
					updateGUI(projectTemp, critical.isSelected(), option);
				}

			}
		});
		taskSetting.add(low);

		ButtonGroup optionGroup = new ButtonGroup();
		optional.setActionCommand("0");
		high.setActionCommand("1");
		medium.setActionCommand("2");
		low.setActionCommand("3");

		optionGroup.add(optional);
		optionGroup.add(high);
		optionGroup.add(medium);
		optionGroup.add(low);

		// add checkbox controls

		critical.setBounds(141, 37, 135, 23);
		critical.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateGUI(projectTemp, critical.isSelected(), option);
			}
		});
		taskSetting.add(critical);

		JLabel lblNewLabel = new JLabel("Critical Path");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel.setBounds(131, 21, 92, 14);
		taskSetting.add(lblNewLabel);

		JPanel projectSetting = new JPanel();
		projectSetting.setBorder(
				new TitledBorder(null, "Project Setting", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		projectSetting.setBounds(10, 21, 381, 109);
		controls.add(projectSetting);
		projectSetting.setLayout(null);

		JLabel dLabel = new JLabel("Dimension:");
		dLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
		dLabel.setBounds(20, 78, 80, 20);
		projectSetting.add(dLabel);
		JComboBox comboBox_1 = new JComboBox(
				new String[] { "Project", "Size", "Productivity", "Worker-hour", "Duration", "Cost" });
		comboBox_1.setBounds(91, 78, 150, 20);
		projectSetting.add(comboBox_1);

		deadline = new JTextField(Utils.round(project.getDeadline()));
		deadline.setBounds(90, 31, 86, 20);
		projectSetting.add(deadline);
		deadline.setColumns(10);
		JLabel deadlineLable = new JLabel("Deadline");
		deadlineLable.setFont(new Font("Tahoma", Font.PLAIN, 9));
		deadlineLable.setBounds(20, 31, 80, 20);
		projectSetting.add(deadlineLable);

		JButton deadlineButton = new MyButton("Set", 10);
		deadlineButton.setBounds(179, 31, 40, 20);
		projectSetting.add(deadlineButton);

		deadlineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				projectTemp.setDeadline(Double.parseDouble(deadline.getText()));
				projectTemp.update();
				projectTemp.calcProb();

				project.setDeadline(Double.parseDouble(deadline.getText()));
				project.update();
				project.calcProb();
				dimensionList = project.getTasks().get(0).getDimensionList();
				System.out.println(projectTemp.getProb());
				updateGUI(projectTemp, critical.isSelected(), option);
			}
		});
		comboBox_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JComboBox comboBox = (JComboBox) e.getSource();
				String o = (String) comboBox.getSelectedItem();
				System.out.println(o);
				for (Dimension d : dimensionList) {
					if (d.getName() == o) {
						projectTemp = d;
						updateGUI(d, critical.isSelected(), option);
					}
				}
				if (o == "Project") {
					projectTemp = project;
					updateGUI(projectTemp, critical.isSelected(), option);
				}
			}
		});
		DescriptionPanel des = new DescriptionPanel();
		des.setBounds(0, 480, 273, 200);
		contentPane.add(des);

	}

	public void removeAll() {
		contentPane.removeAll();
	}

	public void updateGUI(Project d, boolean isSet, int opt) {
		// delete graph
		contentPane.remove(taskNet);
		contentPane.repaint();
		contentPane.revalidate();
		// update graph
		taskNet = new TaskNetPanel(d);
		taskNet.showVertex(isSet, opt);
		taskNet.setBounds(276, 0, (int) width - panel.getWidth(), (int) getHeight() * 7 / 10);
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
