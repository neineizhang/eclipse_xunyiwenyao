package com.zll.xunyiwenyao.webservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zll.xunyiwenyao.dbitem.Doctor;
import com.zll.xunyiwenyao.dbitem.Inspection;
import com.zll.xunyiwenyao.util.HttpHelper;
import com.zll.xunyiwenyao.util.JsonHelper;
import com.zll.xunyiwenyao.webitem.ResponseItem;

/**
 * Created by rxz on 2017/3/21.
 * 
 * only should change addDoctor
 */

public class DoctorWebService {

    private static int MAX_ID = 1;
    private static List<Doctor> doctorlist;
    private static List<String> hospitalList;
    private static List<String> titleList;
    private static List<String> departmentList;

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
                    jsonobj.get("password").toString(),
                    jsonobj.getInt("sex"),
                    jsonobj.getString("title"),
                    jsonobj.getString("department"),
                    jsonobj.getString("goodat"),
                    jsonobj.getString("profile"));

        	doctorlist.add(doctor);
        	System.out.println("success add:"+JsonHelper.toJSON(doctor));
        }
            //获取部门列表
            String dep_url = "http://222.29.100.155/b2b2c/api/mobile/doctor/getAllDepartment.do?";
            departmentList =  new ArrayList<String>();
            s = HttpHelper.sendGet(dep_url, "");
            m = JsonHelper.toMap(s);
            responditem = new  ResponseItem();
            responditem = (ResponseItem) JsonHelper.toJavaBean(responditem, m);
            System.out.println(JsonHelper.toJSON(responditem));
            System.out.println("___________");
            jo = new JSONObject(s);
            ja = jo.getJSONArray("data");
            System.out.println(ja.length());

            for(int i = 0; i < ja.length(); i++){
                JSONObject jsonobj = (JSONObject) ja.get(i);
                departmentList.add(jsonobj.getString("department_name"));
//            arrs_type[i]=jsonobj.getString("type_name");
                System.out.println("success add:"+jsonobj.getString("department_name"));
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
//    	item.setId(MAX_ID);
//    	MAX_ID++;
//		doctorlist.add(item);
        try {
            String jsString = getJsonString(item);
            String url = "http://222.29.100.155/b2b2c/api/mobile/doctor/addDoctor.do";
            System.out.println(url+"?"+jsString);
//            jsString = URLEncoder.encode(jsString,"UTF-8");
//            String s = HttpHelper.sendGet(url, jsString);
            String s = HttpHelper.sendPost(url,jsString);
            System.out.println(s);

            //更新本地list
            initDB();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String[] listAllDepartment(){
        String[] arrs_department =  new String[departmentList.size()];
        departmentList.toArray(arrs_department);
        return arrs_department;
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


    public static String getJsonString(Doctor item){
        String jsonString = "doctor_id="+item.getId()+"&real_name="+item.getRealName()
                +"&type="+item.getType()+"&hospital="+item.getHospital()
                +"&reg_name="+item.getUsername()+"&password="+item.getPasswd()
                +"&sex="+item.getSex()
                +"&title="+item.getTitle()+"&department="+item.getDepartment()
                +"&goodat="+item.getGoodat()+"&profile="+item.getProfile();
        return jsonString;
    }

}
