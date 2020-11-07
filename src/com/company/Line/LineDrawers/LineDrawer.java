package com.company.Line.LineDrawers;

import com.company.Point.ScreenPoint;

import java.awt.*;

public interface LineDrawer {
    void drawLine(ScreenPoint p1, ScreenPoint p2, Color c);
}
