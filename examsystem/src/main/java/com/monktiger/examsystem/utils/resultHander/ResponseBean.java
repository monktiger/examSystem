package com.monktiger.examsystem.utils.resultHander;

public class ResponseBean<T> {
    private boolean success;
    private T data;
    private String errCode;
    private String errMsg;

    public ResponseBean(){

    }

    public ResponseBean(boolean success,T data, String errCode, String errMsg){
        this.success = success;
        this.data = data;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ResponseBean(boolean success, T data){
        this.success = success;
        this.data = data;
    }

    public ResponseBean(boolean success, T data, CommonErrorEnum enums){
        this.success = success;
        this.data = data;
        this.errCode = enums.getCode();
        this.errMsg = enums.getMsg();
    }

    public ResponseBean(boolean success, CommonErrorEnum enums){
        this.success = success;
        this.errCode = enums.getCode();
        this.errMsg = enums.getMsg();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "success=" + success +
                ", data=" + data +
                ", errCode='" + errCode + '\'' +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}
    