package com.example.csvDemo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

@Service
public class EmployeeService {
	private static final String employeeCsvUrl = "http://my-csv-demo.herokuapp.com/emp";
	private static Map<String, String> mapping;
	@Autowired
	public ProjectService projectService;

	public EmployeeService() {
		super();
	}

	static {
		mapping = new HashMap<>();
		mapping.put("emp_id", "empId");
		mapping.put("emp_name", "empName");
		mapping.put("emp_city", "empCity");
	}

	public List<Employee> getEmployeesFromCsv() {
		CSVReader csvReader = null;
		try {
			URL url = new URL(employeeCsvUrl);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			csvReader = new CSVReader(in);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		HeaderColumnNameTranslateMappingStrategy<Employee> strategy = new HeaderColumnNameTranslateMappingStrategy<Employee>();
		strategy.setType(Employee.class);
		strategy.setColumnMapping(mapping);
		CsvToBean<Employee> csvBean = new CsvToBean<>();
		csvBean.setMappingStrategy(strategy);
		csvBean.setCsvReader(csvReader);
		return csvBean.parse();
	}

	public List<Employee> getAllEmployeesWithProjects() {
		List<Employee> employees = this.getEmployeesFromCsv();
		List<Project> projects = projectService.getAllProjects();
		for (Employee e : employees) {
			List<Project> project = new ArrayList<>();
			for (Project p : projects) {
				if (e.getEmpId() == p.getEmpId()) {
					project.add(p);
				}
			}
			e.setProjects(project);
		}
		return employees;
	}
}
