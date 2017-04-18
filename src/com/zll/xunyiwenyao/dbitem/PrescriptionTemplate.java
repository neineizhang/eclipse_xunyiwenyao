package com.zll.xunyiwenyao.dbitem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rxz on 2017/3/21.
 */

public class PrescriptionTemplate {
    private int id;
    private String name;
    private int department;
    //private Map<Drug, Integer> drugmap;
    private List<Prescription_drugmap> druglist;

    public PrescriptionTemplate(){

    }

    public PrescriptionTemplate(int id, String name, int department, List<Prescription_drugmap> druglist) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.druglist = druglist;
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

    public List<Prescription_drugmap> getDruglist() {
        return druglist;
    }

    public void setDruglist(List<Prescription_drugmap> druglist) {
        this.druglist = druglist;
    }
}
