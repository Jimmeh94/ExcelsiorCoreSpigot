package ecore.services.particles.effects;

import ecore.services.particles.EffectData;
import ecore.services.particles.ServiceParticles;

import java.util.Arrays;

public class TornadoEffect extends AbstractEffect {

	private double[][][] coordinates;
	private int spinner;
	private int lines;

	public TornadoEffect(EffectData effectData, double height, double heightStep, double maxRadius, int lines) {
		super(effectData);

		spinner = 0;
		init(height, heightStep, maxRadius);
		setLines(lines);
	}

	public TornadoEffect(EffectData effectData, ServiceParticles.Loaded loaded, long delay, long interval, int cancel){
		super(effectData, delay, interval, cancel);

		TornadoEffect tornadoEffect = (TornadoEffect)loaded.getEffect();
		coordinates = Arrays.copyOf(tornadoEffect.coordinates, tornadoEffect.coordinates.length);
		lines = tornadoEffect.lines;
		spinner = 0;
	}

	public TornadoEffect(double height, double heightStep, double maxRadius, int lines){
		super(null);

		spinner = 0;
		init(height, heightStep, maxRadius);
		setLines(lines);
	}

	private void init(double height, double heightStep, double maxRadius) {
		coordinates = new double[(int) (height / heightStep) + 1][][];
		double radiusIncreasePerHeight = maxRadius / height;
		int i1 = 0, i2 = 0;
		for (double y = 0; y < height; y += heightStep) {
			double radius = radiusIncreasePerHeight * y;
			coordinates[i1] = new double[61][];
			for (double a = 0; a < Math.PI * 2; a += Math.PI / 30) {
				double x, z;
				x = Math.cos(a) * radius;
				z = Math.sin(a) * radius;
				coordinates[i1][i2] = new double[] { x, y, z };
				i2++;
			}
			i2 = 0;
			i1++;
		}
	}

	@Override
	public void play() {
		spinner++;
		for (int i = 0; i < coordinates.length; i++) {
			// i = current height.
			for (int line = 0; line < lines; line++) {
				int stepPerLine = 60 / lines;
				if (coordinates[i] == null)
					continue;
				double[] coordinateArray = coordinates[i][(((stepPerLine * line) % 60) + (i * 2 % 60)
						+ (59 - ((spinner) % 60))) % 60];
				effectData.setDisplayAt(effectData.getDisplayAt().add(coordinateArray[0], coordinateArray[1], coordinateArray[2]));
				playParticle();
				effectData.getDisplayAt().subtract(coordinateArray[0], coordinateArray[1], coordinateArray[2]);
			}
		}
	}

	public TornadoEffect setLines(int lines) {
		this.lines = lines;
		return this;
	}

}