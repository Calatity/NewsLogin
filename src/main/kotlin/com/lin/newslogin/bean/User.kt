package com.lin.newslogin.bean

import org.springframework.web.bind.annotation.RequestBody
import javax.persistence.Basic
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class User {
    @Id
    @Column(name = "userName", nullable = false, length = 20)
    var userName: String? = null

    @Basic
    @Column(name = "password", nullable = true, length = 20)
    var password: String? = null

    @Basic
    @Column(name = "userEmail", nullable = true, length = 50)
    var userEmail: String? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val user = o as User
        if (if (userName != null) userName != user.userName else user.userName != null) return false
        if (if (password != null) password != user.password else user.password != null) return false
        return if (if (userEmail != null) userEmail != user.userEmail else user.userEmail != null) false else true
    }

    override fun hashCode(): Int {
        var result = if (userName != null) userName.hashCode() else 0
        result = 31 * result + if (password != null) password.hashCode() else 0
        result = 31 * result + if (userEmail != null) userEmail.hashCode() else 0
        return result
    }
}