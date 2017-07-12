package tk.mybatis.springboot.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.TimeUtil;

/**
 * Created by wudongfeng on 17/7/11.
 */
public class FFMpegUtil implements IStringGetter{
    private int runtime=0;
    private String ffmpegUri;//ffmpeg地址
    private String originFileUri;//视频源文件的地址
    private enum FFMpegUtilStatus{Empty,CheckingFile,GettingRuntime,Convert};
    private FFMpegUtilStatus status=FFMpegUtilStatus.Empty;

    private List<String> cmd = new ArrayList<String>();
    private boolean isSupported;
    /**
     * 构造函数
     * @param ffmpegUri ffmpeg的全路径
     *      如e:/ffmpeg/ffmpeg.exe 或 /root/ffmpeg/bin/ffmpeg
     * @param originFileUri 所操作视频文件的全路径
     *      如e:/upload/temp/test.wmv
     */
    public FFMpegUtil(String ffmpegUri,String originFileUri){
        this.ffmpegUri=ffmpegUri;
        this.originFileUri=originFileUri;
    }
    /**
     * 获取视频时长
     * @return
     */
    public  int getRuntime(){
        runtime=0;
        status=FFMpegUtilStatus.GettingRuntime;
        cmd.clear();
        cmd.add(ffmpegUri);
        cmd.add("-i");
        cmd.add(originFileUri);
        System.out.println(cmd);
        CmdExecuter.exec(cmd,this);
        return runtime;
    }



    public void converVideo2Audio(){

        cmd.clear();
        cmd.add(ffmpegUri);
        cmd.add("-i");
        cmd.add(originFileUri);
        cmd.add("-f");
        cmd.add("mp3");
        cmd.add("-vn");
        cmd.add("//Users//wudongfeng//downloads//yuyin//ffmpeg-2.8.4/apple22.mp3");
        CmdExecuter.exec(cmd,this);
    }

    /**
     * 检测文件是否是支持的格式
     * 将检测视频文件本身，而不是扩展名
     * @return
     */
    public boolean isSupported(){
        isSupported = true;
        status = FFMpegUtilStatus.CheckingFile;
        cmd.clear();
        cmd.add(ffmpegUri);
        cmd.add("-i");
        cmd.add(originFileUri);
        System.out.println(cmd);
        CmdExecuter.exec(cmd, this);
        return isSupported;
    }

    /**
     * 生成视频截图
     * @param imageSavePath 截图文件保存全路径
     * @param screenSize 截图大小 如640x480
     */
    public void makeScreenCut( String imageSavePath , String screenSize ){
        cmd.clear();
        cmd.add(ffmpegUri);
        cmd.add("-i");
        cmd.add(originFileUri);
        cmd.add("-y");
        cmd.add("-f");
        cmd.add("image2");
        cmd.add("-ss");
        cmd.add("8");
        cmd.add("-t");
        cmd.add("0.001");
        cmd.add("-s");
        cmd.add(screenSize);
        cmd.add(imageSavePath);
        CmdExecuter.exec(cmd, null);
    }

    /**
     * 视频转换
     * @param fileSavePath 文件保存全路径（包括扩展名）如 e:/abc/test.flv
     * @param screenSize 视频分辨率 如640x480
     * @param audioByte 音频比特率
     * @param audioCollection 音频采样率
     * @param quality 视频质量(0.01-255)越低质量越好
     * @param fps 每秒帧数（15或29.97）
     */
    public void videoTransfer(
            String fileSavePath,
            String screenSize,
            int audioByte,
            int audioCollection,
            double quality,
            double fps ){
        cmd.clear();
        cmd.add(ffmpegUri);
        cmd.add("-i");
        cmd.add(originFileUri);
        cmd.add("-y");
        cmd.add("-ab");
        cmd.add( Integer.toString(audioByte) );
        cmd.add("-ar");
        cmd.add( Integer.toString(audioCollection) );
        cmd.add("-qscale");
        cmd.add( Double.toString(quality) );
        cmd.add("-r");
        cmd.add( Double.toString(fps) );
        cmd.add("-s");
        cmd.add(screenSize);
        cmd.add( fileSavePath );
        CmdExecuter.exec(cmd, null);
    }

    @Override
    public void dealString( String str ) {

        switch (status) {
            case Empty:
                break;
            case CheckingFile: {
                Matcher m = Pattern.compile("Unknown format").matcher(str);
                if (m.find())
                    this.isSupported = false;
                break;
            }
            case GettingRuntime: {
                Matcher m = Pattern.compile("Duration: //w+://w+://w+").matcher(str);
                while (m.find()) {
                    String msg = m.group();
                    msg = msg.replace("Duration: ", "");
                    //                    runtime = TimeUtil.runtimeToSecond(msg);
                }
                break;
            }
        }
    }

    public static void main(String[] args) {

        String ffmpegUri="/Users/wudongfeng/downloads/yuyin/ffmpeg-2.8.4/ffmpeg";//ffmpeg地址
        String originFileUri="/Users/wudongfeng/downloads/yuyin/ffmpeg-2.8.4/apple.mp4";//视频源文件的地址
        FFMpegUtil ffMpegUtil=new FFMpegUtil(ffmpegUri,originFileUri);
        ffMpegUtil.converVideo2Audio();
//        int time=ffMpegUtil.getRuntime();
//        System.out.println("shipindehjidg------------="+time);
    }


}
