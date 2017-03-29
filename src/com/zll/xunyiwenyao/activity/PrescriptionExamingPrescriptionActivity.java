package com.zll.xunyiwenyao.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.activity.PrescriptionCreateMainActivity.ScrollAdapter;
import com.zll.xunyiwenyao.dbitem.Drug;
import com.zll.xunyiwenyao.dbitem.Prescription;
import com.zll.xunyiwenyao.dbitem.PrescriptionTemplate;
import com.zll.xunyiwenyao.webservice.PrescriptionTemplateWebService;
import com.zll.xunyiwenyao.webservice.PrescriptionWebService;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PrescriptionExamingPrescriptionActivity extends Activity {
	
	private Button approved_btn,refused_btn;
	private TextView  examing_prescription_name,examing_patient_name_text,examing_patient_sex_text,examing_patient_age_text,examing_clinical_diagnosis_text;
	private TextView  examing_prescription_data_et,examing_doctor_name_et,examing_checker_name_et,examing_other_information_et;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.examiningprescription);
		examing_prescription_name = (TextView) findViewById(R.id.examing_prescription_name);
		examing_patient_name_text  = (TextView) findViewById(R.id.examing_patient_name_text); 
		examing_patient_sex_text = (TextView) findViewById(R.id.examing_patient_sex_text); 
		examing_patient_age_text = (TextView) findViewById(R.id.examing_patient_age_text); 
		examing_clinical_diagnosis_text= (TextView) findViewById(R.id.examing_clinical_diagnosis_text); 
	    examing_prescription_data_et = (TextView) findViewById(R.id.examing_clinical_diagnosis_text);
	    examing_doctor_name_et= (TextView) findViewById(R.id.examing_doctor_name_et);
	    examing_checker_name_et= (TextView) findViewById(R.id.examing_checker_name_et);
	    examing_other_information_et= (TextView) findViewById(R.id.examing_other_information_et);
	    
	    Bundle extras = getIntent().getExtras(); 
		String prescription_name = extras.getString("prescription_name");
		if(!prescription_name.trim().equals("")){
			Prescription prescription = PrescriptionWebService.getPrescriptionByName(prescription_name);
			if(prescription == null){
				Toast.makeText(PrescriptionExamingPrescriptionActivity.this, "不存在这张处方单", Toast.LENGTH_SHORT).show();
				
			}else{
				//chufangmingcheng.setText(template_name);
				
//				Map<Drug, Integer> drugmap = prescription.getDrugmap();
//				List<Map<String, String>> datas = (List<Map<String, String>>) ((ScrollAdapter)drugs_lv.getAdapter()).getData();
//				if(datas == null){
//					datas = new ArrayList<Map<String,String>>();
//				}
//				for(Drug drug : drugmap.keySet()){
//					Map<String, String> tempdata = new HashMap<String, String>();
//					tempdata.put("title", String.valueOf(drug.getId()));
//					tempdata.put("data_" + 1, drug.getName());
//					tempdata.put("data_" + 2, drug.getSpecification());
//					tempdata.put("data_" + 3, drugmap.get(drug)+"");
//					tempdata.put("data_" + 4, drug.getPrice());
//					tempdata.put("data_" + 5, drug.getDescription());
//					datas.add(tempdata);
//				}
//				((ScrollAdapter)drugs_lv.getAdapter()).setData(datas);
//				((ScrollAdapter)drugs_lv.getAdapter()).notifyDataSetChanged();
			}
			
		}
		
	}

}
