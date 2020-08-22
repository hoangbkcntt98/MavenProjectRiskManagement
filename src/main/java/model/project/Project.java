package model.project;

import java.util.List;

import model.project.task.Task;

public class Project {
	String projectPath ;
	List<Task> tasks;
	
	public Project(String path)
	{
		this.projectPath = path;
		
	}
	public void update() {
		 
	}
}
