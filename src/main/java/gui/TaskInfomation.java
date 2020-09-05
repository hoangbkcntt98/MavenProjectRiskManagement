package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import model.dimension.Dimension;
import model.risk.Risk;
import model.task.Task;

public class TaskInfomation extends JFrame {

	private JPanel contentPane;
	private JLabel name;
	private JLabel expectedTime;
	private JLabel es;
	private JLabel ef;
	private JLabel ls;
	private JLabel lf;
	private JLabel predecessor;
	Task task;
	
	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskInfomation frame = new TaskInfomation(task);
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
	public TaskInfomation(Task task) {
		setTitle("Task Information");
		this.task = task;
		String nameData = task.getName();
		String expectTimeData = String.valueOf(task.getExpectedTime());
		String esData =  String.valueOf(task.getEs());
		String efData =  String.valueOf(task.getEf());;
		String lsData = String.valueOf(task.getLs());
		String lfData = String.valueOf(task.getLf());
		String slackData = String.valueOf(task.getSlack());
		String probData = String.valueOf((double) Math.round(task.getProb()*1000)/1000);
		List<Double> dimensionProbList = task.getDimensionProbList();
		List<Dimension> dimensionList = task.getDimensionList();
		List<Risk> risks = task.getRisks();
		System.out.println("alo");
		String predecessorData = "";
		if(task.getPredecessor()!=null) {
			for(Task t:task.getPredecessor()) {
				predecessorData += t.getName();
				predecessorData+=" ";
			}
		}
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 505, 329);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, 0, 489, 290);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel expectedTimeLabel = new JLabel("Expected Time :");
		expectedTimeLabel.setBounds(10, 48, 99, 14);
		panel.add(expectedTimeLabel);
		
		JLabel esLabel = new JLabel("Early Start :");
		esLabel.setBounds(10, 80, 75, 14);
		panel.add(esLabel);
		
		JLabel lsLabel = new JLabel("Late Start :");
		lsLabel.setBounds(10, 113, 84, 14);
		panel.add(lsLabel);
		
		JLabel efLabel = new JLabel("Early Finish :");
		efLabel.setBounds(10, 147, 75, 14);
		panel.add(efLabel);
		
		JLabel lfLabel = new JLabel("Late Finish :");
		lfLabel.setBounds(10, 180, 84, 14);
		panel.add(lfLabel);
		
		name = new JLabel(nameData);
		name.setEnabled(false);
		name.setBounds(61, 23, 109, 14);
		panel.add(name);
		
		expectedTime = new JLabel(expectTimeData);
		expectedTime.setEnabled(false);
		expectedTime.setBounds(117, 48, 67, 14);
		panel.add(expectedTime);
		
		es = new JLabel(esData);
		es.setEnabled(false);
		es.setBounds(86, 80, 109, 14);
		panel.add(es);
		
		ls = new JLabel(lsData);
		ls.setEnabled(false);
		ls.setBounds(86, 113, 46, 14);
		panel.add(ls);
		
		ef = new JLabel(efData);
		ef.setEnabled(false);
		ef.setBounds(86, 147, 46, 14);
		panel.add(ef);
		
		lf = new JLabel(lfData);
		lf.setEnabled(false);
		lf.setBounds(86, 180, 46, 14);
		panel.add(lf);
		
		JLabel predecessorLabel = new JLabel("Predecessor : ");
		predecessorLabel.setBounds(218, 23, 84, 14);
		panel.add(predecessorLabel);
		
		predecessor = new JLabel(predecessorData);
		predecessor.setEnabled(false);
		predecessor.setBounds(304, 23, 99, 14);
		panel.add(predecessor);
		
		JLabel lblName = new JLabel("Name :");
		lblName.setBounds(10, 23, 84, 14);
		panel.add(lblName);
		
		JLabel slackLabel = new JLabel("Slack: ");
		slackLabel.setBounds(10, 216, 84, 14);
		panel.add(slackLabel);
		
		JLabel slack = new JLabel(slackData);
		slack.setEnabled(false);
		slack.setBounds(86, 216, 46, 14);
		panel.add(slack);
		
		JLabel probLabel = new JLabel("Probibability: ");
		probLabel.setBounds(218, 48, 84, 14);
		panel.add(probLabel);
		
		JLabel prob = new JLabel(probData);
		prob.setEnabled(false);
		prob.setBounds(304, 48, 75, 14);
		panel.add(prob);
		
		
		
		int i=0;
		if(dimensionList!=null) {
			JLabel DiemsionLabel = new JLabel("Prob in Dimension : ");
			DiemsionLabel.setBounds(218, 80, 150, 14);
			panel.add(DiemsionLabel);
			for(Dimension d: dimensionList) {
				JButton dimension = new JButton(d.getDimensionId());
				dimension.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						TaskNet taskNet = new TaskNet(d.getTasks());
						taskNet.run();
					}
				});
				dimension.setBounds(218, 109+i*26, 113, 23);
				panel.add(dimension);
				JLabel dimensionProb = new JLabel(String.valueOf(dimensionProbList.get(i)));
				dimensionProb.setBounds(341, 113+i*26, 100, 14);
				panel.add(dimensionProb);
				i++;
			}
		}
		if(risks!=null) {
			JLabel DiemsionLabel = new JLabel("Risk Model : ");
			DiemsionLabel.setBounds(218, 80, 150, 14);
			panel.add(DiemsionLabel);
			JButton dimension = new JButton("View Risk Model");
			dimension.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// view risk net
					RiskNet riskNet = new RiskNet(risks);
					riskNet.run();
				}
			});
			dimension.setBounds(218, 109, 113, 23);
			panel.add(dimension);
		}
		
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		close.setBounds(390, 256, 89, 23);
		panel.add(close);
		
		
	}
}
