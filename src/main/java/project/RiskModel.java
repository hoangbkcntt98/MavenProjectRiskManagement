package project;

import java.util.ArrayList;
import java.util.List;

import bayesian_network.BayesianNetwork;
import bayesian_network.RiskNet;
import utils.Utils;

public class RiskModel {
	public RiskNet bayesNet ;
	public RiskModel(String name,List<Risk> risks) {
		this.bayesNet = new RiskNet(name,risks);
	}
	public void calcRiskProb(Risk risk) {
		
	}
	public void calcProb() {
		System.out.println("Probability of all risks is :"+bayesNet.calcProb());
	}
	public List<Risk> getOrder(List<Risk> risks){
		List<Risk> riskListOrdered = new ArrayList<Risk>();
		
		int size = risks.size();
		int [] prob = new int [size];// The array which contain prob of all row in matOrder if prob[i] =1 then risk ith hasn't parent risk
		int [][] matOrder = Utils.matrix(size, size, 1);// The matrix which contain order of all risks 
		//update order matrix 
		for(int i=0;i<size;i++) {
			List<Risk> parent = risks.get(i).getParentRisk();
			if(parent!=null) {
				for(Risk risk : parent) {
					matOrder[i][Integer.parseInt(risk.getId())-1] = 0;
				}
			}
		}
		// get ordered list of risks
		while(riskListOrdered.size()!=size) {
			prob = Utils.getProb(matOrder);
			List<Risk> riskUpdates = Utils.riskNeedUpdateBefore(risks, prob);
			riskListOrdered.addAll(riskUpdates);
			Utils.updateMatrix(riskUpdates, matOrder,prob);
		}
		return riskListOrdered;
	}

}
