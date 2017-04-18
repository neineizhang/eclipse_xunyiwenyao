package com.zll.xunyiwenyao.webservice.real;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zll.xunyiwenyao.dbitem.Doctor;
import com.zll.xunyiwenyao.dbitem.Drug;
import com.zll.xunyiwenyao.dbitem.Patient;
import com.zll.xunyiwenyao.dbitem.Prescription2;
import com.zll.xunyiwenyao.dbitem.PrescriptionTemplate;
import com.zll.xunyiwenyao.dbitem.Utils;
import com.zll.xunyiwenyao.util.HttpHelper;
import com.zll.xunyiwenyao.util.JsonHelper;
import com.zll.xunyiwenyao.webitem.ResponseItem;

public class PrescriptionWebService {
	
	private static List<Prescription2> prescriptionlist;
	private static int MAX_ID = 1;

//	static {
//		prescriptionlist = new ArrayList<Prescription>();
//		
//		Doctor doctor = null;
//		Patient patient = null;
//		Prescription  prescription = null;
//		String date = "2017-04-04 10:40:40";
//		//doctor = new Doctor(1, "doctor A", Utils.DOCTOR_TYPE.DOCTOR.ordinal(), "Hospital 1");
//		doctor = DoctorWebService.getAllDoctor().get(0);
//		patient = PatientWebService.getAllPatient().get(1);
//
//		Map<Drug, Integer> drugmap = new HashMap<Drug, Integer>();
//		drugmap.put(DrugWebService.getAllDrug().get(0), 1);
//		drugmap.put(DrugWebService.getAllDrug().get(1), 3);
//		drugmap.put(DrugWebService.getAllDrug().get(2), 4);
//		drugmap.put(DrugWebService.getAllDrug().get(4), 2);
//		drugmap.put(DrugWebService.getAllDrug().get(3), 1);
//		
//		prescription= new Prescription(1,"coach", Utils.DEPARTMENT.NEIKE.ordinal(), 
//				doctor, patient, drugmap, Utils.STATUS.APPROVED.ordinal(), date,"头疼发热无过敏历史");
//		prescriptionlist.add(prescription);
//		
//		doctor = DoctorWebService.getAllDoctor().get(1);
//		patient = PatientWebService.getAllPatient().get(0);
//
//		Map<Drug, Integer> drugmap1 = new HashMap<Drug, Integer>();
//		drugmap1.put(DrugWebService.getAllDrug().get(0), 1);
//		drugmap1.put(DrugWebService.getAllDrug().get(1), 3);
//		drugmap1.put(DrugWebService.getAllDrug().get(2), 4);
//		
//		prescription= new Prescription(2,"toothache", Utils.DEPARTMENT.WAIKE.ordinal(), 
//				doctor,patient, drugmap, Utils.STATUS.COMMITED.ordinal(),date,"青霉素过敏");
//		prescriptionlist.add(prescription);
//		MAX_ID = 3;
//	}
	public static void initDB() throws JSONException{
		String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/getRecipe.do";
		
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
        Prescription2  prescription = null;
        prescriptionlist = new ArrayList<Prescription2>();
        for(int i = 0; i < ja.length(); i++){
        	JSONObject jsonobj = (JSONObject) ja.get(i);
        	
        	int id = jsonobj.getInt("recipe_id");
        	String name = jsonobj.getString("name"); /////?
        	int department = 0;//////?
        	Doctor doctor = DoctorWebService.getDoctorByID(jsonobj.getInt("creator_id"));
        	Doctor checker = DoctorWebService.getDoctorByID(jsonobj.getInt("reviewer_id"));
        	String patient_name =jsonobj.getString("user_name");
        	int user_id =jsonobj.getInt("user_id");
        	int user_age =jsonobj.getInt("user_age");
        	int user_sex =jsonobj.getInt("user_sex");
        	Map<Drug, Integer> drugmap = new HashMap<Drug, Integer>(); 
        	JSONArray jsonarray = jsonobj.getJSONArray("detailList");
        	for(int j = 0; j < jsonarray.length(); j++){
        		JSONObject tmpobj = (JSONObject) jsonarray.get(j);
        		Drug tmpdrug = DrugWebService.getDrugByID(tmpobj.getInt("drug_id")); 
        		int cnt = tmpobj.getInt("count");
        		drugmap.put(tmpdrug, cnt);
        	}
        	
        	
        	int status = jsonobj.getInt("status");
        	String date = jsonobj.getString("create_time"); /////???
        	String clinical_diagnosis = jsonobj.getString("content");; //////?
        	
        	prescription = new Prescription2(
        			id, name, department, doctor, patient_name, user_sex,user_age,
        			drugmap, status, date, clinical_diagnosis);
        	        
        	prescriptionlist.add(prescription);
        	System.out.println("success add:"+JsonHelper.toJSON(prescription));
        }
    }
    
	public static void AddPrescription(Prescription2 item){
		Prescription2 prescription_inDB = getPrescriptionByName(item.getName());

		if(prescription_inDB == null){
			item.setId(MAX_ID);
	    	MAX_ID++;
			prescriptionlist.add(item);
		}else{
			prescriptionlist.set(prescriptionlist.indexOf(prescription_inDB), item);
		}
	}
	
	public static void updatePrescription(Prescription2 item){
		Prescription2 presciption = getPrescriptionByName(item.getName());
    	int index = prescriptionlist.indexOf(presciption);
    	prescriptionlist.set(index, item);
	}

	public static List<Prescription2> getAllPrescription()
	{
		return prescriptionlist;
	}
	
	public static List<Prescription2> getPrescriptionbyStatus(int status)
	{   
		//List<Prescription> prescriptionsaved,prescriptioncommited,prescriptionapproved,prescriptionrefused; 
		List<Prescription2> prescription_result_lt = new ArrayList<Prescription2>(); 
		List<Prescription2> prescriptionlist = getAllPrescription();
		for(Prescription2 item :prescriptionlist){
			
			if(item.getStatus() == status){
				prescription_result_lt.add(item);
			}
			
		}
		
		return prescription_result_lt;
	
	}
	
	public static Prescription2 getPrescriptionByName(String name){
		for(Prescription2 item :prescriptionlist){
			if(item.getName().equals(name)){
				return item;
			}
		}
		return null;
	}
	
	public static List<String> getAllPrescriptionName(){
    	List<String> namelist = new ArrayList<String>();
    	for(Prescription2 item : prescriptionlist){
    		namelist.add(item.getName());
    	}
    	return namelist;
    }
    
	
	
}
