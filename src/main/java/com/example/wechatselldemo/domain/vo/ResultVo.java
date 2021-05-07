package com.example.wechatselldemo.domain.vo;

import lombok.Data;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
@Data
public class ResultVo<T> {
    /**状态码*/
    private Integer code;

    /***状态码信息*/
    private String message;

    /***数据*/
    private T data;
}
