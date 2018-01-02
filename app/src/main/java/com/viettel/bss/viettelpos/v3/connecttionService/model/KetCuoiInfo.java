package com.viettel.bss.viettelpos.v3.connecttionService.model;

/**
 * Created by mantd-outsource on 7/15/16.
 * Class chứa thông tin kết cuối
 */
public class KetCuoiInfo {
    private String congnghe;
    private String maKetCuoi;
    private String diachiKetCuoi;
    private String tainguyenThietBi;
    private String tainguyenKetCuoi;
    private String tainguyenCapGoc;
    private String matram;
    private String donviQuanLy;
    private String potRF;
    private String hotenNV;
    private String sodienthoaiNV;
    private String chucVu;

    public KetCuoiInfo() {}

    public KetCuoiInfo(String congnghe, String maKetCuoi, String diachiKetCuoi, String tainguyenThietBi,
                       String tainguyenKetCuoi, String tainguyenCapGoc, String matram, String donviQuanLy, String potRF,
                       String hotenNV, String sodienthoaiNV, String chucVu) {
        this.congnghe = congnghe;
        this.maKetCuoi = maKetCuoi;
        this.diachiKetCuoi = diachiKetCuoi;
        this.tainguyenThietBi = tainguyenThietBi;
        this.tainguyenKetCuoi = tainguyenKetCuoi;
        this.tainguyenCapGoc = tainguyenCapGoc;
        this.matram = matram;
        this.donviQuanLy = donviQuanLy;
        this.potRF = potRF;
        this.hotenNV = hotenNV;
        this.sodienthoaiNV = sodienthoaiNV;
        this.chucVu = chucVu;
    }

    public String getCongnghe() {
        return congnghe;
    }

    public void setCongnghe(String congnghe) {
        this.congnghe = congnghe;
    }

    public String getMaKetCuoi() {
        return maKetCuoi;
    }

    public void setMaKetCuoi(String maKetCuoi) {
        this.maKetCuoi = maKetCuoi;
    }

    public String getDiachiKetCuoi() {
        return diachiKetCuoi;
    }

    public void setDiachiKetCuoi(String diachiKetCuoi) {
        this.diachiKetCuoi = diachiKetCuoi;
    }

    public String getTainguyenThietBi() {
        return tainguyenThietBi;
    }

    public void setTainguyenThietBi(String tainguyenThietBi) {
        this.tainguyenThietBi = tainguyenThietBi;
    }

    public String getTainguyenKetCuoi() {
        return tainguyenKetCuoi;
    }

    public void setTainguyenKetCuoi(String tainguyenKetCuoi) {
        this.tainguyenKetCuoi = tainguyenKetCuoi;
    }

    public String getMatram() {
        return matram;
    }

    public void setMatram(String matram) {
        this.matram = matram;
    }

    public String getDonviQuanLy() {
        return donviQuanLy;
    }

    public void setDonviQuanLy(String donviQuanLy) {
        this.donviQuanLy = donviQuanLy;
    }

    public String getPotRF() {
        return potRF;
    }

    public void setPotRF(String potRF) {
        this.potRF = potRF;
    }

    public String getHotenNV() {
        return hotenNV;
    }

    public void setHotenNV(String hotenNV) {
        this.hotenNV = hotenNV;
    }

    public String getSodienthoaiNV() {
        return sodienthoaiNV;
    }

    public void setSodienthoaiNV(String sodienthoaiNV) {
        this.sodienthoaiNV = sodienthoaiNV;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getTainguyenCapGoc() {
        return tainguyenCapGoc;
    }

    public void setTainguyenCapGoc(String tainguyenCapGoc) {
        this.tainguyenCapGoc = tainguyenCapGoc;
    }
}
