package com.zll.xunyiwenyao.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.dbitem.Drug;
import com.zll.xunyiwenyao.webservice.DrugWebService;
import com.zll.xunyiwenyao.view.PrescriptionCreateScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrescriptionCreateMainActivity extends Activity {

	private Button add_drug,dialog_ok_btn;
	private View view_custom;
	private Context mContext;
	private AlertDialog alert = null;
	private AlertDialog.Builder builder = null;
	private ExpandableListView add_drugs_lv;
	private MyExpandableListViewAdapter2 adapter;
    private AutoCompleteTextView add_drugs_autv;
	private Map<String, List<String>> dataset = new HashMap<String, List<String>>();
	//private String[] parentList = new String[] { "first", "second", "third" };
    private String[] parentList = new String[] { "first" };
	private List<String> childrenList1 = new ArrayList<String>();
	private List<String> childrenList2 = new ArrayList<String>();
	private List<String> childrenList3 = new ArrayList<String>();
	private static final String[] data = new String[]{
            "first", "second", "third", "forth", "fifth"
    };
	
	private ListView drugs_lv;
	public HorizontalScrollView mTouchView;
	protected List<PrescriptionCreateScrollView> mHScrollViews =new ArrayList<PrescriptionCreateScrollView>();


	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newprescription);

		add_drug = (Button) findViewById(R.id.add_drug);
        
		//璁剧疆listview鐨勬暟鎹�
		initViews();  
		// 鍒濆鍖朆uilder
		builder = new AlertDialog.Builder(this);
		// final LayoutInflater inflater =
		// New_prescription.this.getLayoutInflater();
		view_custom = View.inflate(this, R.layout.add_drugs_dialog, null);
		// builder.setView(view_custom);
		// builder.setCancelable(true);
		add_drug.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				alert.show();
			}
		});

		// 娣诲姞鑽搧鐨刣ialog鐨刲istview
		add_drugs_lv = (ExpandableListView) view_custom.findViewById(R.id.add_drugs_lv);
		add_drugs_autv = (AutoCompleteTextView) view_custom.findViewById(R.id.add_drugs_autv);
		
		
		  //鍒濆鍖杁ialog涓婄殑纭畾鎸夐挳
		dialog_ok_btn = (Button) view_custom.findViewById(R.id.dialog_ok_btn);
		ArrayAdapter<String> autvadapter = new ArrayAdapter<String>(PrescriptionCreateMainActivity.
                this, android.R.layout.simple_dropdown_item_1line, data);
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
				alert.dismiss();
			}
		});
	}

	private void initialData() {
//		childrenList1.add(parentList[0] + "-" + "first");
//		childrenList1.add(parentList[0] + "-" + "second");
//		childrenList1.add(parentList[0] + "-" + "third");
//		childrenList2.add(parentList[1] + "-" + "first");
//		childrenList2.add(parentList[1] + "-" + "second");
//		childrenList2.add(parentList[1] + "-" + "third");
//		childrenList3.add(parentList[2] + "-" + "first");
//		childrenList3.add(parentList[2] + "-" + "second");
//		childrenList3.add(parentList[2] + "-" + "third");
//		dataset.put(parentList[0], childrenList1);
//		dataset.put(parentList[1], childrenList2);
//		dataset.put(parentList[2], childrenList3);

        List<String> namelt = new ArrayList<String>();
		List<Drug> resultDruglt = DrugWebService.getAllDrug();
		//System.out.println(resultDruglt.size());
        for(Drug item : resultDruglt){
            namelt.add(item.getName());
        }
		dataset.put("first", namelt);
	}
    


	
	
	private class MyExpandableListViewAdapter2 extends BaseExpandableListAdapter {

		// 鑾峰緱鏌愪釜鐖堕」鐨勬煇涓瓙椤�
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

		// 鑾峰緱鏌愪釜鐖堕」
		@Override
		public Object getGroup(int parentPos) {
			return dataset.get(parentList[parentPos]);
		}

		// 鑾峰緱鏌愪釜鐖堕」鐨刬d
		@Override
		public long getGroupId(int parentPos) {
			return parentPos;
		}

		// 鑾峰緱鏌愪釜鐖堕」鐨勬煇涓瓙椤圭殑id
		@Override
		public long getChildId(int parentPos, int childPos) {
			return childPos;
		}

		// 鎸夊嚱鏁扮殑鍚嶅瓧鏉ョ悊瑙ｅ簲璇ユ槸鏄惁鍏锋湁绋冲畾鐨刬d锛岃繖涓柟娉曠洰鍓嶄竴鐩撮兘鏄繑鍥瀎alse锛屾病鏈夊幓鏀瑰姩杩�
		@Override
		public boolean hasStableIds() {
			return false;
		}

		// 鑾峰緱鐖堕」鏄剧ず鐨剉iew
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
//			text.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					Toast.makeText(New_prescription.this, "鐐瑰埌浜嗗唴缃殑textview", Toast.LENGTH_SHORT).show();
//				}
//			});
			return view;
		}

		// 瀛愰」鏄惁鍙�変腑锛屽鏋滈渶瑕佽缃瓙椤圭殑鐐瑰嚮浜嬩欢锛岄渶瑕佽繑鍥瀟rue
		@Override
		public boolean isChildSelectable(int i, int i1) {
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolderGroup groupHolder;
			if (convertView == null) {
				convertView = LayoutInflater.from(PrescriptionCreateMainActivity.this).inflate(R.layout.item_exlist_group, parent, false);
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

	private void initViews(){
		List<Map<String, String>> datas = new ArrayList<Map<String,String>>();  
        Map<String, String> data = null;
		PrescriptionCreateScrollView headerScroll = (PrescriptionCreateScrollView) findViewById(R.id.item_scroll_title);
      //娣诲姞澶存粦鍔ㄤ簨浠�   
        mHScrollViews.add(headerScroll); 
        
        drugs_lv = (ListView) findViewById(R.id.drugs_lv);  
        //鍚庢湡鏇存崲鏁版嵁
        for(int i = 0; i < 5; i++) {  
            data = new HashMap<String, String>();  
            data.put("title", "Title_" + i);  
            data.put("data_" + 1, "Date_" + 1 + "_" +i );  
            data.put("data_" + 2, "Date_" + 2 + "_" +i );  
            data.put("data_" + 3, "Date_" + 3 + "_" +i );  
            data.put("data_" + 4, "Date_" + 4 + "_" +i );  
            data.put("data_" + 5, "Date_" + 5 + "_" +i );  
           
            datas.add(data);  
        }  
	    
	//
        SimpleAdapter adapter = new ScrollAdapter(this, datas, R.layout.scroll_item  
                , new String[] { "title", "data_1", "data_2", "data_3", "data_4", "data_5" }  
                , new int[] { R.id.item_title   
                            , R.id.item_data1  
                            , R.id.item_data2  
                            , R.id.item_data3  
                            , R.id.item_data4  
                            , R.id.item_data5   });  
                 drugs_lv.setAdapter(adapter);  
  
	}
	 public void addHViews(final PrescriptionCreateScrollView hScrollView) {
	        if(!mHScrollViews.isEmpty()) {  
	            int size = mHScrollViews.size();
				PrescriptionCreateScrollView scrollView = mHScrollViews.get(size - 1);
	            final int scrollX = scrollView.getScrollX();  
	            //绗竴娆℃弧灞忓悗锛屽悜涓嬫粦鍔紝鏈変竴鏉℃暟鎹湪寮�濮嬫椂鏈姞鍏�  
	            if(scrollX != 0) {  
	                drugs_lv.post(new Runnable() {  
	                    @Override  
	                    public void run() {  
	                        //褰搇istView鍒锋柊瀹屾垚涔嬪悗锛屾妸璇ユ潯绉诲姩鍒版渶缁堜綅缃�  
	                        hScrollView.scrollTo(scrollX, 0);  
	                    }  
	                });  
	            }  
	        }  
	        mHScrollViews.add(hScrollView);  
	    } 
	 public void onScrollChanged(int l, int t, int oldl, int oldt){  
	        for(PrescriptionCreateScrollView scrollView : mHScrollViews) {
	            //闃叉閲嶅婊戝姩  
	            if(mTouchView != scrollView)  
	                scrollView.smoothScrollTo(l, t);  
	        }  
	    }  
	 
	      
	    class ScrollAdapter extends SimpleAdapter {  
	  
	        private List<? extends Map<String, ?>> datas;  
	        private int res;  
	        private String[] from;  
	        private int[] to;  
	        private Context context;  
	        public ScrollAdapter(Context context,  
	                List<? extends Map<String, ?>> data, int resource,  
	                String[] from, int[] to) {  
	            super(context, data, resource, from, to);  
	            this.context = context;  
	            this.datas = data;  
	            this.res = resource;  
	            this.from = from;  
	            this.to = to;  
	        }  
	          
	        @Override  
	        public View getView(int position, View convertView, ViewGroup parent) {  
	            View v = convertView;  
	            if(v == null) {  
	                v = LayoutInflater.from(context).inflate(res, null);  
	                //绗竴娆″垵濮嬪寲鐨勬椂鍊欒杩涙潵  
	                addHViews((PrescriptionCreateScrollView) v.findViewById(R.id.item_scroll));
	                View[] views = new View[to.length];  
	                for(int i = 0; i < to.length; i++) {  
	                    View tv = v.findViewById(to[i]);;  
	                    tv.setOnClickListener(clickListener);  
	                    views[i] = tv;  
	                }  
	                v.setTag(views);  
	            }  
	            View[] holders = (View[]) v.getTag();  
	            int len = holders.length;  
	            for(int i = 0 ; i < len; i++) {  
	                ((TextView)holders[i]).setText(this.datas.get(position).get(from[i]).toString());  
	            }  
	            return v;  
	        }  
	    }  
	      
	    //娴嬭瘯鐐瑰嚮鐨勪簨浠�   
	    protected View.OnClickListener clickListener = new View.OnClickListener() {  
	        @Override  
	        public void onClick(View v) {  
	            Toast.makeText(PrescriptionCreateMainActivity.this, ((TextView)v).getText(), Toast.LENGTH_SHORT).show();
	        }  
	    };  
	
}
