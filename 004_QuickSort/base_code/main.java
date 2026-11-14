import pkg.*;
import java.util.*;
import java.time.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.*;


class main {
	static final long createdNano = System.nanoTime();

	public static void main(String args[]) {

		
		File file = new File("births.csv");
		if (!file.exists()) {
			file = new File("004_QuickSort\\base_code\\births.csv");
		}
		ArrayList<DayBirth> births = new ArrayList<>();
		try (Scanner scanner = new Scanner(file)) {
			births = parseBirths(scanner);
			System.out.println("Loaded " + births.size() + " DayBirth objects.");
		} catch (FileNotFoundException e) {
			System.out.println("Could not find births.csv");
		}	

		ArrayList<DayBirth> boys = new ArrayList<>();
		ArrayList<DayBirth> girls = new ArrayList<>();

		splitBoysAndGirls(births, boys, girls);

		QuickSort(boys, 0, boys.size() - 1);
		QuickSort(girls, 0, girls.size() - 1);
		
		exportData(boys, girls, file.getParentFile());




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
			QuickSort(nums, 0, nums.length - 1);
			//
			s.stop();

			String duration = Stopwatch.readable(s.read());

			System.out.println("Duration: " + duration);
			System.out.println("");
		}
	}

	public static ArrayList<DayBirth> parseBirths(Scanner scanner) {
		ArrayList<DayBirth> births = new ArrayList<>();
		scanner.nextLine(); 
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] parts = line.split(",");
			int year = Integer.parseInt(parts[0]);
			int month = Integer.parseInt(parts[1]);
			Integer day = parts[2].isEmpty() || parts[2].equalsIgnoreCase("null")
				? null
				: Integer.parseInt(parts[2]);
			String gender = parts[3];
			int numOfBirths = Integer.parseInt(parts[4]);
			births.add(new DayBirth(year, month, day, gender, numOfBirths));
		}
		return births;
	}

	public static void splitBoysAndGirls(ArrayList<DayBirth> births, ArrayList<DayBirth> boys, ArrayList<DayBirth> girls) {
		for (DayBirth birth : births) {
			if (birth.getGender().equals("M")) {
				boys.add(birth);
			} else {
				girls.add(birth);
			}
		}
	}

	public static void exportData(ArrayList<DayBirth> boys, ArrayList<DayBirth> girls, File outputDirectory) {
		writeCsv(new File(outputDirectory, "boys_sorted.csv").getPath(), boys);
		writeCsv(new File(outputDirectory, "girls_sorted.csv").getPath(), girls);
	}

	private static void writeCsv(String fileName, ArrayList<DayBirth> data) {
		try (PrintWriter writer = new PrintWriter(fileName)) {
			writer.println("year,month,day,gender,births");
			for (DayBirth birth : data) {
				writer.printf("%d,%d,%s,%s,%d%n",
					birth.getYear(),
					birth.getMonth(),
					birth.getDay() == null ? "" : birth.getDay(),
					birth.getGender(),
					birth.getNumBirths());
			}
			System.out.println("Exported " + data.size() + " rows to " + fileName);
		} catch (IOException e) {
			System.out.println("Could not export " + fileName + ": " + e.getMessage());
		}
	}

	public static void QuickSort(ArrayList<DayBirth> list, int low, int high) {
		if (low < high) {
			int pi = partition(list, low, high);
			QuickSort(list, low, pi - 1);
			QuickSort(list, pi + 1, high);
		}
	}
	public static int partition(ArrayList<DayBirth> list, int low, int high) {
		DayBirth pivot = list.get(high);
		int i = (low - 1);

		for (int j = low; j <= high - 1; j++) {
			if (list.get(j).getNumBirths() < pivot.getNumBirths()) {
				i++;
				Collections.swap(list, i, j);
			}
		}
		Collections.swap(list, i + 1, high);
		return i + 1;
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

		for(int j = low; j <= high - 1; j++) {
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
	public static void QuickSort(int[] arr, int low, int high) {
		if(low < high) {
			int pi = partition(arr, low,  high);
			QuickSort(arr, low, pi - 1);
			QuickSort(arr, pi + 1, high);
		}
	}
	
	
}
