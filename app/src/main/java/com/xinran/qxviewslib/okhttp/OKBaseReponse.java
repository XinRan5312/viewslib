package com.xinran.qxviewslib.okhttp;

/**
 * Created by houqixin on 2017/3/14.
 */
public class OKBaseReponse {
    public int resultCode;
    public String errorMsg;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
