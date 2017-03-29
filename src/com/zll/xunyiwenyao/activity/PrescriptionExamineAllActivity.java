package com.zll.xunyiwenyao.activity;
import java.util.ArrayList;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.adapter.PrescriptionExamineAdapter;
import com.zll.xunyiwenyao.dbitem.Prescription;
import com.zll.xunyiwenyao.webservice.PrescriptionWebService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PrescriptionExamineAllActivity extends Activity implements OnItemClickListener {
	
	 private ArrayList<Prescription> examineprescriptionList = null;
	    private ListView examine_lv;
	    private PrescriptionExamineAdapter mPrescriptionExamineAdapter;

	    private Context mContext;

	    protected void onCreate(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.prescription_examine_lv);
	        examine_lv = (ListView) findViewById(R.id.examine_lv);
	        mContext = PrescriptionExamineAllActivity.this;
	        examineprescriptionList =new ArrayList<Prescription>();
	        
	        intialdata();
	        mPrescriptionExamineAdapter = new PrescriptionExamineAdapter((ArrayList<Prescription>) examineprescriptionList, mContext);
	        examine_lv.setAdapter(mPrescriptionExamineAdapter);
	        examine_lv.setOnItemClickListener(this);
	        
	    }

	    private  void  intialdata(){
	        Prescription onedata = PrescriptionWebService.getAllPrescription().get(1);
	        examineprescriptionList.add(onedata);
	        examineprescriptionList.add(onedata);
	    }

	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        Intent i =new Intent(this,PrescriptionCreateMainActivity.class);
	        TextView template_name_tv=(TextView) view.findViewById(R.id.examine_lvitem_name);
	        String template_name = template_name_tv.getText().toString();
	        i.putExtra("template_name", template_name); 
			startActivity(i);
	        
	    }
	}
