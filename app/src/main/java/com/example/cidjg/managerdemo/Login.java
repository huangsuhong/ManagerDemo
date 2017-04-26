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
import android.widget.Toast;

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

    private UserDataManager mUserDataManager;
    private TextView mChangepwdText;
    private CheckBox mRememberCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAccount = (EditText) findViewById(R.id.login_edit_account);
        mPwd = (EditText) findViewById(R.id.login_edit_pwd);
        mRegisterButton = (Button) findViewById(R.id.login_btn_register);
        mLoginButton = (Button) findViewById(R.id.login_btn_login);
        mChangepwdText = (TextView) findViewById(R.id.login_text_change_pwd);
        mRememberCheck = (CheckBox) findViewById(R.id.Login_Remember);

        login_sp = getSharedPreferences("userInfo", 0);
        String name = login_sp.getString("USER_NAME", "");
        String pwd = login_sp.getString("USER_PWD", "");
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
            case R.id.login_btn_login:                            //登录界面的登录按钮
                Intent intent_Login_to_User = new Intent(Login.this, User.class);
               startActivity(intent_Login_to_User);
               finish();
                break;
            case R.id.login_btn_register:
                Intent intent_login_to_register = new Intent(Login.this, Register.class);
                startActivity(intent_login_to_register);
                finish();
                break;
        }
    }

    private void login() {
        if (isUserNameAndPwdValid()) {
            String userName = mAccount.getText().toString().trim();
            String userPwd = mPwd.getText().toString().trim();
            SharedPreferences.Editor editor = login_sp.edit();
            int result = mUserDataManager.findUserByNameAndPwd(userName, userPwd);
            if (result == -1) {
                editor.putString("USER_NAME", userName);
                editor.putString("USER_PWD", userPwd);
                if (mRememberCheck.isChecked()) {
                    editor.putBoolean("mRememberCheck", true);
                } else {
                    editor.putBoolean("mRememberCheck", false);
                }
                editor.commit();
                Intent intent = new Intent(Login.this, User.class);
                startActivity(intent);
                finish();
                Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
            } else if (result == 0) {
                Toast.makeText(this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();

            }
        }
    }

    public boolean isUserNameAndPwdValid() {
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty), Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mUserDataManager != null) {
            mUserDataManager.closeDataBase();
            mUserDataManager = null;

        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}








