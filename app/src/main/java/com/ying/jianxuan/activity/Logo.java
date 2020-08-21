package com.ying.jianxuan.activity;

import android.content.Context;
import android.content.Intent;
import android.googd.http.http;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.ying.jianxuan.R;
import com.ying.jianxuan.Util;

import java.io.File;

import static com.ying.jianxuan.StatusUtil.getfile;
import static com.ying.jianxuan.StatusUtil.sd;
import static com.ying.jianxuan.StatusUtil.setfile;

public class Logo extends AppCompatActivity implements View.OnClickListener {

    private String appid="101894532";
    public Tencent mTencent;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //qq登入获取openId
            case R.id.qq:
                mTencent.login(this, "all", loginListener);
                //mTencent.login(this, "all", loginListener, true);
                break;
            case R.id.dr:
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            String http = new http().with()
                                    .setURL("//1-2.ltd/logo.php?user="+user.getText().toString()+"&pass="+pass.getText().toString())
                                    .get();
                            JSONObject jsonObject=JSON.parseObject(http);
                            String coek=jsonObject.getString("coek");
                            String _key=jsonObject.getString("_key");
                        }catch (Exception e){
                        }
                    }
                }.start();
                break;
            case R.id.qqdr:

                break;
        }
    }

    private EditText user;
    private EditText pass;
    private void id(){
        user=findViewById(R.id.user);
        pass=findViewById(R.id.pass);
        //注册账号
        TextView qq=findViewById(R.id.qq);
        qq.setOnClickListener(this);

        Button qqdr=findViewById(R.id.qqdr);
        qqdr.setOnClickListener(this);

        Button dr=findViewById(R.id.dr);
        dr.setOnClickListener(this);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);

        mTencent = Tencent.createInstance("101894532", this.getApplicationContext(), "com.ying.qingyuan");
       // tencent();
        id();
        File file=new File(sd+"Tencent");
        if (file.exists()) {
            String json = getfile(file);
            JSONObject jsonObject=JSON.parseObject(json);
            final String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            final String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            mTencent.setAccessToken(token, expires);
            mTencent.setOpenId(openId);
        }
        t();
    }

    private void t(){
        if (this.ready(this)) {
            mTencent.checkLogin(new IUiListener() {
                @Override
                public void onComplete(Object response) {
                    org.json.JSONObject jsonResp = (org.json.JSONObject)response;
                        if (jsonResp.optInt("ret", -1) == 0) {
                            org.json.JSONObject jsonObject = mTencent.loadSession(appid);
                            mTencent.initSessionCache(jsonObject);
                           //Util.showResultDialog(Logo.this, jsonObject.toString(), "登录成功");
                        } else {
                            Util.showResultDialog(Logo.this, "token过期，请调用登录接口拉起手Q授权登录", "登录失败");
                        }
                }
                @Override
                public void onError(UiError e) {
                    Util.showResultDialog(Logo.this, "token过期，请调用登录接口拉起手Q授权登录", "登录失败");
                }
                @Override
                public void onCancel() {
                    Util.toastMessage(Logo.this, "onCancel");
                }
            });
        }

    }

    IUiListener loginListener = new BaseUiListener() {
            @Override
            protected void doComplete(JSONObject values) {
                Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
                initOpenidAndToken(values);
            }
    };

    private void http(final String openId, final String token){

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    final String f=   new http().with()
                            .setURL("http://1-2.ltd/zc.php?openid="+openId+"&access_token="+token+"&qq=")
                            .get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject json=JSON.parseObject(f);
                            String coek=json.getString("coek");
                            String logo=json.getString("logo");
                            String user1=json.getString("user");
                            String pass1=json.getString("pass");
                            if (coek.equals("200")){
                                user.setText(user1);
                                pass.setText(pass1);
                                File file=new File(sd+"_key.txt");
                                setfile(file,json.getString("_key"));
                                Toast.makeText(Logo.this,"注册成功",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(Logo.this,"注册失败",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }catch (Exception e){
                }
            }
        }.start();
    }


    public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            final String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            final String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
               // Toast.makeText(this,mTencent.getAccessToken(),Toast.LENGTH_LONG).show();
                mTencent.setOpenId(openId);
                http(openId,token);
              //  tencent();
            }
        } catch(Exception e) {
        }
    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            if (null == response) {
                Util.showResultDialog(Logo.this, "返回为空", "登录失败");
                return;
            }

            JSONObject jsonResponse = JSON.parseObject(response.toString());
            if (null != jsonResponse && jsonResponse.size() == 0) {
                Util.showResultDialog(Logo.this, "返回为空", "登录失败");
                return;
            }
            File file=new File(sd+"Tencent");
            setfile(file,jsonResponse.toString());
                //Toast.makeText(Logo.this,"登入成功",Toast.LENGTH_LONG).show();
              //  Util.showResultDialog(Logo.this, response.toString(), "登录成功");
                doComplete(jsonResponse);
        }

        protected void doComplete(JSONObject values) {
        }

        @Override
        public void onError(UiError e) {
            Util.toastMessage(Logo.this, "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            Util.toastMessage(Logo.this, "取消登入");
            Util.dismissDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        }
    }

    public boolean ready(Context context) {
        if (mTencent == null) {
            return false;
        }
        boolean ready = mTencent.isSessionValid()
                && mTencent.getQQToken().getOpenId() != null;
        if (!ready) {
            Toast.makeText(context, "login and get openId first, please!",
                    Toast.LENGTH_SHORT).show();
        }
        return ready;
    }

}
