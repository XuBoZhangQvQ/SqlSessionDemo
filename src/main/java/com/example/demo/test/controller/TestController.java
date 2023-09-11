package com.example.demo.test.controller;

import com.example.demo.common.entity.User;
import com.example.demo.common.mapper.UserMapper;
import com.example.demo.test.sqlsession.datasource1.DataSource1SqlSession;
import com.example.demo.test.sqlsession.datasource2.DataSource2SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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

    @Autowired
    UserMapper userMapper;

    /**
     * 两个数据源,批量新增提交
     *
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

    /**
     * 两个数据源,批量新增提交
     *
     * @return
     */
    @GetMapping("/testTransactional")
    @Transactional
    public String testTransactionalBatchInsert() {

        try {
            //初始化数据源
            dataSource1SqlSession.initSqlSession();
            dataSource2SqlSession.initSqlSession();

            ArrayList<User> users1 = new ArrayList<>();
            ArrayList<User> users2 = new ArrayList<>();
            users1.add(new User().setId(BigDecimal.valueOf(1)).setName("张三"));
            users1.add(new User().setId(BigDecimal.valueOf(2)).setName("李四"));
            users2.add(new User().setId(BigDecimal.valueOf(1)).setName("张三"));
            users2.add(new User().setId(BigDecimal.valueOf(1)).setName("李四"));
            dataSource1SqlSession.insertUserList(users1);
            dataSource2SqlSession.insertUserList(users2);
            //提交事务
            dataSource1SqlSession.commit();
            dataSource2SqlSession.commit();
            return "true";
        } finally {
            dataSource1SqlSession.closeSqlSession();
            dataSource2SqlSession.closeSqlSession();
        }

    }


    /**
     * 两个数据源,批量新增提交
     *
     * @return
     */
    @GetMapping("/test1")
    public String test1() {

        return userMapper.selectCustome();

    }


}
