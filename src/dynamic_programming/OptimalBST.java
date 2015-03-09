package dynamic_programming;

import java.util.Arrays;

public class OptimalBST<T extends Comparable<T>> {

	private T k[];

	private double f[];

	public OptimalBST(T k[], double f[]) {
		this.k = Arrays.copyOf(k, k.length);
		Arrays.sort(k);
		this.f = Arrays.copyOf(f, f.length);
	}

	public double getOptimalSearchCost() {
		double c[][] = new double[k.length][k.length];
		for (int s = 1; s <= k.length; s++) {
			for (int i = 0; i < k.length - s + 1; i++) {
				double minCost = Double.MAX_VALUE;
				double freq = 0;
				for (int r = i; r < (i + s); r++) {
					freq += f[r];
				}
				for (int r = i; r < (i + s); r++) {
					double cost;
					if (r == i && r == (i + s - 1)) {
						cost = freq;
					} else if (r == i) {
						cost = freq + c[r + 1][i + s - 1];
					} else if (r == (i + s - 1)) {
						cost = freq + c[i][r - 1];
					} else {
						cost = freq + c[i][r - 1] + c[r + 1][i + s - 1];
					}

					if (cost < minCost) {
						minCost = cost;
					}
				}
				c[i][i + s - 1] = minCost;
			}
		}
		return c[0][k.length - 1];
	}

	public BST<T> reconstructSolution() {
		double c[][] = new double[k.length][k.length];
		int rootIndex[][] = new int[k.length][k.length];
		for (int s = 1; s <= k.length; s++) {
			for (int i = 0; i < k.length - s + 1; i++) {
				double minCost = Double.MAX_VALUE;
				double freq = 0;
				for (int r = i; r < (i + s); r++) {
					freq += f[r];
				}
				for (int r = i; r < (i + s); r++) {
					double cost;
					if (r == i && r == (i + s - 1)) {
						cost = freq;
					} else if (r == i) {
						cost = freq + c[r + 1][i + s - 1];
					} else if (r == (i + s - 1)) {
						cost = freq + c[i][r - 1];
					} else {
						cost = freq + c[i][r - 1] + c[r + 1][i + s - 1];
					}
					if (cost < minCost) {
						minCost = cost;
						rootIndex[i][i + s - 1] = r;
					}
				}
				c[i][i + s - 1] = minCost;
			}
		}
		int i = 0, j = k.length - 1;
		BST<T> bst = new BST<T>();
		createBST(bst, k, rootIndex, i, j);
		return bst;
	}

	private void createBST(BST<T> bst, T k[], int rootIndex[][], int i, int j) {
		if (i <= j) {
			int r = rootIndex[i][j];
			bst.insert(k[r]);
			createBST(bst, k, rootIndex, i, r - 1);
			createBST(bst, k, rootIndex, r + 1, j);
		}
	}

	public static void main(String[] args) {
		Integer keys[] = { 10, 12, 20 };
		double freq[] = { 34, 8, 50 };
		OptimalBST<Integer> ob = new OptimalBST<Integer>(keys, freq);
		System.out.println(ob.getOptimalSearchCost());
		BST<Integer> bst = ob.reconstructSolution();
		bst.inOrder();
	}

	private static class BST<T extends Comparable<T>> {

		private BSTNode root = null;

		private class BSTNode {

			T data;

			BSTNode left;

			BSTNode right;

			public BSTNode(T data) {
				this.data = data;
				this.left = null;
				this.right = null;
			}
		}

		public void insert(T data) {
			root = insert(root, data);
		}

		private BSTNode insert(BSTNode r, T data) {
			if (r == null) {
				return new BSTNode(data);
			} else {
				if (data.compareTo(r.data) > 0) {
					r.right = insert(r.right, data);
				} else {
					r.left = insert(r.left, data);
				}
			}
			return r;
		}

		public void inOrder() {
			inOrder(root);
		}

		private void inOrder(BSTNode r) {
			if (r != null) {
				inOrder(r.left);
				System.out.println(r.data);
				inOrder(r.right);
			}
		}
	}
}