package com.andy.treebuild.util;

import com.andy.treebuild.util.base.AbstractTree;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Description: 构建树状结构工具类
 * Author: Andy.wang
 * Date: 2019/10/30 16:02
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
public class TreeBuildTools<T extends AbstractTree> {
    /**
     * 根节点对象集合
     */
    private List<T> rootList;

    /**
     * 所有节点集合（可以包含根节点，也可以不包含）
     */
    private List<T> bodyList;

    /**
     * 调用方法入口
     *
     * @return
     */
    public List<T> build() {
        if (bodyList != null && !bodyList.isEmpty()) {

            //声明一个map，用来过滤已操作过的数据
            Map<String, String> map = Maps.newHashMapWithExpectedSize(bodyList.size());

            // 循环根节点集合，传递父节点和一个空map
            rootList.forEach(beanTree -> getChild(beanTree, map));
            return rootList;
        }
        return null;
    }

    /**
     * 递归构建 （深层）子节点
     *
     * @param beanTree 父节点
     * @param map      过滤元素的Map
     */
    public void getChild(T beanTree, Map<String, String> map) {
        List<T> childList = Lists.newArrayList();
        bodyList.stream()
                .filter(element -> !map.containsKey(element.getId())) //过滤掉 map内不包含子节点的 元素
                .filter(element -> element.getParentId().equals(beanTree.getId())) //过滤掉 子节点的parentId等于根节点的id
                .forEach(element -> {
                    map.put(element.getId(), element.getParentId());//当前节点code和父节点id

                    getChild(element, map);//递归调用

                    childList.add(element);
                });
        beanTree.setChildren(childList);
    }

}

