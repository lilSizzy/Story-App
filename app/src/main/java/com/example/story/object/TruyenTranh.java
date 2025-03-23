package com.example.story.object;

import org.json.JSONException;
import org.json.JSONObject;

public class TruyenTranh {
    private String tenTruyen;
    private String tenChap;
    private int AnhTruyen;

    public TruyenTranh(JSONObject object)throws JSONException {
        tenTruyen = object.getString("tenTruyen");
        tenChap = object.getString("tenChap");
        AnhTruyen = object.getInt("anhTruyen");
    }



    public TruyenTranh(String tenTruyen, String tenChap, int anhTruyen) {
        this.tenTruyen = tenTruyen;
        this.tenChap = tenChap;
        AnhTruyen = anhTruyen;
    }
    public TruyenTranh(int anhTruyen) {
        AnhTruyen = anhTruyen;
    }

    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public String getTenChap() {
        return tenChap;
    }

    public void setTenChap(String tenChap) {
        this.tenChap = tenChap;
    }

    public int getAnhTruyen() {
        return AnhTruyen;
    }

    public void setAnhTruyen(int anhTruyen) {
        AnhTruyen = anhTruyen;
    }
}
