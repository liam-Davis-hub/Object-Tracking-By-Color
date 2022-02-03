package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.util.ArrayList;


public class Pipeline implements PixelFilter {

    ArrayList<PixelFilter> filters = new ArrayList<>();







    public Pipeline() {
        PixelFilter outline = new ColorMasking();
        PixelFilter next = new ConvolutionFilter1();
        PixelFilter last = new FixedThresholdFilter();

        filters.add(outline);
        filters.add(next);
        filters.add(last);


    }

    @Override
    public DImage processImage(DImage img) {
        for(PixelFilter filter : filters){
            img = filter.processImage(img);

        }
        return img;
    }
}
