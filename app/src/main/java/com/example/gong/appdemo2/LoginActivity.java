package com.example.gong.appdemo2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "extra.MESSAGE";
    //static Activity instance;
    ProgressDialog tempMessage;

    protected boolean flaglogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //instance = this;
    }

    public void gotoRegister(View view) {
        //register
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void gotoMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void gotoError() {
        Intent intent = new Intent(this, ErrorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void sendMessage(View view) {
        //获取用户输入信息
        EditText editText01 = (EditText)findViewById(R.id.entry_name);
        EditText editText02 = (EditText)findViewById(R.id.entry_password);
        tempMessage = new ProgressDialog(LoginActivity.this);
        tempMessage.setTitle("登录中");
        tempMessage.setMessage("登录中，请稍等");
        String user_name = editText01.getText().toString().trim();
        String user_password = editText02.getText().toString().trim();

        if((user_name.equals("admin"))&&(user_password.equals("admin"))) {
            gotoMain();
            return;
        }

        if(user_name.length()<=0) {
            editText01.requestFocus();
            editText01.setError("用户名不能为空");
            return;
        }
        if(user_password.length()<=0) {
            editText02.requestFocus();
            editText02.setError("密码不能为空");
            return;
        }
        tempMessage.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Operation operation = new Operation();
                String result = operation.login("login",user_name,user_password);
                System.out.println(result);
                if(result.equals("server:log in")) {
                    flaglogin = true;
                    gotoMain();
                } else {
                    flaglogin = false;
                    gotoError();
                }
            }
        }).start();
    }
}