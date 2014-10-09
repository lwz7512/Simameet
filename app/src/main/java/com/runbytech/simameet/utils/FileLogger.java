package com.runbytech.simameet.utils;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liwenzhi on 14-10-9.
 */
public class FileLogger {

    private static final String TAG = "sima";
    public static final String LOGFILENAME = "app_running_log.txt";


    public static void writeLogFileToSDCard(String msg){
        try {
            File logdir = FileHelper.getBasePath();
            if(logdir!=null){

                File logFile = new File(logdir, LOGFILENAME);
                String logFilePath = logFile.getAbsolutePath();

                String now = getNowString();
                if(!logFile.exists()){//如果不存在，新建txt文件
                    //FIXME, 创建空文件，解决三星手机没文件的问题
                    //2012/03/21
                    logFile.createNewFile();
                    //写内容
                    FileHelper.writeFileSdcard(logFilePath, ">>>file created at "+now+"\n",true);
                }else{
                    //大于10K，重新写文件
                    if(FileHelper.getFileLength(logFilePath)>10240) {
                        FileHelper.writeFileSdcard(logFilePath, msg,true);
//                    }else{//追加文件在顶部
//                        String newContent = now+": "+msg+"\n"+FileHelper.readFileSdcard(logFilePath,false);
//                        FileHelper.writeFileSdcard(logFilePath, newContent);
//                    }
                    }else {//追加到底部
                        FileHelper.writeFileSdcard(logFilePath, now+": "+msg+"\n", true);
                    }
                }

            }else{
                Log.e(TAG, "cannot write log file to SD card...");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static String getNowString(){
        DateFormat df = new SimpleDateFormat("MM-dd HH:mm");
        return df.format(new Date());
    }

}
