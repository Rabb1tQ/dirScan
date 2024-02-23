package com.rabbitq.utils;


import cn.hutool.core.io.file.FileReader;
import com.rabbitq.entity.TargetOptionsEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadConf implements Runnable {


    /**
     * 添加动作监听器的线程类
     */

    @Override
    public void run() {
        System.out.println(this.getClass().getSimpleName() + ": 主线程开始运行");
    }

    public void scanAction(TargetOptionsEntity targetOptionsEntity) {
        //设置目标地址
        String strUrl = targetOptionsEntity.getUrl();
        if (!strUrl.endsWith("/")) {
            strUrl = strUrl + "/";
        }
        TCPThread.strUrl = strUrl;

        List<String> dictList =getDict(targetOptionsEntity.getDict());
        //线程数配置
        int numOfThreads = targetOptionsEntity.getNumOfThreads();

        int dictSize = dictList.size();
        if (dictSize < numOfThreads) {
            numOfThreads = dictSize;
        }
        System.out.println("\033[32m[*]\033[m最大启动线程数为" + numOfThreads);

        //字典平均分配
        List<List<String>> averageDict = averageAssign(dictList, numOfThreads);

        //超时时间配置
        TCPThread.timeOut=targetOptionsEntity.getTimeOut();

        //扫描类型配置
        TCPThread.scanType = targetOptionsEntity.getScanType();


        ThreadPoolExecutor myExecutor = new ThreadPoolExecutor(numOfThreads, numOfThreads, 200, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());
        for (int i = 0; i < numOfThreads; i++) {
            ArrayList<String> castedList = new ArrayList<>(averageDict.get(i));
            myExecutor.submit(new TCPThread("T" + i, castedList));

        }
        myExecutor.shutdown();

    }


    public List<String> getDict(String dict) {
        FileReader fileReader = new FileReader(dict);
        List<String> dictList= fileReader.readLines();
        System.out.println("\033[32m[*]\033[m读取目录字典成功，共读取到" + dictList.size() + "个");
        return dictList;
    }

    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remaider = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remaider > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }
}
