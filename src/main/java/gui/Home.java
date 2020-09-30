package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import config.Configuaration;
import model.Project;

public class Home extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton exeButton;
	private JButton taskInfoButton;
	private JButton DInfoButton;
	private JButton riskInfoButton;
	private JButton riskDisButton;
	private JButton taskDisButton;
	private JButton riskRelateButton;
	private JButton autoInput;
	private JFileChooser fc;
	private String riskInfo;
	private String taskInfo;
	private String riskRelate;
	private String riskDis;
	private String taskDis;
	private String DInfo;
	private JTextArea log;
	private StringBuilder logStr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
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
	public Home() {
		setResizable(false);
		setTitle("RiskManagement App");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 565, 427);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel input = new JPanel();
		input.setLayout(null);
		input.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Input",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		input.setBounds(0, 0, 549, 233);
		contentPane.add(input);

		JLabel lblNewLabel_1 = new JLabel("Task Info");
		lblNewLabel_1.setBounds(22, 25, 112, 27);
		input.add(lblNewLabel_1);

		taskInfoButton = new JButton("Import");
		taskInfoButton.setBounds(113, 29, 89, 23);
		taskInfoButton.addActionListener(this);
		input.add(taskInfoButton);

		JLabel lblNewLabel_1_1 = new JLabel("DimensionInfo");
		lblNewLabel_1_1.setBounds(22, 53, 112, 27);
		input.add(lblNewLabel_1_1);

		DInfoButton = new JButton("Import");
		DInfoButton.setBounds(113, 57, 89, 23);
		DInfoButton.addActionListener(this);
		input.add(DInfoButton);

		JLabel lblNewLabel_1_2 = new JLabel("Risk Info");
		lblNewLabel_1_2.setBounds(22, 86, 112, 27);
		input.add(lblNewLabel_1_2);

		riskInfoButton = new JButton("Import");
		riskInfoButton.setBounds(113, 90, 89, 23);
		riskInfoButton.addActionListener(this);
		input.add(riskInfoButton);

		JLabel lblNewLabel_1_3 = new JLabel("Risk Relation");
		lblNewLabel_1_3.setBounds(22, 120, 112, 27);
		input.add(lblNewLabel_1_3);

		riskRelateButton = new JButton("Import");
		riskRelateButton.addActionListener(this);
		riskRelateButton.setBounds(113, 124, 89, 23);
		input.add(riskRelateButton);

		JLabel lblNewLabel_1_4 = new JLabel("Risk Distribution");
		lblNewLabel_1_4.setBounds(22, 153, 112, 27);
		input.add(lblNewLabel_1_4);

		riskDisButton = new JButton("Import");
		riskDisButton.addActionListener(this);
		riskDisButton.setBounds(113, 155, 89, 23);
		input.add(riskDisButton);

		exeButton = new JButton("Excute");
		exeButton.setBounds(199, 199, 89, 23);
		exeButton.addActionListener(this);
		input.add(exeButton);

		autoInput = new JButton("Auto Set");
		autoInput.addActionListener(this);
		autoInput.setBounds(299, 199, 89, 23);
		input.add(autoInput);
		
		JLabel taskDistribute = new JLabel("Task Distribution");
		taskDistribute.setBounds(286, 25, 112, 27);
		input.add(taskDistribute);
		
		taskDisButton = new JButton("Import");
		taskDisButton.addActionListener(this);
		taskDisButton.setBounds(377, 27, 89, 23);
		input.add(taskDisButton);

		JPanel logPanel = new JPanel();
		logPanel.setBounds(0, 233, 549, 158);
		contentPane.add(logPanel);
		logPanel.setBorder(new TitledBorder(null, "Log", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		logPanel.setLayout(null);
		log = new JTextArea(10, 20);
		log.setFont(new Font("Monospaced", Font.PLAIN, 10));
		log.setEditable(false);
		log.setLineWrap(true);
		log.setBackground(Color.WHITE);
		log.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(log);
		scroll.setLocation(5, 20);
		scroll.setSize(534, 133);
		logPanel.add(scroll); // Object of Jpanel
	}

	public void addLog(String str) {
		log.append(str);
		log.setCaretPosition(log.getDocument().getLength());
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == autoInput) {
			taskInfo = Configuaration.inputPath+"0.csv" ;
			taskDis = Configuaration.inputPath+"task_distribution.csv" ;
		}
		if (e.getSource() != exeButton &&e.getSource() != autoInput ) {
			fc = new JFileChooser(Configuaration.inputPath);
			int returnVal = fc.showOpenDialog(Home.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				logStr = new StringBuilder();
				File file = fc.getSelectedFile();
				if (e.getSource() == taskInfoButton) {
					taskInfo = file.getPath();
					addLog("Task info:\n" + "Importing file ..." + taskInfo + "\n");

				}
				if (e.getSource() == taskDisButton) {
					taskDis = file.getPath();
					addLog("Task Distribution:\n" + "Importing file ...." + taskDis + "\n");

				}
				if (e.getSource() == riskInfoButton) {
					riskInfo = file.getPath();
					addLog("Risk info:\n" + "Importing file ...." + riskInfo + "\n");
				}
				if (e.getSource() == riskRelateButton) {
					riskRelate = file.getPath();
					addLog("Risk relations:\n" + "Importing file ..." + riskRelate + "\n");

				}
				if (e.getSource() == riskDisButton) {
					riskDis = file.getPath();
					addLog("Risk Distribution:\n" + "Importing file ...." + riskDis + "\n");

				}
				if (e.getSource() == DInfoButton) {
					DInfo = file.getPath();
					addLog("Dimension Info:\n" + "Importing file ...." + DInfo + "\n");
				}
			}

		} else {
			
			/* execute the program */
			log.append("Caculating probability ....\n");
			log.setCaretPosition(log.getDocument().getLength());
			try {
				// caculate prob of each task in project 
				Project project = new Project(taskInfo,taskDis,40);
				project.update();
				project.calcProb();
				addLog("Task Network Visualization");
//				TaskInfomation t = new TaskInfomation(project.getTasks().get(0));
//				t.run();
				MainResult rs = new MainResult(project);
				rs.run();
				
//				TaskNet taskNet = new TaskNet(project.getTasks());
//				
//				taskNet.run();
				addLog("Bayesian Network Visualization ....");
				this.setVisible(false);
			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

	}
}
