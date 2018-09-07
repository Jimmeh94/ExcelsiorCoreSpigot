package ecore.services.particles;

import org.bukkit.Location;
import org.bukkit.Particle;

public class EffectData {

    public static EffectDataBuilder getBuilder(){return new EffectDataBuilder();}

    private Location center, displayAt;

    private int amount = 10;
    protected Particle particle = Particle.FLAME;
    private double xOffset, yOffset, zOffset;
    private boolean randomizeOffsets = false;
    private double velocity;
    private double extra = -1;
    private Cloneable data;
    private DisplayMethod displayMethod;

    public EffectData(EffectDataBuilder builder) {
        this.center = builder.center;
        this.displayAt = center.clone();

        this.amount = builder.amount;
        this.particle = builder.particle;
        this.xOffset = builder.xOffset;
        this.yOffset = builder.yOffset;
        this.zOffset = builder.zOffset;
        this.randomizeOffsets = builder.randomizeOffsets;
        this.velocity = builder.velocity;
        this.extra = builder.extra;
        this.data = builder.data;
        this.displayMethod = builder.displayMethod;
    }

    public void display(){
        displayMethod.display(this);
    }

    public void setDisplayAt(Location center) {
        this.center = center;
    }

    public Cloneable getData() {
        return data;
    }

    public int getAmount() {
        return amount;
    }

    public Particle getParticle() {
        return particle;
    }

    public double getxOffset() {
        return xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public double getzOffset() {
        return zOffset;
    }

    public boolean isRandomizeOffsets() {
        return randomizeOffsets;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getExtra() {
        return extra;
    }

    public Location getCenter() {
        return center;
    }

    public Location getDisplayAt() {
        return displayAt;
    }

    public static class EffectDataBuilder {

        private Location center;

        private int amount = 10;
        protected Particle particle = Particle.FLAME;
        private double xOffset = 0, yOffset = 0, zOffset = 0;
        private boolean randomizeOffsets = false;
        private double velocity;
        private double extra = -1;
        private Cloneable data;
        private DisplayMethod displayMethod;

        public EffectDataBuilder displayMethod(DisplayMethod d){
            this.displayMethod = d;
            return this;
        }

        public EffectDataBuilder extra(double extra){
            this.extra = extra;
            return this;
        }

        public EffectDataBuilder data(Cloneable data){
            this.data = data;
            return this;
        }

        public EffectDataBuilder amount(int amount){
            this.amount = amount;
            return this;
        }

        public EffectDataBuilder particle(Particle particle){
            this.particle = particle;
            return this;
        }

        public EffectDataBuilder particleOffsets(double x, double y, double z){
            this.xOffset = x;
            this.yOffset = y;
            this.zOffset = z;
            return this;
        }

        public EffectDataBuilder randomizeOffsets(boolean a){
            randomizeOffsets = a;
            return this;
        }

        public EffectDataBuilder velocity(double v){
            this.velocity = v;
            return this;
        }

        public EffectDataBuilder center(Location center){
            this.center = center.clone();
            return this;
        }

        public EffectData build(){return new EffectData(this);}

    }
}
