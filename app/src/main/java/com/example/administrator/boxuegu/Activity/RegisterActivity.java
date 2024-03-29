package com.example.administrator.boxuegu.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.boxuegu.R;
import com.example.administrator.boxuegu.utils.MD5Utils;

public class RegisterActivity extends AppCompatActivity {
    private TextView tv_main_title;
    private TextView tv_bsck;
    private Button btn_register;
    private EditText et_user_name, et_psw, et_psw_agin;
    private String userName, psw, pswAgain;
    private RelativeLayout rl_title_bar;

    @Override
    protected void onCreate(Bundle savaInstanceStante) {
        super.onCreate(savaInstanceStante);
        //设置页面布局//
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
//从main_title_bar.cml页面布局中获得相应的UI控件//
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        tv_bsck = (TextView) findViewById(R.id.tv_main_title);
        rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);
        //从activity_register.xml页面布局中获得对应的UI控件//
        btn_register = (Button) findViewById(R.id.btn_register);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_psw = (EditText) findViewById(R.id.et_psw);
        et_psw_agin = (EditText) findViewById(R.id.et_psw_again);
        tv_bsck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditString();
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(RegisterActivity.this, "请输入用户名",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(RegisterActivity.this, "请输入密码",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(pswAgain)) {
                    Toast.makeText(RegisterActivity.this, "请再次输入密码",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (!psw.equals(pswAgain)) {
                    Toast.makeText(RegisterActivity.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
                    return;
                } else if (isExistUserName(userName)) {
                    Toast.makeText(RegisterActivity.this, "此账户名已存在", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    //把用户名和密码保存到SharedPreferences中//
                    saveRegisterInfo(userName, psw);
                    //注册成功后把用户名传递到LoginActivity.java中//
                    Intent data = new Intent();
                    data.putExtra("userName", "userName");
                    setResult(RESULT_OK, data);
                    RegisterActivity.this.finish();
                }
            }
        });
    }
private void getEditString(){
        userName=et_user_name.getText().toString().trim();
        psw=et_psw.getText().toString().trim();
        pswAgain=et_psw_agin.getText().toString().trim();

}
private boolean isExistUserName(String userName) {
    boolean has_userName = false;
    SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
    String spPsw = sp.getString(userName, "");
    if (!TextUtils.isEmpty(spPsw)) {
        has_userName = true;
    }
    return has_userName;
}
private void saveRegisterInfo(String userName,String psw){
String md5Psw=MD5Utils.md5(psw);
SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
SharedPreferences.Editor editor=sp.edit();
editor.putString(userName,md5Psw);
editor.commit();//添加修改
}
    }

