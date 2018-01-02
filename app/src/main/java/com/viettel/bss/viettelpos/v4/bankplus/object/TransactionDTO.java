package com.viettel.bss.viettelpos.v4.bankplus.object;

import java.util.Random;

/**
 * Created by hantt47 on 8/3/2017.
 */

public class TransactionDTO {
    private String idTransaction;
    private String numberMoney;
    private String phoneNumberCus;
    private String addressCus;
    private int timeExpire;
    private String status;
    private String ctv;

    public TransactionDTO() {
        this.idTransaction = "12489325470569087";
        this.numberMoney = "1.000.000 VND";
        this.phoneNumberCus = "0123456789";
        this.addressCus = "số 1 Đại Cồ Việt, Hai Bà Trưng, Hà Nội";
        Random random = new Random();
        this.timeExpire = random.nextInt(60 * 5);
        this.status="Đã giao việc chưa xác nhận";

    }

    public String timeToString() {
        String result = "";
        if (timeExpire / 60 > 0) {
            result += (timeExpire / 60) + " giờ, ";
        }
        if (timeExpire % 60 != 0) {
            result += (timeExpire % 60) + " phút ";
        }
        return result;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public String getNumberMoney() {
        return numberMoney;
    }

    public String getPhoneNumberCus() {
        return phoneNumberCus;
    }


    public String getAddressCus() {
        return addressCus;
    }

    public String getCtv() {
        return ctv;
    }

    public void setCtv(String ctv) {
        this.ctv = ctv;
    }

    public String getStatus() {
        return status;
    }

    public int getTimeExpire() {
        return timeExpire;
    }
}
