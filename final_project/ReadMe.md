注册接口： /user/register
params:
`{
    "username": "Tom",
    "password": "123456",
    "phone": "12341234112"
}`
`return:
{
"msg": "用户已经注册",
"user": {
"id": null,
"username": "Jack",
"phone": "12341234111",
"sex": null,
"password": "123456",
"birthday": null,
"edu": null,
"role": null,
"info": null,
"email": null
},
"status": 200
}
`
登录接口: /login
params:
`{
    "password": "123456",
    "phone": "12341234112"
}`
return:
`{
"msg": "登录成功",
"user": {
"id": 17,
"username": "Jack",
"phone": "12341234111",
"sex": "F",
"password": "123456",
"birthday": "19960101",
"edu": "大专",
"role": "5",
"info": "穷啊",
"email": "abcd123@qq.com"
},
"status": 200
}`


完善信息接口: /user/update
params:
{

         "username": "Jack234",
        "phone": "12341234112",
        "sex": "F",
        "birthday": "19960101",
        "edu": "本科",
        "role": "5",
        "info": "很穷啊啊啊啊啊",
        "email": "abcd1234@qq.com"

}
return:
`{
"msg": "信息更新成功",
"user": {
"id": 17,
"username": "Jack",
"phone": "12341234111",
"sex": "F",
"password": "123456",
"birthday": "19960101",
"edu": "大专",
"role": "5",
"info": "穷啊",
"email": "abcd123@qq.com"
},
"status": 200
}`


借贷出金额接口： /getloan
params: None

是否借出： /submit
params: 
`{
"username": "Tom",
"phone": "12341234111",
"isSubmit": false,
"amount": 10000
}`
return:
`{
"msg": "可以借出2万",
"amount": 20000,
"user": {
"id": 17,
"username": "Jack",
"phone": "12341234111",
"sex": "F",
"password": "123456",
"birthday": "19960101",
"edu": "大专",
"role": "5",
"info": "穷啊",
"email": "abcd123@qq.com"
},
"status": 200
}`
提交借款: /submit
params:
`{
"username": "Tom",
"phone": "12341234111",
"isSubmit": true,
"amount": 10000
}`
return:
`{
"msg": "恭喜你，借出 10000元",
"status": 200
}
`