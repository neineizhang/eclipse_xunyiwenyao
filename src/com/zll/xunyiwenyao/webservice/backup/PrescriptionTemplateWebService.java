package com.zll.xunyiwenyao.webservice.backup;

import com.zll.xunyiwenyao.dbitem.Doctor;
import com.zll.xunyiwenyao.dbitem.Drug;
import com.zll.xunyiwenyao.dbitem.PrescriptionTemplate;
import com.zll.xunyiwenyao.dbitem.Prescription_drugmap;
import com.zll.xunyiwenyao.dbitem.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rxz on 2017/3/22.
 */

public class PrescriptionTemplateWebService {

    public static List<PrescriptionTemplate> templatelt = new ArrayList<PrescriptionTemplate>();

    static{

        List<Drug> resultDruglt = DrugWebService.getAllDrug();
        PrescriptionTemplate template = null;

		List<Prescription_drugmap> druglist = new ArrayList<Prescription_drugmap>();
		druglist.add(new Prescription_drugmap(DrugWebService.getAllDrug().get(0), 1, "111"));
		druglist.add(new Prescription_drugmap(DrugWebService.getAllDrug().get(1), 2, "222"));
		druglist.add(new Prescription_drugmap(DrugWebService.getAllDrug().get(2), 3, "333"));
		druglist.add(new Prescription_drugmap(DrugWebService.getAllDrug().get(3), 2, "444"));
		druglist.add(new Prescription_drugmap(DrugWebService.getAllDrug().get(4), 1, "555"));
		Doctor doctor = DoctorWebService.getAllDoctor().get(0);

        template = new PrescriptionTemplate( 1, "template 1", Utils.DEPARTMENT.NEIKE.ordinal(), druglist, doctor);
        templatelt.add(template);

		druglist = new ArrayList<Prescription_drugmap>();
		druglist.add(new Prescription_drugmap(DrugWebService.getAllDrug().get(0), 1, "aaa"));
		
		doctor = DoctorWebService.getAllDoctor().get(0);
        template = new PrescriptionTemplate( 2, "template 2", Utils.DEPARTMENT.NEIKE.ordinal(), druglist, doctor);
//        template.getDrugmap().put(resultDruglt.get(0), 10);
//        template.getDrugmap().put(resultDruglt.get(1), 20);
        templatelt.add(template);

		druglist = new ArrayList<Prescription_drugmap>();
		druglist.add(new Prescription_drugmap(DrugWebService.getAllDrug().get(1), 2, "bbb"));

		doctor = DoctorWebService.getAllDoctor().get(0);
        template = new PrescriptionTemplate( 3, "template 3", Utils.DEPARTMENT.WAIKE.ordinal(), druglist, doctor);
//        template.getDrugmap().put(resultDruglt.get(2), 10);
//        template.getDrugmap().put(resultDruglt.get(1), 2);
        templatelt.add(template);

    }

    public static List<PrescriptionTemplate> getAllTemplate(){
        return templatelt;
    }
    
    public static List<String> getAllTemplateName(){
    	List<String> namelist = new ArrayList<String>();
    	for(PrescriptionTemplate item : templatelt){
    		namelist.add(item.getName());
    	}
    	return namelist;
    }
    
    
    public static void addPrescriptionTemplate(PrescriptionTemplate item){
    	templatelt.add(item);
    }
    
    public static void updatePrescriptionTemplate(PrescriptionTemplate item){
    	PrescriptionTemplate presciption = getPrescriptionTemplateByName(item.getName());
    	int index = templatelt.indexOf(presciption);
//    	presciption.setDrugmap(item.getDrugmap());
    	presciption.setDruglist(item.getDruglist());
    	templatelt.set(index, presciption);
    }
    
    public static void deletePrescriptionTemplate(PrescriptionTemplate item){
    	PrescriptionTemplate presciption = getPrescriptionTemplateByName(item.getName());
    	int index = templatelt.indexOf(presciption);
    	templatelt.remove(index);
    }
    
    public static PrescriptionTemplate getPrescriptionTemplateByName(String name){
    	for(PrescriptionTemplate item : templatelt){
    		if(item.getName().equals(name)){
    			return item;
    		}
    	}
    	return null;
    }
    
}
