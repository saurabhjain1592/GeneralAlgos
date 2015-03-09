package dynamic_programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class WeightedDirectedGraphBF {
	private int v;
	private int e;
	private ArrayList<ArrayList<WeightedDirectedEdge>> adj;

	public WeightedDirectedGraphBF(int v) {
		this.v = v;
		this.e = 0;
		adj = new ArrayList<ArrayList<WeightedDirectedEdge>>();
		for (int i = 0; i < v; i++) {
			adj.add(new ArrayList<WeightedDirectedEdge>());
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
	}

	public void addEdge(int v, int w, double weight) {
		adj.get(v).add(new WeightedDirectedEdge(v, w, weight));
		e++;
	}

	public double[] getSingleSourceShortestPathDistances(int s) {
		double a[][] = new double[v + 1][v];
		ArrayList<Integer> tempVertices1 = new ArrayList<Integer>();
		ArrayList<Integer> tempVertices2 = new ArrayList<Integer>();
		for (int i = 0; i <= v; i++) {
			for (int j = 0; j < v; j++) {
				a[i][j] = Double.MIN_VALUE;
			}
		}
		for (int i = 0; i < v; i++) {
			if (i == s) {
				a[0][i] = 0;
				tempVertices1.add(i);
			} else {
				a[0][i] = Double.MAX_VALUE;
			}
		}
		int i;
		for (i = 1; i <= v; i++) {
			for (int x : tempVertices1) {
				for (WeightedDirectedEdge wde : adj.get(x)) {
					a[i][wde.to()] = min(a[i - 1][wde.to()],
							a[i - 1][wde.from()] + wde.weight);
					if (a[i][wde.to()] != a[i - 1][wde.to()]) {
						tempVertices2.add(wde.to());
					}
				}
			}
			for (int k = 0; k < v; k++) {
				if (a[i][k] == Double.MIN_VALUE) {
					a[i][k] = a[i - 1][k];
				}
			}
			if (tempVertices2.size() == 0) {
				break;
			}
			if (i == v && tempVertices2.size() != 0) {
				// This means a directed negative weight cycle in the graph
				break;
			}
			tempVertices1.clear();
			tempVertices1.addAll(tempVertices2);
			tempVertices2.clear();
		}
		if (tempVertices2.size() == 0) {
			double d[] = new double[v];
			for (int j = 0; j < v; j++) {
				d[j] = a[i][j];
			}
			return d;
		} else {
			// Throw some exception reporting presence of directed negative
			// weight cycle in the graph
			return null;
		}
	}

	public LinkedList<Integer> reconstructSolution(int s, int t) {
		double a[][] = new double[v + 1][v];
		int p[][] = new int[v + 1][v];
		ArrayList<Integer> tempVertex1 = new ArrayList<Integer>();
		ArrayList<Integer> tempVertex2 = new ArrayList<Integer>();
		for (int i = 0; i <= v; i++) {
			for (int j = 0; j < v; j++) {
				p[i][j] = -1;
				a[i][j] = Double.MIN_VALUE;
			}
		}
		for (int j = 0; j < v; j++) {
			if (j == s) {
				a[0][j] = 0;
				p[0][j] = j;
				tempVertex1.add(j);
			} else {
				a[0][j] = Double.MAX_VALUE;
				p[0][j] = -1;
			}
		}
		int i;
		for (i = 1; i <= v; i++) {
			for (int x : tempVertex1) {
				for (WeightedDirectedEdge wde : adj.get(x)) {
					a[i][wde.to()] = min(a[i - 1][wde.to()],
							a[i - 1][wde.from()] + wde.weight());
					if (a[i][wde.to()] != a[i - 1][wde.to()]) {
						tempVertex2.add(wde.to());
						p[i][wde.to()] = wde.from();
					} else {
						p[i][wde.to()] = p[i - 1][wde.to()];
					}
				}
			}
			for (int k = 0; k < v; k++) {
				if (a[i][k] == Double.MIN_VALUE) {
					a[i][k] = a[i - 1][k];
				}
				if (p[i][k] == -1) {
					p[i][k] = p[i - 1][k];
				}
			}
			if (tempVertex2.size() == 0) {
				break;
			}
			if (tempVertex2.size() != 0 && i == v) {
				// negative weight cycle
				break;
			}
			tempVertex1.clear();
			tempVertex1.addAll(tempVertex2);
			tempVertex2.clear();
		}
		if (tempVertex2.size() != 0) {
			// negative weight cycle
			return null;
		} else {
			LinkedList<Integer> ll = new LinkedList<Integer>();
			ll.addFirst(t);
			int j = p[i][t];
			while (j != -1 && j != s) {
				ll.addFirst(j);
				j = p[i][j];
			}
			if (j == -1) {
				return null;
			} else {
				ll.addFirst(j);
				return ll;
			}
		}
	}

	public double[] getSingleSourceShortestPathDistancesSpaceOptimized(int s) {
		double d1[] = new double[this.v];
		double d2[] = new double[this.v];

		ArrayList<Integer> al1 = new ArrayList<Integer>();
		ArrayList<Integer> al2 = new ArrayList<Integer>();
		for (int j = 0; j < v; j++) {
			if (j == s) {
				d1[j] = 0;
				d2[j] = 0;
				al1.add(j);
			} else {
				d1[j] = Double.MAX_VALUE / 3;
				d2[j] = Double.MAX_VALUE / 3;
			}
		}
		int i;
		for (i = 1; i <= v; i++) {
			for (int x : al1) {
				for (WeightedDirectedEdge wde : this.adj.get(x)) {
					d2[wde.to()] = min(d1[wde.to()],
							d1[wde.from()] + wde.weight());
					if (d1[wde.to()] != d2[wde.to()]) {
						al2.add(wde.to());
					}
				}
			}
			if (al2.isEmpty()) {
				break;
			}
			if (i == v && !al2.isEmpty()) {
				break;
			}
			al1.clear();
			al1.addAll(al2);
			al2.clear();
			d1 = Arrays.copyOf(d2, d2.length);
		}
		if (i == v && !al2.isEmpty()) {
			return null;
		} else {
			return d1;
		}
	}

	public LinkedList<Integer> reconstructSolutionSpaceOptimized(int s, int t) {
		double d1[] = new double[v];
		double d2[] = new double[v];

		int prec1[] = new int[v];
		int prec2[] = new int[v];

		ArrayList<Integer> al1 = new ArrayList<Integer>();
		ArrayList<Integer> al2 = new ArrayList<Integer>();
		for (int j = 0; j < v; j++) {
			if (j == s) {
				d1[j] = 0;
				prec1[j] = j;
				d2[j] = 0;
				prec2[j] = j;
				al1.add(j);
			} else {
				d1[j] = Double.MAX_VALUE / 3;
				prec1[j] = -1;
				d2[j] = Double.MAX_VALUE / 3;
				prec2[j] = -1;
			}
		}
		for (int i = 1; i <= v; i++) {
			for (int x : al1) {
				for (WeightedDirectedEdge wde : this.adj.get(x)) {
					d2[wde.to()] = min(d1[wde.to()],
							d1[wde.from()] + wde.weight());
					if (d2[wde.to()] != d1[wde.to()]) {
						al2.add(wde.to());
						prec2[wde.to()] = wde.from();
					}
				}
			}
			if (al2.isEmpty()) {
				break;
			}
			if (i == v && !al2.isEmpty()) {
				break;
			}
			al1.clear();
			al1.addAll(al2);
			al2.clear();
			d1 = Arrays.copyOf(d2, d2.length);
			prec1 = Arrays.copyOf(prec2, prec2.length);
		}
		LinkedList<Integer> ll = new LinkedList<Integer>();
		ll.addFirst(t);
		int j = prec1[t];
		while (j != s && j != -1) {
			ll.addFirst(j);
			j = prec1[j];
		}
		if (j == -1) {
			return null;
		} else {
			ll.addFirst(j);
			return ll;
		}
	}

	private double min(double a, double b) {
		return a < b ? a : b;
	}

	public static void main(String[] args) {
		WeightedDirectedGraphBF wdg = new WeightedDirectedGraphBF(5);
		wdg.addEdge(0, 1, -1);
		wdg.addEdge(0, 2, 4);
		wdg.addEdge(1, 2, 3);
		wdg.addEdge(1, 3, 2);
		wdg.addEdge(1, 4, 2);
		wdg.addEdge(3, 2, 5);
		wdg.addEdge(3, 1, 1);
		wdg.addEdge(4, 3, -3);

		double d[] = wdg.getSingleSourceShortestPathDistances(0);
		int i = 0;
		for (double x : d) {
			System.out.println(i + " : " + x);
			i++;
		}
		LinkedList<Integer> ll = wdg.reconstructSolution(0, 4);
		i = 0;
		for (int x : ll) {
			if (i != ll.size() - 1)
				System.out.print(x + "->");
			else
				System.out.print(x);
			i++;
		}

		System.out.println();
		System.out.println();

		d = wdg.getSingleSourceShortestPathDistancesSpaceOptimized(0);
		i = 0;
		for (double x : d) {
			System.out.println(i + " : " + x);
			i++;
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