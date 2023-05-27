package com.mywork.loan.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.JdbcType;

import java.io.InputStream;

public class getDB {
    private InputStream in;
    private SqlSession sqlSession;
    private UserDao userDao;
    private LoanDao loanDao;
    private UserRecordDao userRecordDao;

    public SqlSession init() throws Exception {
        // 1. 读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 2.创建SqlSessionFactory 工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //定义Impl来实现
//        userDao =  new UserDaoImpl(factory);


        // 3.使用工厂生产sqlsession 对象
        sqlSession = factory.openSession(true);
        sqlSession.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
        // 4.使用SqlSession创建Dao接口的代理对象
//        userDao = sqlSession.getMapper(UserDao.class);
//        return userDao;
        return sqlSession;
    }
    public void destroy() throws Exception {
        sqlSession.commit();
//        sqlSession.close();
        in.close();

    }

    public UserDao getUserDao() throws Exception {
        getDB getDB = new getDB();
        SqlSession sqlSession1 = getDB.init();
        userDao = sqlSession1.getMapper(UserDao.class);
        return userDao;
    }

    public LoanDao getLoanDao() throws  Exception {
        getDB getDB = new getDB();
        SqlSession sqlSession1 = getDB.init();
        loanDao = sqlSession1.getMapper(LoanDao.class);
        return loanDao;
    }
    public UserRecordDao  getUserRecordDao() throws  Exception{
        getDB getDB = new getDB();
        SqlSession sqlSession1 = getDB.init();
        userRecordDao = sqlSession1.getMapper(UserRecordDao.class);
        return userRecordDao;
    }

}