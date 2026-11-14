import pkg.*;
import java.util.*;
import java.time.*;
import java.lang.*;


class main {
	static final long createdNano = System.nanoTime();

	public static void main(String args[]) {

		
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
			Randomize(nums);
			//
			s.stop();

			String duration = Stopwatch.readable(s.read());

			System.out.println("Duration: " + duration);
			System.out.println("");
		}
	}

	public static void Randomize(int[] nums){
		Random rand = new Random();
		for(int i = 0; i < nums.length; i++){
			nums[i] = rand.nextInt(200001);
		}
	}

	public static int partition(int[] arr, int low, int high) {
		int pivot = arr[high];
		int i = (low - 1);

		for(int j = low, j <= high - 1; j++) {
			if (arr[j] < pivot) {
				i++;
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}

		}
		int temp = arr[i + 1];
		arr[i + 1] = arr[high];
		arr[high] = temp;

		return i + 1;
	}
	public static void quickSort(int[] arr, int low, int high) {
		if(low < high) {
			int pi = partition(arr, low,  high);
			quickSort(arr, low, pi - 1);
			quickSort(arr, pi + 1, high);
		}
	}
	
	
}
