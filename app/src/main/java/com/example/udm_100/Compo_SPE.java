package com.example.udm_100;

public class Compo_SPE {

    private String start;
    private String end;
    private int target;
    private int total ;
    private int ok;
    private int ng ;

    public Compo_SPE(String start, String end, int target, int total, int ok, int ng) {
        this.start = start;
        this.end = end;
        this.target = target;
        this.total = total;
        this.ok = ok;
        this.ng = ng;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }

    public int getNg() {
        return ng;
    }

    public void setNg(int ng) {
        this.ng = ng;
    }
}
