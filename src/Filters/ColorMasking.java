package Filters;

import Interfaces.PixelFilter;
import core.DImage;
import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;

public class ColorMasking implements PixelFilter {
    private int colorVal, otherVal, threshold;
    public static ArrayList<Point> objectPixels;

    public ColorMasking(){
        colorVal = 200;
        otherVal = 0;
        threshold = 100;
        objectPixels = new ArrayList<>();
    }


    public DImage processImage(DImage img) {
        short[][] red =  img.getRedChannel();
        short[][] blue = img.getBlueChannel();
        short[][] green = img.getGreenChannel();

        for (int i = 0; i < red.length; i++) {
            for (int j = 0; j < red[i].length; j++) {
                int redVal = red[i][j];
                int greenVal = green[i][j];
                int blueVal = blue[i][j];

                if(calculateDistance(redVal, greenVal, blueVal)<= threshold){
                    red[i][j] = 255;
                    blue[i][j] = 0;
                    green[i][j] = 0;

                    objectPixels.add(new Point(i, j));
                }
                else{
                    red[i][j] = 0;
                    blue[i][j] = 0;
                    green[i][j] = 0;
                }
            }

        }
        System.out.println(calculateCenter(img));
        img.setColorChannels(red, green, blue);
        return img;


    }


    public double calculateDistance(int r, int g, int b){
        return Math.sqrt( ((r-colorVal)*(r-colorVal)) + ((g-otherVal)*(g-otherVal))+ ((b-otherVal)*(b-otherVal)));
    }

    public Point calculateCenter(DImage image){
        int num = 0;
        int rowCenter = 0;
        int colCenter = 0;
        int rowTotal = 0;
        int colTotal = 0;
        short[][] red = image.getRedChannel();
        short[][] blue = image.getBlueChannel();
        short[][] green = image.getGreenChannel();
        for (int i = 0; i < red.length ; i++) {
            for (int j = 0; j < red[0].length; j++) {
                if(red[i][j] == 255){
                    rowTotal+= i;
                    num++;
                    colTotal +=j;

                }
            }

        }

        if (num == 0) {
            return new Point(-1, -1);
        }

        rowCenter = rowTotal/ (num);
        colCenter = colTotal/(num);

        Point center = new Point(colCenter, rowCenter);
        return center;
    }

    public void drawOverlay(PApplet window, DImage original, DImage filtered) {


    }
}

