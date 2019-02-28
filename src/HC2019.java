import java.io.*;
import java.util.*;


public class HC2019 {

	static String PATH = "/home/erik/git/hashcode2018/src/";

	public static void main(String[] args) {
		String id = "Traverse";

		Instance I = readInput(id);
		Solution S = solve(I);
		
		System.out.printf("Score: %d\n", eval(I, S));
		writeToFile(S, PATH + "" + id + ".out");
	}
	
	public static void improve(Instance I, Solution S) {
		
	}

	
	public static Solution solve(Instance I) {
		Solution S = new Solution();

		return S;
	}

	static class Solution {

		Solution() {
		}
	}

	static class Instance {
		public int n;
		public Photo[] photos;
		
		Instance() {
		}
	}
	
	static class Photo {
		public boolean h;
		public HashSet<String> tags;
		
		Photo() {
			tags = new HashSet<>();
		}
	}

	public static Instance readInput(String id) {
		BufferedReader br = null;
		Instance I = new Instance();

		try {
			br = new BufferedReader(new FileReader(PATH + id + ".txt"));
			String line = br.readLine();
			String[] nrs = line.split(" ");
			I.n = Integer.parseInt(nrs[0]);
			I.photos = new Photo[I.n];
			
			for (int j = 0; j < I.n; j++) {
				line = br.readLine();
				String [] split = line.split(" ");
				I.photos[j] = new Photo();
				I.photos[j].h = split[0].equals("H");
				int m = Integer.parseInt(split[1]);
				for (int k = 0; k < split.length; k++) {
					I.photos[j].tags.add(split[k+2]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		return I;
	}

	public static void writeToFile(Solution S, String file) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);


			bw.close();
			fw.close();
			System.out.printf("Writing to file %s complete\n", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static long eval(Instance I, Solution S) {
		long res = 0;


		return res;
	}
}
