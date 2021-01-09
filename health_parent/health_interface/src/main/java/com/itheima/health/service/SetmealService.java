package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

/**
 * @Author Sean
 * @Date 2021/1/9 9:45
 */
public interface SetmealService {
    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 套餐的分页条件查询
     * @param queryPageBean
     * @return
     */
    PageResult<Setmeal> findByCondition(QueryPageBean queryPageBean);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    Setmeal findById(int id);

    /**
     * 查询选中的检查组id集合
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIdsBySetmealId(int id);

    /**
     * 修改套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void update(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 根据id删除套餐
     * @param id
     */
    void deleteById(int id) throws MyException;
}
