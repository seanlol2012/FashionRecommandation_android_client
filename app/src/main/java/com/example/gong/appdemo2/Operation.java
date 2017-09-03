package com.example.gong.appdemo2;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.util.ArrayList;

public class Operation {
    public Operation() {
    }
    public String login(String url, String username, String password) {
        String result = null;
        //创建ConnNet类，包括了HttpURLConnection getConn(String urlpath)和HttpPost gethttPost(String uripath)两个方法
        ConnNet connNet = new ConnNet();
        ArrayList params = new ArrayList();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));

        try {
            HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            HttpPost httpPost = connNet.gethttPost(url);
            httpPost.setEntity(entity);

            //设置请求超时的处理
            HttpParams httpParameters = new BasicHttpParams();
            //设置请求超时10秒
            HttpConnectionParams.setConnectionTimeout(httpParameters, 10*1000);
            //设置等待数据超时10秒
            HttpConnectionParams.setSoTimeout(httpParameters, 10*1000);

            HttpClient client = new DefaultHttpClient(httpParameters);
            HttpResponse httpResponse = client.execute(httpPost);

            if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) //200
            {
                result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            } else {
                result = "client: failed to log in";
            }
        } catch (UnsupportedEncodingException var11) {
            var11.printStackTrace();
        } catch (ClientProtocolException var12) {
            var12.printStackTrace();
        } catch (IOException var13) {
            var13.printStackTrace();
        }
        return result;
    }

    public String register(String url, String username, String password, String sexName) {
        String result = null;
        //创建ConnNet类，包括了HttpURLConnection getConn(String urlpath)和HttpPost gethttPost(String uripath)两个方法
        ConnNet connNet = new ConnNet();
        ArrayList params = new ArrayList();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("sex", sexName));

        try {
            HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            HttpPost httpPost = connNet.gethttPost(url);
            httpPost.setEntity(entity);

            //设置请求超时的处理
            HttpParams httpParameters = new BasicHttpParams();
            //设置请求超时10秒
            HttpConnectionParams.setConnectionTimeout(httpParameters, 10*1000);
            //设置等待数据超时10秒
            HttpConnectionParams.setSoTimeout(httpParameters, 10*1000);

            HttpClient client = new DefaultHttpClient(httpParameters);
            HttpResponse httpResponse = client.execute(httpPost);

            if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) //200
            {
                result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            } else {
                result = "client: failed to register";
            }
        } catch (UnsupportedEncodingException var11) {
            var11.printStackTrace();
        } catch (ClientProtocolException var12) {
            var12.printStackTrace();
        } catch (IOException var13) {
            var13.printStackTrace();
        }
        return result;
    }

    public String checkname(String url, String username) {
        String result = null;
        ConnNet connNet = new ConnNet();
        ArrayList params = new ArrayList();
        params.add(new BasicNameValuePair("username", username));

        try {
            HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            HttpPost httpPost = connNet.gethttPost(url);
            httpPost.setEntity(entity);

            //设置请求超时的处理
            HttpParams httpParameters = new BasicHttpParams();
            //设置请求超时10秒
            HttpConnectionParams.setConnectionTimeout(httpParameters, 10*1000);
            //设置等待数据超时10秒
            HttpConnectionParams.setSoTimeout(httpParameters, 10*1000);

            HttpClient client = new DefaultHttpClient(httpParameters);
            HttpResponse httpResponse = client.execute(httpPost);

            if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) //200
            {
                result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            } else {
                result = "client: username has been registered";
            }
        } catch (UnsupportedEncodingException var11) {
            var11.printStackTrace();
        } catch (ClientProtocolException var12) {
            var12.printStackTrace();
        } catch (IOException var13) {
            var13.printStackTrace();
        }

        return result;
    }
}