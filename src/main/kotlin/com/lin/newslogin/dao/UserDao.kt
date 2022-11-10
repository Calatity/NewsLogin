package com.lin.newslogin.dao

import com.lin.newslogin.bean.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserDao:JpaRepository<User,Long>  {
    @Query(value = "select * from user where userName = ?1", nativeQuery = true)
    fun selectByName(userName:String):List<User>
}