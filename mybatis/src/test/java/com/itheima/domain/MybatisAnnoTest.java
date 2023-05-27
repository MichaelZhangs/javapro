package com.itheima.domain;

import com.itheima.dao.IUserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MybatisAnnoTest {

    public static void main(String[] args) throws IOException {
        // 获取字节输入流
        InputStream in  = Resources.getResourceAsStream("SqlMapConfig.xml");

        // 根据字节输入流构建SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);


        //根据SqlSessionFactory 生产一个SqlSession
        SqlSession sqlSession = factory.openSession();

        //执行Dao的方法
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<User> users = userDao.findAll();
        for (User user:users
             ) {
            System.out.println(user);
        }

//        System.out.println(new Date(1996,12,21));
        Date date = new Date();
        long time = date.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        System.out.println(format);
        System.out.println(new Date(time));
    }


}
