package com.lin.newslogin.util

import com.lin.newslogin.bean.User

class Util {
    companion object{
        fun checkUser(user: User):Boolean{
            if ( user.userName ==null || user.password ==null || user.userName === "" || user.password === ""){
                return true
            }
            return false
        }
    }
}