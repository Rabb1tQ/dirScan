package com.rabbitq.utils;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.List;
import java.util.Map;

public class TCPThread extends Thread {

    //目标IP
    static String strUrl = "";


    List<String> dict;
    // 最大启动线程数

    static int timeOut;

    static String scanType;

    public TCPThread(String name, List<String> dict) {
        super(name);
        this.dict = dict;
    }

    /**
     * 运行方法
     */
    @Override
    public void run() {
        try {
            if (scanType.equals("head")) {
                headScan(dict);
            } else {
                getScan(dict);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 头部扫描
     */
    private void headScan(List<String> dict) throws UnknownHostException {
        for (int i = 0; i < dict.size(); i++) {
            String  requestURL = null;
            try {
                requestURL = strUrl + URLEncoder.encode(dict.get(i), "ISO-8859-1");
                HttpResponse response =HttpRequest.head(requestURL).timeout(timeOut).execute();
                int code= response.getStatus();
                if(code==200){
                    System.out.println("状态码：200\t地址：\033[32m"+requestURL+"\033[m");
                }else if (code==301||code==302||code==403||code==500){
                    System.out.println("状态码："+code+"\t地址：\033[31m"+requestURL+"\033[0m");
                }
            } catch (Exception e) {
                continue;
               // System.out.println(e.toString());
            }

        }
    }

    /**
     * 端口扫描，按照分好的端口列表进行扫描
     */
    private void getScan(List<String> dict) throws UnknownHostException {
        for (int i = 0; i < dict.size(); i++) {
            String requestURL= null;
            try {

                requestURL = strUrl + URLEncoder.encode(dict.get(i), "ISO-8859-1");
                HttpResponse response =HttpRequest.get(requestURL).timeout(timeOut).execute();
                int code= response.getStatus();
                if(code==200){
                    if(response.body()!=null&&response.body().length()>0){
                        String title= ReUtil.findAll("<title>(.*?)</title>", response.body(), 1).toString();
                        System.out.println("状态码：200\ttitle："+title+"\t地址：\033[32m"+requestURL+"\033[m");
                    }
                    else {
                        System.out.println("状态码：200\t地址：\033[32m"+requestURL+"\033[m");
                    }

                }else if (code==301||code==302||code==403||code==500){
                    if(response.body()!=null&&response.body().length()>0){
                        String title= ReUtil.findAll("<title>(.*?)</title>", response.body(), 1).toString();
                        System.out.println("状态码："+code+"\ttitle："+title+"\t地址：\033[32m"+requestURL+"\033[m");
                    }
                    else {
                        System.out.println("状态码："+code+"\t地址：\033[32m"+requestURL+"\033[m");
                    }
                }
            } catch (Exception e) {
                continue;
                // System.out.println(e.toString());
            }

        }
    }

}