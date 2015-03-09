package dynamic_programming;

import java.util.Arrays;
import java.util.HashSet;

public class MaxWeightIndependentSet {

	int w[];

	public MaxWeightIndependentSet(int w[]) {
    	this.w = Arrays.copyOf(w, w.length);
	}

	public int getMaxWeightValue() {
    	int a[] = new int[w.length];
    	a[0] = w[0];
    	a[1] = max(0 + w[1], w[0]);
    	for (int i = 2; i < a.length; i++) {
        	a[i] = max(a[i - 1], a[i - 2] + w[i]);
    	}
    	return a[a.length - 1];
	}

	public HashSet<Integer> reconstructSolution() {
    	int a[] = new int[w.length];
    	a[0] = w[0];
    	a[1] = max(w[0], 0 + w[1]);
    	for (int i = 2; i < a.length; i++) {
        	a[i] = max(a[i - 1], a[i - 2] + w[i]);
    	}
    	HashSet<Integer> hs = new HashSet<Integer>();
    	int j = a.length - 1;
    	while (j >= 2) {
        	if (a[j - 1] > a[j - 2] + w[j]) {
            	j = j - 1;
        	} else {
            	hs.add(j);
            	j = j - 2;
        	}
    	}
    	hs.add(max(a[0], a[1]) == a[0] ? 0 : 1);
    	return hs;
	}

	private int max(int a, int b) {
    	return a > b ? a : b;
	}
    
	public static void main(String[] args){
    	int w[] = {20,8,4,12,10,14,22,25};
    	MaxWeightIndependentSet mis = new MaxWeightIndependentSet(w);
    	System.out.println(mis.getMaxWeightValue());
    	HashSet<Integer> hs = mis.reconstructSolution();
    	for(int x : hs){
        	System.out.print(w[x]+" ");
    	}
	}
}


