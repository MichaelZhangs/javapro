package com.itheima;

import com.itheima.dao.IRoleDao;
import com.itheima.dao.IUserDao;
import com.itheima.domain.Role;
import com.itheima.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class RoleTest {
    private InputStream in;
    private SqlSession sqlSession;
    private IRoleDao roleDao;

    @Before // 用户测试方法执行之前
    public void init() throws Exception{
        // 1. 读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 2.创建SqlSessionFactory 工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory =  builder.build(in);
        sqlSession = factory.openSession(true);
        roleDao = sqlSession.getMapper(IRoleDao.class);

//        // 3.使用工厂生产sqlsession 对象
//        sqlSession =  factory.openSession();
//        // 4.使用SqlSession创建Dao接口的代理对象
//        userDao = sqlSession.getMapper(IUserDao.class);
    }
    @After // 测试执行方法之后执行
    public void destroy() throws Exception{
//        sqlSession.commit();
        sqlSession.close();
        in.close();

    }

    @Test
    public void testFindAll(){
        List<Role>  roles = roleDao.findAll();
        for (Role  role: roles) {
            System.out.println(role);
            System.out.println(role.getUsers());
        }

    }


}

