package com.zll.xunyiwenyao;

import com.zll.xunyiwenyao.util.TopBarView;
import com.zll.xunyiwenyao.util.TopBarView.onTitleBarClickListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Inspection_create extends Activity implements onTitleBarClickListener{

	private  TopBarView topbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inspection_create);
		
		topbar = (TopBarView)findViewById(R.id.topbar);
		
		topbar.setClickListener(this);
	}
	@Override
	public void onBackClick() {
		Inspection_create.this.finish();		
	}
	@Override
	public void onRightClick() {
		Toast.makeText(Inspection_create.this, "�������Ҳఴť", Toast.LENGTH_SHORT).show();
		
	}
	


}
