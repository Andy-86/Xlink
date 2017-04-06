package com.example.andy.connectutil.Bean;

/**
 * Created by 95815 .
 * Date:2017/3/30.
 * Writter: waiwen .
 * E-mail:iwaiwen@163.com .
 */

public class Equitment {

    private String equit_name;
    private int equit_kind;
    private int equit_resId;

    public Equitment(String name, int Id) {
        this.equit_name = name;
        this.equit_resId = Id;
    }

    public String getEquit_name() {
        return equit_name;
    }

    public void setEquit_name(String equit_name) {
        this.equit_name = equit_name;
    }

    public int getEquit_kind() {
        return equit_kind;
    }

    public void setEquit_kind(int equit_kind) {
        this.equit_kind = equit_kind;
    }

    public int getEquit_resId() {
        return equit_resId;
    }

    public void setEquit_resId(int equit_resId) {
        this.equit_resId = equit_resId;
    }
}
