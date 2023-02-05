package com.itheima;

import com.itheima.dao.IAccountDao;
import com.itheima.dao.IUserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.Account;
import com.itheima.domain.AccountUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
public class AccountTest {
    private InputStream in;
    private SqlSession sqlSession;
    private IAccountDao accountDao;

    @Before // 用户测试方法执行之前
    public void init() throws Exception{
        // 1. 读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 2.创建SqlSessionFactory 工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory =  builder.build(in);
        sqlSession = factory.openSession(true);
        accountDao = sqlSession.getMapper(IAccountDao.class);

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
        List<Account>  accounts = accountDao.findAll();
        for (Account account: accounts) {
            System.out.println(account);
            System.out.println(account.getUser());
        }

    }
    @Test
    public void testFindAccount(){
        List<AccountUser> accountUsers = accountDao.findAllAccount();
        for (AccountUser accountUser: accountUsers){
            System.out.println(accountUser);
            System.out.println(accountUser.getUsername() + accountUser.getMoney());

        }
    }

}
