package gui.panels;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import gui.infomation.TaskInfomation;
import model.Project;
import model.dimension.Dimension;
import model.task.Task;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class FindTask extends JPanel {
//	private Project pj;
	/**
	 * Create the panel.
	 */
	private Task task;
	private String taskToFind;
	private Dimension dimensionToFind;
	private boolean hasRiskToFind;
	private boolean criticalToFind;
	private List<Dimension> dimensionList;
	private Project pj;
	JCheckBox hasRisk;
	JCheckBox critical;
	public FindTask(Project pj) {
		this.pj = pj;
		this.dimensionList = pj.getTasks().get(0).getDimensionList();
		setLayout(null);
		setBounds(0, 0, 549, 427);
		JPanel input = new JPanel();
		input.setBorder(new TitledBorder(null, "Find Task", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		input.setBounds(0, 0, 549, 401);
		add(input);
		input.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Task");
		lblNewLabel.setBounds(93, 54, 35, 14);
		input.add(lblNewLabel);
		
		JButton find = new JButton("Find");
		find.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Task taskRes = findTask();
				if(taskRes == null) {
					JOptionPane.showMessageDialog(null, "Not found");
					System.out.println("Not found");
					System.out.println("");
				}else {
					TaskInfomation taskInfo = new TaskInfomation(taskRes);
					taskInfo.run();
				}
			}
		});
		find.setBounds(424, 294, 89, 23);
		input.add(find);
		
		String [] comboItem = new String [pj.getTasks().size()];
		for(int i=0;i<comboItem.length;i++) {
			comboItem[i] = pj.getTasks().get(i).getName();
		}
		
		JComboBox tasks = new JComboBox(comboItem);
		tasks.setBounds(158, 50, 100, 22);
		input.add(tasks);
		tasks.addActionListener(new ActionListener() {

	        public void actionPerformed(ActionEvent e)
	        {
	            JComboBox comboBox = (JComboBox) e.getSource();
	            String o = (String)comboBox.getSelectedItem();
	            taskToFind = o;
	        }
	    });  
	
		
		JLabel dLabel = new JLabel("Dimension");
		dLabel.setBounds(81, 105, 67, 14);
		input.add(dLabel);
		
		JComboBox dimensions = new JComboBox(new String[] {"Project","Size","Productivity","Worker-hour","Duration","Cost"});
		dimensions.setBounds(158, 101, 100, 22);
		input.add(dimensions);
		dimensions.addActionListener(new ActionListener() {

	        public void actionPerformed(ActionEvent e)
	        {
	            JComboBox comboBox = (JComboBox) e.getSource();
	            String o = (String)comboBox.getSelectedItem();
	            for(Dimension d:dimensionList) {
	            	if(d.getName().equals(o)) {
	            		 dimensionToFind = d;
	            	}
	            }
	           
	        }
	    }); 
		hasRisk = new JCheckBox("has Risk");
		
		hasRisk.setBounds(176, 165, 97, 23);
		input.add(hasRisk);
		
		critical = new JCheckBox("on Critical Path");
		critical.setBounds(176, 191, 97, 23);
		
		input.add(critical);

	}
	public Task findTask() {
		hasRiskToFind = hasRisk.isSelected();
		criticalToFind = critical.isSelected();
		if(taskToFind==null) taskToFind ="A";
		if(dimensionToFind==null) {
			for(Task t:pj.getTasks()) {
				if(t.getName().equals(taskToFind)) {
					if(checkTask(t, hasRiskToFind, criticalToFind)) {
						return t;
					}else {
						return null;
					}
					
				}
			}
		}
		System.out.println("Task Name:"+ taskToFind);
		System.out.println("Dimension:" + dimensionToFind.getName());
		System.out.println("hasRisk :" + hasRiskToFind);
		System.out.println("ciritcal"+ criticalToFind);
		for(Task t:dimensionToFind.getTasks()) {
			if(t.getName().equals(taskToFind)) {
				if(checkTask(t, hasRiskToFind, criticalToFind)) {
					return t;
				}else {
					return null;
				}
				
			}
		}
		return null;
	}
	public boolean checkTask(Task t,boolean hasRisk,boolean critical) {
		boolean checkRisk =false;
		boolean checkCrit = false;
		if(t.getRisks()!=null) {
			checkRisk= true;
		}
		if(t.getSlack()==0) {
			checkCrit = true;
		}
		return ((hasRisk==checkRisk)&&(critical == checkCrit))?true:false;
	}
}
