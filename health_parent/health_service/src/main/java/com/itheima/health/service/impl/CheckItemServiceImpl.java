package com.itheima.health.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Sean
 * @Date 2021/1/5 17:57
 * 使用alibaba的包
 * interfaceClass指定服务的接口类
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Resource
    private CheckItemDao checkItemDao;

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 检查项的分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        // 因为queryPageBean.getPageSize()不能无限大，做一个限制
        Integer pageSize = queryPageBean.getPageSize() >= 50 ? 50 : queryPageBean.getPageSize();

        // 使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(),pageSize);

        // 条件查询
        if (StringUtils.isNotEmpty(queryPageBean.getQueryString())) {
            // 有查询条件，模糊查询
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // page extends arrayList
        Page<CheckItem> page = checkItemDao.findByCondition(queryPageBean.getQueryString());
        PageResult<CheckItem> pageResult = new PageResult<CheckItem>(page.getTotal(),page.getResult());
        return pageResult;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }

    /**
     * 修改检查项
     * @param checkItem
     */
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    /**
     * 根据id删除检查项
     * @param id
     * @throws MyException
     */
    @Override
    public void deleteById(int id) {
        // 统计使用了这个检查项的个数
        int count = checkItemDao.findCountByCheckItemId(id);
        if (count > 0) {
            throw new MyException("该检查项被使用了，不能删除！");
        }

        // 如果没有说明可以删除
        checkItemDao.deleteById(id);
    }
}
