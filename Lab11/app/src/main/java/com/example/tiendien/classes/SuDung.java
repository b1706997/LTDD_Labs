package com.example.tiendien.classes;

import java.util.Date;

public class SuDung {
    public int chisodien;
    public String ngay;
    public int tiendien;
    public SuDung(int chiso,String ngayy ) {
        ngay = ngayy;
        chisodien = chiso;
        tiendien = this.tinhTienDien(chiso);
    }
    public int tinhTienDien(int chiso) {
        if(chiso > 0 && chiso < 50) {
            return chiso * 1678;
        }
        if(chiso > 51 && chiso < 100) {
            return chiso * 1734;
        }
        if(chiso > 101 && chiso < 200) {
            return chiso * 2014;
        }
        if(chiso > 201 && chiso < 300) {
            return chiso * 2536;
        }
        if(chiso > 301 && chiso < 400) {
            return chiso * 2834;
        }
//        > 400
        return chiso * 2927;
    }
}
