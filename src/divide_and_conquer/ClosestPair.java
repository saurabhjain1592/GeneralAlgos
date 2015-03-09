package divide_and_conquer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

	private static class Pair {
		double x, y;

		public Pair(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			int result = 1;
			int prime = 31;
			result = result * prime + Double.valueOf(x).hashCode();
			result = result * prime + Double.valueOf(y).hashCode();
			return result;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null) {
				return false;
			}
			if (this.getClass() != o.getClass()) {
				return false;
			}
			Pair other = (Pair) o;
			return this.x == other.x && this.y == other.y;
		}

		@Override
		public String toString() {
			return x + " " + y;
		}
	}

	private static class PairXComparator implements Comparator<Pair> {
		@Override
		public int compare(Pair o1, Pair o2) {
			return o1.x - o2.x != 0 ? (o1.x - o2.x > 0 ? 1 : -1)
					: (o1.y - o2.y > 0 ? 1 : (o1.y - o2.y < 0 ? -1 : 0));
		}
	}

	private static class PairYComparator implements Comparator<Pair> {
		@Override
		public int compare(Pair o1, Pair o2) {
			return o1.y - o2.y != 0 ? (o1.y - o2.y > 0 ? 1 : -1)
					: (o1.x - o2.x > 0 ? 1 : (o1.x - o2.x < 0 ? -1 : 0));
		}
	}

	private Pair pairs[];

	public ClosestPair(Pair pairs[]) {
		this.pairs = new Pair[pairs.length];
		this.pairs = Arrays.copyOf(pairs, pairs.length);
	}

	public ArrayList<Pair> getClosestPair() {
		if (pairs.length < 2)
			return null;

		Pair[] pairsX = new Pair[pairs.length];
		pairsX = Arrays.copyOf(pairs, pairs.length);
		Arrays.sort(pairsX, new PairXComparator());

		Pair[] pairsY = new Pair[pairs.length];
		pairsY = Arrays.copyOf(pairs, pairs.length);
		Arrays.sort(pairsY, new PairYComparator());

		ArrayList<Pair> hs = getClosestPair(pairsX, pairsY, 0, pairs.length - 1);
		return hs;
	}

	private ArrayList<Pair> getClosestPair(Pair pairX[], Pair pairY[], int l,
			int r) {
		if (r - l <= 2) {
			double minDist = Double.MAX_VALUE;
			ArrayList<Pair> al = new ArrayList<Pair>();
			for (int i = l; i <= r; i++) {
				for (int j = i + 1; j <= r; j++) {
					if (minDist > getEucladianDistance(pairX[i], pairX[j])) {
						minDist = getEucladianDistance(pairX[i], pairX[j]);
						al.clear();
						al.add(pairX[i]);
						al.add(pairX[j]);
					}
				}
			}
			return al;
		} else {
			Pair temp[] = new Pair[r - l + 1];
			for (int k = 0; k < temp.length; k++) {
				temp[k] = pairY[l + k];
			}
			int i = l, j = (l + r) / 2 + 1, k = 0;
			double xMid = pairX[(l + r) / 2].x;
			while (k < temp.length) {
				if (temp[k].x <= xMid && i <= (l + r) / 2) {
					pairY[i++] = temp[k++];
				} else {
					pairY[j++] = temp[k++];
				}
			}
			ArrayList<Pair> al1 = getClosestPair(pairX, pairY, l, (l + r) / 2);
			ArrayList<Pair> al2 = getClosestPair(pairX, pairY, (l + r) / 2 + 1,
					r);
			double sameMinDist = min(
					getEucladianDistance(al1.get(0), al1.get(1)),
					getEucladianDistance(al2.get(0), al2.get(1)));
			ArrayList<Pair> alSame = sameMinDist == getEucladianDistance(
					al1.get(0), al1.get(1)) ? al1 : al2;
			ArrayList<Pair> alCross = closestSplitPair(pairX, pairY,
					sameMinDist, l, r);
			double crossMinDist = getEucladianDistance(alCross);
			return sameMinDist < crossMinDist ? alSame : alCross;
		}
	}

	private ArrayList<Pair> closestSplitPair(Pair pairX[], Pair pairY[],
			double sameMinDist, int l, int r) {
		ArrayList<Pair> tempList = new ArrayList<Pair>();
		double midX = pairX[(l + r) / 2].x;
		for (int i = 0; i < pairY.length; i++) {
			if (pairY[i].x >= midX - sameMinDist
					&& pairY[i].x <= midX + sameMinDist) {
				tempList.add(pairY[i]);
			}
		}

		double tempMinDist = Integer.MIN_VALUE;
		ArrayList<Pair> crossPair = new ArrayList<Pair>();

		for (int i = 0; i < tempList.size(); i++) {
			for (int j = i + 1; j < min(i + 7, tempList.size()); j++) {
				if (tempMinDist > getEucladianDistance(tempList.get(i),
						tempList.get(j))) {
					tempMinDist = getEucladianDistance(tempList.get(i),
							tempList.get(j));
					crossPair.clear();
					crossPair.add(tempList.get(i));
					crossPair.add(tempList.get(j));
				}
			}
		}
		return crossPair;
	}

	private static double min(double a, double b) {
		return a < b ? a : b;
	}

	private double getEucladianDistance(Pair p1, Pair p2) {
		return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y)
				* (p1.y - p2.y));
	}

	private double getEucladianDistance(ArrayList<Pair> al) {
		if (al == null || al.size() < 2) {
			return Double.MAX_VALUE;
		} else {
			return getEucladianDistance(al.get(0), al.get(1));
		}
	}

	public static void main(String[] args) {
		Pair P[] = { new Pair(2, 3), new Pair(12, 30), new Pair(40, 50),
				new Pair(5, 1), new Pair(12, 10), new Pair(3, 4) };
		ClosestPair cp = new ClosestPair(P);
		ArrayList<Pair> pairs = cp.getClosestPair();
		System.out.println(pairs.get(0));
		System.out.println(pairs.get(1));
	}
}
