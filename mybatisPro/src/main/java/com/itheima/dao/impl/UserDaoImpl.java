package com.itheima.dao.impl;

import com.itheima.dao.IUserDao;
import com.itheima.domain.QueryVo;
import com.itheima.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class UserDaoImpl  implements IUserDao {

    private SqlSessionFactory factory;
    public UserDaoImpl(SqlSessionFactory factory){
        this.factory = factory;
    }
    @Override
    public List<User> findAll() {

        // 根据factory 获取sqlSession对象
        SqlSession session = factory.openSession(true);
        List<User>  users = session.selectList("com.itheima.dao.IUserDao.findAll");

        return users;
    }

    @Override
    public void saveUser(User user) {
        SqlSession session = factory.openSession();
        int insert = session.insert("com.itheima.dao.IUserDao.saveUser", user);
        System.out.println("insert =  " + insert);
        // 提交事务
        session.commit();
        session.close();
    }

    @Override
    public void updateUser(User user) {
        SqlSession session = factory.openSession(true);
        int insert = session.update("com.itheima.dao.IUserDao.updateUser", user);
        System.out.println("insert =  " + insert);
        // 提交事务
//        session.commit();
        session.close();
    }

    @Override
    public void deleteUser(Integer userId) {
        SqlSession session = factory.openSession();
        session.delete("com.itheima.dao.IUserDao.deleteUser", userId);

        // 提交事务
        session.commit();
        session.close();
    }

    @Override
    public User findById(Integer userId) {
        SqlSession session = factory.openSession();
        User user = session.selectOne("com.itheima.dao.IUserDao.findById", userId);
        session.close();

        return user;
    }

    @Override
    public List<User> findByName(String username) {
        SqlSession session = factory.openSession();
        List<User>  users = session.selectList("com.itheima.dao.IUserDao.findByName", username);

        session.close();
        return users;

    }

    @Override
    public int findTotal() {

        SqlSession session = factory.openSession();
        int total = session.selectOne("com.itheima.dao.IUserDao.findTotal");

        session.close();

        return total;
    }

//    @Override
//    public List<User> findByQueryVo(QueryVo username) {
//        return null;
//    }

}
