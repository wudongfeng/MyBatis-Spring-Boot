package tk.mybatis.springboot.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sun.rowset.internal.Row;

import tk.mybatis.springboot.mapper.ElementMapper;
import tk.mybatis.springboot.model.Element;
import tk.mybatis.springboot.util.ExcelImportUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by wudongfeng on 17/7/11.
 */
@Service
public class ElementService {
    @Autowired
    private ElementMapper elementMapper;

    public List<Element> listElementByCourseId(Long courseId){
        return elementMapper.listElementByCourseId(courseId);

    }


}
