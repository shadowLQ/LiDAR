package com.lq.lidar.controller;


import com.github.pagehelper.PageInfo;
import com.lq.lidar.common.annotation.Log;
import com.lq.lidar.common.annotation.TaskTime;
import com.lq.lidar.common.core.controller.BaseController;
import com.lq.lidar.common.core.domain.ResponseEntity;
import com.lq.lidar.common.enums.BusinessType;
import com.lq.lidar.common.enums.StatusCode;
import com.lq.lidar.common.utils.DataConvert;
import com.lq.lidar.domain.entity.CbaySysUser;
import com.lq.lidar.service.ICbaySysUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户管理前端控制器
 * </p>
 *
 * @author LQ
 * @since 2022-02-28
 */
@RestController
@RequestMapping("/system/cbaySysUser")
public class CbaySysUserController extends BaseController {

    @Resource
    ICbaySysUserService iCbaySysUserService;

    /**
     * 用户管理列表
     *
     * @param sysUser
     * @return
     */
    @GetMapping("/list")
    @TaskTime
    public ResponseEntity list(CbaySysUser sysUser) {
        startPage();
        PageInfo<CbaySysUser> pageInfo = iCbaySysUserService.list(sysUser);
        return ResponseEntity.success(getDataTable(pageInfo));
    }

    /**
     * 添加用户
     *
     * @param sysUser 用户
     */
    @PostMapping("/saveOrUpdate")
    @Log(title = "用户管理",businessType = BusinessType.INSERT)
    public ResponseEntity saveOrUpdate(@RequestBody @Validated CbaySysUser sysUser) {
        boolean save = iCbaySysUserService.saveOrUpdate(sysUser);
        if (save) {
            return ResponseEntity.success("操作成功");
        }
        return ResponseEntity.error("操作失败");
    }

    /**
     * 通过userId获取用户信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/getByUserId/{userId}")
    @TaskTime
    public ResponseEntity getByUserId(@PathVariable("userId") String userId) {
        logger.info("通过userId获取用户信息:{}",userId);
        return ResponseEntity.success(iCbaySysUserService.getByUserId(userId));
    }

    /**
     * 用户启用禁用
     *
     * @param sysUser
     * @return
     */
    @PostMapping("/updateStatus")
    public ResponseEntity updateStatus(@RequestBody CbaySysUser sysUser) {
        boolean update = iCbaySysUserService.updateById(sysUser);
        if (update) {
            return ResponseEntity.success("操作成功");
        }
        return ResponseEntity.error("操作失败");
    }

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity delete(@PathVariable String userId) {
        boolean update = iCbaySysUserService.removeById(userId);
        if (update) {
            return ResponseEntity.success("删除成功");
        }
        return ResponseEntity.error("删除失败");
    }

    @GetMapping("/getUsers")
    @TaskTime
    public ResponseEntity getUsers() {
        List<CbaySysUser> sysUsers = iCbaySysUserService.lambdaQuery().eq(CbaySysUser::getValidInd, StatusCode.VALID_IND_YES.getCode()).list();
        List list = DataConvert.ListToLV(sysUsers, CbaySysUser::getUserNm, CbaySysUser::getUserId);
        return ResponseEntity.success(list);

    }

}

