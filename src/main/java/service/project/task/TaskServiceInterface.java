package service.project.task;

import java.util.List;
import java.util.stream.Collectors;

import model.project.task.Task;

public interface TaskServiceInterface {
	public List<Task> readTaskListInfo(String path);
	public void readTaskDistribution(String path, List<Task> tasks);
	public List<Task> convertToPredecessorList(List<String> strListPredecessor,List<Task> tasks) ;
	public Task getTaskInfoFromString(String[] str) ;
	public Task findTaskByName(String name,List<Task> taskList);
	public double getTaskProb(double time,Task task);

}
