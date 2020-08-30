import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algorithms.bayesian_network.TaskNet;
import algorithms.pert.Pert;
import config.Configuaration;
import model.Project;
import model.dimension.Dimension;
import model.task.Task;

public class Main {
	public static void main(String args[]) {
		// caculate prob of each task in project 
		Project project = new Project(Configuaration.inputPath+"0.csv",Configuaration.inputPath + "task_distribution.csv",40);
		project.update();
		project.calcProb();
	}
}
