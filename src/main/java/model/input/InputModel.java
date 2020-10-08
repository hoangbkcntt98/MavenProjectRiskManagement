package model.input;

public class InputModel {
	private String taskDis;
	private String taskInfo;
	private String dimensionInfo;
	private String riskInfo;
	private String riskRelate;
	private String riskDis;
	public String checkEmpty() {
		if(taskDis ==null)return "Task Distribution";
		if(taskInfo ==null) return "Task Relation";
		if(dimensionInfo ==null) return "Dimension Info";
		if(riskInfo ==null) return "Risk Info";
		if(riskRelate ==null) return "Risk Relation";
		if(riskDis ==null) return "Risk Distribution";
		
		return "OK";
		
	}
	public void reset() {
		taskDis=null;
		taskInfo=null;
		dimensionInfo=null;
		riskInfo=null;
		riskRelate=null;
		riskDis=null;
	}
	public String getTaskDis() {
		return taskDis;
	}
	public void setTaskDis(String taskDis) {
		this.taskDis = taskDis;
	}
	public String getTaskInfo() {
		return taskInfo;
	}
	public void setTaskInfo(String taskInfo) {
		this.taskInfo = taskInfo;
	}
	public String getDimensionInfo() {
		return dimensionInfo;
	}
	public void setDimensionInfo(String dimensionInfo) {
		this.dimensionInfo = dimensionInfo;
	}
	public String getRiskInfo() {
		return riskInfo;
	}
	public void setRiskInfo(String riskInfo) {
		this.riskInfo = riskInfo;
	}
	public String getRiskRelate() {
		return riskRelate;
	}
	public void setRiskRelate(String riskRelate) {
		this.riskRelate = riskRelate;
	}
	public String getRiskDis() {
		return riskDis;
	}
	public void setRiskDis(String riskDis) {
		this.riskDis = riskDis;
	}
	
	
}
