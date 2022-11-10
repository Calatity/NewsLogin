package com.lin.newslogin.util

class BaseReturnBack : java.util.HashMap<String?, Any?>() {
    init {
        this[CommonConstants.RETURN_VIEW_MSG] = "查询成功"
        this[CommonConstants.RETURN_VIEW_STATUS] = "200"
        this[CommonConstants.RETURN_VIEW_DATA] = ""
        this[CommonConstants.RETURN_VIEW_STATUS] = CommonConstants.STATUS_FAIL
    }
}