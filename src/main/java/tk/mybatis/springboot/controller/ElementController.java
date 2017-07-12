package tk.mybatis.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import tk.mybatis.springboot.model.Element;
import tk.mybatis.springboot.model.UserInfo;
import tk.mybatis.springboot.service.ElementService;

/**
 * Created by wudongfeng on 17/7/11.
 */
@RestController
@RequestMapping("/elements")
public class ElementController {
    @Autowired
    private ElementService elementService;
    @RequestMapping(value = "/view/{id}")
    public List<Element> view(@PathVariable Long id) {
        ModelAndView result = new ModelAndView();
        List<Element> elements = elementService.listElementByCourseId(id);
        return elements;
    }
}
