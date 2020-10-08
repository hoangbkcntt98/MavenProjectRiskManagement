package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import config.Configuaration;
import gui.common.MyButton;
import gui.common.MyLabel;
import gui.panels.FindTask;
import gui.panels.MainResult;
import model.Project;
import model.input.InputModel;

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
	private JButton showResult;
	private JFileChooser fc;
	private String riskInfo;
	private String taskInfo;
	private String riskRelate;
	private String riskDis;
	private String taskDis;
	private String DInfo;
	private JTextArea log;
	private JPanel input;
	private JPanel logPanel;
	private StringBuilder logStr;
	private Project pj;
	private InputModel inputModel;

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
		setTitle("Risk measurement");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 565, 427);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		inputModel = new InputModel();

		// add menu bar
		JMenuBar menubar = new JMenuBar();

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		JMenuItem inputItem = new JMenuItem("Input");
		inputItem.setMnemonic(KeyEvent.VK_I);
		inputItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contentPane.removeAll();
				initInputGUI();
				contentPane.revalidate();
				contentPane.repaint();
			}
		});
		file.add(inputItem);
		JMenuItem eMenuItem = new JMenuItem("Exit");
		eMenuItem.setMnemonic(KeyEvent.VK_E);
		eMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		file.add(eMenuItem);
		menubar.add(file);

		JMenu tools = new JMenu("Tools");
		tools.setMnemonic(KeyEvent.VK_T);

		JMenuItem findTask = new JMenuItem("Find Task");
		tools.add(findTask);
		menubar.add(tools);
		findTask.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contentPane.removeAll();
				FindTask findTask;
				findTask = new FindTask(pj);
				System.out.println();

				contentPane.add(findTask);
				contentPane.revalidate();
				contentPane.repaint();

			}
		});
		String instructions =  "<html>" +

			"<h3>Instructions:</h3>" + "<ul>" 
			+ "<li> First, enter your input in Input Screen"
			+ "<li> If you want show taskNet click to show result"
			+ "<li> Click Tools -> 'the functions which you want to use'"
			+ "<li> Click File -> input to reset Input"
			+ "<li> Click File -> exit for existing program"
			+ "</ul>"
			+ "</html>";
		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		
		

		JMenuItem about = new JMenuItem("How to use");
		help.add(about);
		menubar.add(help);
		about.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, instructions);
			}
		});

		setJMenuBar(menubar);
		initInputGUI();
	}

	public void initInputGUI() {
		input = new JPanel();
		input.setLayout(null);
		input.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Input",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		input.setBounds(0, 0, 549, 233);

		contentPane.add(input);

		exeButton = new JButton("Excute");
		exeButton.setBounds(154, 199, 89, 23);
		exeButton.addActionListener(this);
		input.add(exeButton);

		autoInput = new JButton("Auto Set");
		autoInput.addActionListener(this);
		autoInput.setBounds(253, 199, 89, 23);
		input.add(autoInput);

		showResult = new JButton("Result");
		showResult.addActionListener(this);
		showResult.setBounds(359, 199, 89, 23);
		input.add(showResult);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Task Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(20, 25, 223, 147);
		input.add(panel);
		panel.setLayout(null);

		JLabel taskDistribute = new MyLabel("Task Distribution",10);
		taskDistribute.setBounds(10, 21, 112, 27);
		panel.add(taskDistribute);

		taskDisButton = new MyButton("Import",10);
		taskDisButton.setBounds(113, 26, 70, 17);
		panel.add(taskDisButton);
		JLabel lblNewLabel_1 = new MyLabel("Task Info",10);
		lblNewLabel_1.setBounds(10, 54, 112, 27);
		panel.add(lblNewLabel_1);

		taskInfoButton = new MyButton("Import",10);
		taskInfoButton.setBounds(113, 59, 70, 17);
		panel.add(taskInfoButton);

		JLabel lblNewLabel_1_1 = new MyLabel("DimensionInfo",10);
		lblNewLabel_1_1.setBounds(10, 87, 106, 27);
		panel.add(lblNewLabel_1_1);

		DInfoButton = new MyButton("Import",10);
		DInfoButton.setBounds(113, 92, 70, 17);
		panel.add(DInfoButton);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Risk Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(286, 25, 237, 147);
		input.add(panel_1);
		panel_1.setLayout(null);

		riskInfoButton = new MyButton("Import",10);
		riskInfoButton.setBounds(123, 32, 70, 17);
		panel_1.add(riskInfoButton);

		JLabel lblNewLabel_1_3 = new MyLabel("Risk Relation",10);
		lblNewLabel_1_3.setBounds(28, 58, 112, 27);
		panel_1.add(lblNewLabel_1_3);

		riskRelateButton = new MyButton("Import",10);
		riskRelateButton.setBounds(123, 63, 70, 17);
		panel_1.add(riskRelateButton);

		riskDisButton = new MyButton("Import",10);
		riskDisButton.setBounds(123, 97, 70, 17);
		panel_1.add(riskDisButton);

		JLabel lblNewLabel_1_2 = new MyLabel("Risk Info",10);
		lblNewLabel_1_2.setBounds(28, 27, 112, 27);
		panel_1.add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_4 = new MyLabel("Risk Distribution",10);
		lblNewLabel_1_4.setBounds(28, 92, 112, 27);
		panel_1.add(lblNewLabel_1_4);
		riskDisButton.addActionListener(this);
		riskRelateButton.addActionListener(this);
		riskInfoButton.addActionListener(this);
		DInfoButton.addActionListener(this);
		taskInfoButton.addActionListener(this);
		taskDisButton.addActionListener(this);

		logPanel = new JPanel();
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
		if (e.getSource() == autoInput) {
			taskInfo = Configuaration.inputPath + "0.csv";
			taskDis = Configuaration.inputPath + "task_distribution.csv";
		}
		if (e.getSource() != exeButton && e.getSource() != autoInput && e.getSource()!= showResult) {
			fc = new JFileChooser(Configuaration.inputPath);
			int returnVal = fc.showOpenDialog(Home.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				logStr = new StringBuilder();
				File file = fc.getSelectedFile();
				if (e.getSource() == taskInfoButton) {
					taskInfo = file.getPath();
					addLog("Task info:\n" + "Importing file ..." + taskInfo + "\n");
					inputModel.setTaskInfo(taskInfo);
					
				}
				if (e.getSource() == taskDisButton) {
					taskDis = file.getPath();
					addLog("Task Distribution:\n" + "Importing file ...." + taskDis + "\n");
					inputModel.setTaskDis(taskDis);

				}
				if (e.getSource() == riskInfoButton) {
					riskInfo = file.getPath();
					addLog("Risk info:\n" + "Importing file ...." + riskInfo + "\n");
					inputModel.setRiskInfo(riskInfo);
				}
				if (e.getSource() == riskRelateButton) {
					riskRelate = file.getPath();
					addLog("Risk relations:\n" + "Importing file ..." + riskRelate + "\n");
					inputModel.setRiskRelate(riskRelate);

				}
				if (e.getSource() == riskDisButton) {
					riskDis = file.getPath();
					addLog("Risk Distribution:\n" + "Importing file ...." + riskDis + "\n");
					inputModel.setRiskDis(riskDis);

				}
				if (e.getSource() == DInfoButton) {
					DInfo = file.getPath();
					addLog("Dimension Info:\n" + "Importing file ...." + DInfo + "\n");
					inputModel.setDimensionInfo(DInfo);
				}
				
			}

		} else {
			if (e.getSource() == showResult) {
				addLog("Task Network Visualization");
				MainResult rs = new MainResult(pj);
				rs.run();

				addLog("Bayesian Network Visualization ....");
			}

			/* execute the program */
			log.append("Caculating probability ....\n");
			log.setCaretPosition(log.getDocument().getLength());
			
				String checkInput = inputModel.checkEmpty();
				if(checkInput.equals("OK")) {
					// caculate prob of each task in project
					pj = new Project(inputModel, 40);

					pj.update();
					pj.calcProb();
					addLog("Resources are updated");
				}else {
					JOptionPane.showMessageDialog(null, checkInput, "Error",JOptionPane.ERROR_MESSAGE);
				}
			

		}

	}
}
