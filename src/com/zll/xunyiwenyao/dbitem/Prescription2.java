package com.zll.xunyiwenyao.dbitem;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rxz on 2017/3/21.
 */

public class Prescription2 {
    private int id;
    private String name;
    private int department;
    private Doctor doctor;
    private Doctor checker;
    private String patient_name;
    private int patient_age;
    private int patient_sex;
    private Map<Drug, Integer> drugmap;
    private int status;
    private String date;
    private String clinical_diagnosis;
    

    public Prescription2(){
        setDrugmap(new HashMap<Drug, Integer>());
    }
  
    

    public Prescription2(int id, String name, int department, Doctor doctor, String patient_name,int patient_age,int patient_sex,
			Map<Drug, Integer> drugmap, int status,String date,String clinical_diagnosis) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.doctor = doctor;
		this.patient_name = patient_name;
		this.patient_age = patient_age;
		this.patient_sex = patient_sex;
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

   

    public String getPatient_name() {
		return patient_name;
	}



	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}



	public int getPatient_age() {
		return patient_age;
	}



	public void setPatient_age(int patient_age) {
		this.patient_age = patient_age;
	}



	public int getPatient_sex() {
		return patient_sex;
	}



	public void setPatient_sex(int patient_sex) {
		this.patient_sex = patient_sex;
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
