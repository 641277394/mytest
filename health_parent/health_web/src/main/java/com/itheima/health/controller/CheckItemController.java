package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Sean
 * @Date 2021/1/5 17:52
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    /**
     * 订阅检查项服务
     */
    @Reference
    private CheckItemService checkItemService;

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll() {
        // 调用服务查询
        List<CheckItem> checkItemList = checkItemService.findAll();
        // 封装到result返回数据到页面
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemList);
    }

    /**
     * 添加检查项
     * @param checkItem
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        // 调用服务添加
        checkItemService.add(checkItem);
        // 返回操作的结果
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 检查项的分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        // 调用服务分页查询
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
    }

    /**
     * 通过id查询
     * 数据回显
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id) {
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);
    }

    /**
     * 修改检查项
     * @param checkItem
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem) {
        // 调用服务添加
        checkItemService.update(checkItem);
        // 返回操作的结果
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id) {
        checkItemService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
}
