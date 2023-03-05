package com.itheima;

import com.itheima.dao.IUserDao;
import com.itheima.domain.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class MybatisTest {

    private InputStream in;
    private SqlSession sqlSession;
    private IUserDao userDao;

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
        // 4.使用SqlSession创建Dao接口的代理对象
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @After // 测试执行方法之后执行
    public void destroy() throws Exception {
//        sqlSession.commit();
//        sqlSession.close();
        in.close();

    }

    @Test
    public void testfindAll() {

        // 5. 使用代理对象执行方法
        List<User> users = userDao.findAll();

        // 6.释放资源
        for (User user : users) {
            System.out.println(user);
        }

    }

    /**
     * 测试保存操作
     */
    @Test
    public void testSave() {
        User user = new User();
        user.setUsername("Lily");
        user.setAddress("大湾区");
        user.setSex("女");
        user.setBirthday(new Date());

        userDao.saveUser(user);
    }

    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(55);
        user.setUsername("Lisa Liu");
        user.setAddress("England");
        user.setSex("女");
        user.setBirthday(new Date(19951215));
        System.out.println("user = " + user);
        userDao.updateUser(user);

    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        userDao.deleteUser(58);

    }

    /**
     * 根据Id查询
     */
    @Test
    public void testFindById() {
        User user = userDao.findById(55);
        System.out.println("user = " + user);
    }

    /**
     * 根据名称模糊查询
     */
    @Test
    public void testFindByName() {
        List<User> users = userDao.findByName("%王%");
//        List<User> users = userDao.findByName("王");

        for (User user : users) {
            System.out.println("udser = " + user);
        }
    }


    @Test
    public void testFindTotal() {
        int total = userDao.findTotal();
        System.out.println("total = " + total);

    }
}

//    @Test
//    public void testFindByQueryVo(){
//        QueryVo queryVo = new QueryVo();
//        User user = new User();
//        user.setUsername("%王%");
//        queryVo.setUser(user);
//        List<User>  users = userDao.findByQueryVo(queryVo);
//
//        for (User u: users) {
//            System.out.println("user = " + u);
//        }
//    }
