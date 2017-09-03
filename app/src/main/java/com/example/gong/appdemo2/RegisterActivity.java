package com.example.gong.appdemo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RegisterActivity extends AppCompatActivity {

    private RadioGroup mSex_group;
    private RadioButton mMale;
    private RadioButton mFemale;

    //初始化sex，规定1为男性，0为女性
    private String sexName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //获取radio相关控件的信息
        sexName = "1";
        mSex_group = (RadioGroup)findViewById(R.id.group_sex);
        mMale = (RadioButton)findViewById(R.id.radioButton1);
        mFemale = (RadioButton)findViewById(R.id.radioButton2);

        //设置控件的监听器
        mSex_group.setOnCheckedChangeListener(SexListener);
    }

    RadioGroup.OnCheckedChangeListener SexListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(mMale.getId() == checkedId) {
                sexName = mMale.getText().toString();
                System.out.println(sexName);
            } else if(mFemale.getId() == checkedId) {
                sexName = mFemale.getText().toString();
                System.out.println(sexName);
            }
        }
    };

    public void gotoLogin() {
        //register
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void gotoError() {
        Intent intent = new Intent(this, Error2Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void gotoError3() {
        Intent intent = new Intent(this, Error3Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void userRegister(View view) {
        //获取用户输入信息
        EditText editText01 = (EditText)findViewById(R.id.user_name);
        EditText editText02 = (EditText)findViewById(R.id.user_password);
        EditText editText03 = (EditText)findViewById(R.id.user_password2);

        String user_name = editText01.getText().toString().trim();
        String user_password = editText02.getText().toString().trim();
        String user_password2 = editText03.getText().toString().trim();
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
        if(user_password2.length()<=0) {
            editText03.requestFocus();
            editText03.setError("确认密码不能为空");
            return;
        }
        if(!user_password2.equals(user_password)) {
            editText03.requestFocus();
            editText03.setError("确认密码须与上相同");
            return;
        }
        if(user_password.length()<6) {
            editText02.requestFocus();
            editText02.setError("密码太短");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                //为了防止线程运行时sexName的值尚未改变，于是将判断语句放置于线程内
                if(sexName.equals("male"))
                {
                    sexName = "1";
                }
                if(sexName.equals("female"))
                {
                    sexName = "0";
                }

                Operation operation = new Operation();
                String result = operation.checkname("checkname",user_name);
                System.out.println(result);
                if(result.equals("server:legal username")) {
                    result = operation.register("register",user_name,user_password,sexName);
                    System.out.println(result);
                    if(result.equals("server:registered")) {
                        gotoLogin();
                    } else {
                        gotoError();
                    }
                } else {
                    gotoError3();
                }
            }
        }).start();
    }

    public void userLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
