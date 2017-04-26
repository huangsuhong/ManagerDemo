package com.example.cidjg.managerdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private  EditText mPwd_again;                       //再次确认密码
    private Button  mRegisterSureButton;               //确认按钮
    private Button mRegisterButton;                   //注册按钮
    private Button mLoginButton;                      //登录按钮
    private UserDataManager mUserDataManager;          //用户数据管理类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAccount = (EditText) findViewById(R.id.resetpwd_edit_name);
        mPwd = (EditText) findViewById(R.id.resetpwd_edit_pwd_old);
        mPwd_again = (EditText) findViewById(R.id.resetpwd_edit_pwd_again);
        mRegisterSureButton = (Button) findViewById(R.id.register_btn_sure);
        mRegisterButton = (Button) findViewById(R.id.login_btn_register);
        mLoginButton = (Button) findViewById(R.id.register_btn_register);
        mRegisterSureButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);

        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn_sure:
                register_again();
                break;

            case R.id.login_btn_register:
                Intent intent_Login_to_Register = new Intent(Register.this, Login.class);
                intent_Login_to_Register.putExtra("mAccount", mAccount.getText().toString());
                intent_Login_to_Register.putExtra("mPwd", mPwd.getText().toString());
                startActivity(intent_Login_to_Register);
                finish();
                break;
            case R.id.register_btn_register:
                 Intent it=new Intent();
                it.putExtra("mAccount", mAccount.getText().toString());
                it.putExtra("mPwd", mPwd.getText().toString());
                it.putExtra("mPwdAgain", mPwd_again.getText().toString());
                break;
        }
    }

    private void register_again() {
  if (isUserNammeAndPwdValid()){
      String userName=mAccount.getText().toString().trim();
       String userPwd=mPwd.getText().toString().trim();
      String userPwdAgain = mPwd_again.getText().toString().trim();
      //检查用户是否存在
      int count = mUserDataManager.findUserByName(userName);
      if (count>0){
          Toast.makeText(this, getString(R.string.name_already_exist, userName),Toast.LENGTH_SHORT).show();
          return;
      }
    if (userPwd.equals(userPwdAgain)==false){
          Toast.makeText(this,getString(R.string.pwd_not_the_same),Toast.LENGTH_SHORT).show();
        return;
      }
      else {
          UserData mUser=new UserData(userName,userPwd);
          mUserDataManager.openDataBase();
          long flag = mUserDataManager.insertUserData(mUser);
if (flag==-1){
    Toast.makeText(this,getText(R.string.register_fail),Toast.LENGTH_SHORT).show();
}
else {
    Toast.makeText(this,getText(R.string.register_success),Toast.LENGTH_SHORT).show();
    Intent Register_to_Login = new Intent(Register.this, Login.class);
    startActivity(Register_to_Login);
    finish();
             }
          }
        }
    }

    private boolean isUserNammeAndPwdValid() {
        if (mAccount.getText().toString().trim().equals("")) {
  Toast.makeText(this,getText(R.string.account_empty),Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, getText(R.string.pwd_empty), Toast.LENGTH_SHORT).show();
            return false;        }
        else if (mPwd_again.getText().toString().trim().equals("")) {
            Toast.makeText(this, getText(R.string.pwd_again_empty), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}