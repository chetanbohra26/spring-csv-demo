package com.example.csvDemo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
	private ProjectService projectService;
	private EmployeeService employeeService;

	@Autowired
	public MyController(ProjectService projectService, EmployeeService employeeService) {
		this.projectService = projectService;
		this.employeeService = employeeService;
	}

	@RequestMapping("/project")
	private List<Project> getProjects() {
		return projectService.getAllProjects();
	}

	@RequestMapping("/employee")
	private List<Employee> getEmployees() {
		return employeeService.getAllEmployeesWithProjects();
	}
}
