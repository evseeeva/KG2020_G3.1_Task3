package com.company.Segment;

import java.awt.*;

public class Segment {
    int x, y, r, startAngle, endAngle;

    public Segment(int x, int y, int r, int startAngle, int endAngle) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }
    public  static  void  drawSegment(Graphics2D g,int x, int y, int r,int startAngle, int endAngle ){
        g.drawArc(x - r, y - r, 2 * r, 2 * r, startAngle, endAngle - startAngle);
       if(Math.abs(endAngle-startAngle)<360){
           double x1 = r * Math.cos(startAngle * Math.PI / 180);
           double y1 = r * Math.sin(startAngle * Math.PI / 180);
           double x2 = r * Math.cos(endAngle * Math.PI / 180);
           double y2 = r * Math.sin(endAngle * Math.PI / 180);
           g.drawLine(x, y, x + (int) x1, y - (int) y1);
           g.drawLine(x, y, x + (int) x2, y - (int) y2);
       }
    }
}

