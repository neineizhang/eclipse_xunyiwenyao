package com.zll.xunyiwenyao.webservice.real;

import com.zll.xunyiwenyao.dbitem.Doctor;
import com.zll.xunyiwenyao.dbitem.Drug;
import com.zll.xunyiwenyao.dbitem.Prescription;
import com.zll.xunyiwenyao.dbitem.PrescriptionTemplate;
import com.zll.xunyiwenyao.dbitem.Utils;
import com.zll.xunyiwenyao.util.HttpHelper;
import com.zll.xunyiwenyao.util.JsonHelper;
import com.zll.xunyiwenyao.webitem.ResponseItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rxz on 2017/3/22.
 */

public class PrescriptionTemplateWebService {

    public static List<PrescriptionTemplate> templatelt = new ArrayList<PrescriptionTemplate>();

    public static void initDB() throws JSONException{
		String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/getAllRecipeTemplate.do";
		
		String s = HttpHelper.sendGet(url, "");
        Map m = JsonHelper.toMap(s);
        ResponseItem responditem = new  ResponseItem();
        responditem = (ResponseItem) JsonHelper.toJavaBean(responditem, m);
        System.out.println(JsonHelper.toJSON(responditem));
        System.out.println("___________");
        
        
        JSONObject jo = new JSONObject(s);
        JSONArray ja = jo.getJSONArray("data");
        System.out.println(ja.length());

        ////////
        PrescriptionTemplate  prescriptiontemplate = null;
        templatelt = new ArrayList<PrescriptionTemplate>();
        for(int i = 0; i < ja.length(); i++){
        	JSONObject jsonobj = (JSONObject) ja.get(i);
        	
        	int id = jsonobj.getInt("recipeTemplate_id");
        	String name = jsonobj.getString("template_name"); 
        	int department = jsonobj.getInt("department");
        	Doctor doctor = DoctorWebService.getDoctorByID(jsonobj.getInt("creator_id"));
        	Map<Drug, Integer> drugmap = new HashMap<Drug, Integer>(); 
        	JSONArray jsonarray = jsonobj.getJSONArray("detailList");
        	for(int j = 0; j < jsonarray.length(); j++){
        		JSONObject tmpobj = (JSONObject) jsonarray.get(j);
        		Drug tmpdrug = DrugWebService.getDrugByID(tmpobj.getInt("drug_id")); 
        		int cnt = tmpobj.getInt("count");
        		drugmap.put(tmpdrug, cnt);
        	}
        }
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
    	presciption.setDrugmap(item.getDrugmap());
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
