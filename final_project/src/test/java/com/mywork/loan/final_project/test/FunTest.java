package com.mywork.loan.final_project.test;

import com.mywork.loan.dao.UserRecordDao;
import com.mywork.loan.entity.UserRecord;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.JdbcType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;

public class FunTest {


    private InputStream in;
    private SqlSession sqlSession;
    private UserRecordDao userRecordDao;

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
        userRecordDao = sqlSession.getMapper(UserRecordDao.class);
    }
    @After // 测试执行方法之后执行
    public void destroy() throws Exception {
//        sqlSession.commit();
        sqlSession.close();
        in.close();

    }


    @Test
    public  void testDate(){
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();

        Date date = new Date();
        Timestamp nousedate = new Timestamp(date.getTime());
        System.out.println("nousedate = " + nousedate);

//        DateTime now1 = DateTime.now();
//        System.out.println("now 1 = " + now1);
//        System.out.println("now = " + now);
//        String date = formatter.format(now);
////        System.out.println(date);
//        Timestamp timestamp = Timestamp.valueOf(date);
//        System.out.println("timestamp = " + timestamp);
        UserRecord userRecord = new UserRecord();

        String phone = "12341234111";
        String username = "Jack";
        String check = "0";
        int amount = 100;
        userRecord.setCreate_date(nousedate);
        userRecord.setUsername(username);
        userRecord.setPhone(phone);
        userRecord.setCheck_status(check);
        userRecord.setAmount(amount);

        userRecordDao.saveUserRecord(userRecord);
    }
}
