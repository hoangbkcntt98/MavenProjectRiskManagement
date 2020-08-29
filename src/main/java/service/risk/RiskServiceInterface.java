package service.risk;

import java.util.List;

import model.risk.Risk;

public interface RiskServiceInterface {
	public List<Risk> readRiskRelationInfo(String path);
	public List<Risk> convertToParentRiskList(List<String> strParentList,List<Risk> risks);
	public void readRiskDistribution(String path,List<Risk> risks);
	public Risk getRiskById(String id,List<Risk> risks);
}
