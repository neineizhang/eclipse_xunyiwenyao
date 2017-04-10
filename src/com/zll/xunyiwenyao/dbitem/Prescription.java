package com.zll.xunyiwenyao.dbitem;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rxz on 2017/3/21.
 */

public class Prescription {
    private int id;
    private String name;
    private int department;
    private Doctor doctor;
    private Doctor checker;
    private Patient patient;
    private Map<Drug, Integer> drugmap;
    private int status;
    private String date;
    private String clinical_diagnosis;
    

    public Prescription(){
        setDrugmap(new HashMap<Drug, Integer>());
    }
  
    

    public Prescription(int id, String name, int department, Doctor doctor, Patient patient,
			Map<Drug, Integer> drugmap, int status,String date,String clinical_diagnosis) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.doctor = doctor;
		this.patient = patient;
		this.drugmap = drugmap;
		this.status = status;
		this.date =date;
		this.clinical_diagnosis=clinical_diagnosis;
	}

    

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Map<Drug, Integer> getDrugmap() {
        return drugmap;
    }

    public void setDrugmap(Map<Drug, Integer> drugmap) {
        this.drugmap = drugmap;
    }



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public String getClinical_diagnosis() {
		return clinical_diagnosis;
	}



	public void setClinical_diagnosis(String clinical_diagnosis) {
		this.clinical_diagnosis = clinical_diagnosis;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public Doctor getChecker() {
		return checker;
	}



	public void setChecker(Doctor checker) {
		this.checker = checker;
	}

    


	
	
    
    
}
