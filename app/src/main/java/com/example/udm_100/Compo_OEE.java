package com.example.udm_100;


public class Compo_OEE {
    private int type;
    private String name;
    private int a;
    private int p;
    private int q;
    private int oee;

    public Compo_OEE(int type, String name, int a, int p, int q, int oee) {
        this.type = type;
        this.name = name;
        if (a > 100)
            this.a = 100;
        else
            this.a = a;
        if (p > 100)
            this.p = 100;
        else
            this.p = p;

        if (q > 100)
            this.q = 100;
        else
            this.q = q;
        if (oee > 100)
            this.oee = 100;
        else
            this.oee = oee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public int getOee() {
        return oee;
    }

    public void setOee(int oee) {
        this.oee = oee;
    }

    public int getType(){return  type;}

    public void setType(int type) { this.type = type;}

}
