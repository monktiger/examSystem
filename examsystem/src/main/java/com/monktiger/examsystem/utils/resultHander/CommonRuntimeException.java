package com.monktiger.examsystem.utils.resultHander;

public class CommonRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected String code;

    protected String msg;

    //打印出日志信息
    protected String message;

    public CommonRuntimeException(CommonErrorEnum enums, String message){
        this.code = enums.getCode();
        this.msg = enums.getMsg();
        this.message = message;
    }

    public CommonRuntimeException(CommonErrorEnum enums){
        super();
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }

    public CommonRuntimeException(String message, Throwable cause){
        super(message,cause);
    }
}
