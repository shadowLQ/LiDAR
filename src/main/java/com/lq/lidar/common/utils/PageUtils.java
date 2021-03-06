package com.lq.lidar.common.utils;

import com.github.pagehelper.PageHelper;
import com.lq.lidar.common.core.page.PageDomain;
import com.lq.lidar.common.core.page.TableSupport;

/**
 * 分页工具类
 * 
 * @author LQ
 */
public class PageUtils extends PageHelper
{
    /**
     * 设置请求分页数据
     */
    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
//            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            Boolean reasonable = pageDomain.getReasonable();
            PageHelper.startPage(pageNum, pageSize).setReasonable(reasonable);
        }
    }
}
