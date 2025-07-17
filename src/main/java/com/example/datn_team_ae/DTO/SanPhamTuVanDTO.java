package com.example.datn_team_ae.DTO;

public class SanPhamTuVanDTO {
    private String tenSanPham;
    private String mauSac;
    private String kichThuoc;
    private int soLuong;

    public SanPhamTuVanDTO(String tenSanPham, String mauSac, String kichThuoc, int soLuong) {
        this.tenSanPham = tenSanPham;
        this.mauSac = mauSac;
        this.kichThuoc = kichThuoc;
        this.soLuong = soLuong;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
