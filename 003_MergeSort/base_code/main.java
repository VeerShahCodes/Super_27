import pkg.*;
import java.util.*;
import java.time.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.*;


class main {
	static final long createdNano = System.nanoTime();

	public static void main(String args[]) {

		File file = new File("003_MergeSort\\base_code\\births.csv");

		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				System.out.println(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("no find bruh");
		}
		long start = System.nanoTime();
		long finish = System.nanoTime();
		System.out.println("Created: " + createdNano);
		
		int [] times = {10, 100, 1000, 10000, 100000, 1000000, 10000000};
		int [] nums = new int[100];

		Stopwatch s = new Stopwatch();

		System.out.println("-------------------Test-------------------");
		System.out.println("");
		for(int i : times){
			System.out.println("Interval: " + i);
			
			nums = new int[i];
			s.start();

			//  Put your method between these two comments
			Randomize(nums, i);
			MergeSort(nums, 0, nums.length - 1);
			//
			s.stop();

			System.out.println("Duration: " + (Stopwatch.readable(s.read())));
			System.out.println("");
		}
	}

	
	public static void Randomize(int[] nums, int n) {
		Random rand = new Random();
		for(int i = 0; i < n; i++) {
			nums[i] = rand.nextInt(n);
		}
	}

	public static void MergeSort(int[] arr, int l, int r) {
		if (l < r) {
			int m = l + (r - l) / 2;

			MergeSort(arr, l, m);
			MergeSort(arr, m + 1, r);

			Merge(arr, l, m, r);
		}
	}

	public static void Merge(int[] arr, int l, int m, int r) {
		int n1 = m - l + 1;
		int n2 = r - m;

		int[] L = new int[n1];
		int[] R = new int[n2];

		for (int i = 0; i < n1; ++i)
			L[i] = arr[l + i];
		for (int j = 0; j < n2; ++j)
			R[j] = arr[m + 1 + j];

		int i = 0, j = 0;
		int k = l;
		while (i < n1 && j < n2) {
			if (L[i] <= R[j]) {
				arr[k] = L[i];
				i++;
			} else {
				arr[k] = R[j];
				j++;
			}
			k++;
		}

		while (i < n1) {
			arr[k] = L[i];
			i++;
			k++;
		}

		while (j < n2) {
			arr[k] = R[j];
			j++;
			k++;
		}
	}
}
