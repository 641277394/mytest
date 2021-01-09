package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Sean
 * @Date 2021/1/8 17:24
 */
public interface CheckGroupDao {

    /**
     * 添加检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 添加检查项与检查组的关系
     * @param checkGroupId
     * @param checkitemId
     */
    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId,@Param("checkitemId") Integer checkitemId);

    /**
     * 条件查询
     * @param queryString
     * @return
     */
    Page<CheckGroup> findByCondition(String queryString);

    /**
     * 根据id查询检查组
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 通过检查组id查询选中的检查项id
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    /**
     * 更新检查组
     * @param checkGroup
     */
    void update(CheckGroup checkGroup);

    /**
     * 删除旧关系
     * @param id
     */
    void deleteCheckGroupCheckItem(Integer id);

    /**
     * 通过检查组id查询是否被套餐使用了
     * @param id
     * @return
     */
    int findCountByCheckGroupId(int id);

    /**
     * 根据检查组id删除检查组
     * @param id
     */
    void deleteById(int id);

    /**
     * 查询所有的检查组
     * @return
     */
    List<CheckGroup> findAll();
}
