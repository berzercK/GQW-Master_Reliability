package Project.Window.POJO;

public class Entry {
    // t k p1 p2 p3 p4 <p5> РСП РС
    private int t;
    private int k;
    private double p1;
    private double p2;
    private double p3;
    private double p4;
    private double p5;
    private double rsp;
    private double rs;

    public Entry(int t, int k, double p1, double p2, double p3, double p4, double rsp, double rs) {
        this.t = t;
        this.k = k;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.rsp = rsp;
        this.rs = rs;
    }

    public Entry() {
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public double getP1() {
        return p1;
    }

    public void setP1(double p1) {
        this.p1 = p1;
    }

    public double getP2() {
        return p2;
    }

    public void setP2(double p2) {
        this.p2 = p2;
    }

    public double getP3() {
        return p3;
    }

    public void setP3(double p3) {
        this.p3 = p3;
    }

    public double getP4() {
        return p4;
    }

    public void setP4(double p4) {
        this.p4 = p4;
    }

    public double getP5() {
        return p5;
    }

    public void setP5(double p5) {
        this.p5 = p5;
    }

    public double getRsp() {
        return rsp;
    }

    public void setRsp(double rsp) {
        this.rsp = rsp;
    }

    public double getRs() {
        return rs;
    }

    public void setRs(double rs) {
        this.rs = rs;
    }

}
