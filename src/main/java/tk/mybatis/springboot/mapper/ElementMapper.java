package tk.mybatis.springboot.mapper;

import java.util.List;

import tk.mybatis.springboot.model.Element;
import tk.mybatis.springboot.util.MyMapper;

/**
 * Created by wudongfeng on 17/7/11.
 */
public interface ElementMapper extends MyMapper<Element> {
    public List<Element> listElementByCourseId(Long courseId);
}
