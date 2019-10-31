package com.andy.treebuild.util;

import com.andy.treebuild.util.base.AbstractTree;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description: 区划管理 树状结构 VO
 * Author: Andy.wang
 * Date: 2019/10/30 13:36
 */
@Data
@NoArgsConstructor
public class AreaMenuVO extends AbstractTree<AreaMenuVO> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 区域代码
     */
    private String areaCode;

    /**
     * 区域名称
     */
    private String areaName;

}
