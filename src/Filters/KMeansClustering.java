package Filters;

import Interfaces.Drawable;
import Interfaces.PixelFilter;
import core.DImage;
import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;

public class KMeansClustering implements PixelFilter, Drawable {
    private ArrayList<Point> allObjectPixels;
    private ArrayList<Point> cluster1, cluster2, cluster3;
    private boolean done;
    private Point cluster1Center, cluster2Center, cluster3Center;

    public KMeansClustering() {
        cluster1 = new ArrayList<>();
        cluster2 = new ArrayList<>();
        cluster3 = new ArrayList<>();

        allObjectPixels = ColorMasking.objectPixels;
        done = false;

        // initialize random points
        cluster1Center = allObjectPixels.get((int)(Math.random() * allObjectPixels.size()));
        cluster2Center = allObjectPixels.get((int)(Math.random() * allObjectPixels.size()));
        cluster3Center = allObjectPixels.get((int)(Math.random() * allObjectPixels.size()));
    }

    @Override
    public DImage processImage(DImage img) {
        createClusters(allObjectPixels);
        return img;
    }

    private void createClusters(ArrayList<Point> objectPixels) {
        do {
            // clear data
            cluster1.clear();
            cluster2.clear();
            cluster3.clear();

            // initialize random points
            cluster1Center = allObjectPixels.get((int)(Math.random() * allObjectPixels.size()));
            cluster2Center = allObjectPixels.get((int)(Math.random() * allObjectPixels.size()));
            cluster3Center = allObjectPixels.get((int)(Math.random() * allObjectPixels.size()));

            // assign clusters
            assignClusters(objectPixels);

            // save centers
            Point originalCluster1 = cluster1Center;
            Point originalCluster2 = cluster2Center;
            Point originalCluster3 = cluster3Center;

            // recalculate cluster centers
            reCalculateClusters();

            // save recalculated centers and differences
            Point nextCluster1 = cluster1Center;
            Point nextCluster2 = cluster2Center;
            Point nextCluster3 = cluster3Center;

            double cluster1Movement = originalCluster1.distance(nextCluster1);
            double cluster2Movement = originalCluster2.distance(nextCluster2);
            double cluster3Movement = originalCluster3.distance(nextCluster3);

            // evaluate if done
            if (cluster1Movement == 0 && cluster2Movement == 0 && cluster3Movement == 0) done = true;

            System.out.println(cluster1Center + ", " + cluster2Center + ", " + cluster3Center);
        } while (!done);
    }

    private void reCalculateClusters() {
        // cluster 1
        int cluster1x = 0;
        int cluster1y = 0;

        int cluster1size = cluster1.size();
        if (cluster1size == 0) cluster1size = 1;

        for (Point p : cluster1) {
            cluster1x += p.getX();
            cluster1y += p.getY();
        }

        cluster1Center = new Point(cluster1x / cluster1size, cluster1y / cluster1size);


        // cluster 2
        int cluster2x = 0;
        int cluster2y = 0;

        int cluster2size = cluster2.size();
        if (cluster2size == 0) cluster2size = 1;

        for (Point p : cluster2) {
            cluster2x += p.getX();
            cluster2y += p.getY();
        }

        cluster2Center = new Point(cluster2x / cluster2size, cluster2y / cluster2size);

        // cluster 3
        int cluster3x = 0;
        int cluster3y = 0;

        int cluster3size = cluster3.size();
        if (cluster3size == 0) cluster3size = 1;

        for (Point p : cluster3) {
            cluster3x += p.getX();
            cluster3y += p.getY();
        }

        cluster3Center = new Point(cluster3x / cluster3size, cluster3y / cluster3size);
    }


    private void assignClusters(ArrayList<Point> objectPixels) {
        // iterate over all points and assign to center
        for (Point p : objectPixels) {
            double distanceCluster1 = p.distance(cluster1Center);
            double distanceCluster2 = p.distance(cluster2Center);
            double distanceCluster3 = p.distance(cluster3Center);

            if (distanceCluster1 < distanceCluster2 & distanceCluster1 < distanceCluster3) cluster1.add(p);
            else if (distanceCluster2 < distanceCluster1 && distanceCluster2 < distanceCluster3) cluster2.add(p);
            else cluster3.add(p);
        }
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        window.ellipse(cluster1Center.x, cluster1Center.y, 5, 5);
        window.ellipse(cluster2Center.x, cluster2Center.y, 5, 5);
        window.ellipse(cluster3Center.x, cluster3Center.y, 5, 5);
    }
}
