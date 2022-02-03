package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.awt.*;

public class ColorMasking implements PixelFilter {
    private int rVal, bVal,gVal, threshold;

    public ColorMasking(){
        rVal = 160;
        gVal = 0;
        bVal = 0;
        threshold = 100;
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
                    blue[i][j] = 255;
                    green[i][j] = 255;
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
        double distance = Math.sqrt( ((r-rVal)*(r-rVal)) + ((g-gVal)*(g-gVal))+ ((b-bVal)*(b-bVal)));
        return distance;
    }

    public Point calculateCenter(DImage image){
        int r = 0;
        int jx = 0;
        int rowCenter = 0;
        int colCenter = 0;
        int rowTotal = 0;
        int colTotal = 0;
        short[][] red = image.getRedChannel();
        short[][] blue = image.getBlueChannel();
        short[][] green = image.getGreenChannel();

        for (int i = 0; i < red.length ; i++) {
            for (int j = 0; j < red[0].length; j++) {
                if(red[i][j] == 255 && blue[i][j] == 255 && green[i][j] == 255){
                    rowTotal+= i;
                    r++;
                    colTotal +=j;
                    jx++;
                }
            }

        }

        if (r == 0 || jx == 0) {
            return new Point(-1, -1);
        }

        rowCenter = rowTotal/ (r);
        colCenter = colTotal/(jx);

        Point center = new Point(colCenter, rowCenter);
        return center;
    }
}