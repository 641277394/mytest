package com.itheima.health.exception;

/**
 * 自定义异常：
 *      1.友好提示
 *      2.区分异常的类型（业务、系统、未知）
 *      3.终止已知不符合业务逻辑代码的继续执行
 * @Author Sean
 * @Date 2021/1/8 13:17
 */
public class MyException extends RuntimeException{
    public MyException(String message) {
        super(message);
    }
}
