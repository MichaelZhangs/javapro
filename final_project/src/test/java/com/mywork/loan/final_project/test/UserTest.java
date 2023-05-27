package com.mywork.loan.final_project.test;

import com.mywork.loan.dao.UserDao;
import com.mywork.loan.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.JdbcType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class UserTest {


    private InputStream in;
    private SqlSession sqlSession;
    private UserDao userDao;

    @Before // 用户测试方法执行之前
    public void init() throws Exception {
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
        userDao = sqlSession.getMapper(UserDao.class);
    }
    @After // 测试执行方法之后执行
    public void destroy() throws Exception {
//        sqlSession.commit();
        sqlSession.close();
        in.close();

    }
    @Test
    public void testSaveUser(){
        User user = new User();
        user.setUsername("张三");
        user.setPassword("123456");
        user.setPhone("12341234123");

        userDao.saveUser(user);
    }
    @Test
    public void testJudgePhone(){
      int t =   userDao.judgePhone("12341234112");
        System.out.println("t = " + t);
    }
    @Test
    public void testLogin(){
        User user = userDao.Login("12341235112", "123456");

        System.out.println("user = " + user);
    }
    @Test
    public void testUserInfo(){
        User user = userDao.userInfo("12341234111");
        System.out.println("user = " + user);
    }
    @Test
    public void testUpdateUser(){
        User user = new User();
        user.setInfo("穷啊");
        user.setEmail("abcd123@qq.com");
        user.setRole("5");
        user.setBirthday("19960101");
        user.setEdu("大专");
        user.setPhone("12341234111");
        user.setSex("F");
        System.out.println("sex = "+ user.getSex());
        int i = userDao.updateUser(user);
        System.out.println("user = " + i);
    }
}
