package com.example.demo.test.sqlsession.datasource1.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.demo.common.entity.User;
import com.example.demo.common.mapper.UserMapper;
import com.example.demo.test.sqlsession.datasource1.DataSource1SqlSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * 全国业务库的SqlSession的实现类
 *
 * @author zhangxubo
 * @date 2021/12/14 5:07 PM
 */
@Service
@DS("datasource1")
@Slf4j
public class DataSource1SqlSessionImpl implements DataSource1SqlSession {


    final SqlSessionTemplate sqlSessionTemplate;

    private volatile SqlSession sqlSession = null;

    /**
     * 使用构造方法注入
     */
    public DataSource1SqlSessionImpl(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    private SqlSession createNewSqlSession() {
        return sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
    }

    @Override
    @PostConstruct
    public synchronized void initSqlSession() {
        try {
            if (ObjectUtil.isNotNull(sqlSession) && sqlSession.getConnection().isValid(1)) {
                return;
            }
        } catch (Exception e) {
            log.warn("sqlSession连接已失效,重新创建");
        }
        sqlSession = createNewSqlSession();
    }

    @Override
    @PreDestroy
    public void closeSqlSession() {
        if (ObjectUtil.isNotNull(sqlSession)) {
            this.sqlSession.close();
            this.sqlSession = null;
        }
    }


    @Override
    public void flushStatements() {
        sqlSession.flushStatements();
    }


    @Override
    public void commit() {
        sqlSession.commit();
        sqlSession.clearCache();
    }

    @Override
    public void rollback() {
        sqlSession.rollback();
        sqlSession.clearCache();
    }


    @Override
    public void insertUser(User user) {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.insert(user);
    }

    @Override
    public void insertUserList(List<User> users) {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        for (User user : users) {
            userMapper.insert(user);
        }
    }


}
