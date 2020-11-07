package com.company.Line.LineDrawers;

import com.company.Point.ScreenPoint;
import com.company.PixelDrawers.PixelDrawer;

import java.awt.*;

public class BrezenhamLineDrawer implements LineDrawer {
    private PixelDrawer pd;
    public BrezenhamLineDrawer(PixelDrawer pd) {
        this.pd = pd;
    }
    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2, Color color) {
        int x1 = p1.getX();
        int y1 = p1.getY();
        int x2 = p2.getX();
        int y2 = p2.getY();
        int dx = x2 - x1;
        int dy = y2 - y1;
        int incline;
        if (Math.abs(dx) > Math.abs(dy)) {
            if (x1 > x2) {
                int tmp = x1;
                x1 = x2;
                x2 = tmp;
                tmp = y1;
                y1 = y2;
                y2 = tmp;
                dx = -dx;
                dy = -dy;
            }
            incline = (y2 >= y1)?1:-1;
                int d = incline*2 * dy - dx;
                for (int i = x1; i <= x2; i++) {
                    pd.drawPixel(x1, y1, color);
                    if (d >= 0) {
                        y1+=incline;
                        d += incline*2 * dy - 2 * dx;
                    } else {
                        d += incline*2 * dy;
                    }
                    x1++;
                }
        }
         else if (Math.abs(dx) <= Math.abs(dy)) {
            if (y1 > y2) {
                int tmp = x1;
                x1 = x2;
                x2 = tmp;
                tmp = y1;
                y1 = y2;
                y2 = tmp;
                dx = -dx;
                dy = -dy;
            }
            incline = (x2 >= x1)?1:-1;
                int d = incline*2 * dx - dy;
                for (int i = y1; i <= y2; i++) {
                    pd.drawPixel(x1, y1, color);
                    if (d >= 0) {
                        x1+=incline;
                        d += incline*2 * dx - 2 * dy;
                    } else {
                        d += incline * 2 * dx;
                    }
                    y1++;
                }

        }
    }

}
