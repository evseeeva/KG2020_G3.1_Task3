package com.company.Segment;

import com.company.ArcDrawers.ArcDrawer;
import com.company.Point.ScreenPoint;
import com.company.Line.LineDrawers.LineDrawer;

import java.awt.*;

public class SegmentDrawer {
    private LineDrawer ld;
    private ArcDrawer ad;

    public SegmentDrawer(LineDrawer ld, ArcDrawer ad) {
        this.ld = ld;
        this.ad = ad;
    }
    public void drawSegment(ScreenPoint p, int r, int startAngle, int endAngle ) {
        int x = p.getX();
        int y = p.getY();
        ad.drawArc(x , y ,  r, startAngle, endAngle - startAngle);
        if (Math.abs(endAngle - startAngle) < 360) {
            double x1 = r * Math.cos(startAngle * Math.PI / 180);
            double y1 = r * Math.sin(startAngle * Math.PI / 180);
            double x2 = r * Math.cos(endAngle * Math.PI / 180);
            double y2 = r * Math.sin(endAngle * Math.PI / 180);
            ld.drawLine(p, new ScreenPoint(x + (int) x1, y - (int) y1), Color.BLACK);
            ld.drawLine(p, new ScreenPoint(x + (int) x2, y - (int) y2), Color.BLACK);

        }
    }
}
