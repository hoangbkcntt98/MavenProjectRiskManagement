package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import model.Project;
import model.task.Task;
import utils.Utils;

public class StatusBar extends JPanel {

	/**
	 * Create the 
	 */
	public StatusBar(double height,Project project) {
		setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Status",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setBounds(0, 0, 273,(int) height-100);
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

		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setValue(100);
		progressBar_1.setForeground(Color.red);
		progressBar_1.setBounds(10, 608, 63, 14);
		add(progressBar_1);

		JProgressBar progressBar_1_1 = new JProgressBar();
		progressBar_1_1.setValue(100);
		progressBar_1_1.setForeground(Color.yellow);
		progressBar_1_1.setBounds(97, 608, 63, 14);
		add(progressBar_1_1);

		JProgressBar progressBar_1_2 = new JProgressBar();
		progressBar_1_2.setValue(100);
		progressBar_1_2.setForeground(Color.green);
		progressBar_1_2.setBounds(181, 608, 63, 14);
		add(progressBar_1_2);

		JLabel lblNewLabel_1 = new JLabel("Low");
		lblNewLabel_1.setBounds(20, 633, 46, 14);
		add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Medium");
		lblNewLabel_1_1.setBounds(107, 633, 46, 14);
		add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("High");
		lblNewLabel_1_2.setBounds(191, 633, 46, 14);
		add(lblNewLabel_1_2);
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
			JButton detail = new JButton("Detail");
			detail.setBounds(200, 39 + (i + 1) * 35, 50, 14);
			detail.setFont(new Font("Arial", Font.PLAIN, 10));
			detail.setMargin(new Insets(0, 0, 0, 0));
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
