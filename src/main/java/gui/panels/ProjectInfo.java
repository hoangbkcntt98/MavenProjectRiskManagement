package gui.panels;

import javax.swing.JPanel;

import algorithms.pert.Pert;
import model.Project;
import model.task.Task;
import utils.Utils;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProjectInfo extends JPanel {

	/**
	 * Create the panel.
	 */
	
	public ProjectInfo(Project pj) {
		
		setLayout(null);
		String probStr = Utils.round(pj.getProb());
		String critPathStr = "";
		double totalTimeP =0;
		for(Task t:pj.getCriticalPath()) {
			critPathStr = critPathStr+t.getName()+" -->";
			totalTimeP+=t.getExpectedTime();
			
		}
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Project Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, 0, 450, 200);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Prob");
		lblNewLabel.setBounds(32, 57, 46, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("CriticalPath");
		lblNewLabel_1.setBounds(32, 86, 76, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Duration");
		lblNewLabel_2.setBounds(32, 111, 76, 14);
		panel.add(lblNewLabel_2);
		
		JLabel prob = new JLabel(probStr);
		prob.setBounds(118, 57, 46, 14);
		panel.add(prob);
		
		JLabel critPath = new JLabel(critPathStr);
		critPath.setBounds(118, 86, 301, 14);
		panel.add(critPath);
		
		JLabel totalTime = new JLabel(Utils.round(totalTimeP));
		totalTime.setBounds(118, 111, 46, 14);
		panel.add(totalTime);
		
		
		

	}

}
