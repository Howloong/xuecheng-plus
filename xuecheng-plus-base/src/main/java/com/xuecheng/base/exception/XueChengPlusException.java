package com.xuecheng.base.exception;

public class XueChengPlusException extends RuntimeException {

    private String errMsg;

    public XueChengPlusException() {

    }

    public XueChengPlusException(String message) {
        this.errMsg = message;
    }

    public static void cast(String errMsg) {
        throw new XueChengPlusException(errMsg);
    }

    public static void cast(CommonError commonError) {
        throw new XueChengPlusException(commonError.getErrMessage());
    }
}
