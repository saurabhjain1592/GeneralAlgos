package hueristics;

import java.util.Arrays;

public class KnapsackDPHueristic {
	int v[];
	double w[], capacity;

	public KnapsackDPHueristic(int v[], double w[], double capacity) {
		this.v = Arrays.copyOf(v, v.length);
		this.w = Arrays.copyOf(w, w.length);
		this.capacity = capacity;
	}

	public int getMaxKnapsackValue() {
		int sum = 0;
		for (int i = 0; i < v.length; i++) {
			sum += v[i];
		}
		double wt[][] = new double[v.length + 1][sum + 1];
		for (int i = 0; i <= v.length; i++) {
			for (int j = 0; j <= sum; j++) {
				if (i == 0) {
					if (j == 0) {
						wt[i][j] = 0;
					} else {
						wt[i][j] = Integer.MAX_VALUE / 4;
					}
				} else {
					if (j < v[i - 1])
						wt[i][j] = wt[i - 1][j];
					else
						wt[i][j] = Math.min(wt[i - 1][j], w[i - 1]
								+ wt[i - 1][j - v[i - 1]]);
				}
			}
		}
		int maxV = Integer.MIN_VALUE;
		for (int i = 0; i <= sum; i++) {
			if (wt[v.length][i] <= capacity) {
				maxV = Math.max(i, maxV);
			}
		}
		return maxV;
	}

	public static void main(String[] args) {
		int val[] = { 60, 100, 120 };
		double wt[] = { 10, 20, 30 };
		double W = 50;
		KnapsackDPHueristic kdh = new KnapsackDPHueristic(val, wt, W);
		System.out.println(kdh.getMaxKnapsackValue());
	}
}
