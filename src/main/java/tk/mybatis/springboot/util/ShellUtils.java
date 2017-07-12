package tk.mybatis.springboot.util;

import java.io.IOException;

/**
 * Created by wudongfeng on 17/7/11.
 */
public class ShellUtils {
    public static void main(String[] args) {
        System.out.println("just for test");
        Process process=null;
        String command1="cd //Users//wudongfeng//downloads//yuyin//ffmpeg-2.8.4;ffmpeg -i apple.mp4 -f mp3 -vn "
                + "apple2.mp3";
        try{
            Runtime.getRuntime().exec(command1).waitFor();
        }catch (IOException e){
            e.printStackTrace();
        }catch(InterruptedException e2){
            e2.printStackTrace();
        }
        System.out.println("ok");
    }
}
