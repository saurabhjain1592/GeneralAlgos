package hueristics;

import java.util.Arrays;

public class KnapsackHueristicDPApproximate {
	private double v[], w[], capacity;

	public KnapsackHueristicDPApproximate(double v[], double w[],
			double capacity) {
		this.v = Arrays.copyOf(v, v.length);
		this.w = Arrays.copyOf(w, w.length);
		this.capacity = capacity;
	}

	public double getApproximateOptimalValue(double epsilon) {
		if (epsilon < 0.01 && epsilon > 0.2)
			return -1;
		double max = Double.MIN_VALUE;
		for (int i = 0; i < v.length; i++) {
			max = Math.max(max, v[i]);
		}
		double m = (epsilon * max) / v.length;
		int v1[] = new int[v.length];
		int sum = 0;
		for (int i = 0; i < v.length; i++) {
			v1[i] = (int) (v[i] / m);
			sum += v1[i];
		}

		double wt[][] = new double[v1.length + 1][sum + 1];
		for (int i = 0; i <= v1.length; i++) {
			for (int j = 0; j <= sum; j++) {
				if (i == 0) {
					if (j == 0) {
						wt[i][j] = 0;
					} else {
						wt[i][j] = Double.MAX_VALUE / 4;
					}
				} else {
					if (j >= v1[i - 1]) {
						wt[i][j] = Math.min(wt[i - 1][j], w[i - 1]
								+ wt[i - 1][j - v1[i - 1]]);
					} else {
						wt[i][j] = wt[i - 1][j];
					}
				}
			}
		}
		int maxV = Integer.MIN_VALUE;
		int q = -1;
		for (int j = 0; j <= sum; j++) {
			if (wt[v1.length][j] <= capacity) {
				int t = maxV;
				maxV = Math.max(maxV, j);
				if (t != maxV)
					q = j;
			}
		}

		int p = v.length;
		double optimalV = 0;
		while (p != 0 && q != 0) {
			if (q >= v1[p - 1]
					&& wt[p][q] == wt[p - 1][q - v1[p - 1]] + w[p - 1]) {
				optimalV += v[p - 1];
				q = q - v1[p - 1];
				p = p - 1;
			} else {
				p = p - 1;
			}
		}
		return optimalV;
	}

	public static void main(String[] args) {
		double val[] = { 60, 100, 120 };
		double wt[] = { 10, 20, 30 };
		double W = 50;
		KnapsackHueristicDPApproximate khda = new KnapsackHueristicDPApproximate(
				val, wt, W);
		System.out.println(khda.getApproximateOptimalValue(0.01));
	}
}
