import pkg.*;
import java.util.*;
import java.time.*;
import java.lang.*;


class main {
	public static void main(String args[]) {
		int [] times = {10, 100, 1000, 10000, 100000, 1000000, 10000000};
		int [] nums = new int[100];
		Stopwatch s = new Stopwatch();

		System.out.println("-------------------Test-------------------");
		System.out.println("");
		for(int i : times){
			System.out.println("Interval: " + i);
			s.start();	
			nums = new int[i];
			//  Put your method between these two comments
			InsertionSort(nums, nums.length);
			//
			s.stop();
			System.out.println("Duration: " + Stopwatch.readable(s.read()));
			System.out.println("");
		}
	}


	public static void BubbleSort(int[] nums, int n) {
		Randomize(nums, n);
		int i,j;
		for(i = 0; i < n - 1; i++) {
			for(j = 0; j < n - i - 1; j++) {
				if(nums[j] > nums[j+1]) {
					if(nums[j] > nums[j+1]) {
						int temp = nums[j+1];
						nums[j+1] = nums[j];
						nums[j] = temp;
					}
				}
			}
		}
	}

	public static void SelectionSort(int arr[], int n) {
		Randomize(arr, n);
		int i, j, min_idx;

		for(i = 0; i < n -1; i++) {
			min_idx = i;
			for(j = i + 1; j < n; j++) {
				if(arr[j] < arr[min_idx]) {
					min_idx = j;
				}
				int temp = arr[min_idx];
				arr[min_idx] = arr[i];
				arr[i] = temp;
			}
		}
	}

	public static void InsertionSort(int arr[], int n) {
		Randomize(arr, n);
		int i, key, j;
		for(i = 1; i < n; i++) {
			key = arr[i];
			j = i - 1;

			while(j >= 0 && arr[j] > key) {
				arr[j + 1] = arr[j];
				j = j - 1;
			}
			arr[j + 1] = key;
		}	
	}

	public static void Randomize(int arr[], int n) {
		for(int i = 0; i < n; i++) {
			arr[i] = (int)(Math.random() * 200001);
		}
	}

	public static boolean Search(int arr[], int n) {
		Randomize(arr, n);

		int randomNum = (int)(Math.random() * 200001);
		for(int i = 0; i < n; i++) {
			if(arr[i] == randomNum) {
				return true;
			}
		}
		return false;
	}
}

