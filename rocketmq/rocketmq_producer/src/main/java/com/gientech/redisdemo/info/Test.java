package com.gientech.redisdemo.info;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class Test {

   public static void main(String[] args) {
       UserInfo u = new UserInfo("1", "张三", "16322221111");
       System.out.println(u);
       String s = JSON.toJSONString(u);
       System.out.println(s);

       JSONObject  jsonObject = new JSONObject().parseObject(s);
       System.out.println(jsonObject);
       Object id = jsonObject.get("id");
       Object mobile = jsonObject.get("mobile");
       System.out.println(id+""+ mobile);

   }
}
