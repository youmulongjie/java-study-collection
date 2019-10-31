package com.andy.treebuild.util.base;

import lombok.Data;

import java.util.List;

/**
 * Description: 树状结构 父类，具体结构要继承该父类
 * Author: Andy.wang
 * Date: 2019/10/30 15:58
 */
@Data
public class AbstractTree<T> {

    /**
     * 记录ID（特别注意：如果你的 entity 中 id 和 vo 中 id 不一致，需自己处理下赋值）
     */
    protected String id;

    /**
     * 父级ID（特别注意：如果你的 entity 中 parentId 和 vo 中 parentId 不一致，需自己处理下赋值）
     */
    protected String parentId;

    /**
     * 子节点 集合
     */
    protected List<T> children;
}
