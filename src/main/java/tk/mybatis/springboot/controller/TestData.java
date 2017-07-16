package tk.mybatis.springboot.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by wudongfeng on 17/7/16.
 */
public class TestData {
    public static void main(String[] args) {
        File f=new File("/Users/wudongfeng/desktop/shelltest/1.txt");
        ArrayList<String> al=new ArrayList<String>();
        al.add("first");
        al.add("second");
        al.add("third");
        BufferedWriter bw= null;
        try {
            bw = new BufferedWriter(new FileWriter(f));
            for(int i=0;i<al.size();i++){
                bw.write(al.get(i));
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
