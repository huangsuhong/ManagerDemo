package com.example.cidjg.managerdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private Button mRegisterButton;                   //注册按钮
    private Button mLoginButton;                      //登录按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAccount = (EditText) findViewById(R.id.login_edit_account);
        mPwd = (EditText) findViewById(R.id.login_edit_pwd);
        mRegisterButton = (Button) findViewById(R.id.login_btn_register);
        mLoginButton = (Button) findViewById(R.id.register_btn_register);
        mRegisterButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                break;
        }
    }
}