package hashcode2018;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class HC18 {
	
	static String PATH = "/home/erik/Documents/HashCode2018/";

	public static void main(String[] args) {
		String id = "example";
		Instance I = readInput(id);
		Solution S = solve(I);
		System.out.printf("Score: %d\n", eval(S));
		writeToFile(S, PATH + "" + id + ".out");
	}

	public static Solution solve(Instance I) {
		Solution S = new Solution();

		

		return S;
	}

	public static long eval(Solution S) {
		long res = 0;
		
		
		
		return res;
	}

	public static boolean validate(Solution S, Instance I) {
		

		return true;
	}


	public static void writeToFile(Solution S, String file) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);

			bw.write("test");
			
			bw.close();
			fw.close();
			System.out.println("Done writing to file");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Instance readInput(String id) {
		BufferedReader br = null;
		Instance I = new Instance();

		try {
			br = new BufferedReader(new FileReader(PATH + id + ".in"));
			String line = br.readLine();
			String[] nrs = line.split(" ");
			
			I.r = Integer.parseInt(nrs[0]);
			

			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		return I;
	}

	static class Instance {
		public int r, c, l, h;
		boolean[][] m;
	}

	static class Solution {

		Solution() {
			
		}
	}



}
