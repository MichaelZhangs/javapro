package com.itheima.dao;

import com.itheima.domain.QueryVo;
import com.itheima.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.List;

public interface IUserDao {
    /**
     * 同时获取账户的信息
     * @return
     */
//    @Select("select * from user")
    List<User> findAll();

    /**
     *
     * 保存方法
     * @param user
     */
    void saveUser(User user);

    /**
     * 更新
     * @param user
     */
    void updateUser(User user);

    /**
     * 删除操作
     * @param userId
     */
    void deleteUser(Integer userId);

    /**
     * 查询一个
     */
    User findById(Integer userId);

    /**
     * 根据名称模糊查询
     */
    List<User> findByName(String username);

    /**
     * 查询总记录条数
     */
    int findTotal();

    /**
     * 根据queryVo 查询
     * @param username
     */
//    List<User> findByQueryVo(QueryVo username);
}