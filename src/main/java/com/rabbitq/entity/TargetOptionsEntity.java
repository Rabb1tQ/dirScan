package com.rabbitq.entity;

import com.beust.jcommander.Parameter;
import com.rabbitq.utils.AllowedValuesValidator;

public class TargetOptionsEntity {

    @Parameter(names = {"--url", "-u"},
            description = "目标地址",
            required = true)
    private String url;

    @Parameter(names = {"--dict", "-d"},
            description = "字典")
    private String dict = "dict.txt";

    @Parameter(names = {"--threads", "-t"},
            description = "线程数")
    private int numOfThreads = 100;

    @Parameter(names = {"--timeOut", "-to"},
            description = "线程数")
    private int timeOut = 200;
    @Parameter(names = {"--scanType", "-s"},
            description = "扫描类型，head或者get",
            required = true,
            validateWith = AllowedValuesValidator.class)
    private String scanType;

    @Parameter(names = {"help", "--help"},
            description = "查看帮助信息",
            help = true)
    private boolean help;

    public boolean isHelp() {
        return help;
    }

    public String getUrl() {
        return url;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public String getDict() {
        return dict;
    }

    public int getNumOfThreads() {
        return numOfThreads;
    }

    public String getScanType() {
        return scanType;
    }
}
