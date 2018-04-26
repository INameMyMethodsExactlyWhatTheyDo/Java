/* 
 * This file is how you might test out your code.  Don't submit this, and don't 
 * have a main method in SortTools.java.
 */

package assignment1;
public class Main {
	public static void main(String [] args) {
		// call your test methods here
		// SortTools.isSorted() etc.		
		testFind();
		testInsertGeneral();
		testInsertInPlace();
		testSort();
		/*
		SortToolsTest test = new SortToolsTest();
		test.testFindFoundFull();
		test.testInsertGeneralPartialEnd();
		*/
	}
	public static void testFind(){
		int[] x = new int[]{-2,-1,0,1,2,3};
		if(SortTools.find(x,6,1) == 3){
			System.out.println("Find1 Passed");
		}else{
			System.out.println("Find1 Failed");
		}
		int[] y = new int[]{-2,-1,0,1,2,3,0,9,0};
		if(SortTools.find(y,6,0) == 2){
			System.out.println("Find2 Passed");
		}else{
			System.out.println("Find2 Failed");
		}
	}
	public static void testInsertGeneral (){
		int in = 2;
		boolean check = true;
		int[]x = new int[]{-2,-1,0,1,3,5};
		//first test
		int[]ans1 = new int[]{-2,-1,0,1,2,3,5};
		int[]out1 = SortTools.insertGeneral(x, 6, in);	
		for(int i = 0; i < 7; i++){
			if(out1[i] != ans1[i]){
				check = false;
				break;
			}
		}
		if(check){
			System.out.println("Insert General 1 pass");
		}else{
			System.out.println("Insert General 1 fail");
		}
		// second test
		in = 6;
		check = true;
		int[] ans2 = new int[]{-2,-1,0,1,3,5,6};
		int[] out2 = SortTools.insertGeneral(x, 6, in);
		for(int i = 0; i < 7; i++){
			if( out2[i] != ans2[i]){
				check = false;
			}
		}
		if(check){
			System.out.println("Insert General 2 pass");
		}else{
			System.out.println("Insert General 2 fail");
		}
		//third test 
		in = -3;
		check = true;
		int[] ans3 = new int[]{-3,-2,-1,0,1,3,5,6};
		int[] out3 = SortTools.insertGeneral(x, 6, in);
		for(int i = 0; i < 7; i++){
			if( out3[i] != ans3[i]){
				check = false;
			}
		}
		if(check){
			System.out.println("Insert General 3 pass");
		}else{
			System.out.println("Insert General 3 fail");
		}
		//4th test 
		in = 0;
		check = true;
		int[] ans4 = new int[]{-2,-1,0,1,3,5,6};
		int[] out4 = SortTools.insertGeneral(x, 6, in);
		for(int i = 0; i < 6; i++){
			if( out3[i] != ans3[i]){
				check = false;
			}
		}
		if(check){
			System.out.println("Insert General 4 pass");
		}else{
			System.out.println("Insert General 4 fail");

		}
	}
	public static void testInsertInPlace(){
		
		int[] x  = new int[]{1, 2, 4, 5, 0};
		int[] y =  new int[]{1, 2, 3, 4, 5};
		int n = 4;
		boolean check = true;
		n = SortTools.insertInPlace(x, n, 3);
		for(int i = 0; i < n; i++){
			if( x[i] != y[i]){
				check = false;
			}
		}
		if(check){
			System.out.println("Insert In place 1 pass");
		}else{
			System.out.println("Insert In place 1 fail");
		}		
	}	
	public static void testSort(){
		int[] x = new int[]{1};
		int[] y = new int[]{1};
		SortTools.insertSort(x, 1);
		for(int i = 0; i < 1; i++){
			if( x[i] != y[i]){
				System.out.println("Insert Sort 1 Failed");
				return;
			}
		}
		System.out.println("Insert sort 1 Passed");
	}
}
