package com.company.ArcDrawers;

import com.company.PixelDrawers.PixelDrawer;

import java.awt.*;

public class BrezenhamArcDrawer implements ArcDrawer {
    private PixelDrawer pd;

    public BrezenhamArcDrawer(PixelDrawer pd) {
        this.pd = pd;
    }

    @Override
    public void drawArc(int x, int y, int r, int startAngle, int endAngle) {
        /*if(startAngle>endAngle){
            int tmp=startAngle;
            startAngle=endAngle;
            endAngle=tmp;
        }
        if (setPart(startAngle) == 1 ) {
            setFirst(x, y, r, startAngle, endAngle);
        }
        if (setPart(startAngle) >=1 && (setPart(endAngle)==2 || setPart(endAngle)==3)){
            setSecond(x, y, r, startAngle, endAngle);
        }
        if (setPart(startAngle) <=3 && setPart(endAngle)>=3) {
            setThird(x, y, r, startAngle, endAngle);
        }
        if (setPart(startAngle) <= 4 && setPart(endAngle)==4) {
            setForth(x, y, r, startAngle, endAngle);
        } */

    if (startAngle <= 90) {
            setFirst(x, y, r, startAngle, endAngle);
        }
        if (( startAngle > 90 &&  endAngle <= 180) || ( startAngle <= 90 &&  endAngle > 90) || ( startAngle> 90 &&  endAngle >= 180 &&  endAngle < 270)) {
            setSecond(x, y, r,  startAngle,  endAngle);
        }
        if (( startAngle > 180 &&  endAngle <= 270) || ( startAngle <= 180 &&  endAngle > 180) || ( startAngle > 180 &&  endAngle >= 270)) {
            setThird(x, y, r, startAngle,  endAngle);
        }
        if (( startAngle > 270 &&  endAngle<= 360) || ( startAngle<= 270 &&  endAngle > 270) || ( startAngle > 270 &&  endAngle >= 360)) {
            setForth(x, y, r, startAngle,  endAngle);
        }



    }
  private  int setPart(int angle){
      boolean reverseAngle=false;
      if (angle<0){
          reverseAngle=true;
          angle=-angle;
      }
      while(angle>360) {
          angle-= 360;
      }
      angle = (int)(angle * Math.PI / 180);
      if (angle <= Math.PI / 2){
          if(reverseAngle) {
              return 4;
          }
          return 1;
      }
      if (angle > Math.PI / 2 && angle <= Math.PI){
          if(reverseAngle) {
              return 3;
          }
          return 2;
      }
      if (angle > Math.PI && angle <= 3*Math.PI/2){
          if(reverseAngle) {
              return 2;
          }
          return 3;
      }
      if (angle > 3*Math.PI/2 && angle <= 2*Math.PI/2){
          if(reverseAngle) {
              return 1;
          }
          return 4;
      }
      return -1;
  }


    public void setFirst(int xC, int yC, int r, int sAngle, int fAngle)
    {
        int sAngleCopy = sAngle;
        int fAngleCopy;
        if(fAngle > 90)
        {
            fAngleCopy=90;
        }else
        {
            fAngleCopy=fAngle;
        }
      int   x=Math.abs((int)(r*Math.cos((fAngleCopy*(Math.PI/180)))));
       int y=Math.abs((int)(r*Math.sin((fAngleCopy*(Math.PI/180)))));
        double d=2*(1-r),d1,d2;
        int limit=Math.abs((int)(r*Math.sin((sAngleCopy*(Math.PI/180)))));

        while (y>=limit)
        {

            pd.drawPixel(x+xC,-y+yC, Color.BLACK); //первая четверть углы наоборот

            if (d<0)
            {
                d1=2*d-2*y-1;
                if (d1<0)
                {
                    x=x+1;
                    d=d+2*x+1;
                }
                if (d1>0)
                {
                    x++;
                    y--;
                    d=d+2*x-2*y+2;
                }
            }
            if (d>0)
            {
                d2=2*d-2*x-1;
                if (d2<=0)
                {
                    x++;
                    y--;
                    d=d+2*x-2*y+2;
                }
                if (d2>0)
                {
                    y--;
                    d=d-2*y+1;
                }
            }
            if (d==0)
            {
                x++;
                y--;
                d=d+2*x-2*y+2;
            }
        }
    }


    public void setSecond(int xC, int yC, int r, int sAngle, int fAngle)
    {
        int sAngleCopy = sAngle;
        int fAngleCopy;
        if(fAngle > 180)
        {
            fAngleCopy=180;
        }else
        {
            fAngleCopy=fAngle;
        }
        if(sAngle < 90)
        {
            sAngleCopy=90;
        }else
        {
            sAngleCopy=sAngle;
        }
        int  x=Math.abs((int)(r*Math.cos((sAngleCopy*(Math.PI/180)))));
        int y=Math.abs((int)(r*Math.sin((sAngleCopy*(Math.PI/180)))));
        double d=2*(1-r),d1,d2;
        int limit=Math.abs((int)(r*Math.sin((fAngleCopy*(Math.PI/180)))));

        while (y>=limit)
        {
            pd.drawPixel(-x+xC,-y+yC, Color.BLACK);

            if (d<0)
            {
                d1=2*d-2*y-1;
                if (d1<0)
                {
                    x=x+1;
                    d=d+2*x+1;
                }
                if (d1>0)
                {
                    x++;
                    y--;
                    d=d+2*x-2*y+2;
                }
            }
            if (d>0)
            {
                d2=2*d-2*x-1;
                if (d2<=0)
                {
                    x++;
                    y--;
                    d=d+2*x-2*y+2;
                }
                if (d2>0)
                {
                    y--;
                    d=d-2*y+1;
                }
            }
            if (d==0)
            {
                x++;
                y--;
                d=d+2*x-2*y+2;
            }
        }
    }

    public void setThird(int xC, int yC, int r, int sAngle, int fAngle)
    {
        int sAngleCopy = sAngle;
        int fAngleCopy;
        if(fAngle > 270)
        {
            fAngleCopy=270;
        }else
        {
            fAngleCopy=fAngle;
        }
        if(sAngle < 180)
        {
            sAngleCopy=180;
        }else
        {
            sAngleCopy=sAngle;
        }
       int x=Math.abs((int)(r*Math.cos((fAngleCopy*(Math.PI/180)))));
        int y=Math.abs((int)(r*Math.sin((fAngleCopy*(Math.PI/180)))));
        double d=2*(1-r),d1,d2;
        int limit=Math.abs((int)(r*Math.sin((sAngleCopy*(Math.PI/180)))));

        while (y>=limit)
        {
            pd.drawPixel(-x+xC,y+yC, Color.BLACK);

            if (d<0)
            {
                d1=2*d-2*y-1;
                if (d1<0)
                {
                    x=x+1;
                    d=d+2*x+1;
                }
                if (d1>0)
                {
                    x++;
                    y--;
                    d=d+2*x-2*y+2;
                }
            }
            if (d>0)
            {
                d2=2*d-2*x-1;
                if (d2<=0)
                {
                    x++;
                    y--;
                    d=d+2*x-2*y+2;
                }
                if (d2>0)
                {
                    y--;
                    d=d-2*y+1;
                }
            }
            if (d==0)
            {
                x++;
                y--;
                d=d+2*x-2*y+2;
            }
        }
    }

    public void setForth(int xC, int yC, int r, int sAngle, int fAngle)
    {
        int sAngleCopy = sAngle;
        int fAngleCopy;
        if(fAngle > 360)
        {
            fAngleCopy=360;
        }else
        {
            fAngleCopy=fAngle;
        }
        if(sAngle < 270)
        {
            sAngleCopy=270;
        }else
        {
            sAngleCopy=sAngle;
        }
      int  x=Math.abs((int)(r*Math.cos((sAngleCopy*(Math.PI/180)))));
      int  y=Math.abs((int)(r*Math.sin((sAngleCopy*(Math.PI/180)))));
        double d=2*(1-r),d1,d2;
        int limit=Math.abs((int)(r*Math.sin((fAngleCopy*(Math.PI/180)))));

        while (y>=limit)
        {
            pd.drawPixel(x+xC,y+yC, Color.BLACK);
            if (d<0)
            {
                d1=2*d-2*y-1;
                if (d1<0)
                {
                    x=x+1;
                    d=d+2*x+1;
                }
                if (d1>0)
                {
                    x++;
                    y--;
                    d=d+2*x-2*y+2;
                }
            }
            if (d>0)
            {
                d2=2*d-2*x-1;
                if (d2<=0)
                {
                    x++;
                    y--;
                    d=d+2*x-2*y+2;
                }
                if (d2>0)
                {
                    y--;
                    d=d-2*y+1;
                }
            }
            if (d==0)
            {
                x++;
                y--;
                d=d+2*x-2*y+2;
            }
        }
    }

}


