package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author Sean
 * @Date 2021/1/9 9:39
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    private static Logger log = LoggerFactory.getLogger(SetmealController.class);

    @Reference
    private SetmealService setmealService;

    /**
     * 上传图片
     * @param imgFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        // 获取源文件名
        String originalFilename = imgFile.getOriginalFilename();
        // 截取后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 生成唯一id
        String uniqueId = UUID.randomUUID().toString();
        // 拼接唯一文件名
        String fileName = uniqueId + suffix;
        // 调用七牛工具上传图片
        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(),fileName);
            // 构建返回的数据
            /*
            {返回的类型
            	imgName: 图片名 , 补全formData.img
                domain: 七牛的域名 图片回显imageUrl = domain+图片名
	        }
             */
            Map<String,String> map = new HashMap<>(2);
            map.put("imgName",fileName);
            map.put("domian",QiNiuUtils.DOMAIN);
            // 放到result里，再返回给页面
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,map);
        } catch (IOException e) {
            log.error("上传文件失败",e);
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds) {
        // 调用服务添加套餐
        setmealService.add(setmeal,checkgroupIds);
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Setmeal> setmealPageResult = setmealService.findByCondition(queryPageBean);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmealPageResult);
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id) {
        // 套餐信息
        Setmeal setmeal = setmealService.findById(id);
        // 构建前端需要的数据，还有域名
        Map<String, Object> map = new HashMap<>(2);
        map.put("setmeal",setmeal);
        map.put("domain",QiNiuUtils.DOMAIN);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,map);
    }

    /**
     * 查询选中的检查组id集合
     * @param id
     * @return
     */
    @GetMapping("/findCheckGroupIdsBySetmealId")
    public Result findCheckGroupIdsBySetmealId(int id) {
        List<Integer> checkgroupIds = setmealService.findCheckGroupIdsBySetmealId(id);
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,checkgroupIds);
    }

    /**
     * 修改套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupIds) {
        // 调用服务修改套餐
        setmealService.update(setmeal,checkgroupIds);
        return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
    }

    @PostMapping("/deleteById")
    public Result deleteById(int id) {
        setmealService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }
}
