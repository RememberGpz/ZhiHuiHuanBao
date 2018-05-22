package com.lee.zhihuihuanbao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author Lee
 * @Time 2017/11/16
 * @Theme   个人信息Bean
 */

public class UserBean implements Parcelable{


    /**
     * message : 登录成功
     * status : 200
     * result : {"id":139420,"memberName":"5555","mobile":"18813758261","password":"F59BD65F7EDAFB087A81D4DCA06C4910","sex":"男","account":null,"email":"null","qq":"null","registrationTime":"2017-11-22 15:34:18.0","head":"","age":"41岁以上","educationLevel":"本科以上","job":"总经理","maritalStatus":"保密","monthlyIncome":"6000以上","code":null,"expireTime":"2018-11-30","token":"0735796695c6b0f1b6d92bbb934ecc92","memberType":"钻石卡"}
     * result1 : null
     * result2 : null
     * result3 : null
     */

    private String message;
    private String status;
    private ResultBean result;
    private Object result1;
    private Object result2;
    private Object result3;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public Object getResult1() {
        return result1;
    }

    public void setResult1(Object result1) {
        this.result1 = result1;
    }

    public Object getResult2() {
        return result2;
    }

    public void setResult2(Object result2) {
        this.result2 = result2;
    }

    public Object getResult3() {
        return result3;
    }

    public void setResult3(Object result3) {
        this.result3 = result3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static class ResultBean {
        /**
         * id : 139420
         * memberName : 5555
         * mobile : 18813758261
         * password : F59BD65F7EDAFB087A81D4DCA06C4910
         * sex : 男
         * account : null
         * email : null
         * qq : null
         * registrationTime : 2017-11-22 15:34:18.0
         * head :
         * age : 41岁以上
         * educationLevel : 本科以上
         * job : 总经理
         * maritalStatus : 保密
         * monthlyIncome : 6000以上
         * code : null
         * expireTime : 2018-11-30
         * token : 0735796695c6b0f1b6d92bbb934ecc92
         * memberType : 钻石卡
         */

        private int id;
        private String memberName;
        private String mobile;
        private String password;
        private String sex;
        private Object account;
        private String email;
        private String qq;
        private String registrationTime;
        private String head;
        private String age;
        private String educationLevel;
        private String job;
        private String maritalStatus;
        private String monthlyIncome;
        private Object code;
        private String expireTime;
        private String token;
        private String memberType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Object getAccount() {
            return account;
        }

        public void setAccount(Object account) {
            this.account = account;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getRegistrationTime() {
            return registrationTime;
        }

        public void setRegistrationTime(String registrationTime) {
            this.registrationTime = registrationTime;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getEducationLevel() {
            return educationLevel;
        }

        public void setEducationLevel(String educationLevel) {
            this.educationLevel = educationLevel;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public String getMonthlyIncome() {
            return monthlyIncome;
        }

        public void setMonthlyIncome(String monthlyIncome) {
            this.monthlyIncome = monthlyIncome;
        }

        public Object getCode() {
            return code;
        }

        public void setCode(Object code) {
            this.code = code;
        }

        public String getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getMemberType() {
            return memberType;
        }

        public void setMemberType(String memberType) {
            this.memberType = memberType;
        }
    }
}
