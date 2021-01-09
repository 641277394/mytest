package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

/**
 * @Author Sean
 * @Date 2021/1/8 17:00
 */
public interface CheckGroupService {
    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 检查组的分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    /**
     * 根据id查询检查组
     * @param id
     * @return
     */
    CheckGroup findBuId(int id);

    /**
     * 通过检查组id查询选中的检查项id
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    /**
     * 修改检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void update(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 根据id删除检查组
     * @param id
     * @throws MyException
     */
    void deleteById(int id) throws MyException;

    /**
     * 查询所有的检查组
     * @return
     */
    List<CheckGroup> findAll();
}
