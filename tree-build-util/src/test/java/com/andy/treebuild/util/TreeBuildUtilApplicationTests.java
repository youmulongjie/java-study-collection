package com.andy.treebuild.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TreeBuildUtilApplicationTests {

    // 测试
    @Test
    public void contextLoads() {
        List<AreaMenuVO> rootList = rootList();
        List<AreaMenuVO> childList = chileList();
        TreeBuildTools treeBuildTools = new TreeBuildTools(rootList, childList);

        List<AreaMenuVO> tree = treeBuildTools.build();


        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(tree);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    // 模拟 构建根节点集合
    private List<AreaMenuVO> rootList(){
        String rootId = "0";
        List<AreaMenuVO> rootList = Lists.newArrayListWithExpectedSize(3);

        AreaMenuVO vo = new AreaMenuVO();
        vo.setId("1001");
        vo.setParentId(rootId);
        vo.setAreaName("北京");
        vo.setAreaCode("10000");
        rootList.add(vo);

        vo = new AreaMenuVO();
        vo.setId("1002");
        vo.setParentId(rootId);
        vo.setAreaName("河南");
        vo.setAreaCode("10002");
        rootList.add(vo);

        return rootList;
    }

    // 模拟构建 所有节点集合
    private List<AreaMenuVO> chileList(){
        List<AreaMenuVO> chileList = Lists.newArrayListWithExpectedSize(10);
        // 北京
        String beijingId = "1001";

        AreaMenuVO vo = new AreaMenuVO();
        vo.setId("10011");
        vo.setParentId(beijingId);
        vo.setAreaName("房山");
        vo.setAreaCode("10011");
        chileList.add(vo);

        vo = new AreaMenuVO();
        vo.setId("10012");
        vo.setParentId(beijingId);
        vo.setAreaName("丰台");
        vo.setAreaCode("10012");
        chileList.add(vo);

        vo = new AreaMenuVO();
        vo.setId("10013");
        vo.setParentId(beijingId);
        vo.setAreaName("海淀");
        vo.setAreaCode("10013");
        chileList.add(vo);

        // 河南
        String henanId = "1002";

        vo = new AreaMenuVO();
        vo.setId("10021");
        vo.setParentId(henanId);
        vo.setAreaName("郑州");
        vo.setAreaCode("10021");
        chileList.add(vo);

        vo = new AreaMenuVO();
        vo.setId("10022");
        vo.setParentId(henanId);
        vo.setAreaName("平顶山");
        vo.setAreaCode("10022");
        chileList.add(vo);

        // 河南 - 平顶山
        String pingdingshanId = "10022";
        vo = new AreaMenuVO();
        vo.setId("100222");
        vo.setParentId(pingdingshanId);
        vo.setAreaName("汝州");
        vo.setAreaCode("100222");
        chileList.add(vo);

        vo = new AreaMenuVO();
        vo.setId("100223");
        vo.setParentId(pingdingshanId);
        vo.setAreaName("鲁山");
        vo.setAreaCode("100223");
        chileList.add(vo);

        vo = new AreaMenuVO();
        vo.setId("10023");
        vo.setParentId(henanId);
        vo.setAreaName("驻马店");
        vo.setAreaCode("10023");
        chileList.add(vo);

        return chileList;
    }


    // 以下是实际开发中的例子（区域管理 左边树状结构 菜单）

    /*public List<BaseAreaMenuVO> menu() {
        // 构建 根节点集合
        List<BaseAreaVO> rootMenu = (List<BaseAreaVO>) sonList("0", 0, 100).getData();
        List<BaseAreaMenuVO> rootList = Lists.newArrayListWithExpectedSize(rootMenu.size());
        rootMenu.forEach(root -> {
            BaseAreaMenuVO vo = new BaseAreaMenuVO();
            BeanUtils.copyProperties(root, vo);

            // 特别注意的地方（对象copy时，属性名称不一致需自己自己处理）
            vo.setId(root.getAreaid());

            rootList.add(vo);
        });

        // 构建 所有节点集合
        List<BaseAreaEntity> allEntitys = baseAreaRepository.queryAllByDeletemark(DeleteMarkEnum.EFFECTIVE.getIndex());
        List<BaseAreaMenuVO> bodyList = Lists.newArrayListWithExpectedSize(rootMenu.size());
        allEntitys.forEach(entity -> {
            BaseAreaMenuVO vo = new BaseAreaMenuVO();
            BeanUtils.copyProperties(entity, vo);

            // 特别注意的地方（对象copy时，属性名称不一致需自己自己处理）
            vo.setId(entity.getAreaid());

            bodyList.add(vo);
        });

        TreeTools treeTools = new TreeTools(rootList, bodyList);
        return treeTools.getTree();
    }*/

}
