package com.example.wechatselldemo.utils;

import com.example.wechatselldemo.domain.vo.ResultVo;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
public class ResultVoUtils {
    public static <T> ResultVo<T> success(T t) {
        return of(0, "success", t);
    }

    public static ResultVo success() {
        return success(null);
    }

    public static <T> ResultVo<T> failure(T t) {
        return of(500, "failure", t);
    }

    public static <T> ResultVo<T> of(int code, String message, T t) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(code);
        resultVo.setMessage(message);
        resultVo.setData(t);
        return resultVo;
    }

    public static ResultVo of(int code, String message) {
        return of(code, message, null);
    }
}
