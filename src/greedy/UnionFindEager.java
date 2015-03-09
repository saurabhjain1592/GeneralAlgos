package greedy;

import java.util.ArrayList;

public class UnionFindEager {
	private int a[];
	private ArrayList<ArrayList<Integer>> s;

	public UnionFindEager(int n) {
		a = new int[n];
		s = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < n; i++) {
			a[i] = i;
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.add(i);
			s.add(temp);
		}
	}

	public void union(int x, int y) {
		if (s.get(x).size() > s.get(y).size()) {
			for (int p : s.get(y)) {
				a[p] = x;
			}
			s.get(x).addAll(s.get(y));
			s.get(y).clear();
		} else {
			for (int p : s.get(x)) {
				a[p] = y;
			}
			s.get(y).addAll(s.get(x));
			s.get(x).clear();
		}
	}

	public int find(int x) {
		return a[x];
	}

	public boolean isConnected(int x, int y) {
		return find(x) == find(y);
	}
}
