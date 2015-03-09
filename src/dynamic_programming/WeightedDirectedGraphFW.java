package dynamic_programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class WeightedDirectedGraphFW {

	private ArrayList<ArrayList<WeightedDirectedEdge>> adj;

	private int v;

	private int e;

	public WeightedDirectedGraphFW(int v) {
		this.v = v;
		this.e = 0;
		this.adj = new ArrayList<ArrayList<WeightedDirectedEdge>>();
		for (int i = 0; i < v; i++) {
			this.adj.add(new ArrayList<WeightedDirectedEdge>());
		}
	}

	private class WeightedDirectedEdge {

		private int v, w;

		private double weight;

		public WeightedDirectedEdge(int v, int w, double weight) {
			this.v = v;
			this.w = w;
			this.weight = weight;
		}

		public int from() {
			return v;
		}

		public int to() {
			return w;
		}

		public double weight() {
			return weight;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + v;
			result = prime * result + w;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			WeightedDirectedEdge other = (WeightedDirectedEdge) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (v != other.v)
				return false;
			if (w != other.w)
				return false;
			return true;
		}

		private WeightedDirectedGraphFW getOuterType() {
			return WeightedDirectedGraphFW.this;
		}

	}

	public double[][] getAllPairShortestPath() {
		double d[][][] = new double[v][v][v];

		for (int i = 0; i < v; i++) {
			for (int j = 0; j < v; j++) {
				if (i == j)
					d[i][j][0] = 0;
				else
					d[i][j][0] = Double.MAX_VALUE / 3;
			}
			for (WeightedDirectedEdge wde : adj.get(i)) {
				if (wde.from() != wde.to() || wde.weight() < 0)
					d[wde.from()][wde.to()][0] = wde.weight();
			}
		}
		for (int k = 1; k < v; k++) {
			for (int i = 0; i < v; i++) {
				for (int j = 0; j < v; j++) {
					d[i][j][k] = min(d[i][j][k - 1], d[i][k][k - 1]
							+ d[k][j][k - 1]);
				}
			}
		}
		double f[][] = new double[v][v];
		boolean negativeWtCycleFlg = false;
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < v; j++) {
				f[i][j] = d[i][j][v - 1];
				if (i == j && f[i][j] < 0) {
					negativeWtCycleFlg = true;
				}
			}
		}
		if (negativeWtCycleFlg) {
			return null;
		} else {
			return f;
		}
	}

	private double min(double a, double b) {
		return a < b ? a : b;
	}

	public LinkedList<Integer> reconstructSolution(int s, int t) {
		double a[][][] = new double[v][v][v];
		int p[][] = new int[v][v];

		for (int i = 0; i < v; i++) {
			for (int j = 0; j < v; j++) {
				p[i][j] = -1;
			}
		}
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < v; j++) {
				if (i == j) {
					a[i][j][0] = 0;
					p[i][j] = i;
				} else {
					a[i][j][0] = Double.MAX_VALUE / 3;
				}
			}
			for (WeightedDirectedEdge wde : adj.get(i)) {
				if (wde.from() != wde.to() || wde.weight() < 0) {
					a[wde.from()][wde.to()][0] = wde.weight();
					p[wde.from()][wde.to()] = wde.from();
				}
			}
		}
		for (int k = 1; k < v; k++) {
			for (int i = 0; i < v; i++) {
				for (int j = 0; j < v; j++) {
					a[i][j][k] = min(a[i][j][k - 1], a[i][k][k - 1]
							+ a[k][j][k - 1]);
					if (a[i][j][k] != a[i][j][k - 1]) {
						p[i][j] = k;
					}
				}
			}
		}
		double f[][] = new double[v][v];
		boolean negativeWtCycleFlg = false;
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < v; j++) {
				f[i][j] = a[i][j][v - 1];
				if (i == j && f[i][j] < 0) {
					negativeWtCycleFlg = true;
				}
			}
		}
		if (negativeWtCycleFlg) {
			return null;
		} else {
			LinkedList<Integer> ll = createSolution(p, s, t);
			if (ll != null)
				ll.addLast(t);
			return ll;
		}
	}

	public LinkedList<Integer> createSolution(int p[][], int l, int r) {
		int x = p[l][r];
		if (x == -1) {
			return null;
		}
		if (l == x) {
			LinkedList<Integer> ll = new LinkedList<Integer>();
			ll.add(l);
			return ll;
		}
		if (r == x) {
			LinkedList<Integer> ll = new LinkedList<Integer>();
			ll.add(l);
			return ll;
		}
		LinkedList<Integer> ll = new LinkedList<Integer>();
		LinkedList<Integer> ll1 = createSolution(p, l, x);
		ll.addAll(ll1);
		LinkedList<Integer> ll2 = createSolution(p, x, r);
		ll.addAll(ll2);
		return ll;
	}

	public void addEdge(int v, int w, double weight) {
		adj.get(v).add(new WeightedDirectedEdge(v, w, weight));
		e++;
	}

	public double[][] getAllPairShortestPathSpaceOptimized() {
		double d[][] = new double[v][v];
		double dCopy[][] = new double[v][v];
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < v; j++) {
				if (i == j) {
					d[i][j] = 0;
				} else {
					d[i][j] = Double.MAX_VALUE / 3;
				}
			}
			for (WeightedDirectedEdge wde : adj.get(i)) {
				if (wde.from() != wde.to() || wde.weight() < 0) {
					d[wde.from()][wde.to()] = wde.weight();
				}
			}
		}
		for (int i = 0; i < d.length; i++) {
			dCopy[i] = Arrays.copyOf(d[i], d[i].length);
		}
		for (int k = 1; k < v; k++) {
			for (int i = 0; i < v; i++) {
				for (int j = 0; j < v; j++) {
					dCopy[i][j] = min(d[i][j], d[i][k] + d[k][j]);
				}
			}
			for (int i = 0; i < d.length; i++) {
				d[i] = Arrays.copyOf(dCopy[i], dCopy[i].length);
			}
		}
		for (int i = 0; i < v; i++) {
			if (d[i][i] < 0) {
				return null;
			}
		}
		return d;
	}

	public LinkedList<Integer> reconstructSolutionSpaceOptimized(int s, int t) {
		double d[][] = new double[v][v];
		double dCopy[][] = new double[v][v];

		int p[][] = new int[v][v];

		for (int i = 0; i < v; i++) {
			for (int j = 0; j < v; j++) {
				if (i == j) {
					d[i][j] = 0;
					p[i][j] = i;
				} else {
					d[i][j] = Double.MAX_VALUE / 3;
					p[i][j] = -1;
				}
			}
			for (WeightedDirectedEdge wde : this.adj.get(i)) {
				if (wde.from() != wde.to() || wde.weight() < 0) {
					d[wde.from()][wde.to()] = wde.weight();
					p[wde.from()][wde.to()] = wde.from();
				}
			}
		}

		for (int i = 0; i < d.length; i++) {
			dCopy[i] = Arrays.copyOf(d[i], d[i].length);
		}

		for (int k = 1; k < v; k++) {
			for (int i = 0; i < v; i++) {
				for (int j = 0; j < v; j++) {
					dCopy[i][j] = min(d[i][j], d[i][k] + d[k][j]);
					if (d[i][j] != dCopy[i][j]) {
						p[i][j] = k;
					}
				}
			}
			for (int i = 0; i < d.length; i++) {
				d[i] = Arrays.copyOf(dCopy[i], dCopy[i].length);
			}
		}
		for (int i = 0; i < v; i++) {
			if (d[i][i] < 0)
				return null;
		}
		LinkedList<Integer> ll = createSolutionOptimized(p, s, t);
		ll.addLast(t);
		return ll;
	}

	private LinkedList<Integer> createSolutionOptimized(int p[][], int s, int t) {
		int x = p[s][t];
		if (x == -1)
			return null;
		if (x == s) {
			LinkedList<Integer> ll = new LinkedList<Integer>();
			ll.add(s);
			return ll;
		} else {
			LinkedList<Integer> ll1 = createSolutionOptimized(p, s, x);
			LinkedList<Integer> ll2 = createSolutionOptimized(p, x, t);
			if (ll1 == null || ll2 == null)
				return null;
			ll1.addAll(ll2);
			return ll1;
		}
	}

	public static void main(String[] args) {
		WeightedDirectedGraphFW wdg = new WeightedDirectedGraphFW(5);
		wdg.addEdge(0, 1, -1);
		wdg.addEdge(0, 2, 4);
		wdg.addEdge(1, 2, 3);
		wdg.addEdge(1, 3, 2);
		wdg.addEdge(1, 4, 2);
		wdg.addEdge(3, 2, 5);
		wdg.addEdge(3, 1, 1);
		wdg.addEdge(4, 3, -3);

		double d[][] = wdg.getAllPairShortestPath();
		for (int i = 0; i < d.length; i++) {
			for (int j = 0; j < d.length; j++) {
				System.out.println(i + " to " + j + " : " + d[i][j]);
			}
		}
		LinkedList<Integer> ll = wdg.reconstructSolution(0, 4);
		int i = 0;
		for (int x : ll) {
			if (i != ll.size() - 1)
				System.out.print(x + "->");
			else
				System.out.print(x);
			i++;
		}

		System.out.println();
		System.out.println();

		d = wdg.getAllPairShortestPathSpaceOptimized();
		for (i = 0; i < d.length; i++) {
			for (int j = 0; j < d.length; j++) {
				System.out.println(i + " to " + j + " : " + d[i][j]);
			}
		}
		ll = wdg.reconstructSolutionSpaceOptimized(0, 4);
		i = 0;
		for (int x : ll) {
			if (i != ll.size() - 1)
				System.out.print(x + "->");
			else
				System.out.print(x);
			i++;
		}
	}
}