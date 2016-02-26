package com.eol.test;

import java.util.HashMap;
import java.util.Random;

import android.app.Activity;
import android.graphics.drawable.Drawable.Callback;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class TestActivity extends Activity {

	Button mBtnBindPhone;
	String APPKEY="fc39c1a33368";
	String APPSecrete="438e2ad68b6eb44d56907eecd2e7e807";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //初始化SDK
        SMSSDK.initSDK(this,APPKEY,APPSecrete);
        //配置
        
        mBtnBindPhone=(Button) this.findViewById(R.id.btn_bind_phone);
        
        //设置点击事件
        mBtnBindPhone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 注册手机号
				RegisterPage registerPage=new RegisterPage();
				//注册回调事件
				registerPage.setRegisterCallback(new EventHandler(){
					//事件完成后调用
					@Override
					public void afterEvent(int event, int result,Object data){
						//判断结果是否已经完成
						if (result==SMSSDK.RESULT_COMPLETE){
							//获取数据Data
							HashMap<String, Object>	maps=(HashMap<String, Object>) data;				
							//国家信息
							String country=(String) maps.get("country");
							//手机号信息
							String phone =(String) maps.get("phone");
							submitUserInfo(country, phone);
						}
						
					}
				});
				//显示注册界面
				registerPage.show(TestActivity.this);
			}
		});
    }
    
    /**
     * 提交用户信息
     * @param country
     * @param phone
     */
    public void submitUserInfo(String country,String phone) {
    	Random r=new Random();
    	String uid=Math.abs(r.nextInt())+"";
    	String nickName="in";
		SMSSDK.submitUserInfo(uid, nickName, null, country, phone);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }
    
}
