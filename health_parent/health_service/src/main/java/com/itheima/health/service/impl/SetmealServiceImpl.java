package com.itheima.health.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Sean
 * @Date 2021/1/9 17:58
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        // 添加套餐
        setmealDao.add(setmeal);
        // 获取套餐的id
        Integer setmealId = setmeal.getId();
        // 遍历checkgroupIds数组
        if (checkgroupIds != null) {
            // 添加套餐与检查组的关系
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmealId,checkgroupId);
            }
        }
        // 事务控制
    }

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Setmeal> findByCondition(QueryPageBean queryPageBean) {
        // 插件分页
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        // 条件查询
        if (StringUtils.isNotEmpty(queryPageBean.getQueryString())) {
            // 模糊查询
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        Page<Setmeal> setmealPage = setmealDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<>(setmealPage.getTotal(),setmealPage.getResult());
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    /**
     * 查询选中的检查组id集合
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(int id) {
        return setmealDao.findCheckGroupIdsBySetmealId(id);
    }

    /**
     * 修改套餐
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        // 更新套餐
        setmealDao.update(setmeal);
        // 删除套餐与检查组的关系
        setmealDao.deleteSetmealCheckGroup(setmeal.getId());
        // 遍历添加新关系
        if (checkgroupIds != null) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmeal.getId(),checkgroupId);
            }
        }
        // 事务控制
    }

    /**
     * 根据id删除套餐
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(int id) {
        // 判断 是否被订单使用了
        int count = setmealDao.findCountBySetmealId(id);
        // 使用了，就报错，接口方法需要异常声明
        if (count > 0) {
            throw new MyException("该套餐被订单使用了不能删除！");
        }
        // 没使用，则要删除套餐与检查组的关系
        setmealDao.deleteSetmealCheckGroup(id);
        // 再删除套餐
        setmealDao.deleteById(id);
    }
}
