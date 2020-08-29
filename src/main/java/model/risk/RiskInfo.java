package model.risk;

import java.util.ArrayList;
import java.util.List;

public class RiskInfo {
	private String taskId;
	private String dimensionId;
	private String riskRelationFile;
	private String riskDistributionFile;
	public RiskInfo(String dimensionId,String taskId,String riskRelateFile,String riskDisFile) {
		this.taskId = taskId;
		this.dimensionId = dimensionId;
		this.riskRelationFile = riskRelateFile;
		this.riskDistributionFile = riskDisFile;
	}
	public RiskInfo(String [] data) {
		this.taskId = data[1];
		this.dimensionId = data[0];
		this.riskRelationFile = data[2];
		this.riskDistributionFile = data[3];
	}
	public boolean check(String dimensionId,String taskId) {
		return (this.dimensionId.equals(dimensionId)&&this.taskId.equals(taskId))?true:false;
	}
	

}
