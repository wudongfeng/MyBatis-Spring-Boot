package tk.mybatis.springboot.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import tk.mybatis.springboot.model.Element;

/**
 * Created by wudongfeng on 17/7/11.
 */
public class ExcelImportUtils {
    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    //@描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 验证EXCEL文件
     * @param filePath
     * @return
     */
    public static boolean validateExcel(String filePath){
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))){
            return false;
        }
        return true;
    }

    public static String uploadFile(MultipartFile file){
        if (!file.isEmpty()) {
            try{
                BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(new File(file
                        .getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();

            }catch(FileNotFoundException e){
                e.printStackTrace();
                return "fail upload"+e.getMessage();
            }catch(IOException e){
                e.printStackTrace();
                return "fail upload"+e.getMessage();
            }
            return "success";
        }else {
            return "failed because the file is empty";
        }

    }
}
