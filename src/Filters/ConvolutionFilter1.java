package Filters;

import Interfaces.PixelFilter;
import core.DImage;
import processing.core.PApplet;

public class ConvolutionFilter1 implements PixelFilter {
    private double[][] blurKernel =
            {{1.0 / 9, 1.0 / 9, 1.0 / 9},
                    {1.0 / 9, 1.0 / 9, 1.0 / 9},
                    {1.0 / 9, 1.0 / 9, 1.0 / 9}};

    private double[][] outlineKernel =
            {
                    {-1, -1, -1},
                    {-1, 8, -1},
                    {-1, -1, -1}};

    private double[][] embossKernel =
            {
                    {-2, -1, 0},
                    {-1, 1, 1},
                    {0, 1, 2}};

    private double[][] sobelGx =
            {
                    {-1, 0, 1},
                    {-2, 0, 2},
                    {-1, 0, 1}};

    private double[][] sobelGy =
            {
                    {1, 2, 1},
                    {0, 0, 0},
                    {-1, -2, -1}};


    public DImage processImage(DImage img) {
        double blurKernelWeight = 0;
        for (int i = 0; i < blurKernel.length; i++) {
            for (int j = 0; j < blurKernel[0].length; j++) {
                blurKernelWeight += blurKernel[i][j];
            }
        }
        short[][] pixels = img.getBWPixelGrid();
        short[][] outputPixels = img.getBWPixelGrid();


        for (int row = 0; row < pixels.length - 2; row++) {
            for (int col = 0; col < pixels[row].length - 2; col++) {
                int output = 0;

                output += OutputSum(row, col, blurKernel, pixels);
                if (blurKernelWeight != 0) output = (int) (output / blurKernelWeight);
                if (output < 0) output = 0;
                if (output > 255) output = 255;
                outputPixels[row + 1][col + 1] = (short) output;


            }
        }

        img.setPixels(outputPixels);
        return img;
    }

    private double OutputSum(int row, int col, double[][] kernel, short[][] grid) {
        double output = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                double kernelVal = kernel[r][c];
                int pixelVal = grid[row][col];
                output += kernelVal * pixelVal;
            }
        }
        return output;
    }


}


