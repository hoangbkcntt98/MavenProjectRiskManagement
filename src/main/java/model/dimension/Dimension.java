package model.dimension;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import algorithms.bayesian_network.DimensionNet;
import algorithms.bayesian_network.RiskNet;
import config.Configuaration;
import model.Project;
import model.risk.Risk;
import model.risk.RiskInfo;
import model.task.Task;
import service.risk.RiskServiceImpl;
import service.risk.RiskServiceInterface;
import utils.Utils;

public class Dimension extends Project{
	public String dimensionId;
	public Map<String,Double> taskDeadlineMap;
	public List<Risk> allRisks;
	public Dimension(String path,double deadline,String dimensionId) {
//		super(path,deadline);
		this.taskRelatePath = path;
		this.dimensionId = dimensionId;
	}
	public void calcProb() {
		update();
		// calc prob
		List<Task> tasks = getTasks();
		List<RiskInfo> riskInfoList = readRiskInfo(Configuaration.inputPath+"risk_info.csv");
		int checkRisk;
		for(Task t:tasks) {
			double taskProb = Utils.gauss(taskDeadlineMap.get(t.getName()),t.getMostlikely(),t.getStandardDeviation());
			checkRisk =0;
			DimensionNet DNet = new DimensionNet("Task"+t.getName()+" Dimension "+dimensionId);
			for(RiskInfo r: riskInfoList) {
				if(r.check(this.dimensionId,t.getName())) {
					checkRisk =1;
					
					double riskProb = getRiskProb(Configuaration.inputPath+"risk_relation.csv", Configuaration.inputPath+"risk_distribution.csv");
					DNet.setRiskProb(riskProb);
					DNet.setTaskProb(taskProb);
					t.setProb(DNet.calcProb());
				}
			}
			if(checkRisk ==0) {
//				System.out.println("d :"+this.dimensionId+" task: "+t.getName());
//				System.out.println("taskProb no risk "+taskProb);
				t.setProb(taskProb);
			}
			if(allRisks!=null) {
				t.setRisks(allRisks);
			}
		}
	}
	public double getRiskProb(String relatePath,String disPath) {
		RiskServiceInterface riskServices = new RiskServiceImpl();
		// get informations of risk from data input

		allRisks = riskServices.readRiskRelationInfo(relatePath);
		riskServices.readRiskDistribution(disPath, allRisks);
		// init bayesian network for all risks
		RiskNet riskModel = new RiskNet("Riskmanagement of project",allRisks);
		riskModel.createNet();
		// calc prob of all risks and update probs
		riskModel.updateRiskProb();
		double result = allRisks.get(allRisks.size()-1).getProbability();
		return result;
	}
	public List<RiskInfo> readRiskInfo(String path) {
		List<RiskInfo> riskInfoList = new ArrayList<RiskInfo>();
		try {
			FileReader fileReader = new FileReader(path);
			CSVReader csvReader = new CSVReaderBuilder(fileReader) 
                    .withSkipLines(1) 
                    .build(); 
			String [] nextRecord;
			while ((nextRecord = csvReader.readNext()) != null) { 
	            riskInfoList.add(new RiskInfo(nextRecord));
	        } 
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return riskInfoList;
	}
	public static List<Double> getTaskProbFromDimensionList(List<Dimension> dimensionList,String name) {
		List<Double> probList = new ArrayList<Double>();
		for(Dimension d : dimensionList) {
			for(Task t: d.getTasks()) {
				if(t.getName().equals(name)) {
					probList.add(t.getProb());
				}
			}
		}
		return probList;
	}
	public Map<String, Double> getTaskDeadlineMap() {
		return taskDeadlineMap;
	}
	public void setTaskDeadlineMap(Map<String, Double> taskDeadlineMap) {
		this.taskDeadlineMap = taskDeadlineMap;
	}
	public String getDimensionId() {
		return dimensionId;
	}
	public void setDimensionId(String dimensionId) {
		this.dimensionId = dimensionId;
	}
	
}
