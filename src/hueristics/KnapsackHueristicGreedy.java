package hueristics;

import java.util.Arrays;
import java.util.TreeSet;

public class KnapsackHueristicGreedy {
	private double v[], w[], capacity;

	public KnapsackHueristicGreedy(double v[], double w[], double capacity) {
		this.v = Arrays.copyOf(v, v.length);
		this.w = Arrays.copyOf(w, w.length);
		this.capacity = capacity;
	}

	public double getApproximateOptimalValue() {
		TreeSet<KnapsackItem> ts = new TreeSet<KnapsackItem>();
		double maxValue = Double.MIN_VALUE;
		for (int i = 0; i < v.length; i++) {
			ts.add(new KnapsackItem(v[i], w[i], i));
			if (maxValue < v[i])
				;
			maxValue = v[i];
		}
		double c = capacity;
		double sum = 0;
		for (KnapsackItem ki : ts) {
			if (c >= ki.w)
				sum += ki.v;
			c -= ki.w;
		}
		return maxValue > sum ? maxValue : sum;
	}

	private class KnapsackItem implements Comparable<KnapsackItem> {
		double v, w, pref;
		int index;

		public KnapsackItem(double v, double w, int index) {
			this.v = v;
			this.w = w;
			this.pref = w != 0 ? (v / w) : Double.MAX_VALUE;
			this.index = index;
		}

		public int compareTo(KnapsackItem ki) {
			if (this.pref != ki.pref) {
				return (int) (this.pref - ki.pref);
			}
			if (this.v != ki.v) {
				return (int) (this.v - ki.v);
			}
			if (this.w != ki.w) {
				return (int) (this.w - ki.w);
			}
			return this.index - ki.index;
		}
	}

	public static void main(String[] args) {
		double val[] = { 60, 100, 120 };
		double wt[] = { 10, 20, 30 };
		double W = 50;
		KnapsackHueristicGreedy khg = new KnapsackHueristicGreedy(val, wt, W);
		System.out.println(khg.getApproximateOptimalValue());
	}
}
