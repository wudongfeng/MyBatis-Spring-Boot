package tk.mybatis.springboot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import tk.mybatis.springboot.model.Element;
import tk.mybatis.springboot.model.UserInfo;
import tk.mybatis.springboot.service.ElementService;
import tk.mybatis.springboot.service.Video2AudioService;
import tk.mybatis.springboot.util.ExcelImportUtils;

/**
 * Created by wudongfeng on 17/7/11.
 */
@Controller
public class ElementController {
    Logger logger = Logger.getLogger(ElementController.class);
    @Autowired
    private ElementService elementService;
    @Autowired
    private Video2AudioService video2AudioService;

    @RequestMapping(value = "/view/{id}")
    public List<Element> view(@PathVariable Long id) {
        ModelAndView result = new ModelAndView();
        List<Element> elements = elementService.listElementByCourseId(id);
        return elements;
    }

    @RequestMapping("/file")
    public String file() {
        return "/file";
    }

    @RequestMapping("/uploadFile")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        System.out.println("hllo need to be upload");
        return ExcelImportUtils.uploadFile(file);
    }

    @RequestMapping("/upload")
    public ModelAndView uploadExcel(@RequestParam("file") MultipartFile file) {
        final List<Element> elementList = new ArrayList<>();
        try {
            Workbook wb = new HSSFWorkbook(file.getInputStream());
            org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Long id = (long) row.getCell(0).getNumericCellValue();

                logger.info("第" + i + "个ID为：" + id);
                List<Element> elements = elementService.listElementByCourseId(id);
                for (Element element : elements) {
                    if (element.getType() == 2 || element.getType() == 3) {
                        elementList.add(element);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("elements");
        modelAndView.addObject("elementList", elementList);
        modelAndView.addObject("hello", new String("hello"));
        /**
         * 开始执行计算任务
         */
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        final String ffmpegUri="/Users/wudongfeng/downloads/yuyin/ffmpeg-2.8.4/ffmpeg";
        final String baseUri="/Users/wudongfeng/downloads";
//        final String originFileUri="";
//        final String resultUri="";

        for (int i = 0; i < elementList.size(); i++) {
            final int index = i;
            final String videoId=elementList.get(index).getId();
            final String sourceUri=elementList.get(index).getAttachPath();
            final Long coureseId=elementList.get(index).getCourseId();
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String originFileUriIndex= String.format("%s%s", baseUri, sourceUri);
                    logger.info("originFileUriIndex:"+originFileUriIndex);
                    String folder=baseUri+"/convert/"+coureseId;
                    logger.info("创建的folder为："+folder);
                    video2AudioService.makeFolder(folder);
                    String resultUriIndex=folder+"/"+videoId+".mp3";
                    logger.info("转换后的名称为："+resultUriIndex);
                    logger.info("=========================");
                    logger.info("ffmpegUri:"+ffmpegUri);
                    logger.info("originFileUriIndex:"+originFileUriIndex);
                    logger.info("resultUriIndex:"+resultUriIndex);
                    logger.info("=========================");
                    video2AudioService.convert(ffmpegUri,originFileUriIndex,resultUriIndex);
                }
            });
        }

        return modelAndView;
    }

    @RequestMapping("/convert")
    @ResponseBody
    public String convert(ModelAndView modelAndView) {
        System.out.println("begin convert video");
        List<Element> elementList = (List<Element>) modelAndView.getModel().get("elementList");
        System.out.println(elementList);
        return elementList.toString();
    }
}
