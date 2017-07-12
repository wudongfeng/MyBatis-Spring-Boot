package tk.mybatis.springboot.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import tk.mybatis.springboot.model.Element;
import tk.mybatis.springboot.service.ElementService;

/**
 * Created by wudongfeng on 17/7/11.
 */
@Controller
public class FileUploadController {
    Logger logger=Logger.getLogger(FileUploadController.class);

    @Autowired
    private ElementService elementService;

    @RequestMapping("/file")
    public String file() {
        return "/file";
    }

    @RequestMapping("/upload0")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        System.out.println("hllo need to be upload");
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

    @RequestMapping("/upload")
    public ModelAndView uploadExcel(@RequestParam("file") MultipartFile file){
        List<Element> elementList=new ArrayList<>();
        try{
            Workbook wb=new HSSFWorkbook(file.getInputStream());
            org.apache.poi.ss.usermodel.Sheet sheet=wb.getSheetAt(0);

            for(int i=1;i<=sheet.getLastRowNum();i++){
                Row row =sheet.getRow(i);
                logger.info(row.getCell(0).getNumericCellValue());
                Long id=(long)row.getCell(0).getNumericCellValue();

                logger.info("第"+i+"个ID为："+id);
                List<Element> elements=elementService.listElementByCourseId(id);
                for(Element element:elements){
                    if(element.getType()==2 || element.getType()==3){
                        elementList.add(element);
                    }
                }
//                elementList.addAll(elements);
//                logger.info("size-------："+elements.size());
                logger.info(elements);

            }

        }catch(IOException e){
            e.printStackTrace();
        }
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("elements");
        modelAndView.addObject("elementList",elementList);
        modelAndView.addObject("hello",new String("hello"));

        return modelAndView;
    }



}
