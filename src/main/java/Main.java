import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algorithms.bayesian_network.TaskNet;
import algorithms.pert.Pert;
import config.Configuaration;
import model.project.Project;
import model.project.dimension.Dimension;
import model.project.task.Task;

public class Main {
	public static void main(String args[]) {
		// caculate prob of each task in project 
		Project project = new Project(Configuaration.inputPath+"0.csv",40);
		project.update();
		List<Task> tasks = project.getTasks();
		project.readTaskDistribution(Configuaration.inputPath+"task_distribution.csv",tasks);
		// get task from data input
		List<Dimension> dimensionList = new ArrayList<Dimension>();
		Map<String,Double> deadlineMap = new HashMap<String,Double>();
		for(Task t:tasks) {
			deadlineMap.put(t.getName(),t.getExpectedTime());
		}
		// update prob for all tasks in each dimension
		for(int i=0;i<5;i++)
		{
			String dimensionId = String.valueOf(i);
			Dimension dimension = new Dimension(Configuaration.inputPath+"0_"+dimensionId+".csv",30,dimensionId);
			dimension.setTaskDeadlineMap(deadlineMap);
			dimension.calcProb();
			dimensionList.add(dimension);
		}
		Map<Task,List<Double>> taskProbMap = new HashMap<Task,List<Double>>();
		for(Task t: tasks) {
			taskProbMap.put(t, Dimension.getTaskProbFromDimensionList(dimensionList, t.getName()));
			TaskNet taskNet = new TaskNet("Task "+t.getName(),t,Dimension.getTaskProbFromDimensionList(dimensionList, t.getName()));
			taskNet.calcProb();
		}
		for(Task t:tasks) {
			System.out.println("Probability of Task "+t.getName()+" is :"+ t.getProb());
		}
		System.out.println("Probability of project : " + project.getProb());
		
		System.out.println();
	}
}
