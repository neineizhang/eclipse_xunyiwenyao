package com.zll.xunyiwenyao.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.dbitem.Drug;
import com.zll.xunyiwenyao.dbitem.Patient;
import com.zll.xunyiwenyao.dbitem.Prescription;
import com.zll.xunyiwenyao.dbitem.PrescriptionTemplate;
import com.zll.xunyiwenyao.dbitem.Utils;
import com.zll.xunyiwenyao.view.PrescriptionCreateScrollView;
import com.zll.xunyiwenyao.webservice.DrugWebService;
import com.zll.xunyiwenyao.webservice.PrescriptionTemplateWebService;
import com.zll.xunyiwenyao.webservice.PrescriptionWebService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PrescriptionCreateMainActivity extends Activity {

	private Button save, savetotemplate, commit;
	private EditText patient_name_text,chufangmingcheng;
	private EditText prescription_data_et,doctor_name_et,checker_name_et,other_information_et,clinical_diagnosis_et;
	private RadioGroup radioGroupsex;
	private NumberPicker patient_age_text;
	int minAge = 1, maxAge =100;
	private RadioButton radioman,radiowoman;
	private Button add_drug, dialog_ok_btn;
	private View view_custom;
	private Context mContext;
	private AlertDialog alert = null;
	private AlertDialog.Builder builder = null;
	private ExpandableListView add_drugs_lv;
	private MyExpandableListViewAdapter2 adapter;
	private AutoCompleteTextView add_drugs_autv;
	private Map<String, List<String>> dataset = new HashMap<String, List<String>>();
	private String[] parentList = new String[] { "first" };
	private static final String[] data = new String[] { "first", "second", "third", "forth", "fifth" };
    public int patient_sex,patient_age ;
	private ListView drugs_lv;
	public HorizontalScrollView mTouchView;
	protected List<PrescriptionCreateScrollView> mHScrollViews = new ArrayList<PrescriptionCreateScrollView>();

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newprescription);
		
		patient_age_text = (NumberPicker)findViewById(R.id.patient_age_text);
		patient_age_text.setMinValue(minAge);
		patient_age_text.setMaxValue(maxAge);
		patient_age_text.setValue(1);
		patient_age_text.setOnValueChangedListener(new OnValueChangeListener() {
			
			public void onValueChange(NumberPicker packer, int oldVal, int newVal) {
				// TODO Auto-generated method stub
				patient_age =newVal;
			}
		});
		prescription_data_et = (EditText) findViewById(R.id.prescription_data_et);
		doctor_name_et = (EditText) findViewById(R.id.doctor_name_et);
		checker_name_et = (EditText) findViewById(R.id.checker_name_et);
		other_information_et = (EditText) findViewById(R.id.other_information_et);
		clinical_diagnosis_et = (EditText) findViewById(R.id.clinical_diagnosis_text);
		

		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");       
		String date = sDateFormat.format(new java.util.Date());    
		prescription_data_et.setText(date);
		prescription_data_et.setEnabled(false);
		doctor_name_et.setText(Utils.LOGIN_DOCTOR.getRealName());
		doctor_name_et.setEnabled(false);
		checker_name_et.setText("");
		checker_name_et.setEnabled(false);
		other_information_et.setText("");
		
		add_drug = (Button) findViewById(R.id.add_drug);

		initViews();
		builder = new AlertDialog.Builder(this);
		view_custom = View.inflate(this, R.layout.add_drugs_dialog, null);
		add_drug.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				alert.show();
			}
		});

		add_drugs_lv = (ExpandableListView) view_custom.findViewById(R.id.add_drugs_lv);
		add_drugs_autv = (AutoCompleteTextView) view_custom.findViewById(R.id.add_drugs_autv);

		dialog_ok_btn = (Button) view_custom.findViewById(R.id.dialog_ok_btn);
		ArrayAdapter<String> autvadapter = new ArrayAdapter<String>(PrescriptionCreateMainActivity.this,
				android.R.layout.simple_dropdown_item_1line, data);
		add_drugs_autv.setAdapter(autvadapter);

		initialData();
		adapter = new MyExpandableListViewAdapter2();
		add_drugs_lv.setAdapter(adapter);
		add_drugs_lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView expandableListView, View view, int parentPos, int childPos,
					long l) {
				add_drugs_autv.setText(dataset.get(parentList[parentPos]).get(childPos));
				Toast.makeText(PrescriptionCreateMainActivity.this, dataset.get(parentList[parentPos]).get(childPos),
						Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		builder.setView(view_custom);
		alert = builder.create();

		dialog_ok_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String drugname = add_drugs_autv.getText().toString();
				Drug drug = DrugWebService.getDrugByName(drugname);
				if(drug == null){
					/////// zll add !!1!
					alert.dismiss();
					return;
				}

				Map<String, String> tempdata = new HashMap<String, String>();
				tempdata.put("title", String.valueOf(drug.getId()));
				tempdata.put("data_" + 1, drug.getName());
				tempdata.put("data_" + 2, drug.getSpecification());
				tempdata.put("data_" + 3, "1");
				tempdata.put("data_" + 4, drug.getPrice());
				tempdata.put("data_" + 5, drug.getDescription());
				List<Map<String, String>> datas = (List<Map<String, String>>) ((ScrollAdapter)drugs_lv.getAdapter()).getData();
				datas.add(tempdata);
				((ScrollAdapter)drugs_lv.getAdapter()).setData(datas);
				((ScrollAdapter)drugs_lv.getAdapter()).notifyDataSetChanged();
				alert.dismiss();
			}
		});

		mContext = this;
		
		save = (Button) findViewById(R.id.save);
		savetotemplate = (Button) findViewById(R.id.savetotemplate);
		commit = (Button) findViewById(R.id.commit);
		chufangmingcheng = (EditText) findViewById(R.id.editText1);
		patient_name_text = (EditText) findViewById(R.id.patient_name_text);
		radioGroupsex = (RadioGroup) findViewById(R.id.radioGroupsex);
		
		radioman = (RadioButton) findViewById(R.id.radioman);
		radiowoman = (RadioButton) findViewById(R.id.radiowoman);
		radioGroupsex.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				RadioButton radbtn = (RadioButton) findViewById(checkedId);
				if( radbtn == radioman)
						{
					 patient_sex =0;
			}
				else if( radbtn == radiowoman)
				{
					  patient_sex= 1;
	}
			}
			});
		///////////// add template data
		Bundle extras = getIntent().getExtras(); 
		String template_name = extras.getString("template_name");
		if(template_name != null){
			if(!template_name.trim().equals("")){
				PrescriptionTemplate prescriptionTemplate = PrescriptionTemplateWebService.getPrescriptionTemplateByName(template_name);
				if(prescriptionTemplate == null){
					
					
				}else{
					//chufangmingcheng.setText(template_name);
					
					Map<Drug, Integer> drugmap = prescriptionTemplate.getDrugmap();
					List<Map<String, String>> datas = (List<Map<String, String>>) ((ScrollAdapter)drugs_lv.getAdapter()).getData();
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
					((ScrollAdapter)drugs_lv.getAdapter()).setData(datas);
					((ScrollAdapter)drugs_lv.getAdapter()).notifyDataSetChanged();
				}
				
			}
		}
		///////////// end add template data
		
		
		String prescription_name = extras.getString("prescription_name");
		if(prescription_name != null){
			Prescription prescription = PrescriptionWebService.getPrescriptionByName(prescription_name);
			if(prescription == null){
				Toast.makeText(mContext, "处方名称无效", Toast.LENGTH_SHORT).show();
				
			}else{
				//chufangmingcheng.setText(template_name);
				String patient_name = prescription.getPatient().getName().toString();  
	            int patient_age = prescription.getPatient().getAge();
	            int patient_sex =prescription.getPatient().getSex();
//	            String doctor_name=prescription.getDoctor().getName().toString();
//	            String  prescription_date = prescription.getDate().toString();
	            String clinical_diagnosis = prescription.getClinical_diagnosis().toString();
	            
				chufangmingcheng.setText(prescription_name);
				patient_name_text.setText(patient_name);
				patient_age_text.setValue(patient_age);
				
				if( patient_sex==0){
					radioman.setChecked(true);
				}
				else 
					radiowoman.setChecked(true);
//					
//				doctor_name_et.setText(doctor_name);
//				prescription_data_et.setText(prescription_date);
				clinical_diagnosis_et.setText(clinical_diagnosis);
				Map<Drug, Integer> drugmap = prescription.getDrugmap();
				List<Map<String, String>> datas = (List<Map<String, String>>) ((ScrollAdapter)drugs_lv.getAdapter()).getData();
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
				((ScrollAdapter)drugs_lv.getAdapter()).setData(datas);
				((ScrollAdapter)drugs_lv.getAdapter()).notifyDataSetChanged();
				
				///////
				
			}
			
		}
		
		
		
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String prescription_name = chufangmingcheng.getText().toString();
				String patient_name = patient_name_text.getText().toString();
				String prescription_date = prescription_data_et.getText().toString();
				String clinical_diagnosis = clinical_diagnosis_et.getText().toString();
						
				Patient patient = new Patient();
				patient.setAge(patient_age);
				patient.setName(patient_name);
				patient.setSex(patient_sex);
				
				Map<Drug, Integer> drugmap = new HashMap<Drug, Integer>();
				
				List<Map<String, String>> datas = (List<Map<String, String>>) ((ScrollAdapter)drugs_lv.getAdapter()).getData();
				for(Map<String, String> item : datas){
					Drug drug = new Drug();
					drug.setId(Integer.parseInt(item.get("title")));
					drug.setDescription(item.get("data_5"));
					drug.setPrice(item.get("data_4"));
					drug.setName(item.get("data_1"));
					drug.setSpecification(item.get("data_2"));
					int count = Integer.valueOf(item.get("data_3"));
					drugmap.put(drug, count);
				}
				
				Prescription prescription = new Prescription();
				prescription.setPatient(patient);
				prescription.setName(prescription_name);
				prescription.setDrugmap(drugmap);
				prescription.setStatus(Utils.STATUS.SAVED.ordinal());
				prescription.setDate(prescription_date);
				prescription.setClinical_diagnosis(clinical_diagnosis);
				
				PrescriptionWebService.AddPrescription(prescription);
				
				Toast.makeText(mContext, "SAVE SUCCESS", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		savetotemplate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String prescription_name = chufangmingcheng.getText().toString();
				PrescriptionTemplate prescriptionTemplate_indb = PrescriptionTemplateWebService.getPrescriptionTemplateByName(prescription_name);
				if(prescriptionTemplate_indb != null){
					Toast.makeText(mContext, "HAVED, SAVE FAILURE", Toast.LENGTH_SHORT).show();
					return;
				}
				
				Map<Drug, Integer> drugmap = new HashMap<Drug, Integer>();
				List<Map<String, String>> datas = (List<Map<String, String>>) ((ScrollAdapter)drugs_lv.getAdapter()).getData();
				for(Map<String, String> item : datas){
					Drug drug = new Drug();
					drug.setId(Integer.parseInt(item.get("title")));
					drug.setDescription(item.get("data_5"));
					drug.setPrice(item.get("data_4"));
					drug.setName(item.get("data_1"));
					drug.setSpecification(item.get("data_2"));
					int count = Integer.valueOf(item.get("data_3"));
					drugmap.put(drug, count);
				}

				PrescriptionTemplate prescriptionTemplate = new PrescriptionTemplate();
				prescriptionTemplate.setName(prescription_name);
				prescriptionTemplate.setDrugmap(drugmap);
				PrescriptionTemplateWebService.addPrescriptionTemplate(prescriptionTemplate);

				Toast.makeText(mContext, "SAVE SUCCESS", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		
		commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String prescription_name = chufangmingcheng.getText().toString();
				String patient_name = patient_name_text.getText().toString();
				String prescription_date = prescription_data_et.getText().toString();
				String clinical_diagnosis = clinical_diagnosis_et.getText().toString();
				
				Patient patient = new Patient();
				patient.setAge(patient_age);
				patient.setName(patient_name);
				patient.setSex(patient_sex);
				
				Map<Drug, Integer> drugmap = new HashMap<Drug, Integer>();
				//List<Drug> druglt = new ArrayList<Drug>();
				List<Map<String, String>> datas = (List<Map<String, String>>) ((ScrollAdapter)drugs_lv.getAdapter()).getData();
				for(Map<String, String> item : datas){
					Drug drug = new Drug();
					drug.setId(Integer.parseInt(item.get("title")));
					drug.setDescription(item.get("data_5"));
					drug.setPrice(item.get("data_4"));
					drug.setName(item.get("data_1"));
					drug.setSpecification(item.get("data_2"));
					int count = Integer.valueOf(item.get("data_3"));
					drugmap.put(drug, count);
				}
				
				Prescription prescription = new Prescription();
				prescription.setPatient(patient);
				prescription.setName(prescription_name);
				prescription.setDrugmap(drugmap);
				prescription.setStatus(Utils.STATUS.COMMITED.ordinal());
				prescription.setDate(prescription_date);
				prescription.setClinical_diagnosis(clinical_diagnosis);
				
				PrescriptionWebService.AddPrescription(prescription);
				
				Toast.makeText(mContext, "SAVE SUCCESS", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}

	private void initialData() {
		List<String> namelt = new ArrayList<String>();
		List<Drug> resultDruglt = DrugWebService.getAllDrug();
		// System.out.println(resultDruglt.size());
		for (Drug item : resultDruglt) {
			namelt.add(item.getName());
		}
		dataset.put("first", namelt);
	}

	private class MyExpandableListViewAdapter2 extends BaseExpandableListAdapter {

		@Override
		public Object getChild(int parentPos, int childPos) {
			return dataset.get(parentList[parentPos]).get(childPos);
		}

		@Override
		public int getGroupCount() {
			return dataset.size();
		}

		@Override
		public int getChildrenCount(int parentPos) {
			return dataset.get(parentList[parentPos]).size();
		}

		@Override
		public Object getGroup(int parentPos) {
			return dataset.get(parentList[parentPos]);
		}

		@Override
		public long getGroupId(int parentPos) {
			return parentPos;
		}

		@Override
		public long getChildId(int parentPos, int childPos) {
			return childPos;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public View getChildView(int parentPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) PrescriptionCreateMainActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.add_drugs_dialog_item, null);
			}
			view.setTag(R.layout.add_drugs_dialog_list, parentPos);
			view.setTag(R.layout.add_drugs_dialog_item, childPos);
			TextView text = (TextView) view.findViewById(R.id.add_drugs_dialog_item);
			text.setText(dataset.get(parentList[parentPos]).get(childPos));
			return view;
		}

		@Override
		public boolean isChildSelectable(int i, int i1) {
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolderGroup groupHolder;
			if (convertView == null) {
				convertView = LayoutInflater.from(PrescriptionCreateMainActivity.this)
						.inflate(R.layout.item_exlist_group, parent, false);
				groupHolder = new ViewHolderGroup();
				groupHolder.tv_group_name = (TextView) convertView.findViewById(R.id.tv_group_name);
				convertView.setTag(groupHolder);
			} else {
				groupHolder = (ViewHolderGroup) convertView.getTag();
			}
			groupHolder.tv_group_name.setText(parentList[0]);
			return convertView;
		}

		private class ViewHolderGroup {
			private TextView tv_group_name;
		}
	}

	private void initViews() {
		List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
		PrescriptionCreateScrollView headerScroll = (PrescriptionCreateScrollView) findViewById(R.id.item_scroll_title);

		mHScrollViews.add(headerScroll);
		drugs_lv = (ListView) findViewById(R.id.drugs_lv);

		ScrollAdapter adapter = new ScrollAdapter(this, datas, R.layout.scroll_item,
				new String[] { "title", "data_1", "data_2", "data_3", "data_4", "data_5" }, new int[] { R.id.item_title,
						R.id.item_data1, R.id.item_data2, R.id.item_data3, R.id.item_data4, R.id.item_data5 });
		drugs_lv.setAdapter(adapter);

	}

	public void addHViews(final PrescriptionCreateScrollView hScrollView) {
		if (!mHScrollViews.isEmpty()) {
			int size = mHScrollViews.size();
			PrescriptionCreateScrollView scrollView = mHScrollViews.get(size - 1);

			final int scrollX = scrollView.getScrollX();
			if (scrollX != 0) {
				drugs_lv.post(new Runnable() {
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
		for (PrescriptionCreateScrollView scrollView : mHScrollViews) {
			if (mTouchView != scrollView)
				scrollView.smoothScrollTo(l, t);
		}
	}

	class ScrollAdapter extends SimpleAdapter {

		private List<Map<String, String>> datas;
		private int res;
		private String[] from;
		private int[] to;
		private Context context;

		public ScrollAdapter(Context context, List<Map<String, String>> data, int resource, String[] from,
				int[] to) {
			super(context, data, resource, from, to);
			this.context = context;
			this.datas = data;
			this.res = resource;
			this.from = from;
			this.to = to;
		}

		public void setData(List<Map<String, String>> newdatas){
			datas = newdatas;
		}
		
		public List<Map<String, String>> getData(){
			return datas;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				v = LayoutInflater.from(context).inflate(res, null);
				addHViews((PrescriptionCreateScrollView) v.findViewById(R.id.item_scroll));
				View[] views = new View[to.length];
				for (int i = 0; i < to.length; i++) {
					View tv = v.findViewById(to[i]);
					;
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
			//Toast.makeText(PrescriptionCreateMainActivity.this, ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
		}
	};

}
