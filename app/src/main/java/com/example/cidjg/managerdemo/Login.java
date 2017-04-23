package com.example.cidjg.managerdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by CIDjg on 2017/4/20.
 */
public class Login extends Activity implements View.OnClickListener {
    Intent it;
    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private Button mRegisterButton;                   //注册按钮
    private Button mLoginButton;                      //登录按钮

    private SharedPreferences login_sp;
    private String userNameValue, passwordValue;

    private UserDataManager mUserDataManager;         //用户数据管理类
    private TextView mChangepwdText;
    private CheckBox mRememberCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAccount = (EditText) findViewById(R.id.login_edit_account1);
        mPwd = (EditText) findViewById(R.id.login_edit_pwd1);
        mRegisterButton = (Button) findViewById(R.id.login_btn_register1);
        mLoginButton = (Button) findViewById(R.id.login_btn_login1);
        mChangepwdText = (TextView) findViewById(R.id.login_text_change_pwd);
        mRememberCheck = (CheckBox) findViewById(R.id.Login_Remember);

        login_sp = getSharedPreferences("userInfo", 0);
        String name = login_sp.getString("USER_NAME", "");
        String pwd = login_sp.getString("PASSWORD", "");
        boolean choseRemember = login_sp.getBoolean("mRememberCheck", false);
        boolean choseAutoLogin = login_sp.getBoolean("mAutologinCheck", false);
        if(choseRemember){
            mAccount.setText(name);
            mPwd.setText(pwd);
            mRememberCheck.setChecked(true);
        }
        mRegisterButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();                              //建立本地数据库
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_register1:                            //登录界面的注册按钮
                Intent intent_Login_to_Register = new Intent(Login.this, Register.class);    //切换Login Activity至User Activity
                startActivity(intent_Login_to_Register);
                finish();
                break;

        }
    }
}








