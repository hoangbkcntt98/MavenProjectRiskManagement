package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
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
import gui.common.IconGenerator;
import gui.common.MyButton;
import gui.common.MyFrame;
import gui.common.MyLabel;
import gui.common.MyProgress;
import gui.common.ThreadSimple;
import gui.panels.FindTask;
import gui.panels.MainResult;
import gui.panels.SpiderMappingPanel;
import model.Project;
import model.dimension.Dimension;
import model.input.InputModel;
import javax.swing.JProgressBar;
import java.awt.Panel;
import java.awt.Stroke;

public class Home extends MyFrame implements ActionListener {

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
	private JLabel dInforLabel;
	private JLabel riskRelateLabel;
	private JLabel riskInfoLabel;
	private JLabel riskDisLabel;
	private JButton multiImport;
	public static Home it;
	private String instructions = "<html>" + "<h3>Instructions:</h3>" 
			+ "<p> First, enter your input in Input Screen</p>" 
			+ "<p> If you want show taskNet click to<span style='font-style:italic;color:#9C5D5D'>Result </span> to show result</p>"
			+ "<p> Click<span style='font-style:italic;color:#9C5D5D'> Tools ->'the functions which you want to use'</span></p>" 
			+ "<p> Click <span style='font-style:italic;color:#9C5D5D'>File -> input</span> to reset Input</p>"
			+ "<p> Click <span style='font-style:italic;color:#9C5D5D'>File -> exit</span> to exit program</p>" 
			+ "</html>";
	private String aboutUs = "<html>" + "<div>" + "<h3 >Risk measurement Tools</h3>"
			+ "<div style = 'font-style: italic;'>" + "<p>Version: 1.1.0</p>"
			+ "<p>Source: <a href = 'https://github.com/VNISTLogistics/PlanningMultiDimensions.git'>https://github.com/VNISTLogistics/PlanningMultiDimensions.git</a></p>"
			+ "<p>Contact: bugger485@gmail.com</p>" + "<br><p >Copyright &copy; 2020 by ProjectII Team</p>" + "</div>"
			+ "</html>" + "</div>";

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
		it = this;
		setResizable(false);

		setTitle("Risk measurement");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 300, 565, 427);
		setLocationRelativeTo(null);
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
		inputItem.setIcon(IconGenerator.getImportIcon(13, 13));
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
		eMenuItem.setIcon(IconGenerator.getIcon(Configuaration.imagesPath+"exit.png", 13, 13));
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
		findTask.setMnemonic(KeyEvent.VK_F);
		findTask.setIcon(IconGenerator.getIcon(Configuaration.imagesPath+"search.png", 13, 13));
		tools.add(findTask);
		menubar.add(tools);
		findTask.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (pj == null) {
					addLog("Empty Input");
					JOptionPane.showMessageDialog(null, "You must enter your 'input' to use this feature",
							"Input empty", JOptionPane.ERROR_MESSAGE);
				} else {
					contentPane.removeAll();
					FindTask findTask;
					findTask = new FindTask(pj);
					contentPane.add(findTask);
					contentPane.revalidate();
					contentPane.repaint();
				}

			}
		});
		// add sprider mapping tools

		JMenuItem spider = new JMenuItem("Spider Mapping");
		tools.add(spider);
		spider.setIcon(IconGenerator.getIcon(Configuaration.imagesPath+"spider.png", 13, 13));
		spider.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (pj == null) {
					addLog("Empty Input");
					JOptionPane.showMessageDialog(null, "You must enter your 'input' to use this feature",
							"Input empty", JOptionPane.ERROR_MESSAGE);
				} else {
					List<Dimension> dimensionList = pj.getTasks().get(0).getDimensionList();
					contentPane.removeAll();
					SpiderMappingPanel spiderMappingPanel = new SpiderMappingPanel(dimensionList);
					spiderMappingPanel.setBounds(160, -10, 551, 388);
					spiderMappingPanel.setBorder(new TitledBorder(
							new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
							"", TitledBorder.CENTER, TitledBorder.BOTTOM, null, new Color(0, 0, 0)));
					contentPane.add(spiderMappingPanel);
					JPanel disPanel = new JPanel() {
						@Override
						protected void paintComponent(Graphics g) {
							super.paintComponent(g);
							Graphics2D g2d = (Graphics2D) g.create();
							Stroke basic = new BasicStroke(2);
							g2d.setStroke(basic);

							for (int i = 0; i < dimensionList.size(); i++) {
								if (i == 0) {
									g2d.setColor(Color.blue);
								}
								if (i == 1)
									g2d.setColor(Color.red);
								if (i == 2)
									g2d.setColor(Color.green);
								if (i == 3)
									g2d.setColor(Color.yellow);
								if (i == 4)
									g2d.setColor(Color.cyan);
								g2d.drawLine(40, 27 + 30 * i, 90, 27 + 30 * i);
								g2d.setColor(Color.black);
								g2d.drawString(dimensionList.get(i).getName(), 45, 40 + 30 * i);

							}
							g2d.setColor(Color.black);
							g2d.setStroke(new BasicStroke(2));
							g2d.drawLine(40, 27 + 30 * 5, 90, 27 + 30 * 5);
							g2d.drawString("Prob Line", 45, 40 + 30 * 5);
							g2d.drawString("0", 30, 27 + 30 * 5);
							g2d.drawString("100(%)", 90, 27 + 30 * 5);

						};
					};
					disPanel.setBounds(0, 0, 150, contentPane.getHeight() - 50);
					disPanel.setBorder(new TitledBorder(
							new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
							"Description", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
					contentPane.add(disPanel);
					JPanel showPanel = new JPanel();
					showPanel.setBounds(0, disPanel.getHeight(), 150, 50);
					contentPane.add(showPanel);
					MyButton showDetail = new MyButton("View Detail Information", 10);
					showDetail.setBounds(0, 400, 10, 24);
					showDetail.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							showDetail();
						}
					});
					showPanel.add(showDetail);
					contentPane.revalidate();
					contentPane.repaint();
				}

			}
		});
		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		JMenuItem howToUse = new JMenuItem("How to use");
		howToUse.setIcon(IconGenerator.getIcon(Configuaration.imagesPath+"how.png", 13, 13));
		howToUse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ImageIcon icon = IconGenerator.getRiskIcon(100, 100);
				JOptionPane.showOptionDialog(null, instructions, "How to Use", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, icon, new Object[] {}, null);
			}
		});
		help.add(howToUse);
		JMenuItem about = new JMenuItem("About");
		about.setIcon(IconGenerator.getIcon(Configuaration.imagesPath+"about.png", 13, 13));
		about.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ImageIcon icon = IconGenerator.getRiskIcon(100, 100);

				JOptionPane.showOptionDialog(null, aboutUs, "About Us", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, icon, new Object[] {}, null);

			}
		});
		help.add(about);
		menubar.add(help);
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

		exeButton = new MyButton("Import",10);
		exeButton.setIcon(IconGenerator.getImportIcon(12, 12));
		exeButton.setBounds(119, 183, 89, 23);
		exeButton.addActionListener(this);
		input.add(exeButton);

		resetInput = new MyButton("Reset", 10,IconGenerator.getIcon(Configuaration.imagesPath+"reset.png", 14, 14));
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

		taskDisButton = new MyButton("Browse", 10,IconGenerator.getIcon(Configuaration.imagesPath+"browse1.png", 14, 14));
		taskDisButton.setBounds(113, 26, 70, 17);

		taskInfoLabel = new MyLabel("Task Info", 10);
		taskInfoLabel.setBounds(10, 54, 112, 27);

		taskInfoButton = new MyButton("Browse", 10,IconGenerator.getIcon(Configuaration.imagesPath+"browse1.png", 14, 14));
		taskInfoButton.setBounds(113, 59, 70, 17);

		dInforLabel = new MyLabel("DimensionInfo", 10);
		dInforLabel.setBounds(10, 87, 106, 27);

		DInfoButton = new MyButton("Browse", 10,IconGenerator.getIcon(Configuaration.imagesPath+"browse1.png", 14, 14));
		DInfoButton.setBounds(113, 92, 70, 17);

		riskInput = new JPanel();
		riskInput.setBorder(new TitledBorder(null, "Risk Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		riskInput.setBounds(302, 25, 237, 147);
		input.add(riskInput);
		riskInput.setLayout(null);

		riskInfoButton = new MyButton("Browse", 10,IconGenerator.getIcon(Configuaration.imagesPath+"browse1.png", 14, 14));
		riskInfoButton.setBounds(123, 32, 70, 17);

		riskRelateLabel = new MyLabel("Risk Relation", 10);
		riskRelateLabel.setBounds(28, 58, 112, 27);

		riskRelateButton = new MyButton("Browse", 10,IconGenerator.getIcon(Configuaration.imagesPath+"browse1.png", 14, 14));
		riskRelateButton.setBounds(123, 63, 70, 17);

		riskDisButton = new MyButton("Browse", 10,IconGenerator.getIcon(Configuaration.imagesPath+"browse1.png", 14, 14));
		riskDisButton.setBounds(123, 97, 70, 17);

		riskInfoLabel = new MyLabel("Risk Info", 10);
		riskInfoLabel.setBounds(28, 27, 112, 27);

		riskDisLabel = new MyLabel("Risk Distribution", 10);
		riskDisLabel.setBounds(28, 92, 112, 27);

		multiImport = new MyButton("AutoSet",10);
		multiImport.setIcon(IconGenerator.getIcon(Configuaration.imagesPath+"auto.png", 13, 13));
		multiImport.addActionListener(this);
		multiImport.setBounds(20, 183, 89, 23);
		input.add(multiImport);

		showResult = new JButton("Result");
		showResult.setBounds(400, 183, 89, 23);
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
			//delete auto set button
			input.remove(multiImport);
			input.revalidate();
			input.repaint();
			fc = new JFileChooser(Configuaration.inputPath);
			int returnVal = fc.showOpenDialog(Home.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				logStr = new StringBuilder();
				File file = fc.getSelectedFile();
				if (e.getSource() == taskInfoButton) {
					taskInfo = file.getPath();
					String redStr = "Task info:";
					addLog(redStr);
					addLog("\n" + "Importing file ..." + file.getName() + "\n");
					inputModel.setTaskInfo(taskInfo);
					updateInputGUI(file.getName(), taskInfoButton, taskInput);

				}
				if (e.getSource() == taskDisButton) {
					taskDis = file.getPath();
					addLog("Task Distribution:\n" + "Importing file ...." + file.getName() + "\n");
					inputModel.setTaskDis(taskDis);
					updateInputGUI(file.getName(), taskDisButton, taskInput);

				}
				if (e.getSource() == riskInfoButton) {
					riskInfo = file.getPath();
					addLog("Risk info:\n" + "Importing file ...." + file.getName() + "\n");
					inputModel.setRiskInfo(riskInfo);
					updateInputGUI(file.getName(), riskInfoButton, riskInput);
				}
				if (e.getSource() == riskRelateButton) {
					riskRelate = file.getPath();
					addLog("Risk relations:\n" + "Importing file ..." + file.getName() + "\n");
					inputModel.setRiskRelate(riskRelate);
					updateInputGUI(file.getName(), riskRelateButton, riskInput);

				}
				if (e.getSource() == riskDisButton) {
					riskDis = file.getPath();
					addLog("Risk Distribution:\n" + "Importing file ...." + file.getName() + "\n");
					inputModel.setRiskDis(riskDis);
					updateInputGUI(file.getName(), riskDisButton, riskInput);

				}
				if (e.getSource() == DInfoButton) {
					DInfo = file.getPath();
					addLog("Dimension Info:\n" + "Importing file ...." + file.getName() + "\n");
					inputModel.setDimensionInfo(DInfo);
					updateInputGUI(file.getName(), DInfoButton, taskInput);
				}

			}

		} else {
			if (e.getSource() == multiImport) {
				input.remove(exeButton);
				input.revalidate();
				input.repaint();
				addLog("Auto Set Input From Folder :" + Configuaration.inputPath);
				setEnabled(false);
				ThreadSimple t1 = new ThreadSimple("Reading Resources ...");
				t1.start();
				Thread t2 = new Thread(new Runnable() {

					@Override
					public void run() {
						DInfo = "dimension_info.csv";
						riskDis = "risk_distribution.csv";
						riskInfo = "risk_info.csv";
						riskRelate = "risk_relation.csv";
						taskInfo = "task_info.csv";
						taskDis = "task_distribution.csv";
						updateInputGUI(DInfo, DInfoButton, taskInput);
						updateInputGUI(riskDis, riskDisButton, riskInput);
						updateInputGUI(riskInfo, riskInfoButton, riskInput);
						updateInputGUI(riskRelate, riskRelateButton, riskInput);
						updateInputGUI(taskInfo, taskInfoButton, taskInput);
						updateInputGUI(taskDis, taskDisButton, taskInput);
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
						Home.it.setVisible(true);
						Home.it.setEnabled(true);

					}
				});
				t2.start();
//				

			}
			if (e.getSource() == showResult) {
				addLog("Generate GUI");
				showDetail();
				addLog("Task Network Visualization");

			}
			if (e.getSource() == resetInput) {
				input.add(exeButton);
				input.add(multiImport);
				input.revalidate();
				input.repaint();
				addLog("Reset input");
				pj = null;
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
				
			
				/* execute the program */

				String checkInput = inputModel.checkEmpty();
				if (checkInput.equals("OK")) {
					setEnabled(false);
					addLog("Importing Resource...");
					ThreadSimple t1 = new ThreadSimple("Importing Resource...");
					t1.start();
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
								pj=null;
								
								// TODO: handle exception
							}
							Home.it.setVisible(true);
							Home.it.setEnabled(true);
						}
					});
					t2.start();
					if(pj==null) t1.stop();
					addLog("Resources are updated");
				} else {
					JOptionPane.showMessageDialog(null, checkInput + " is Empty", "Errors", JOptionPane.ERROR_MESSAGE);
				}
			}

		}

	}

	public void showDetail() {
		if (pj == null) {
			addLog("Empty Input");
			JOptionPane.showMessageDialog(null, "You must enter your 'input' to use this feature", "Input empty",
					JOptionPane.ERROR_MESSAGE);
		} else {
			setEnabled(false);
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
					Home.it.setVisible(true);
					Home.it.setEnabled(true);
				}
			});
			t3.start();// if t1 is finished then t2 will start
		}

	}

	public void updateInputGUI(String name, JButton button, JPanel panel) {
		panel.remove(button);
		JLabel label = new JLabel(name);
		label.setFont(new Font("Arial", Font.PLAIN, 10));
		label.setIcon(IconGenerator.getExcel(20, 20));
		label.setBounds(button.getBounds());
		label.setSize(100, 20);
		panel.add(label);
		input.revalidate();
		input.repaint();
	}
}
