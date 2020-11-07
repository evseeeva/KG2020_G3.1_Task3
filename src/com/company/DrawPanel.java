package com.company;

import javax.swing.*;

import com.company.ArcDrawers.ArcDrawer;
import com.company.ArcDrawers.BrezenhamArcDrawer;
import com.company.ArcDrawers.GraphicsArcDrawer;
import com.company.Line.Line;
import com.company.Line.LineDrawers.GraphicsLineDrawer;
import com.company.Point.RealPoint;
import com.company.Point.ScreenPoint;
import com.company.Segment.Segment;
import com.company.Line.LineDrawers.BrezenhamLineDrawer;
import com.company.Line.LineDrawers.LineDrawer;
import com.company.PixelDrawers.BufferedImagePixelDrawer;
import com.company.PixelDrawers.PixelDrawer;
import com.company.Segment.SegmentDrawer;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
    private ScreenConverter sc = new ScreenConverter(-2, 1.5, 4, 3, 800, 600);
    private Line xAxis = new Line(-100, 0, 100, 0);
    private Line yAxis = new Line(0, -100, 0, 100);
    private ArrayList<Line> allLines = new ArrayList<>();
    private ArrayList<Segment> allSegments = new ArrayList<>();
    private Line currentNewLine = null;
    public DrawPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
    }
    public ScreenConverter getSc() { return sc; }
    public ArrayList<Segment> getAllSegments() { return allSegments; }

    @Override
    public void paint(Graphics g) {
        sc.setScreenWidth(getWidth());
        sc.setScreenHeight(getHeight());
        BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics gr = bi.createGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getWidth(), getHeight());
        Graphics2D g2 = (Graphics2D)gr;
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new BrezenhamLineDrawer(pd);
        ArcDrawer ad = new GraphicsArcDrawer(gr);
        SegmentDrawer sd = new SegmentDrawer(ld,ad);
        drawCoordinates(ld, g2);
        for (Line l: allLines){
            drawLine(ld,l, Color.BLACK);
        }
         if (currentNewLine!=null){
             drawLine(ld, currentNewLine, Color.BLACK);
         }
        Segment s1 = new Segment(new RealPoint(0,0), 1, 220, 250);
        gr.setColor(Color.BLACK);
        allSegments.add(s1);
        for (Segment s: allSegments){
        drawSegment(sd,s);
     }
        gr.dispose();
        g.drawImage(bi, 0, 0, null);

    }
    private  void drawCoordinates(LineDrawer ld, Graphics2D g){
        drawLine(ld,xAxis, Color.RED);
        drawLine(ld,yAxis, Color.BLUE);
        int hx = (int) (sc.getScreenWidth()/sc.getRealWidth());
        int hy = (int) (sc.getScreenWidth()/sc.getRealWidth());
        int x0= sc.r2s(new RealPoint(0,0)).getX();
        int y0= sc.r2s(new RealPoint(0,0)).getY();
        int x1=sc.r2s(xAxis.getP1()).getX();
        int x2=sc.r2s(xAxis.getP2()).getX();
        int y1=sc.r2s(yAxis.getP1()).getY();
        int y2=sc.r2s(yAxis.getP2()).getY();
        Font font = new Font("Arial", Font.PLAIN, 15);
        g.setFont(font);
        g.setColor(Color.RED);
        int v=0;
        int dv=1;
        g.drawString(String.valueOf(v),x0+5 ,y0+20);
        while (hx <= Math.abs(sc.r2s(xAxis.getP1()).getX() - sc.r2s(new RealPoint(0,0)).getX())) {
            v+=dv;
            ld.drawLine(new ScreenPoint(x0+hx, y1 ),new ScreenPoint(x0+hx,y2), Color.LIGHT_GRAY);
            ld.drawLine(new ScreenPoint(x0+hx, y0+5 ),new ScreenPoint(x0+hx,y0-5), Color.RED);
            g.drawString(String.valueOf(v),x0+hx-3 ,y0+5+20);
            ld.drawLine(new ScreenPoint(x0-hx,y1), new ScreenPoint(x0-hx,y2), Color.LIGHT_GRAY);
            ld.drawLine(new ScreenPoint(x0-hx,y0+5),new ScreenPoint(x0-hx,y0-5), Color.RED);
            g.drawString(String.valueOf(-v),x0-hx-6 ,y0+5+20);
            hx+=(int) (sc.getScreenWidth()/sc.getRealWidth());


        }
        v=0;
        g.setColor(Color.BLUE);
        while (hy <= Math.abs(sc.r2s(yAxis.getP1()).getY() - sc.r2s(new RealPoint(0,0)).getY())) {
            v++;
            ld.drawLine(new ScreenPoint(x1, y0+hy ),new ScreenPoint(x2,y0+hy), Color.LIGHT_GRAY);
            ld.drawLine(new ScreenPoint(x0+5,y0+hy),new ScreenPoint(x0-5,y0+hy), Color.BLUE);
            g.drawString(String.valueOf(-v),x0+20,y0+hy+6);
            ld.drawLine(new ScreenPoint(x1, y0-hy ),new ScreenPoint(x2,y0-hy), Color.LIGHT_GRAY);
            ld.drawLine(new ScreenPoint(x0+5,y0-hy),new ScreenPoint(x0-5,y0-hy), Color.BLUE);
            g.drawString(String.valueOf(v),x0+20,y0-hy+3);
            hy+=(int) (sc.getScreenHeight()/sc.getRealHeight());

        }
    }
    private void drawLine(LineDrawer ld, Line l, Color c){
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()), c);
    }
    private void drawSegment(SegmentDrawer sd, Segment s) {
       sd.drawSegment(sc.r2s(s.getP()),(int)(s.getR()*sc.getScreenWidth()/sc.getRealWidth()), s.getStartAngle(), s.getEndAngle());
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
        if(mouseEvent.getButton() == MouseEvent.BUTTON1){
            AddSegmentWindow ad = new AddSegmentWindow(mouseEvent, this);
            ad.setVisible(true);
        }
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
            lastPosition = null;
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
        sc.setCornerX(sc.getCornerX()*scale);
        sc.setCornerY(sc.getCornerY()*scale);
        sc.setRealWidth(sc.getRealWidth()*scale);
        sc.setRealHeight(sc.getRealHeight()*scale);
        repaint();
    }
}