package com.itheima.health.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Sean
 * @Date 2021/1/8 17:01
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 添加检查组
        checkGroupDao.add(checkGroup);
        // 获取检查组的id
        Integer checkGroupId = checkGroup.getId();
        // 遍历选中的检查组id的数组
        if (checkitemIds != null) {
            for (Integer checkitemId : checkitemIds) {
                // 添加检查组与检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkGroupId,checkitemId);
            }
        }
        // 添加事务控制
    }

    /**
     * 检查组的分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
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
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        PageResult<CheckGroup> pageResult = new PageResult<CheckGroup>(page.getTotal(),page.getResult());
        return pageResult;
    }

    /**
     * 根据id查询检查组
     * @param id
     * @return
     */
    @Override
    public CheckGroup findBuId(int id) {
        return checkGroupDao.findById(id);
    }

    /**
     * 通过检查组id查询选中的检查项id
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    /**
     * 修改检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 更新检查组
        checkGroupDao.update(checkGroup);
        // 删除旧关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());
        // 遍历选中的集合
        if (checkitemIds != null) {
            for (Integer checkitemId : checkitemIds) {
                // 添加检查组与检查项的新关系
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(),checkitemId);
            }
        }
        // 添加事务控制
    }

    /**
     * 根据id删除检查组
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(int id) {
        // 通过检查组id查询是否被套餐使用了
        int count = checkGroupDao.findCountByCheckGroupId(id);
        // 使用了则抛出异常
        if (count > 0) {
            throw new MyException("该检查组被订单使用了，不能删除");
        }
        // 没使用，就先删除检查组与检查项的关系
        checkGroupDao.deleteCheckGroupCheckItem(id);
        // 最后删除检查组
        checkGroupDao.deleteById(id);
        // 事务控制
    }

    /**
     * 查询所有的检查组
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
