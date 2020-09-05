package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.risk.Risk;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class RiskInformation extends JFrame {

	private JPanel contentPane;
	private Risk risk;

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RiskInformation frame = new RiskInformation(risk);
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
	public RiskInformation(Risk risk) {
		setTitle("Risk Information");
		this.risk = risk;
//		System.out.println(risk.getId()+" : "+Math.round(risk.getProbability()*100)/100);
		String riskProb = String.valueOf((double)Math.round(risk.getProbability()*1000)/1000);
		String riskParent = "";
		if(risk.getParentRisk()!=null)
		{
			for(Risk r:risk.getParentRisk()) {
				if(r.getId()!=risk.getId())
				riskParent +="Risk "+ r.getId() + " ";
			}
		}
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton close = new JButton("Close");
		close.setBounds(335, 227, 89, 23);
		contentPane.add(close);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Risk Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, 0, 434, 216);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Risk Id");
		lblNewLabel.setBounds(23, 35, 46, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Risk Prob");
		lblNewLabel_1.setBounds(23, 83, 70, 14);
		panel.add(lblNewLabel_1);
		
		JLabel riskId = new JLabel(risk.getId());
		riskId.setBounds(103, 35, 46, 14);
		panel.add(riskId);
		
		JLabel prob = new JLabel(riskProb);
		prob.setBounds(103, 83, 97, 14);
		panel.add(prob);
		
		JLabel lblNewLabel_2 = new JLabel("Risk Parents");
		lblNewLabel_2.setBounds(23, 131, 84, 14);
		panel.add(lblNewLabel_2);
		if(risk.getParentRisk()!=null) {
			JLabel riskPar = new JLabel(riskParent);
			riskPar.setBounds(115, 131, 55+risk.getParentRisk().size()*55, 14);
			panel.add(riskPar);
		}
		
		
	}
}
