package model.project;

import java.util.List;

import algorithms.pert.Pert;
import model.project.task.Task;
import service.project.task.TaskServiceImpl;
import service.project.task.TaskServiceInterface;
import utils.Utils;

public class Project {
	public String projectPath ;
	public List<Task> tasks;
	public List<Task> criticalPath;
	public double prob;
	public double deadline;
	public Project(String path,double deadline)
	{
		this.projectPath = path;
		this.deadline = deadline;
	}
	public void update() {
		TaskServiceInterface taskServices = new TaskServiceImpl();
		this.tasks = taskServices.readTaskListInfo(projectPath);
		criticalPath = Pert.excute(tasks);
		double projectVariance=0;
		double expectedTime =0;
		double sigma;
		for(Task t : tasks) {
			projectVariance += t.getVariance();
			expectedTime += t.getExpectedTime();
		}
		sigma = Math.sqrt(projectVariance);
		prob = Utils.gauss(deadline, expectedTime, sigma);
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
	
	
}
