package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * @Author Sean
 * @Date 2021/1/6 8:17
 */
public interface CheckItemDao {
    /**
     * 查询所有
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 添加检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 检查项的分页查询
     * @param queryString
     * @return
     */
    Page<CheckItem> findByCondition(String queryString);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    CheckItem findById(int id);

    /**
     * 修改检查项
     * @param checkItem
     */
    void update(CheckItem checkItem);

    /**
     * 统计使用了这个检查项的个数
     * @param id
     * @return
     */
    int findCountByCheckItemId(int id);

    /**
     * 根据id删除检查项
     * @param id
     */
    void deleteById(int id);
}
