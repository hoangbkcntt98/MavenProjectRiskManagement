import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algorithms.pert.Pert;
import config.Configuaration;
import model.project.dimension.Dimension;
import model.project.task.Task;
import service.project.risk.RiskServiceImpl;
import service.project.risk.RiskServiceInterface;
import service.project.task.TaskServiceImpl;
import service.project.task.TaskServiceInterface;

public class Main {
	public static void main(String args[]) {
		// get task from data input
		TaskServiceInterface taskServices = new TaskServiceImpl();
		List<Task> tasks = taskServices.readTaskListInfo(Configuaration.inputPath+"0.csv");
		List<Task> criticlePath = Pert.excute(tasks);
		Pert.showCriticalPath(criticlePath);
		List<Dimension> dimensionList = new ArrayList<Dimension>();
		Map<String,Double> deadlineMap = new HashMap<String,Double>();
		for(Task t:tasks) {
			deadlineMap.put(t.getName(),t.getExpectedTime());
		}
		// update prob for all tasks in each dimension
		for(int i=0;i<5;i++)
		{
			String dimensionId = String.valueOf(i);
			Dimension dimension = new Dimension(Configuaration.inputPath+"0_"+dimensionId+".csv",55,dimensionId);
			dimension.setTaskDeadlineMap(deadlineMap);
			dimension.calcProb();
			dimensionList.add(dimension);
		}
		System.out.println();
		// caculate prob of each task in project 
	
	}
}
