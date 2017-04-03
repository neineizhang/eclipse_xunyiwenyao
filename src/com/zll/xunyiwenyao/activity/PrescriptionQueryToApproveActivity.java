package com.zll.xunyiwenyao.activity;

import java.util.ArrayList;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.adapter.PrescriptionQueryAdapter;
import com.zll.xunyiwenyao.dbitem.Prescription;
import com.zll.xunyiwenyao.webservice.PrescriptionWebService;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Created by admin on 2017/3/23.
 */

public class PrescriptionQueryToApproveActivity extends Activity  implements  OnItemClickListener{
	
	private ListView allprescription_lv;
	private ArrayList<Prescription>  mPrescription = null;
	private Context mContext;
	private PrescriptionQueryAdapter mPrescriptionQueryAdapter = null;
	
	
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.prescriptionqueryall);
        
        allprescription_lv = (ListView) findViewById(R.id.allprescription_lv);
        
        mContext = PrescriptionQueryToApproveActivity.this;
        mPrescription =new ArrayList<Prescription>();
        intialData();
        mPrescriptionQueryAdapter = new PrescriptionQueryAdapter((ArrayList<Prescription>) mPrescription, mContext);
        allprescription_lv.setAdapter(mPrescriptionQueryAdapter);
        allprescription_lv.setOnItemClickListener(this);
    }

	private void intialData() {
		// TODO Auto-generated method stub
		 Prescription onedata = PrescriptionWebService.getAllPrescription().get(0);
		 mPrescription.add(onedata);
		 mPrescription.add(onedata);
		 
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Toast.makeText(mContext,"�����˵�" + position + "��",Toast.LENGTH_SHORT).show();
	}
	}