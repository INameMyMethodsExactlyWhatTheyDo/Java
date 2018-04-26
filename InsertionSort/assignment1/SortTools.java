package assignment1;
public class SortTools {
	/**
	  * This method tests to see if the given array is sorted.
	  * @param x is the array
	  * @param n is the size of the input to be checked
	  * @return true if array is sorted
	  * non desending order 
	  */
	public static boolean isSorted(int[] x, int n) {
		if( n == 0){
			return false;
		}
		for(int i = 1; i < n ; i++){
			if(x[i-1] > x[i]){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This is an implementation of a binary search
	 * Operates on the first n elements of the array
	 * looks for int k
	 */
	public static int find(int[] x, int n, int k){
		int low = 0, mid = n/2, high = n;
		for (int i = 0; i < n; i++){
			if(k == x[mid]){
				return mid;
			}else if(high - low == 1){
				return -1;
			}else if(k > x[mid]){
				low = mid;
				mid = (high + low)/2;
			}else if(k < x[mid]){
				high = mid;
				mid = (high + low)/2;
			}
		}
		return -1;
	}
	/**
	 * This method will insert an element into it's place
	 * and return a new array
	 * (no duplicates)
	 * Operate on the first N elements of the array 
	 * Insert int v
	 */
	public static int[] insertGeneral (int[]x , int n, int v){
		if(find(x, n, v) >= 0){
			return x;
		}else{
			int[] list = new int[n+1];
			int step = 0;
			for (int i = 0; i < n ; i++){
				// in the middle or end
				if((step == 0 && i + 1 == n)|| (v > x[i] && v < x[i+1])){
					list[i + step] = x[i];
					step = 1;
					list[i + step] = v;
				// in the beginning
				}else if(step == 0 && i == 0 && v < x[i]){
					list[0] = v;
					step = 1;
					list[i + step] = x[0]; 
				//transcribe
				}else{
					list[i + step] = x[i];
				}
			}
			return list;
		}
	}

	/**
	 * This method will insert a element in place of the original array
	 * Will return the length of the final array
	 */
	public static int insertInPlace(int[] x, int n, int v){
		if(find(x, n, v) >= 0){
			return n;
		}else{
			int[] y = insertGeneral(x, n, v);
			for(int i = 0; i < n+1; i++){
				x[i] = y[i];
			}
		}
		return n+1;
	}
	
	/**
	 * This is an implementation of an incertion sort, 
	 * the best case senario will be 0(n)
	 * Worst will be O(n^2)
	 */
	public static void insertSort(int[]x, int n){

		for(int i = 0; i < n-1; i++){
			if(x[i] > x[i+1]){
				insertHelper(x, i+1, x[i+1]);
			}
		}
	}
	//basically incert in place with duplicate helper for insertSort
	public static void insertHelper(int[] x, int n, int v){
		int[] list = new int[n+1];
		int step = 0;
		for (int i = 0; i < n ; i++){
			if(step == 0 && i == 0 && v <= x[i]){
				list[0] = v;
				step = 1;
				list[i + step] = x[0]; 
			}else if((step == 0 && i + 1 == n)|| (v >= x[i] && v <= x[i+1])){
				list[i + step] = x[i];
				step = 1;
				list[i + step] = v;	
			}else{
				list[i + step] = x[i];
			}
		}
		//update first n +1 elements 
		for(int i = 0; i < n + 1; i++){
			x[i] = list[i];
		}
	}
}
