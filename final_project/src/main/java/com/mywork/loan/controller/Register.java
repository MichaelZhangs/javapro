package com.mywork.loan.controller;

import com.mywork.loan.dao.UserDao;
import com.mywork.loan.dao.getDB;
import com.mywork.loan.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class Register {

    @Resource
    StringRedisTemplate stringRedisTemplate;
    // 获取对象， 保存到mysql
    getDB getDB = new getDB();
    UserDao userDao = getDB.getUserDao();

    Logger logger = LoggerFactory.getLogger(Register.class);

    public Register() throws Exception {
    }

    /**
     *注册
     * @param User
     * @return
     */
    @PostMapping("/user/register")
    @ResponseBody
    public Map<String , Object> registerUser(@RequestBody Map<String , Object> User, HttpServletRequest httpServletRequest) throws Exception {
        System.out.println("user : " + User);

        String name = (String) User.get("username");
        String pwd = (String) User.get("password");
        String phone = (String) User.get("phone") ;
        logger.info("用户: "+phone +"  注册");
        User user = new User();
        user.setUsername(name);
        user.setPassword(pwd);
        user.setPhone(phone);

        Map<String , Object> map = new HashMap<>();


        // 判断是否存在已经注册
        int i = userDao.judgePhone(phone);
        if (i >0   ){
            map.put("user", user);
            map.put("status", 200);
            map.put("msg", "用户已经注册");
            return map;
        }

        System.out.println("userDao =" + userDao);

        System.out.println("user_2 : " + user);
        userDao.saveUser(user);

        // 设置session
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("phone", phone);
        session.setMaxInactiveInterval(600);

        // 保存到redis
        stringRedisTemplate.opsForValue().set(phone, pwd);
        stringRedisTemplate.expire(phone, 600, TimeUnit.SECONDS);

        // 返回json 成功

        map.put("user", user);
        map.put("status", 200);
        map.put("msg", "注册成功");


        return map;
    }

    /**
     * 登录
     *
     * @param user_dict
     * @return 接受参数 phone, password
     */
    @PostMapping("/login")
    public Map<Object, Object> Login(@RequestBody Map user_dict , HttpServletRequest httpServletRequest){
        String phone = (String) user_dict.get("phone");
        String password = (String) user_dict.get("password");
        System.out.println("phone = "+phone + "==== " + "password = " + password);

        Map<Object, Object> map = new HashMap<>();

        User user = userDao.Login(phone, password);
        // 为Null说明输入的密码或电话号码错误， 登录失败
        if (user == null){
            map.put("msg", "登录失败, 用户名或密码错误");
            map.put("status", 200);
            map.put("user", null);
            return map;
        }
        // 说明登录成功
        //写入缓存，session
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("phone", phone);
        session.setMaxInactiveInterval(600);

        // 写入redis
        stringRedisTemplate.opsForValue().set(phone, password);
        stringRedisTemplate.expire(phone, 600, TimeUnit.SECONDS);
        // 返回值
        map.put("msg", "登录成功");
        map.put("status", 200);
        map.put("user", user);

        return map;
    }

    /**
     * 借贷前更新个人信息， 否则不予 借贷, 是已经登录状态才可用
     *
     * @param user_dict
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/user/update")
    public Map<Object, Object> updateUser(@RequestBody Map user_dict, HttpServletRequest httpServletRequest){
        // 校验是否登录 状态下
        HttpSession session = httpServletRequest.getSession();
        String phone = (String) session.getAttribute("phone");
        System.out.println("phone = "+ phone);
        Map<Object, Object> map = new HashMap<>();
        if (phone == null){
            // 说明没有登录
            map.put("msg", "请先登录!");
            map.put("status", 200);
            map.put("user", null);
            return map;
        }
        // 说明已经在登录
        User user = userDao.userInfo(phone);
        // 判断用户信息是否完善
        String email = user.getEmail();
        String edu = user.getEdu();
        String sex = user.getSex();
        String birthday = user.getBirthday();
        if (email == null && edu== null && sex==null && birthday == null){
            // 说明信息不完善， 需要完善信息
            String edu_1 = (String) user_dict.get("edu");
            String birthday_1 = (String) user_dict.get("birthday");
            String sex_1 = (String) user_dict.get("sex");
            String email_1 = (String) user_dict.get("email");
            String info = (String) user_dict.get("info");
            String username = (String) user_dict.get("username");


            user.setUsername(username);
            user.setEdu(edu_1);
            user.setBirthday(birthday_1);
            user.setSex(sex_1);
            user.setEmail(email_1);
            user.setInfo(info);
            user.setPhone(phone);
            user.setRole("5"); //在这里的都是普通用户
            userDao.updateUser(user);

        }
        map.put("user", user);
        map.put("status", 200);
        map.put("msg", "信息更新成功");
        return map;

    }



    @GetMapping(value="/test/user")
    public String sayHello(  @RequestParam("phone") String phone,
                           HttpServletRequest httpServletRequest) {
        System.out.println("phone = " + phone);
        HttpSession session = httpServletRequest.getSession();
        String name = (String) session.getAttribute(phone);

        System.out.println("姓名是 ： name = " + name);
        String pwd = stringRedisTemplate.opsForValue().get(phone);
        System.out.println("密码是: pwd = " + pwd);
        return "Hello world";
    }
}
