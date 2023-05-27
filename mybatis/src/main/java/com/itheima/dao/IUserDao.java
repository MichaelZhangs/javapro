package com.itheima.dao;

import com.itheima.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.net.IDN;
import java.util.List;

/**
 * 在mybatis中，CRUD一共有4个注解
 * @Select @Insert @Update @Delete
 */
public interface IUserDao {
    
    @Select("select * from user")
    @Results(id = "userMap",
            value = {
                    @Result(id = true, column = "id", property = "userId"),
                    @Result(column = "username", property = "userName"),
                    @Result(column = "address", property = "userAddress"),
                    @Result(column = "sex", property = "userSex"),
                    @Result(column = "birthday", property = "userBirthday"),
                    @Result(property = "accounts", column = "id" ,
                            many = @Many(select = "com.itheima.dao.IAccountDao.findAccountByUid",fetchType = FetchType.LAZY))
            }
    )
    List<User> findAll();

    @Insert("insert into user(username,address, sex, birthday)values(#{username}, #{address},#{sex},#{birthday})")
    void saveUser(User user);

    @Update("update user set username=#{username}, sex=#{sex}, birthday=#{birthday}, address=#{address} where id=#{id}")
    void updateUser(User user);

    @Delete("delete from user where id=#{id}")
    void deleteUser(Integer userId);

    @Select("select * from user where id=#{id}")
    @ResultMap(value = "userMap")
    User findById(Integer userId);



}
