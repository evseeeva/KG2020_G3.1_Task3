package com.company.ArcDrawers;

import java.awt.*;

public class GraphicsArcDrawer implements ArcDrawer {
    private Graphics g;

    public GraphicsArcDrawer(Graphics g) {
        this.g = g;
    }

    @Override
    public void drawArc(int x, int y, int width, int height,int startAngle, int arcAngle) {
      g.drawArc(x, y, width,height, startAngle, arcAngle);
    }
}
