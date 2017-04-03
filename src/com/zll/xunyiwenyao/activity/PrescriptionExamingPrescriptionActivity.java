package com.zll.xunyiwenyao.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.dbitem.Drug;
import com.zll.xunyiwenyao.dbitem.Prescription;
import com.zll.xunyiwenyao.dbitem.PrescriptionTemplate;
import com.zll.xunyiwenyao.dbitem.Utils;
import com.zll.xunyiwenyao.view.PrescriptionExamingPrescriptionScrollView;
import com.zll.xunyiwenyao.webservice.PrescriptionTemplateWebService;
import com.zll.xunyiwenyao.webservice.PrescriptionWebService;

import android.app.Activity;
import android.content.Context;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PrescriptionExamingPrescriptionActivity extends Activity {
	
	private Button approved_btn,refused_btn;
	private TextView  examing_prescription_name,examing_patient_name_text,examing_patient_sex_text,examing_patient_age_text,examing_clinical_diagnosis_text;
	private TextView  examing_prescription_data_et,examing_doctor_name_et,examing_checker_name_et,examing_other_information_et;
	private ListView examing_drugs_lv;
	public HorizontalScrollView mTouchView;
	protected List<PrescriptionExamingPrescriptionScrollView> mHScrollViews = new ArrayList<PrescriptionExamingPrescriptionScrollView>();
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
	    
	    approved_btn = (Button) findViewById(R.id.approved_btn);
	    refused_btn = (Button) findViewById(R.id.refused_btn);
	    if(Utils.LOGIN_DOCTOR.getType() == Utils.DOCTOR_TYPE.DOCTOR.ordinal()){
	    	approved_btn.setVisibility(View.INVISIBLE);
	    	refused_btn.setVisibility(View.INVISIBLE);
	    }
	    
	    initViews();
	    initdata();
		
	}
	private void initdata(){
		  Bundle extras = getIntent().getExtras(); 
			String prescription_name = extras.getString("prescription_name");
			Toast.makeText(PrescriptionExamingPrescriptionActivity.this, prescription_name, Toast.LENGTH_SHORT).show();
			if(!prescription_name.trim().equals("")){
				Prescription prescription = PrescriptionWebService.getPrescriptionByName(prescription_name);
				if(prescription == null){
					Toast.makeText(PrescriptionExamingPrescriptionActivity.this, "该处方单表为空", Toast.LENGTH_SHORT).show();
					
				}else{
//		            String patient_name = prescription.getPatient().getName().toString();  
//		            int patient_age = prescription.getPatient().getAge();
//		            String patient_sex =prescription.getPatient().getSex()==0?"男":"女";
//		            String doctor_name=prescription.getDoctor().getName().toString();
//		            
//					examing_prescription_name.setText(prescription_name);
//					examing_patient_name_text.setText(patient_name);
//					examing_patient_age_text.setText(patient_age);
//					examing_patient_sex_text.setText(patient_sex);
//					examing_doctor_name_et.setText(doctor_name);
					
					Map<Drug, Integer> drugmap = prescription.getDrugmap();
					
					List<Map<String, String>> datas = (List<Map<String, String>>) ((ExamingScrollAdapter)examing_drugs_lv.getAdapter()).getData();
					if(datas == null){
						datas = new ArrayList<Map<String,String>>();
					}
					for(Drug drug : drugmap.keySet()){
						Map<String, String> tempdata = new HashMap<String, String>();
						tempdata.put("title", String.valueOf(drug.getId()));
						tempdata.put("data_" + 1, drug.getName());
						tempdata.put("data_" + 2, drug.getSpecification());
						tempdata.put("data_" + 3, drugmap.get(drug)+"");
						tempdata.put("data_" + 4, drug.getPrice());
						tempdata.put("data_" + 5, drug.getDescription());
						datas.add(tempdata);
					}
					((ExamingScrollAdapter)examing_drugs_lv.getAdapter()).setData(datas);
					((ExamingScrollAdapter)examing_drugs_lv.getAdapter()).notifyDataSetChanged();
					
				}
				
			}
	}
	private void initViews() {
		List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
		Map<String, String> data = null;
		PrescriptionExamingPrescriptionScrollView headerScroll = (PrescriptionExamingPrescriptionScrollView) findViewById(R.id.examing_item_scroll_title);

		mHScrollViews.add(headerScroll);

		examing_drugs_lv = (ListView) findViewById(R.id.examing_drugs_lv);

//		for (int i = 0; i < 1; i++) {
//			data = new HashMap<String, String>();
//			data.put("title", "Title_" + i);
//			data.put("data_" + 1, "Date_" + 1 + "_" + i);
//			data.put("data_" + 2, "Date_" + 2 + "_" + i);
//			data.put("data_" + 3, "Date_" + 3 + "_" + i);
//			data.put("data_" + 4, "Date_" + 4 + "_" + i);
//			data.put("data_" + 5, "Date_" + 5 + "_" + i);
//
//			datas.add(data);
//		}
      
		ExamingScrollAdapter adapter = new ExamingScrollAdapter(this, datas, R.layout.examing_scroll_view,
				new String[] { "title", "data_1", "data_2", "data_3", "data_4", "data_5" }, new int[] { R.id.examing_item_title,
						R.id.examing_item_data1, R.id.examing_item_data2, R.id.examing_item_data3, R.id.examing_item_data4, R.id.examing_item_data5 });
		examing_drugs_lv.setAdapter(adapter);

	}

	public void addHViews(final PrescriptionExamingPrescriptionScrollView hScrollView) {
		if (!mHScrollViews.isEmpty()) {
			int size = mHScrollViews.size();
			PrescriptionExamingPrescriptionScrollView scrollView = mHScrollViews.get(size - 1);

			final int scrollX = scrollView.getScrollX();
			if (scrollX != 0) {
				examing_drugs_lv.post(new Runnable() {
					@Override
					public void run() {
						hScrollView.scrollTo(scrollX, 0);
					}
				});
			}
		}
		mHScrollViews.add(hScrollView);
	}

	public void onScrollChanged(int l, int t, int oldl, int oldt) {
		for (PrescriptionExamingPrescriptionScrollView scrollView : mHScrollViews) {
			if (mTouchView != scrollView)
				scrollView.smoothScrollTo(l, t);
		}
	}

	class ExamingScrollAdapter extends SimpleAdapter {

		private List<? extends Map<String, ?>> datas;
		private int res;
		private String[] from;
		private int[] to;
		private Context context;

		public ExamingScrollAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from,
				int[] to) {
			super(context, data, resource, from, to);
			this.context = context;
			this.datas = data;
			this.res = resource;
			this.from = from;
			this.to = to;
		}

		public void setData(List<? extends Map<String, ?>> newdatas){
			datas = newdatas;
		}
		
		public List<? extends Map<String, ?>> getData(){
			return datas;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				v = LayoutInflater.from(context).inflate(res, null);
				addHViews((PrescriptionExamingPrescriptionScrollView) v.findViewById(R.id.examing_item_scroll));
				View[] views = new View[to.length];
				for (int i = 0; i < to.length; i++) {
					View tv = v.findViewById(to[i]);
					tv.setOnClickListener(clickListener);
					views[i] = tv;
				}
				v.setTag(views);
			}
			View[] holders = (View[]) v.getTag();
			int len = holders.length;
			for (int i = 0; i < len; i++) {
				((TextView) holders[i]).setText(this.datas.get(position).get(from[i]).toString());
			}
			return v;
		}
	}

	protected View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(PrescriptionExamingPrescriptionActivity.this, ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
		}
	};

}
