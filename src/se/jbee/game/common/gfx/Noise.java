package se.jbee.game.common.gfx;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Noise {

	private final SimplexOctave[] octaves;
	private final double[] frequencys;
	private final double[] amplitudes;

	public Noise(int largestFeature, double persistence, int seed) {
		// recieves a number (eg 128) and calculates what power of 2 it is (e.g. 2^7)
		int nOctaves = (int) Math.ceil(Math.log10(largestFeature) / Math.log10(2));

		octaves = new SimplexOctave[nOctaves];
		frequencys = new double[nOctaves];
		amplitudes = new double[nOctaves];

		Random rnd = new Random(seed);

		for (int i = 0; i < nOctaves; i++) {
			octaves[i] = new SimplexOctave(rnd.nextInt());
			frequencys[i] = Math.pow(2, i);
			amplitudes[i] = Math.pow(persistence, octaves.length - i);
		}
	}

	public double noiseAt(int x, int y) {
		double res = 0d;
		for (int i = 0; i < octaves.length; i++) {
			res = res + octaves[i].noise(x / frequencys[i], y / frequencys[i]) * amplitudes[i];
		}
		return res;
	}
	
	/**
	 * @return an array with values between 0.0f and 1.0f.
	 */
	public float[] normalizedNoise(int w, int h) {
		float[] noise = new float[w*h];
		int k = 0;
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				// the noiseAt results in -1.0 to 1.0; the below does shift it to 0.0 to 1.0
				noise[k++] = 0.5f*(1f+(float)noiseAt(x, y));;
			}
		}
		return noise;
	}

	public static BufferedImage image(int w, int h, float alpha, Noise noise) {
		BufferedImage image = new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);
	
		// TODO pre-compute noise first an buffer it, than (re)use it to compute different pixels/images from it
		// the images are then just buffer based on the values that make the image, if those changes a new image is created
		int[] pixels = new int[w * h];
		int k = 0;
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				// the noiseAt results in -1.0 to 1.0; the below does shift it to 0.0 to 1.0
				float rgb = 0.5f*(1f+(float)noise.noiseAt(x, y));
				rgb = min(1f, max(0f, rgb));
				float a = min(0.9f, max(0.3f, (1.5f-rgb)*rgb));
				Color c = new Color(1f, rgb, rgb/2f, a); 
				//c = new Color(rgb, rgb, rgb, alpha);
				pixels[k++] = c.getRGB();
			}
		}
		image.getRaster().setDataElements(0,  0,  w, h, pixels);
		return image;
	}

}
