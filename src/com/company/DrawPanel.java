package com.company;

import javax.swing.*;

import com.company.LineDrawers.DDALineDrawer;
import com.company.LineDrawers.LineDrawer;
import com.company.Line.Line;
import com.company.PixelDrawers.BufferedImagePixelDrawer;
import com.company.PixelDrawers.PixelDrawer;
import com.company.Point.RealPoint;
import com.company.Point.ScreenPoint;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
    private Line xAxis = new Line(-1, 0, 1, 0);
    private Line yAxis = new Line(0, -1, 0, 1);
    private ScreenConverter sc = new ScreenConverter(-2, 2, 4, 4, 800, 600);
    private ArrayList<Line> allLines = new ArrayList<>();
    private Line currentNewLine = null;
    public DrawPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    public void paint(Graphics g) {
        sc.setScreenWidth(getWidth());
        sc.setScreenHeight(getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics gr = bi.createGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getWidth(), getHeight());
        gr.dispose();
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new DDALineDrawer(pd);
        drawLine(ld,xAxis);
        drawLine(ld,yAxis);
        for (Line l: allLines){
            drawLine(ld,l);
        }
         if (currentNewLine!=null){
             drawLine(ld, currentNewLine);
         }
        g.drawImage(bi, 0, 0, null);


    }
    private void drawLine(LineDrawer ld, Line l){
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }
    private ScreenPoint lastPosition = null;
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        ScreenPoint currentPosition = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
        if (lastPosition != null) {
            ScreenPoint deltaScreen = new ScreenPoint(currentPosition.getX() - lastPosition.getX(), currentPosition.getY() - lastPosition.getY());
            RealPoint deltaReal = sc.s2r(deltaScreen);
            RealPoint zeroReal = sc.s2r(new ScreenPoint(0, 0));
            RealPoint vector = new RealPoint(deltaReal.getX() - zeroReal.getX(), deltaReal.getY() - zeroReal.getY());
            sc.setCornerX(sc.getCornerX() - vector.getX());
            sc.setCornerY(sc.getCornerY() - vector.getY());
            lastPosition = currentPosition;
        }
        if (currentNewLine!=null){
            currentNewLine.setP2(sc.s2r(currentPosition));
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if( mouseEvent.getButton() == MouseEvent.BUTTON3){
         lastPosition = new ScreenPoint( mouseEvent.getX(), mouseEvent.getY());
        } else if(mouseEvent.getButton() == MouseEvent.BUTTON1){
            currentNewLine = new Line(sc.s2r(new ScreenPoint(mouseEvent.getX(),mouseEvent.getY())),
                    sc.s2r(new ScreenPoint(mouseEvent.getX(),mouseEvent.getY())));
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if( mouseEvent.getButton() == MouseEvent.BUTTON3){
            lastPosition = null;// new ScreenPoint( mouseEvent.getX(), mouseEvent.getY());
        } else if(mouseEvent.getButton() == MouseEvent.BUTTON1){
            allLines.add(currentNewLine);
            currentNewLine=null;
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
       int clicks = mouseWheelEvent.getWheelRotation();
        double scale = 1;
        double coef = clicks<0 ? 1.1 : 0.9;
        for (int i = 0; i< Math.abs(clicks); i++){
            scale *= coef;
        }
        sc.setRealWidth(sc.getRealWidth()*scale);
        sc.setRealHeight(sc.getRealWidth()*scale);
        repaint();
    }
}