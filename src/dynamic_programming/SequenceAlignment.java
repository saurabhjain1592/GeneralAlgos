package dynamic_programming;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

public class SequenceAlignment {

	private Character a[], b[];

	private int w[][];

	private int wGap;

	public SequenceAlignment(Character a[], Character b[], int w[][], int wGap) {
		this.a = Arrays.copyOf(a, a.length);
		this.b = Arrays.copyOf(b, b.length);
		this.w = new int[w.length][w[0].length];
		for (int i = 0; i < w.length; i++) {
			for (int j = 0; j < w[i].length; j++) {
				this.w[i][j] = w[i][j];
			}
		}
		this.wGap = wGap;
	}

	public int getLeastCost() {
		int c[][] = new int[a.length + 1][b.length + 1];
		Character tempA[] = (Character[]) Array.newInstance(a.getClass()
				.getComponentType(), a.length + 1);
		Character tempB[] = (Character[]) Array.newInstance(b.getClass()
				.getComponentType(), b.length + 1);
		for (int i = 1; i < tempA.length; i++) {
			tempA[i] = a[i - 1];
		}
		for (int i = 1; i < tempB.length; i++) {
			tempB[i] = b[i - 1];
		}
		for (int i = 0; i <= a.length; i++) {
			for (int j = 0; j <= b.length; j++) {
				if (i == 0) {
					c[i][j] = j * wGap;
				} else if (j == 0) {
					c[i][j] = i * wGap;
				} else {
					c[i][j] = min(c[i - 1][j - 1] + w[tempA[i]][tempB[j]],
							c[i - 1][j] + wGap, c[i][j - 1] + wGap);
				}
			}
		}
		return c[a.length][b.length];
	}

	public LinkedList<LinkedList<Character>> reconstructSolution() {
		int c[][] = new int[a.length + 1][b.length + 1];
		Character tempA[] = (Character[]) Array.newInstance(a.getClass()
				.getComponentType(), a.length + 1);
		Character tempB[] = (Character[]) Array.newInstance(b.getClass()
				.getComponentType(), b.length + 1);
		for (int i = 1; i < tempA.length; i++) {
			tempA[i] = a[i - 1];
		}
		for (int i = 1; i < tempB.length; i++) {
			tempB[i] = b[i - 1];
		}
		for (int i = 0; i <= a.length; i++) {
			for (int j = 0; j <= b.length; j++) {
				if (i == 0) {
					c[i][j] = j * wGap;
				} else if (j == 0) {
					c[i][j] = i * wGap;
				} else {
					c[i][j] = min(c[i - 1][j - 1] + w[tempA[i]][tempB[j]],
							c[i - 1][j] + wGap, c[i][j - 1] + wGap);
				}
			}
		}

		int i = a.length, j = b.length;
		LinkedList<LinkedList<Character>> mls = new LinkedList<LinkedList<Character>>();
		LinkedList<Character> al1 = new LinkedList<Character>();
		LinkedList<Character> al2 = new LinkedList<Character>();
		while (i != 0 && j != 0) {
			int min = min(c[i - 1][j - 1] + w[tempA[i]][tempB[j]], c[i - 1][j]
					+ wGap, c[i][j - 1] + wGap);
			if (min == c[i - 1][j - 1] + w[tempA[i]][tempB[j]]) {
				al1.addFirst(tempA[i]);
				al2.addFirst(tempB[j]);
				i--;
				j--;
			} else if (min == c[i - 1][j] + wGap) {
				al1.addFirst(tempA[i]);
				al2.addFirst(null);
				i--;
			} else {
				al1.addFirst(null);
				al2.addFirst(tempB[j]);
				j--;
			}
		}
		while (i == 0 && j != 0) {
			al1.addFirst(null);
			al2.addFirst(tempB[j]);
			j--;
		}
		while (j == 0 && i != 0) {
			al1.addFirst(tempA[i]);
			al2.addFirst(null);
			i--;
		}
		mls.add(al1);
		mls.add(al2);
		return mls;
	}

	private int min(int a, int b, int c) {
		return a < b ? (a < c ? a : c) : (b < c ? b : c);
	}

	public static void main(String[] args) {
		Character a[] = { 'a', 'c', 'a', 'g', 't' };
		Character b[] = { 'c', 'c', 'a', 'g', 'c', 'a' };
		int w[][] = new int[255][255];
		w['a']['a'] = 0;
		w['a']['c'] = 2;
		w['a']['g'] = 1;
		w['a']['t'] = 3;
		w['c']['a'] = 2;
		w['c']['c'] = 0;
		w['c']['g'] = 1;
		w['c']['t'] = 3;
		w['g']['a'] = 1;
		w['g']['c'] = 1;
		w['g']['g'] = 0;
		w['g']['t'] = 2;
		w['t']['a'] = 3;
		w['t']['c'] = 3;
		w['t']['g'] = 2;
		w['t']['t'] = 0;
		int wGap = 3;
		SequenceAlignment sa = new SequenceAlignment(a, b, w, wGap);
		System.out.println(sa.getLeastCost());
		LinkedList<LinkedList<Character>> ls = sa.reconstructSolution();
		LinkedList<Character> ls1 = ls.get(0);
		LinkedList<Character> ls2 = ls.get(1);
		for (Character c : ls1) {
			if (c == null)
				System.out.print("_");
			else
				System.out.print(c);
		}
		System.out.println();
		for (Character c : ls2) {
			if (c == null)
				System.out.print("_");
			else
				System.out.print(c);
		}
	}
}