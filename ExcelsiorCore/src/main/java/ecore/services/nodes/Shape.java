package ecore.services.nodes;

import ecore.services.location.ServiceLocationUtils;
import org.bukkit.Location;

public abstract class Shape {

    protected Location center;

    protected abstract void calculateCenter();
    public abstract boolean isWithin(Location location);

    public Shape(){}
    public Shape(Location center){this.center = center;}

    public Location getCenter(){
        if(center == null){
            calculateCenter();
        }
        return center;
    }

    public static class ShapeCircle extends Shape {

        private double radius;
        private boolean ignoreY;

        public ShapeCircle(Location center, double radius, boolean ignoreY){
            super(center);

            this.radius = radius;
            this.ignoreY = ignoreY;
        }


        @Override
        protected void calculateCenter() {

        }

        @Override
        public boolean isWithin(Location location) {
            Location temp = location.clone();
            if(ignoreY){
                temp.setY(center.getY());
            }
            return temp.distance(getCenter()) <= radius;
        }

        public double getRadius() {
            return radius;
        }

        public boolean isIgnoreY() {
            return ignoreY;
        }
    }

    public static class ShapeRectangle extends Shape{

        private Location firstCorner, secondCorner;

        public ShapeRectangle(Location firstCorner, Location secondCorner) {
            this.firstCorner = firstCorner;
            this.secondCorner = secondCorner;
        }

        @Override
        protected void calculateCenter() {
            center = ServiceLocationUtils.getMiddleLocation(firstCorner, secondCorner);
        }

        @Override
        public boolean isWithin(Location location) {
            return ServiceLocationUtils.isWithinTwoLocations(firstCorner, secondCorner, location);
        }

        public Location getFirstCorner() {
            return firstCorner;
        }

        public Location getSecondCorner() {
            return secondCorner;
        }
    }

}


