package com.zll.xunyiwenyao.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.dbitem.PrescriptionTemplate;
import com.zll.xunyiwenyao.dbitem.Utils;
import com.zll.xunyiwenyao.webservice.PrescriptionTemplateWebService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;


public class PrescriptionTemplateActivity extends Activity {
	
	
	private  ExpandableListView template_exist_lol;
	private  AutoCompleteTextView prescription_template_search_text;
	private Map<String, List<String>> dataset = new HashMap<String, List<String>>();
    private String[] parentList;// = new String[]{"first", "second", "third"};
//    private List<String> childrenList1 = new ArrayList<String>();
//    private List<String> childrenList2 = new ArrayList<String>();
//    private List<String> childrenList3 = new ArrayList<String>();
    private Button prescription_template_search_button;
    private String[] data;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.prescription_template);
        
		prescription_template_search_button = (Button) findViewById(R.id.prescription_template_search_button);
		template_exist_lol = (ExpandableListView) findViewById(R.id.template_exist_lol);
		prescription_template_search_text = (AutoCompleteTextView) findViewById(R.id.prescription_template_search_text);
		initialData();  
		MyExpandableListViewAdapter3 adapter = new MyExpandableListViewAdapter3();  
		template_exist_lol.setAdapter(adapter);  
		template_exist_lol.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view,
                                        int parentPos, int childPos, long l) {
            	prescription_template_search_text.setText(dataset.get(parentList[parentPos]).get(childPos));
                Toast.makeText(PrescriptionTemplateActivity.this,
                        dataset.get(parentList[parentPos]).get(childPos), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
		
		ArrayAdapter<String> autvadapter = new ArrayAdapter<String>(PrescriptionTemplateActivity.
                this, android.R.layout.simple_dropdown_item_1line, data);
		prescription_template_search_text.setAdapter(autvadapter);
		
		prescription_template_search_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String template_name = prescription_template_search_text.getText().toString();
				Intent i = new Intent(PrescriptionTemplateActivity.this,PrescriptionTemplateMangeActivity.class);
				i.putExtra("template_name", template_name); 
				startActivity(i);
			}
		});
		

	}
      
		
	private void initialData() {

        //数据准备
        parentList = new String[Utils.DEPARTMENT_ARRAY.length];
        for(int i = 0; i < Utils.DEPARTMENT_ARRAY.length; i++){
        	String item = Utils.DEPARTMENT_ARRAY[i];
            parentList[i] = item;
            dataset.put(item, new ArrayList<String>());
        }

        List<PrescriptionTemplate> templatelt = PrescriptionTemplateWebService.getAllTemplate();
        for(PrescriptionTemplate item : templatelt){
        	String department_name = Utils.DEPARTMENT_ARRAY[item.getDepartment()];
            dataset.get(department_name).add(item.getName());
        }
//        childrenList1.add(parentList[0] + "-" + "first");
//        childrenList1.add(parentList[0] + "-" + "second");
//        childrenList1.add(parentList[0] + "-" + "third");
//        childrenList2.add(parentList[1] + "-" + "first");
//        childrenList2.add(parentList[1] + "-" + "second");
//        childrenList2.add(parentList[1] + "-" + "third");
//        childrenList3.add(parentList[2] + "-" + "first");
//        childrenList3.add(parentList[2] + "-" + "second");
//        childrenList3.add(parentList[2] + "-" + "third");
//        dataset.put(parentList[0], childrenList1);
//        dataset.put(parentList[1], childrenList2);
//        dataset.put(parentList[2], childrenList3);

        // auto-complete
        List<String> namelt = PrescriptionTemplateWebService.getAllTemplateName();
        data = new String[namelt.size()];
        for(int i = 0; i < namelt.size(); i++){
        	data[i] = namelt.get(i);
        }
    }
	
	
	
    private class MyExpandableListViewAdapter3 extends BaseExpandableListAdapter {

	        //  鑾峰緱鏌愪釜鐖堕」鐨勬煇涓瓙椤�
	        @Override
	        public Object getChild(int parentPos, int childPos) {
	            return dataset.get(parentList[parentPos]).get(childPos);
	        }
	     // 鑾峰緱鐖堕」鐨勬暟閲�
			@Override
			public int getGroupCount() {
				return dataset.size();
				// return 0;
			}
			
			// 鑾峰緱鏌愪釜鐖堕」鐨勫瓙椤规暟鐩�
			@Override
			public int getChildrenCount(int parentPos) {
				return dataset.get(parentList[parentPos]).size();
			}
	       
	        //  鑾峰緱鏌愪釜鐖堕」
	        @Override
	        public Object getGroup(int parentPos) {
	            return dataset.get(parentList[parentPos]);
	        }

	        //  鑾峰緱鏌愪釜鐖堕」鐨刬d
	        @Override
	        public long getGroupId(int parentPos) {
	            return parentPos;
	        }

	        //  鑾峰緱鏌愪釜鐖堕」鐨勬煇涓瓙椤圭殑id
	        @Override
	        public long getChildId(int parentPos, int childPos) {
	            return childPos;
	        }

	        //  鎸夊嚱鏁扮殑鍚嶅瓧鏉ョ悊瑙ｅ簲璇ユ槸鏄惁鍏锋湁绋冲畾鐨刬d锛岃繖涓嚱鏁扮洰鍓嶄竴鐩撮兘鏄繑鍥瀎alse锛屾病鏈夊幓鏀瑰姩杩�
	        @Override
	        public boolean hasStableIds() {
	            return false;
	        }

	        //  鑾峰緱鐖堕」鏄剧ず鐨剉iew
	        @Override
	        public View getGroupView(int parentPos, boolean b, View view, ViewGroup viewGroup) {
	            if (view == null) {
	                LayoutInflater inflater = (LayoutInflater)PrescriptionTemplateActivity
	                        .this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                view = inflater.inflate(R.layout.prescription_template_lvgroup, null);
	            }
	            view.setTag(R.layout.prescription_template_lvgroup, parentPos);
	            view.setTag(R.layout.itemoftemplate, -1);
	            TextView text = (TextView) view.findViewById(R.id.template_tv_group_name);
	            text.setText(parentList[parentPos]);
	            return view;
	        }

	        //  鑾峰緱瀛愰」鏄剧ず鐨剉iew
	        @Override
	        public View getChildView(int parentPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
	            if (view == null) {
	                LayoutInflater inflater = (LayoutInflater) PrescriptionTemplateActivity
	                        .this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                view = inflater.inflate(R.layout.itemoftemplate, null);
	            }
	            view.setTag(R.layout.prescription_template_lvgroup, parentPos);
	            view.setTag(R.layout.itemoftemplate, childPos);
	            TextView text = (TextView) view.findViewById(R.id.template_tv_item_name);
	            text.setText(dataset.get(parentList[parentPos]).get(childPos));
	           
	              
	            return view;
	        }

	        //  瀛愰」鏄惁鍙�変腑锛屽鏋滈渶瑕佽缃瓙椤圭殑鐐瑰嚮浜嬩欢锛岄渶瑕佽繑鍥瀟rue
	        @Override
	        public boolean isChildSelectable(int i, int i1) {
	            return true;
	        }
	        private class ViewHolderGroup {
				private TextView template_tv_item_name;
			}

	    }
	}
