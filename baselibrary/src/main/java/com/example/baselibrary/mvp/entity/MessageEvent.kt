package com.example.baselibrary.mvp.entity

import com.example.baselibrary.utils.StringUtils

/**
 * EventBus 消息事件实体类
 */
class MessageEvent {
    var message_type: String? = null
    var message: String? = null
        get() = field
        set(value) {
            if (!StringUtils.isBlank(value)) {
                field = value
            } else {
                field = ""
            }
        }
}