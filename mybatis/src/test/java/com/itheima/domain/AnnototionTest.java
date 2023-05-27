package com.itheima.domain;

import com.itheima.dao.IUserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;


public class AnnototionTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private IUserDao userDao;

    @Before
    public void init() throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        session = factory.openSession();
        userDao =  session.getMapper(IUserDao.class);
    }
    @After
    public void destroy() throws Exception{
        session.commit();
        session.close();
        in.close();
    }

//    @Test
//    public void testSave(){
//        User user = new User();
//        user.setUsername("Michael");
//        user.setAddress("Tokyo");
//        user.setSex("F");
//        user.setBirthday( new Date(19970921));
//        userDao.saveUser(user);
//
//    }
//    @Test
//    public void testUpdate(){
//        User user = new User();
//        user.setUsername("Michael3");
//        user.setAddress("东京");
//        user.setSex("男");
//        user.setBirthday( new Date(19981221));
//        user.setId(60);
//        userDao.updateUser(user);
//
//    }
    @Test
    public void testDelete(){

        userDao.deleteUser(58);

    }
    @Test
    public void testFindById() {

        User user =  userDao.findById(61);
        System.out.println(user);
//        System.out.println(user.getUsername());
    }
    @Test
    public void  testFindAll() {
        List<User> users = userDao.findAll();
        for (User user:users
             ) {
            System.out.println(user.toString());
        }
    }
}
