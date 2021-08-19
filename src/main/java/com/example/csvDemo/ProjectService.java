package com.example.csvDemo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

@Service
public class ProjectService {
	private static final String projectCsvUrl = "http://my-csv-demo.herokuapp.com/project";
	private static Map<String, String> mapping;

	public ProjectService() {
		super();
	}

	static {
		mapping = new HashMap<>();
		mapping.put("proj_id", "projId");
		mapping.put("proj_name", "projName");
		mapping.put("client", "client");
		mapping.put("emp_id", "empId");
	}

	public List<Project> getAllProjects() {
		CSVReader csvReader = null;
		try {
			URL url = new URL(projectCsvUrl);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			csvReader = new CSVReader(reader);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		HeaderColumnNameTranslateMappingStrategy<Project> strategy = new HeaderColumnNameTranslateMappingStrategy<Project>();
		strategy.setType(Project.class);
		strategy.setColumnMapping(mapping);
		CsvToBean<Project> csvBean = new CsvToBean<Project>();
		csvBean.setMappingStrategy(strategy);
		csvBean.setCsvReader(csvReader);
		List<Project> listProjects = csvBean.parse();
		return listProjects;
	}
}
