package com.example.demo.test.sqlsession.datasource1;

import com.example.demo.common.entity.User;

import java.util.List;

/**
 * 全国业务库的SqlSession
 *
 * @author zhangxubo
 * @date 2021/11/30 10:15 上午
 */
public interface DataSource1SqlSession {

    /**
     * 初始化sqlSession
     */
    void initSqlSession();


    /**
     * 执行sql
     */
    void flushStatements();


    /**
     * 提交
     */
    void commit();

    /**
     * rollback
     */
    void rollback();

    /**
     * 关闭sqlSession
     */
    void closeSqlSession();


    /**
     * 写入 user
     *
     * @param user
     */
    void insertUser(User user);


    /**
     * 批量写入 user
     *
     * @param users
     */
    void insertUserList(List<User> users);

}
