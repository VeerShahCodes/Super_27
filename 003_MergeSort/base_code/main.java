import pkg.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


class main {

	public static void main(String args[]) {

		File file = new File("births.csv");
		if (!file.exists()) {
			file = new File("003_MergeSort\\base_code\\births.csv");
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

		MergeSort(boys, 0, boys.size() - 1);
		MergeSort(girls, 0, girls.size() - 1);
		
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
			MergeSort(nums, 0, nums.length - 1);

			s.stop();

			System.out.println("Duration: " + (Stopwatch.readable(s.read())));
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

	public static void MergeSort(ArrayList<DayBirth> arr, int l, int r) {
		if (l < r) {
			int m = l + (r - l) / 2;

			MergeSort(arr, l, m);
			MergeSort(arr, m + 1, r);

			Merge(arr, l, m, r);
		}
	}

	public static void Merge(ArrayList<DayBirth> arr, int l, int m, int r) {
		int n1 = m - l + 1;
		int n2 = r - m;

		ArrayList<DayBirth> L = new ArrayList<>(n1);
		ArrayList<DayBirth> R = new ArrayList<>(n2);

		for (int i = 0; i < n1; ++i)
			L.add(arr.get(l + i));
		for (int j = 0; j < n2; ++j)
			R.add(arr.get(m + 1 + j));

		int i = 0, j = 0;
		int k = l;
		while (i < n1 && j < n2) {
			if (L.get(i).getNumBirths() <= R.get(j).getNumBirths()) {
				arr.set(k, L.get(i));
				i++;
			} else {
				arr.set(k, R.get(j));
				j++;
			}
			k++;
		}

		while (i < n1) {
			arr.set(k, L.get(i));
			i++;
			k++;
		}

		while (j < n2) {
			arr.set(k, R.get(j));
			j++;
			k++;
		}
	}
	





	public static void Randomize(int[] nums) {
		for(int i = 0; i < nums.length; i++) {
			nums[i] = (int) (Math.random() * 200001);
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
