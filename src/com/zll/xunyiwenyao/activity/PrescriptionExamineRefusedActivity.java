package com.zll.xunyiwenyao.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.adapter.PrescriptionExamineAdapter;
import com.zll.xunyiwenyao.adapter.PrescriptionQueryAdapter;
import com.zll.xunyiwenyao.dbitem.Prescription;
import com.zll.xunyiwenyao.webservice.DoctorWebService;
import com.zll.xunyiwenyao.webservice.PrescriptionWebService;

public class PrescriptionExamineRefusedActivity extends Activity implements AdapterView.OnItemClickListener {

    private ArrayList<Prescription> examineprescriptionList = null;
    private ListView examine_lv;
    private PrescriptionExamineAdapter mPrescriptionExamineAdapter;

    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription_examine_lv);
        examine_lv = (ListView) findViewById(R.id.examine_lv);
        mContext = PrescriptionExamineRefusedActivity.this;
        examineprescriptionList =new ArrayList<Prescription>();
        
        intialdata();
        mPrescriptionExamineAdapter = new PrescriptionExamineAdapter((ArrayList<Prescription>) examineprescriptionList, mContext);
        examine_lv.setAdapter(mPrescriptionExamineAdapter);
        examine_lv.setOnItemClickListener(this);
        
    }

    private  void  intialdata(){
        Prescription onedata = PrescriptionWebService.getAllPrescription().get(0);
        examineprescriptionList.add(onedata);
        examineprescriptionList.add(onedata);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(mContext,"你点击了?" + position + "�?",Toast.LENGTH_SHORT).show();
    }
}



