package com.htphy.wx.common.util.page;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
/**
 * Author : zfk
 * Data : 20:24
 */

@Getter
@Setter
public class PageResult {

    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;
    /**
     * 记录总数
     */
    private long totalSize;
    /**
     * 页码总数
     */
    private int totalPages;
    /**
     * 数据模型
     */
    private List<?> content;
}
