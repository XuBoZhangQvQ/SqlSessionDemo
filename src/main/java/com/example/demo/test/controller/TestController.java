package com.example.demo.test.controller;

import com.example.demo.common.entity.User;
import com.example.demo.test.sqlsession.datasource1.DataSource1SqlSession;
import com.example.demo.test.sqlsession.datasource2.DataSource2SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @author ZhangXuBo
 * @date 2023/6/7 16:23
 */
@RestController
public class TestController {
    @Autowired
    DataSource1SqlSession dataSource1SqlSession;


    @Autowired
    DataSource2SqlSession dataSource2SqlSession;

    /**
     * 两个数据源,批量新增提交
     * @return
     */
    @GetMapping("/test")
    public String testBatchInsert() {

        try {
            //初始化数据源
            dataSource1SqlSession.initSqlSession();
            dataSource2SqlSession.initSqlSession();

            ArrayList<User> users = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                User user = new User().setName("name" + i);
                users.add(user);
            }
            dataSource1SqlSession.insertUserList(users);
            dataSource2SqlSession.insertUserList(users);
            //预处理 sql,如果有一个数据源数据写入失败,报错,则进入catch全部回滚
            //简单处理分布式事务
            dataSource1SqlSession.flushStatements();
            dataSource2SqlSession.flushStatements();
            //提交事务
            dataSource1SqlSession.commit();
            dataSource2SqlSession.commit();
            return "true";

        } catch (Exception e) {
            dataSource1SqlSession.rollback();
            dataSource2SqlSession.rollback();
            return "false";
        } finally {
            dataSource1SqlSession.closeSqlSession();
            dataSource2SqlSession.closeSqlSession();
        }

    }
}
