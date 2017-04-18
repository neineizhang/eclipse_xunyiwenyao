package com.zll.xunyiwenyao.webservice.backup;

import com.zll.xunyiwenyao.dbitem.Drug;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rxz on 2017/3/21.
 */

public class DrugWebService {

    private static List<Drug> druglist;

    static {
        Drug drug = null;
        druglist = new ArrayList<Drug>();
        drug = new Drug(1, "Drug A", "12g X 10", "suibian", "bianji");
        druglist.add(drug);
        drug = new Drug(2, "Drug B", "12g X 30", "suibian", "keli");
        druglist.add(drug);
        drug = new Drug(3, "Drug C", "1g X 10", "suibian", "bianji");
        druglist.add(drug);
        drug = new Drug(4, "Drug D", "12g X 13", "suibian", "keli");
        druglist.add(drug);
        drug = new Drug(5, "Drug E", "12g X 120", "suibian", "bianji");
        druglist.add(drug);
    }

    public static List<Drug> getAllDrug() {

        return druglist;

    }


    public static Drug getDrugByName(String name){
    	for(Drug item : druglist){
    		if(item.getName().equals(name)){
    			return item;
    		}
    	}
    	return null;
    }
    
    public static Drug getDrugByID(int id){
    	for(Drug item : druglist){
    		if(item.getId() == id){
    			return item;
    		}
    	}
    	return null;
    }
}
