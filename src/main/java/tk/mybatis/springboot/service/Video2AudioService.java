package tk.mybatis.springboot.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;



/**
 * Created by wudongfeng on 17/7/11.
 */
@Service
public class Video2AudioService {
    private Logger logger=Logger.getLogger(Video2AudioService.class);

    public void makeFolder(String folder){
        List<String> cmd =new ArrayList<String>();
        cmd.clear();
        cmd.add("mkdir");
        cmd.add(folder);
        exec(cmd);

    }
    public void convert(String ffmpegUri,String originFileUri,String resultUri){
        List<String> cmd =new ArrayList<String>();
        cmd.clear();
        cmd.add(ffmpegUri);
        cmd.add("-i");
        cmd.add(originFileUri);
        cmd.add("-f");
        cmd.add("mp3");
        cmd.add("-vn");
        cmd.add(resultUri);
        exec(cmd);

    }

    public void exec(List<String> cmd){
        try{
            ProcessBuilder builder=new ProcessBuilder(); // 新建执行器
            builder.command(cmd);
            builder.redirectErrorStream(true);
            Process proc=builder.start();
            BufferedReader stdout=new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while((line=stdout.readLine())!=null){
                System.out.println(line);
            }
            proc.waitFor();
            stdout.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String folder="/Users/wudongfeng/downloads/yuyin2";

        String ffmpegUri="/Users/wudongfeng/downloads/yuyin/ffmpeg-2.8.4/ffmpeg";//ffmpeg地址
        String originFileUri="/Users/wudongfeng/downloads/yuyin/ffmpeg-2.8.4/apple.mp4";//视频源文件的地址
        String resultUri="/Users/wudongfeng/downloads/yuyin2/apple22.mp3";//目标地址
        Video2AudioService video2AudioService=new Video2AudioService();
        video2AudioService.makeFolder(folder);
//        video2AudioService.convert(ffmpegUri,originFileUri,resultUri);
    }
}
