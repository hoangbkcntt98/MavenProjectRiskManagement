package gui.infomation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.risk.Risk;
import utils.Utils;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;

import gui.common.MyLabel;
import gui.common.MyProgressBar;
import gui.references.RiskNet;

import javax.swing.JButton;

public class RiskLevelInformation extends JFrame {

	private JPanel contentPane;
	private List<Risk> risks;

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RiskLevelInformation frame = new RiskLevelInformation(risks);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Color getColor(double percent) {
		if (percent < 0.4) {
			return Color.red;
		}
		if (percent > 0.7) {
			return Color.green;
		}
		return Color.yellow;

	}

	/**
	 * Create the frame.
	 */
	public RiskLevelInformation(List<Risk> risks) {
		this.risks = risks;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 679, 399);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Risk Level", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 643, 307);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton riskModel = new JButton("Risk Model");
		riskModel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RiskNet riskNet = new RiskNet(risks);
				riskNet.run();

			}
		});
		riskModel.setBounds(287, 326, 89, 23);
		contentPane.add(riskModel);

		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
			}
		});
		close.setBounds(548, 326, 89, 23);
		contentPane.add(close);

		for (int i = 0; i < risks.size(); i++) {
			Risk risk = risks.get(i);
			JLabel riskLabel = new JLabel("Risk " + risk.getId());
			JProgressBar riskLevel = new MyProgressBar(0, 1000, 10);
			riskLevel.setValue((int) (1000 * risk.getProbability()));
			riskLevel.setStringPainted(true);
			riskLevel.setString(Utils.round(risk.getProbability() * 100));
			riskLevel.setForeground(getColor(risk.getProbability()));
			if (i < 8) {
				riskLabel.setBounds(10, i * 30 + 28, 46, 14);
				riskLevel.setBounds(65, i * 30 + 28, 112, 14);

			}
			if (i >= 8 && i < 16) {
				riskLabel.setBounds(227, (i - 8) * 30 + 28, 46, 14);
				riskLevel.setBounds(282, (i - 8) * 30 + 28, 112, 14);

			}
			if (i >= 16 && i < 24) {
				riskLabel.setBounds(449, (i - 16) * 30 + 28, 46, 14);
				riskLevel.setBounds(504, (i - 16) * 30 + 28, 112, 14);
			}
			if (i == 24) {
				riskLevel = new MyProgressBar(0, 1000, 13);
				riskLevel.setValue((int) (1000 * risk.getProbability()));
				riskLevel.setStringPainted(true);
				riskLevel.setString(Utils.round(risk.getProbability() * 100));
				riskLevel.setForeground(getColor(risk.getProbability()));
				riskLabel = new MyLabel("All risks", Font.ITALIC, 12);
				riskLabel.setBounds(217, (i - 16) * 30 + 38, 66, 24);
				riskLevel.setBounds(272, (i - 16) * 30 + 38, 132, 24);
			}
			panel.add(riskLevel);
			panel.add(riskLabel);
		}

	}

}
