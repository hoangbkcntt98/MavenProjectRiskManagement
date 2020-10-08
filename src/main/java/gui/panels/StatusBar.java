package gui.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import gui.common.MyButton;
import gui.infomation.TaskInfomation;
import model.Project;
import model.task.Task;
import utils.Utils;

public class StatusBar extends JPanel {

	/**
	 * Create the
	 */
	public StatusBar(double height, Project project) {
		setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Task successfull level", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setBounds(0, 0, 273, (int) height - 300);
		setLayout(null);
		List<Task> tasks = project.getTasks();
		JLabel projectLabel = new JLabel("Project");
		JProgressBar projectProb = new JProgressBar(0, 1000);
		projectProb.setValue((int) (project.getProb() * 1000));
		projectProb.setStringPainted(true);
		projectProb.setString(Utils.round(project.getProb() * 100) + "%");
		projectProb.setBounds(52, 39, 146, 14);
		projectProb.setForeground(Color.green);
		projectLabel.setBounds(10, 39, 46, 14);
		add(projectLabel);
		add(projectProb);

		JButton detailP = new MyButton("Detail",10);
		detailP.setBounds(200, 39, 50, 14);
		detailP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProjectInformation pInfor = new ProjectInformation(project);
				pInfor.run();
			}
		});
		add(detailP);

		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			double prob = tasks.get(i).getProb();
			JLabel lblNewLabel = new JLabel("Task " + tasks.get(i).getName());
			lblNewLabel.setBounds(10, 39 + (i + 1) * 35, 46, 14);
			add(lblNewLabel);
			JProgressBar progressBar = new JProgressBar(0, 1000);
			progressBar.setValue((int) (1000 * prob));
			progressBar.setStringPainted(true);
			progressBar.setString(Utils.round(prob * 100) + "%");
			progressBar.setBounds(52, 39 + (i + 1) * 35, 146, 14);
			if (prob < 0.4) {
				progressBar.setForeground(Color.red);
			} else if (prob >= 0.4 && prob < 0.7) {
				progressBar.setForeground(Color.yellow);
			} else {
				progressBar.setForeground(Color.green);
			}
			progressBar.setToolTipText("Task");
			JButton detail = new MyButton("Detail",10);
			detail.setBounds(200, 39 + (i + 1) * 35, 50, 14);
			detail.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					TaskInfomation taskInfo = new TaskInfomation(task);
					taskInfo.run();
				}
			});
			add(detail);
			add(progressBar);

		}

	}

}
