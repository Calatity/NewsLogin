package com.lin.newslogin.control

import com.alibaba.fastjson.JSON
import com.lin.newslogin.bean.User
import com.lin.newslogin.dao.UserDao
import com.lin.newslogin.util.BaseReturnBack
import com.lin.newslogin.util.CommonConstants
import com.lin.newslogin.util.JsonTools
import com.lin.newslogin.util.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("user")
class UserControl {
    @Autowired
    private val userDao: UserDao? = null

    @RequestMapping("register")
    fun registerUser(@RequestBody newUser:User): String {
        val baseReturnBack = BaseReturnBack()
        if (Util.checkUser(newUser)){
            baseReturnBack[CommonConstants.RETURN_VIEW_MSG] = "账号密码不能为空"
            return JSON.toJSONString(baseReturnBack)
        }
        if(newUser.userName?.let { userDao?.selectByName(it)?.isNotEmpty() } == true){
            baseReturnBack[CommonConstants.RETURN_VIEW_MSG] = "账号已存在"
        }else{
            userDao?.save(newUser)
            baseReturnBack[CommonConstants.RETURN_VIEW_MSG] = "注册成功"
            baseReturnBack[CommonConstants.RETURN_VIEW_STATUS] = CommonConstants.STATUS_SUCCESS
        }
        return JSON.toJSONString(baseReturnBack)
    }

    @RequestMapping("setEmail")
    fun setEmail(@RequestBody updateUser: User):String{
        val baseReturnBack = BaseReturnBack()
        if (updateUser.userEmail==null || updateUser.userEmail === ""){
            baseReturnBack[CommonConstants.RETURN_VIEW_MSG] = "邮箱不能为空"
            return JSON.toJSONString(baseReturnBack)
        }
        val userList = updateUser.userName?.let { userDao?.selectByName(it) }
        if (userList!!.toString() == "[]"){
            baseReturnBack[CommonConstants.RETURN_VIEW_MSG] = "用户不存在"
            return JSON.toJSONString(baseReturnBack)
        }
        updateUser.password = userList[0].password
        userDao?.save(updateUser)
        baseReturnBack[CommonConstants.RETURN_VIEW_MSG] = "设置成功"
        baseReturnBack[CommonConstants.RETURN_VIEW_STATUS] = CommonConstants.STATUS_SUCCESS
        return JSON.toJSONString(baseReturnBack)
    }

    @RequestMapping("login")
    fun login(@RequestBody loginUser: User):String{
        val baseReturnBack = BaseReturnBack()
        if (Util.checkUser(loginUser)){
            baseReturnBack[CommonConstants.RETURN_VIEW_MSG] = "账号密码不能为空"
            return JSON.toJSONString(baseReturnBack)
        }
        val userList = loginUser.userName?.let { userDao?.selectByName(it) }
        if (userList!!.toString() == "[]"){
            baseReturnBack[CommonConstants.RETURN_VIEW_MSG] = "用户不存在"
            return JSON.toJSONString(baseReturnBack)
        }
        if (userList[0].password == loginUser.password){
            baseReturnBack[CommonConstants.RETURN_VIEW_MSG] = "登录成功"
            baseReturnBack[CommonConstants.RETURN_VIEW_DATA] = userList[0].userEmail
            baseReturnBack[CommonConstants.RETURN_VIEW_STATUS] = CommonConstants.STATUS_SUCCESS
        }else{
            baseReturnBack[CommonConstants.RETURN_VIEW_MSG] = "密码错误"
        }
        return JSON.toJSONString(baseReturnBack)
    }

    @RequestMapping("forGotPassword")
    fun forgotPassword(@RequestBody updateUser: User):String{
        val baseReturnBack = BaseReturnBack()
        val userList = updateUser.userName?.let { userDao?.selectByName(it) }
        if (userList!!.toString() == "[]"){
            baseReturnBack[CommonConstants.RETURN_VIEW_MSG] = "用户不存在"
            return JSON.toJSONString(baseReturnBack)
        }
        if (userList[0].userName == updateUser.userName && userList[0].userEmail == updateUser.userEmail){
            if (updateUser.password == "" || updateUser.password == null){
                baseReturnBack[CommonConstants.RETURN_VIEW_MSG] = "密码不能为空"
                return JSON.toJSONString(baseReturnBack)
            }
            userDao?.save(updateUser)
            baseReturnBack[CommonConstants.RETURN_VIEW_MSG] = "修改成功"
            baseReturnBack[CommonConstants.RETURN_VIEW_STATUS] = CommonConstants.STATUS_SUCCESS
        }else{
            baseReturnBack[CommonConstants.RETURN_VIEW_MSG] = "账号邮箱不匹配，修改失败"
        }
        return JSON.toJSONString(baseReturnBack)
    }

    @RequestMapping("getEmailForName")
    fun getEmailForName(@RequestBody user: User):String{
        val baseReturnBack = BaseReturnBack();
        val userList = user.userName?.let { userDao?.selectByName(it) }
        if (userList!!.toString() == "[]"){
            baseReturnBack[CommonConstants.RETURN_VIEW_MSG] = "用户不存在"
            return JSON.toJSONString(baseReturnBack)
        }
        baseReturnBack[CommonConstants.RETURN_VIEW_MSG] = "查询成功"
        baseReturnBack[CommonConstants.RETURN_VIEW_STATUS] = CommonConstants.STATUS_SUCCESS
        baseReturnBack[CommonConstants.RETURN_VIEW_DATA] = userList[0].userEmail
        return JSON.toJSONString(baseReturnBack)
    }
}