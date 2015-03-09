package greedy;

import java.util.ArrayList;

public class HuffmanCode<T> {

	private HuffmanNode head;

	public HuffmanCode() {
    	this.head = null;
	}

	private class HuffmanNode {

    	private HuffmanNode left;

    	private HuffmanNode right;

    	private T data;

    	private double p;

    	HuffmanNode() {
        	this.data = null;
        	this.p = -1;
        	this.left = null;
        	this.right = null;
    	}

    	HuffmanNode(T data, double p) {
        	this.data = data;
        	this.p = p;
        	this.left = null;
        	this.right = null;
    	}
	}

	public void createHuffmanCode(T a[], double p[]) {
    	quickSort(a, p);
    	double q[] = new double[p.length];
    	ArrayList<ArrayList<Integer>> mnAl = new ArrayList<ArrayList<Integer>>();
    	head = createHuffmanCode(a, p, mnAl, q, 0, 0, -1);
	}

	private HuffmanNode createHuffmanCode(T a[], double p[], ArrayList<ArrayList<Integer>> mnAl, double q[],
        	int l1, int l2, int s2) {
    	if (l2 > s2) {
        	if (l1 < p.length - 2) {
            	int x = l1, y = l1 + 1;
            	q[++s2] = p[x] + p[y];
            	l1 += 2;
            	ArrayList<Integer> al = new ArrayList<Integer>();
            	al.add(l1);
            	al.add(l1 + 1);
            	mnAl.add(al);
            	return createHuffmanCode(a, p, mnAl, q, l1, l2, s2);
        	} else {
            	HuffmanNode hn = new HuffmanNode();
            	hn.left = getHuffmanNode(a, p, l1);
            	hn.right = getHuffmanNode(a, p, l1 + 1);
            	return hn;
        	}
    	} else {
        	if ((p.length - l1) + (s2 - l2 + 1) == 2) {
            	if (s2 - l2 + 1 == 1) {
                	HuffmanNode hn = new HuffmanNode();
                	hn.left = getHuffmanNode(a, p, l1);
                	hn.right = getHuffmanNode(a, mnAl, q, l2, 0);
                	return hn;
            	} else { // or else if(s2-l2+1==2))
                	HuffmanNode hn = new HuffmanNode();
                	hn.left = getHuffmanNode(a, mnAl, p, l2, 0);
                	hn.right = getHuffmanNode(a, mnAl, p, l2 + 1, 0);
                	return hn;
            	}
        	} else {
            	int l11 = l1, l22 = l2;
            	while (l1 + l2 - (l11 + l22) != 2 && l1 < p.length && l2 <= s2) {
                	if (p[l1] < q[l2]) {
                    	l1++;
                	} else {
                    	l2++;
                	}
            	}
            	if (l1 + l2 - (l11 + l22) != 2 && l1 == p.length) {
                	l2++;
            	} else if (l1 + l2 - (l11 + l22) != 2 && l2 > s2) {
                	l1++;
            	}
            	if (l1 - l11 == 2) {
                	int x = l1 - 1;
                	int y = l1 - 2;
                	q[++s2] = p[x] + p[y];
                	ArrayList<Integer> al = new ArrayList<Integer>();
                	al.add(x);
                	al.add(y);
                	mnAl.add(al);
                	return createHuffmanCode(a, p, mnAl, q, l1, l2, s2);
            	} else if (l1 - l11 == 1) {
                	int x = l1 - 1;
                	int y = l2 - 1;
                	q[++s2] = p[x] + q[y];
                	ArrayList<Integer> al = new ArrayList<Integer>(mnAl.get(y));
                	al.add(l1);
                	mnAl.add(al);
                	return createHuffmanCode(a, p, mnAl, q, l1, l2, s2);
            	} else {
                	int x = l2 - 1;
                	int y = l2 - 2;
                	q[++s2] = q[x] + q[y];
                	ArrayList<Integer> al1 = new ArrayList<Integer>(mnAl.get(x));
                	ArrayList<Integer> al2 = new ArrayList<Integer>(mnAl.get(y));
                	al1.addAll(al2);
                	mnAl.add(al1);
                	return createHuffmanCode(a, p, mnAl, q, l1, l2, s2);
            	}
        	}
    	}
	}

	public void printHuffManCode() {
    	StringBuilder sb = new StringBuilder();
    	printHuffManCode(head, sb);
	}

	private void printHuffManCode(HuffmanNode hn, StringBuilder sb) {
    	if (hn != null) {
        	sb.append('0');
        	printHuffManCode(hn.left, sb);
        	sb.deleteCharAt(sb.length() - 1);
        	if (hn.p != -1) {
            	System.out.println(hn.data + " :  " + hn.p + " : " + sb);
        	}
        	sb.append('1');
        	printHuffManCode(hn.right, sb);
        	sb.deleteCharAt(sb.length() - 1);
    	}
	}

	private HuffmanNode getHuffmanNode(T a[], double p[], int l) {
    	HuffmanNode hn = new HuffmanNode(a[l], p[l]);
    	return hn;
	}

	private HuffmanNode getHuffmanNode(T a[], ArrayList<ArrayList<Integer>> mnAl, double p[], int l, int i) {
    	if (i == mnAl.get(l).size() - 2) {
        	HuffmanNode hn = new HuffmanNode();
        	hn.left = new HuffmanNode(a[mnAl.get(l).get(i)], p[mnAl.get(l).get(i)]);
        	hn.right = new HuffmanNode(a[mnAl.get(l).get(i + 1)], p[mnAl.get(l).get(i + 1)]);
        	return hn;
    	} else {
        	HuffmanNode hn = new HuffmanNode();
        	hn.left = new HuffmanNode(a[mnAl.get(l).get(i)], p[mnAl.get(l).get(i)]);
        	hn.right = getHuffmanNode(a, mnAl, p, l, i + 1);
        	return hn;
    	}
	}

	public void quickSort(T a[], double p[]) {
    	quickSort(a, p, 0, a.length - 1);
	}

	public void quickSort(T a[], double p[], int l, int r) {
    	if (l < r) {
        	int i = l + 1, j = l + 1;
        	double pivot = p[l];
        	while (j < a.length) {
            	if (p[j] < pivot) {
                	swap(p, i, j);
                	swap(a, i, j);
                	i++;
            	}
            	j++;
        	}
        	swap(p, i - 1, l);
        	swap(a, i - 1, l);
        	quickSort(a, p, l, i - 2);
        	quickSort(a, p, i, r);
    	}
	}

	private void swap(T a[], int x, int y) {
    	T temp = a[x];
    	a[x] = a[y];
    	a[y] = temp;
	}

	private void swap(double a[], int x, int y) {
    	double temp = a[x];
    	a[x] = a[y];
    	a[y] = temp;
	}

	public static void main(String[] args) {
    	Character a[] = {
            	'a', 'b', 'c', 'd', 'e', 'f'
    	};
    	double f[] = {
            	5, 9, 12, 13, 16, 45
    	};
    	HuffmanCode<Character> hc = new HuffmanCode<Character>();
    	hc.createHuffmanCode(a, f);
    	hc.printHuffManCode();
	}
}



