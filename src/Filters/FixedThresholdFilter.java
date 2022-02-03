package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.awt.*;

public class FixedThresholdFilter implements PixelFilter {
    private int threshold;

    public FixedThresholdFilter() {
        threshold = 100;
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (grid[r][c] > threshold) {
                    grid[r][c] = 255;
                } else {
                    grid[r][c] = 0;
                }
            }
        }

        System.out.println(calculateCenter(img));
        img.setPixels(grid);
        return img;
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

