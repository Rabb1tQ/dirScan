package com.rabbitq;

import com.beust.jcommander.JCommander;
import com.rabbitq.entity.TargetOptionsEntity;
import com.rabbitq.utils.ThreadConf;

import java.util.concurrent.ExecutionException;

public class DirScan {
    public static void main(String[] args) throws ExecutionException {
        printBanner();
        TargetOptionsEntity targetOptionsEntity = new TargetOptionsEntity();
        JCommander commander = JCommander.newBuilder()
                .addObject(targetOptionsEntity)
                .build();
        try {
            commander.parse(args);
        } catch (Exception e){
            commander.usage();
            return;
        }
        if (targetOptionsEntity.isHelp()) {
            commander.usage();
            return;
        }
        ThreadConf threadConf = new ThreadConf();
        threadConf.scanAction(targetOptionsEntity);
    }


    public static void printBanner() {
        System.out.println(
                "██████╗ ██╗   ██╗    ██████╗  █████╗ ██████╗ ██████╗ ██╗████████╗ ██████╗ \n" +
                "██╔══██╗╚██╗ ██╔╝    ██╔══██╗██╔══██╗██╔══██╗██╔══██╗██║╚══██╔══╝██╔═══██╗\n" +
                "██████╔╝ ╚████╔╝     ██████╔╝███████║██████╔╝██████╔╝██║   ██║   ██║   ██║\n" +
                "██╔══██╗  ╚██╔╝      ██╔══██╗██╔══██║██╔══██╗██╔══██╗██║   ██║   ██║▄▄ ██║\n" +
                "██████╔╝   ██║       ██║  ██║██║  ██║██████╔╝██████╔╝██║   ██║   ╚██████╔╝\n" +
                "╚═════╝    ╚═╝       ╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ╚═════╝ ╚═╝   ╚═╝    ╚══▀▀═╝ \n" +
                "                                                                          ");
    }
}
