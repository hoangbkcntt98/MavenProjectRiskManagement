package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import gui.common.MyProgress;
import gui.common.ThreadSimple;
import gui.panels.FindTask;
import gui.panels.MainResult;
import model.Project;
import model.input.InputModel;
import javax.swing.JProgressBar;

public class Home extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton exeButton;
	private JButton taskInfoButton;
	private JButton DInfoButton;
	private JButton riskInfoButton;
	private JButton riskDisButton;
	private JButton taskDisButton;
	private JButton riskRelateButton;
	private JButton resetInput;
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
	private JPanel taskInput;
	private JPanel riskInput;
	private JLabel taskDistribute;
	private JLabel taskInfoLabel;
	JLabel dInforLabel;
	JLabel riskRelateLabel;
	JLabel riskInfoLabel;
	JLabel riskDisLabel;
	JButton multiImport;

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
		String instructions = "<html>" +

				"<h3>Instructions:</h3>" + "<ul>" + "<li> First, enter your input in Input Screen"
				+ "<li> If you want show taskNet click to show result"
				+ "<li> Click Tools -> 'the functions which you want to use'"
				+ "<li> Click File -> input to reset Input" + "<li> Click File -> exit for existing program" + "</ul>"
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

		exeButton = new JButton("Import");
		exeButton.setBounds(119, 183, 89, 23);
		exeButton.addActionListener(this);
		input.add(exeButton);

		resetInput = new MyButton("Reset", 10);
		resetInput.addActionListener(this);
		resetInput.setBounds(239, 91, 53, 23);
		input.add(resetInput);

		taskInput = new JPanel();
		taskInput.setBorder(new TitledBorder(null, "Task Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		taskInput.setBounds(10, 25, 223, 147);
		input.add(taskInput);
		taskInput.setLayout(null);

		taskDistribute = new MyLabel("Task Distribution", 10);
		taskDistribute.setBounds(10, 21, 112, 27);

		taskDisButton = new MyButton("Browse", 10);
		taskDisButton.setBounds(113, 26, 70, 17);

		taskInfoLabel = new MyLabel("Task Info", 10);
		taskInfoLabel.setBounds(10, 54, 112, 27);

		taskInfoButton = new MyButton("Browse", 10);
		taskInfoButton.setBounds(113, 59, 70, 17);

		dInforLabel = new MyLabel("DimensionInfo", 10);
		dInforLabel.setBounds(10, 87, 106, 27);

		DInfoButton = new MyButton("Browse", 10);
		DInfoButton.setBounds(113, 92, 70, 17);

		riskInput = new JPanel();
		riskInput.setBorder(new TitledBorder(null, "Risk Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		riskInput.setBounds(302, 25, 237, 147);
		input.add(riskInput);
		riskInput.setLayout(null);

		riskInfoButton = new MyButton("Browse", 10);
		riskInfoButton.setBounds(123, 32, 70, 17);

		riskRelateLabel = new MyLabel("Risk Relation", 10);
		riskRelateLabel.setBounds(28, 58, 112, 27);

		riskRelateButton = new MyButton("Browse", 10);
		riskRelateButton.setBounds(123, 63, 70, 17);

		riskDisButton = new MyButton("Browse", 10);
		riskDisButton.setBounds(123, 97, 70, 17);

		riskInfoLabel = new MyLabel("Risk Info", 10);
		riskInfoLabel.setBounds(28, 27, 112, 27);

		riskDisLabel = new MyLabel("Risk Distribution", 10);
		riskDisLabel.setBounds(28, 92, 112, 27);

		multiImport = new JButton("AutoSet");
		multiImport.addActionListener(this);
		multiImport.setBounds(20, 183, 89, 23);
		input.add(multiImport);

		showResult = new JButton("Result");
		showResult.setBounds(263, 183, 89, 23);
		input.add(showResult);
		showResult.addActionListener(this);
		initInputComp();

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

	public void initInputComp() {
		riskInput.add(riskDisLabel);
		riskInput.add(riskInfoLabel);
		riskInput.add(riskDisButton);
		riskInput.add(riskRelateButton);
		riskInput.add(riskRelateLabel);
		riskInput.add(riskInfoButton);
		taskInput.add(DInfoButton);
		taskInput.add(taskInfoButton);
		taskInput.add(taskInfoLabel);
		taskInput.add(taskDistribute);
		taskInput.add(taskDisButton);
		taskInput.add(dInforLabel);
	}

	public void addLog(String str) {
		log.append(str);
		log.setCaretPosition(log.getDocument().getLength());
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == resetInput) {
			taskInfo = Configuaration.inputPath + "0.csv";
			taskDis = Configuaration.inputPath + "task_distribution.csv";
		}
		if (e.getSource() != exeButton && e.getSource() != resetInput && e.getSource() != showResult
				&& e.getSource() != multiImport) {
			fc = new JFileChooser(Configuaration.inputPath);
			int returnVal = fc.showOpenDialog(Home.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				logStr = new StringBuilder();
				File file = fc.getSelectedFile();
				if (e.getSource() == taskInfoButton) {
					taskInfo = file.getPath();
					String redStr = "Task info:";
					addLog(redStr);
					addLog("\n" + "Importing file ..." + taskInfo + "\n");
					inputModel.setTaskInfo(taskInfo);
					updateInputGUI(file, taskInfoButton, taskInput);

				}
				if (e.getSource() == taskDisButton) {
					taskDis = file.getPath();
					addLog("Task Distribution:\n" + "Importing file ...." + taskDis + "\n");
					inputModel.setTaskDis(taskDis);
					updateInputGUI(file, taskDisButton, taskInput);

				}
				if (e.getSource() == riskInfoButton) {
					riskInfo = file.getPath();
					addLog("Risk info:\n" + "Importing file ...." + riskInfo + "\n");
					inputModel.setRiskInfo(riskInfo);
					updateInputGUI(file, riskInfoButton, riskInput);
				}
				if (e.getSource() == riskRelateButton) {
					riskRelate = file.getPath();
					addLog("Risk relations:\n" + "Importing file ..." + riskRelate + "\n");
					inputModel.setRiskRelate(riskRelate);
					updateInputGUI(file, riskRelateButton, riskInput);

				}
				if (e.getSource() == riskDisButton) {
					riskDis = file.getPath();
					addLog("Risk Distribution:\n" + "Importing file ...." + riskDis + "\n");
					inputModel.setRiskDis(riskDis);
					updateInputGUI(file, riskDisButton, riskInput);

				}
				if (e.getSource() == DInfoButton) {
					DInfo = file.getPath();
					addLog("Dimension Info:\n" + "Importing file ...." + DInfo + "\n");
					inputModel.setDimensionInfo(DInfo);
					updateInputGUI(file, DInfoButton, taskInput);
				}

			}

		} else {
			if (e.getSource() == multiImport) {
				ThreadSimple t1 = new ThreadSimple("Reading Resources ...");
				t1.start();
				Thread t2 = new Thread(new Runnable() {

					@Override
					public void run() {
//						try {
//							Thread.sleep(1000);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
						inputModel.setDimensionInfo(Configuaration.inputPath + "dimension_info.csv");
						inputModel.setRiskDis((Configuaration.inputPath + "risk_distribution.csv"));
						inputModel.setRiskInfo(Configuaration.inputPath + "risk_info.csv");
						inputModel.setRiskRelate(Configuaration.inputPath + "risk_relation.csv");
						inputModel.setTaskDis(Configuaration.inputPath + "task_distribution.csv");
						inputModel.setTaskInfo(Configuaration.inputPath + "task_info.csv");
						pj = new Project(inputModel, 40);
						pj.update();
						pj.calcProb();
						JOptionPane.showMessageDialog(null, "Resources are updated");
					}
				});
				t2.start();
			}
			if (e.getSource() == showResult) {

				ThreadSimple t1 = new ThreadSimple("Generating GUI ...");
				t1.start();
				Thread t3 = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						MainResult rs = new MainResult(pj);
						rs.run();
						addLog("Bayesian Network Visualization ....");
					}
				});
				t3.start();// if t1 is finished then t2 will start
				if (t3.isAlive()) {

				}
				addLog("Task Network Visualization");

			}
			if (e.getSource() == resetInput) {
				inputModel.reset();
				taskInput.removeAll();
				riskInput.removeAll();
				initInputComp();
				taskInput.revalidate();
				taskInput.repaint();
				riskInput.revalidate();
				riskInput.repaint();
				log.setText("");
				log.revalidate();
				log.repaint();
				input.revalidate();
				input.repaint();

			}
			if (e.getSource() == exeButton) {
				ThreadSimple t1 = new ThreadSimple("Importing Resource...");
				t1.start();
				/* execute the program */
				log.setCaretPosition(log.getDocument().getLength());

				String checkInput = inputModel.checkEmpty();
				if (checkInput.equals("OK")) {
					Thread t2 = new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								pj = new Project(inputModel, 40);
								pj.update();
								pj.calcProb();
								JOptionPane.showMessageDialog(null, "Resources are updated");
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Import Fails!\nResoure is invalid!", "ERRORS",
										JOptionPane.ERROR_MESSAGE);
								// TODO: handle exception
							}
						}
					});
					t2.start();
					addLog("Resources are updated");
				} else {
					JOptionPane.showMessageDialog(null, checkInput, "Errors", JOptionPane.ERROR_MESSAGE);
				}
			}

		}

	}

	public void updateInputGUI(File file, JButton button, JPanel panel) {
		panel.remove(button);
		JLabel label = new JLabel(file.getName());
		label.setFont(new Font("Arial", Font.PLAIN, 10));
		label.setBounds(button.getBounds());
		label.setSize(100, 20);
		panel.add(label);
		input.revalidate();
		input.repaint();
	}

}
