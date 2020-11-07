package com.company.Segment;

import com.company.Point.RealPoint;

public class Segment {
    RealPoint p;
    int r;
    int startAngle;
    int endAngle;
    public Segment(RealPoint p, int r, int startAngle, int endAngle) {
        this.p = p;
        this.r = r;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }
    public Segment(double x, double y, int r, int startAngle, int endAngle) {
        this.p = new RealPoint(x, y);
        this.r = r;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    public RealPoint getP() {
        return p;
    }

    public void setP(RealPoint p) {
        this.p = p;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public int getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(int endAngle) {
        this.endAngle = endAngle;
    }

}

