package com.example.wechatselldemo.utils;

import java.util.Random;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
public class KeyUtil {

    /**
     * 生成唯一主键
     * @return string
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(randomNumber);
    }

}
