package com.zll.xunyiwenyao.webservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zll.xunyiwenyao.dbitem.Doctor;
import com.zll.xunyiwenyao.util.HttpHelper;
import com.zll.xunyiwenyao.util.JsonHelper;
import com.zll.xunyiwenyao.webitem.ResponseItem;

/**
 * Created by rxz on 2017/3/21.
 */

public class DoctorWebService {

    private static int MAX_ID = 1;
    private static List<Doctor> doctorlist;

//    static {
//        Doctor doctor = null;
//        doctorlist = new ArrayList<Doctor>();
//        doctor = new Doctor(1, "doctor A", Utils.DOCTOR_TYPE.DOCTOR.ordinal(), "Hospital 1", "root", "2222");
//        doctorlist.add(doctor);
//        doctor = new Doctor(2, "doctor B", Utils.DOCTOR_TYPE.DOCTOR.ordinal(), "Hospital 2", "admin", "admin");
//        doctorlist.add(doctor);
//        doctor = new Doctor(3, "doctor C", Utils.DOCTOR_TYPE.ACCESSOR.ordinal(), "Hospital 3", "doctor", "doctor");
//        doctorlist.add(doctor);
//        MAX_ID = 4;
//    }

    public static void initDB() throws JSONException{
		String url = "http://222.29.100.155/b2b2c/api/mobile/doctor/getAllDoctor.do";
		
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
        
        Doctor doctor = null;
        doctorlist = new ArrayList<Doctor>();
        for(int i = 0; i < ja.length(); i++){
        	JSONObject jsonobj = (JSONObject) ja.get(i);
        	doctor = new Doctor(jsonobj.getInt("doctor_id"), 
        			jsonobj.getString("real_name"), 
        			jsonobj.getInt("type"), 
        			jsonobj.get("hospital").toString(), 
        			jsonobj.getString("reg_name"), 
        			jsonobj.get("password").toString());
        	doctorlist.add(doctor);
        	System.out.println("success add:"+JsonHelper.toJSON(doctor));
        }
    }
    
    public static void main(String[] args) {
		try {
			DoctorWebService.initDB();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public static List<Doctor> getAllDoctor(){
        return doctorlist;
    }
    
    public static List<Doctor> getDoctorByType(int type){
        List<Doctor> resultlist = new ArrayList<Doctor>();
        for(Doctor doctor : doctorlist){
            if(doctor.getType() == type){
                resultlist.add(doctor);
            }
        }
        return resultlist;
    }
    
    public static Doctor getDoctorByID(int id){
        for(Doctor doctor : doctorlist){
            if(doctor.getId() == id){
                return doctor;
            }
        }
        return null;
    }

    ////// 
    public static void addDoctor(Doctor item){
    	item.setId(MAX_ID);
    	MAX_ID++;
		doctorlist.add(item);
    }
    
    public static Doctor isSuccessLogin(String username, String passwd, int type){
    	List<Doctor> resultlist = getDoctorByType(type);
    	for(Doctor item : resultlist){
    		if(item.getUsername().equals(username) && 
    				item.getPasswd().equals(passwd)){
    			return item;
    		}
    	}
    	return null;
    }
    //�������е�use_rname
    public static List<String> getAllUsername(){
        List<String> usernamelist = new ArrayList<String>();
        for(Doctor doctor:doctorlist)
            usernamelist.add(doctor.getUsername());
        return usernamelist;
    }



}
