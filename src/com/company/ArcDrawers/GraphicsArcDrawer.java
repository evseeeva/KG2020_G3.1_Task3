package com.company.ArcDrawers;

import java.awt.*;

public class GraphicsArcDrawer implements  ArcDrawer {
    private Graphics g;

    public GraphicsArcDrawer(Graphics g) {
        this.g = g;
    }

    @Override
    public void drawArc(int x, int y, int r, int startAngle, int endAngle) {
        g.drawArc(x - r, y - r, 2 * r, 2 * r, startAngle, endAngle );
    }


}
