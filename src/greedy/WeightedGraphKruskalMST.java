package greedy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class WeightedGraphKruskalMST {
	private ArrayList<ArrayList<WeightedEdge>> adj;
	private int v;
	private int e;

	public WeightedGraphKruskalMST(int v) {
		adj = new ArrayList<ArrayList<WeightedEdge>>();
		this.v = v;
		for (int i = 0; i < v; i++) {
			adj.add(new ArrayList<WeightedEdge>());
		}
	}

	public WeightedGraphKruskalMST(WeightedGraphKruskalMST weightedGraph) {
		this.adj = new ArrayList<ArrayList<WeightedEdge>>();
		this.v = weightedGraph.v;
		this.e = weightedGraph.e;
		for (ArrayList<WeightedEdge> al : weightedGraph.adj) {
			ArrayList<WeightedEdge> temp = new ArrayList<WeightedEdge>(al);
			this.adj.add(temp);
		}
	}

	private class WeightedEdge implements Comparable<WeightedEdge> {
		int x, y;
		double weight;

		public WeightedEdge(int x, int y, double weight) {
			this.x = x;
			this.y = y;
			this.weight = weight;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			WeightedEdge other = (WeightedEdge) obj;
			return this.x == other.x && this.y == other.y
					&& this.weight == other.weight;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = result * x + prime;
			result = result * y + prime;
			result = result * Double.valueOf(weight).hashCode() + prime;
			return result;
		}

		@Override
		public int compareTo(WeightedEdge we) {
			if (this.weight - we.weight != 0)
				return Double.valueOf(this.weight).compareTo(
						Double.valueOf(we.weight));
			if (this.x - we.x != 0)
				return this.x - we.x;
			return this.y - we.y;
		}
	}

	public void addEdge(int x, int y, int weight) {
		adj.get(x).add(new WeightedEdge(x, y, weight));
		adj.get(y).add(new WeightedEdge(y, x, weight));
		e++;
	}

	public HashSet<WeightedEdge> kruskalMst() {
		HashSet<WeightedEdge> hs = new HashSet<WeightedEdge>();
		ArrayList<WeightedEdge> completeEdgeList = new ArrayList<WeightedEdge>();
		for (ArrayList<WeightedEdge> al : adj) {
			completeEdgeList.addAll(al);
		}
		Collections.sort(completeEdgeList);
		UnionFindEager ufe = new UnionFindEager(this.v);
		for (WeightedEdge we : completeEdgeList) {
			if (ufe.isConnected(we.x, we.y)) {
				// do nothing
			} else {
				ufe.union(we.x, we.y);
				hs.add(we);
			}
			if (hs.size() == this.v - 1)
				break;
		}
		return hs;
	}
}
