package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algorithms.bayesian_network.TaskNet;
import algorithms.pert.Pert;
import config.Configuaration;
import model.dimension.Dimension;
import model.input.InputModel;
import model.task.Task;
import service.task.TaskServiceImpl;
import service.task.TaskServiceInterface;
import utils.Utils;

public class Project {
	public String taskRelatePath;
	public String taskDisPath;
	public List<Task> tasks;
	public List<Task> criticalPath;
	public double prob;
	public double deadline;
	Map<Task, List<Double>> taskProbMap;
	private InputModel inputModel;
	public static final String [] dName = {"Size","Productivity","Worker-hour","Duration","Cost"};
	public Project() {
		
	}
	public Project(String realatePath,String disPath, double deadline) {
		this.taskDisPath =disPath;
		this.taskRelatePath = realatePath;
		this.deadline = deadline;
	}

	public Project(InputModel inputModel, double deadline) {
		this.inputModel = inputModel;
		this.deadline = deadline;
		this.taskDisPath = inputModel.getTaskDis();
		this.taskRelatePath = inputModel.getTaskInfo();
		// TODO Auto-generated constructor stub
	}
	public void update() {
		TaskServiceInterface taskServices = new TaskServiceImpl();
		this.tasks = taskServices.readTaskListInfo(taskRelatePath);
		
		if(taskDisPath!=null) {
			taskServices.readTaskDistribution(taskDisPath, tasks);
		}
		
		criticalPath = Pert.excute(tasks);
		double projectVariance = 0;
		double expectedTime = 0;
		double sigma;
		for (Task t : criticalPath) {
			projectVariance += t.getVariance();
			expectedTime += t.getExpectedTime();
		}
		sigma = Math.sqrt(projectVariance);
		prob = Utils.gauss(deadline, expectedTime, sigma);
		
	}

	public void calcProb() {
		List<Task> tasks = getTasks();
		readTaskDistribution(taskDisPath, tasks);
		// get task from data input
		List<Dimension> dimensionList = new ArrayList<Dimension>();
		Map<String, Double> deadlineMap = new HashMap<String, Double>();
		for (Task t : tasks) {
			deadlineMap.put(t.getName(), t.getExpectedTime());
		}
		// update prob for all tasks in each dimension
		for (int i = 0; i < 5; i++) {
			String dimensionId = String.valueOf(i);
			Dimension dimension = new Dimension(dName[i],Configuaration.inputPath + "0_" + dimensionId + ".csv", deadline,dimensionId,inputModel);
			dimension.setTaskDeadlineMap(deadlineMap);
			dimension.calcProb();
			dimensionList.add(dimension);
		}
		System.out.println("Dimension List Size :"+ dimensionList.size());
		taskProbMap = new HashMap<Task, List<Double>>();
		for (Task t : tasks) {
			List<Double> list=  Dimension.getTaskProbFromDimensionList(dimensionList, t.getName());
			System.out.println("List Prob size :" + list.size());
			for(double p: list) {
				System.out.println(p);
			}
			taskProbMap.put(t,list);
			t.setDimensionList(dimensionList);
			t.setDimensionProbList(list);
			
			TaskNet taskNet = new TaskNet("Task " + t.getName(), t,Dimension.getTaskProbFromDimensionList(dimensionList, t.getName()));
			taskNet.calcProb();
		}
		for (Task t : tasks) {
			System.out.println("Probability of Task " + t.getName() + " is :" + t.getProb());
		}
		System.out.println("Probability of project : " + getProb());

		System.out.println();
	}

	public void readTaskDistribution(String path, List<Task> task) {
		TaskServiceInterface taskServices = new TaskServiceImpl();
		taskServices.readTaskDistribution(path, tasks);
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<Task> getCriticalPath() {
		return criticalPath;
	}

	public void setCriticalPath(List<Task> criticalPath) {
		this.criticalPath = criticalPath;
	}

	public double getProb() {
		return prob;
	}

	public void setProb(double prob) {
		this.prob = prob;
	}
	public double getDeadline() {
		return deadline;
	}
	public void setDeadline(double deadline) {
		this.deadline = deadline;
	}
	public String getTaskRelatePath() {
		return taskRelatePath;
	}
	public void setTaskRelatePath(String taskRelatePath) {
		this.taskRelatePath = taskRelatePath;
	}
	public InputModel getInputModel() {
		return inputModel;
	}
	public void setInputModel(InputModel inputModel) {
		this.inputModel = inputModel;
	}
	
	
	
}
