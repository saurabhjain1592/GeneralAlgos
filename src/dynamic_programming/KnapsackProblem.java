package dynamic_programming;

import java.util.Arrays;
import java.util.HashSet;

public class KnapsackProblem {

	int v[], w[], capacity;

	public KnapsackProblem(int v[], int w[], int capacity) {
		this.v = Arrays.copyOf(v, v.length);
		this.w = Arrays.copyOf(w, w.length);
		this.capacity = capacity;
	}

	public int getMaxKnapsackValue() {
		int a[][] = new int[v.length][capacity + 1];
		for (int i = 0; i < v.length; i++) {
			for (int j = 0; j <= capacity; j++) {
				if (i == 0) {
					if (j < w[i]) {
						a[i][j] = 0;
					} else {
						a[i][j] = v[i];
					}
				} else {
					if (j < w[i]) {
						a[i][j] = a[i - 1][j];
					} else {
						a[i][j] = max(a[i - 1][j], a[i - 1][j - w[i]] + v[i]);
					}
				}
			}
		}
		return a[v.length - 1][capacity];
	}

	public HashSet<Integer> reconstructSolution() {
		int a[][] = new int[v.length][capacity + 1];
		for (int i = 0; i < v.length; i++) {
			for (int j = 0; j <= capacity; j++) {
				if (i == 0) {
					if (j < w[i]) {
						a[i][j] = 0;
					} else {
						a[i][j] = v[i];
					}
				} else {
					if (j < w[i]) {
						a[i][j] = a[i - 1][j];
					} else {
						a[i][j] = max(a[i - 1][j], a[i - 1][j - w[i]] + v[i]);
					}
				}
			}
		}
		HashSet<Integer> hs = new HashSet<Integer>();
		int i = v.length - 1, j = capacity;
		while (i > 0 && j > 0) {
			if (j < w[i]) {
				i = i - 1;
			} else {
				if (a[i - 1][j] > a[i - 1][j - w[i]] + v[i]) {
					i = i - 1;
				} else {
					hs.add(i);
					j = j - w[i];
					i = i - 1;
				}
			}
		}
		if (i == 0 && j >= w[i]) {
			hs.add(i);
		}
		return hs;
	}

	private int max(int a, int b) {
		return a > b ? a : b;
	}

	public static void main(String[] args) {
		int val[] = { 60, 100, 120 };
		int wt[] = { 10, 20, 30 };
		int W = 50;
		KnapsackProblem kp = new KnapsackProblem(val, wt, W);
		System.out.println(kp.getMaxKnapsackValue());
		HashSet<Integer> hs = kp.reconstructSolution();
		for (int i : hs) {
			System.out.println(val[i] + " " + wt[i]);
		}
	}
}
