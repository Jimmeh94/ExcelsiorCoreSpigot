package ecore.services.particles.effects;


import ecore.services.particles.EffectData;

public class CircleEffect extends AbstractEffect {

    public CircleEffect(EffectData effectData) {
        super(effectData);
    }

    public CircleEffect(EffectData effectData, long delay, long interval, int cancel) {
        super(effectData, delay, interval, cancel);
    }

    @Override
    public void play() {

    }
}
