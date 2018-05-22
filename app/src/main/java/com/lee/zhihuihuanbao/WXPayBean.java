package com.lee.zhihuihuanbao;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class WXPayBean {

    /**
     * message : 操作成功
     * result : {"appId":"wxb87e5855284ef62b","nonceStr":"829f2cd2-9d84-4","out_trade_no":"7170831095701533","packages":"Sign=WXPay","partnerid":"1484947802","paySign":"D28A326099E9448E0079B61791765312","prepayid":"wx2017083109570130defeeeeb0597946995","timeStamp":"1504144621"}
     * status : 200
     */

    private String message;
    private ResultBean result;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ResultBean {
        /**
         * appId : wxb87e5855284ef62b
         * nonceStr : 829f2cd2-9d84-4
         * out_trade_no : 7170831095701533
         * packages : Sign=WXPay
         * partnerid : 1484947802
         * paySign : D28A326099E9448E0079B61791765312
         * prepayid : wx2017083109570130defeeeeb0597946995
         * timeStamp : 1504144621
         */
        private String appId;
        private String nonceStr;
        private String out_trade_no;
        private String packages;
        private String partnerid;
        private String paySign;
        private String prepayid;
        private String timeStamp;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getPackages() {
            return packages;
        }

        public void setPackages(String packages) {
            this.packages = packages;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPaySign() {
            return paySign;
        }

        public void setPaySign(String paySign) {
            this.paySign = paySign;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }
    }
}
