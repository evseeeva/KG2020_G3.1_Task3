package com.company;

import com.company.Point.RealPoint;
import com.company.Point.ScreenPoint;
import com.company.Segment.Segment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class AddSegmentWindow extends JFrame{
    private JLabel getXLabel;
    private JLabel getYLabel;
    private JLabel getRLabel;
    private JLabel getStartAngleLabel;
    private JLabel getEndAngleLabel;
    private JTextField  getXTextField;
    private JTextField getYTextField;
    private JTextField getRTextField;
    private JTextField getStartAngleTextField;
    private JTextField getEndAngleTextField;
    private JButton addSegmentButton;

    public AddSegmentWindow(MouseEvent mouseEvent, DrawPanel dp){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Новый сегмент");
        this.setBounds( mouseEvent.getX(), mouseEvent.getY(),320, 300);
        getXLabel = new JLabel("Координата центра по X:");
        getYLabel = new JLabel("Координата центра по Y:");
        getRLabel = new JLabel("Радиус сегмента:");
        getStartAngleLabel = new JLabel("Начальный угол:");
        getEndAngleLabel = new JLabel("Конечный угол:");
       RealPoint clickPoint = dp.getSc().s2r(new ScreenPoint(mouseEvent.getX(), mouseEvent.getY()));
        getXTextField= new JTextField(String.valueOf((int)Math.round(clickPoint.getX())));
        getYTextField= new JTextField(String.valueOf((int)Math.round(clickPoint.getY())));
        getRTextField= new JTextField("0");
        getStartAngleTextField= new JTextField("0");
        getEndAngleTextField= new JTextField("0");
        getXTextField.setHorizontalAlignment(JTextField.CENTER);
        getYTextField.setHorizontalAlignment(JTextField.CENTER);
        getRTextField.setHorizontalAlignment(JTextField.CENTER);
        getStartAngleTextField.setHorizontalAlignment(JTextField.CENTER);
        getEndAngleTextField.setHorizontalAlignment(JTextField.CENTER);
        addSegmentButton = new JButton("Добавить сегмент");
        JPanel panelMain = new JPanel(new GridLayout(6,2, 5, 5));
        this.add(panelMain);
        panelMain.add(getXLabel);
        panelMain.add(getXTextField);
        panelMain.add(getYLabel);
        panelMain.add(getYTextField);
        panelMain.add(getRLabel);
        panelMain.add(getRTextField);
        panelMain.add(getStartAngleLabel);
        panelMain.add(getStartAngleTextField);
        panelMain.add(getEndAngleLabel);
        panelMain.add(getEndAngleTextField);
        panelMain.add(addSegmentButton);

        addSegmentButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               RealPoint centerPoint =new RealPoint(Math.round(Integer.parseInt(getXTextField.getText())), Math.round(Integer.parseInt(getYTextField.getText())));
                int r = Integer.parseInt(getYTextField.getText());
                int startAngle = Integer.parseInt(getStartAngleTextField.getText());
                int endAngle = Integer.parseInt(getEndAngleTextField.getText());
                Segment s = new Segment(centerPoint, r, startAngle, endAngle);
                dp.getAllSegments().add(s);
                dp.repaint();
            }
        });

    }



}
