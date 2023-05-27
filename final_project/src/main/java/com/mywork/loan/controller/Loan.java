package com.mywork.loan.controller;

import com.mywork.loan.dao.UserDao;
import com.mywork.loan.dao.UserRecordDao;
import com.mywork.loan.dao.getDB;
import com.mywork.loan.entity.User;
import com.mywork.loan.entity.UserRecord;
import com.mywork.loan.entity.loanAmount;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 借贷 相关功能
 */
@RestController
public class Loan {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    // 获取对象， 保存到mysql
    getDB getDB = new getDB();
    UserRecordDao userRecordDao = getDB.getUserRecordDao();
    UserDao userDao = getDB.getUserDao();

    Logger logger = LoggerFactory.getLogger(Loan.class);

    public Loan() throws Exception {
    }

    /**
     * 借贷出金额的接口， 通过session判断是否登录
     *
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/getloan")
    public Map<Object, Object> getLoan( HttpServletRequest httpServletRequest) {
        logger.info("开始进行计算贷款。。。。。。");
        // 校验是否登录 状态下
        HttpSession session = httpServletRequest.getSession();
        String phone = (String) session.getAttribute("phone");
        System.out.println("phone = " + phone);
        Map<Object, Object> map = new HashMap<>();

        if (phone == null) {
            // 说明没有登录
            map.put("msg", "请先登录!");
            map.put("status", 200);
            map.put("user", null);
            return map;
        }
        User user = userDao.userInfo(phone);
        String edu = user.getEdu();
        String birthyear = user.getBirthday().substring(0, 4);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
        System.out.println(dateFormat);
        LocalDate now = LocalDate.now();
        System.out.println(dateFormat.format(now));
        String t = dateFormat.format(now).toString().substring(0, 4);

        int age = Integer.parseInt(t) - Integer.parseInt(birthyear);
        System.out.println("age = " + age);
        System.out.println("edu = " + edu);
        // 计算借贷金额
        KieServices kieServices = KieServices.Factory.get();

        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();

        KieSession kieSession = kieClasspathContainer.newKieSession();

        loanAmount amount = new loanAmount();
        System.out.println("amount = " + amount);
        amount.setAge(age);
        amount.setEdu(edu);

        kieSession.insert(amount);
        kieSession.fireAllRules();

        kieSession.dispose();

        // 计算能贷款的金额
        int total_amount = amount.getAmount();
        String info = amount.getInfo();
        map.put("amount", total_amount);
        map.put("user", user);
        map.put("status", 200);
        map.put("msg", info);

        return map;
    }

    @PostMapping("/submit")
    public Map<String, Object> submitLoan(@RequestBody Map<String, Object> loan_info, HttpServletRequest httpServletRequest){
        // 校验是否登录 状态下
        String  phone =(String)   loan_info.get("phone");
        int amount = (int) loan_info.get("amount");
        String username = (String) loan_info.get("username");
        // 获取提交时的时间
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        // 是否提交
        boolean isSubmit = (boolean) loan_info.get("isSubmit");

        Map<String, Object> map = new HashMap<>();
        if (isSubmit == false){
            // 用户没有提交 ， 直接取消
            map.put("status", 200);
            map.put("msg","用户退出");
            return map;
        }
        map.put("status", 200);
        map.put("msg", "恭喜你，借出 "+amount + "元");
        // 保存用户记录
        UserRecord userRecord = new UserRecord();
        userRecord.setCheck_status("0");
        userRecord.setAmount(amount);
        userRecord.setPhone(phone);
        userRecord.setCreate_date(timestamp);
        userRecord.setUsername(username);

        userRecordDao.saveUserRecord(userRecord);

        return map;

    }


}
//    public static void main(String[] args) {
//        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
//        System.out.println(dateFormat);
//        LocalDate now = LocalDate.now();
//        System.out.println(dateFormat.format(now));
//        String  t =  now.format(dateFormat).toString().substring(0,4);
//        int i = Integer.parseInt(t);
//        System.out.println(i-1996);
//    }
//}

