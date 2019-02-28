import java.io.*;
import java.util.*;


public class HC2019 {

	static String PATH = "/home/erik/git/hashcode2018/src/";

	public static void main(String[] args) {
		String id = "a";

		Instance I = readInput(id);
		Solution S = solve(I);
		
		System.out.printf("Score: %d\n", eval(I, S));
		writeToFile(S, PATH + "" + id + ".out");
	}
	
	public static void improve(Instance I, Solution S) {
		
	}

	
	public static Solution solve(Instance I) {
		Solution S = new Solution();

		for (int j = 0; j < I.n; j++) {
			if (!I.photos[j].h) continue;
			Slide sl = new Slide();
			sl.h = I.photos[j].h;
			sl.p1 = I.photos[j];
			S.slides.add(sl);
		}
		
		return S;
	}

	static class Solution {
		public ArrayList<Slide> slides;
		
		Solution() {
			slides = new ArrayList<Slide>();
		}
	}
	
	static class Slide {
		public boolean h;
		public Photo p1;
		public Photo p2;
		public HashSet<String> t;
		
		public HashSet<String> tags() {
			if (this.h) {
				return p1.tags;
			} else {
				if (t==null) {
					t = new HashSet<>();
					t.addAll(p1.tags);
					t.addAll(p2.tags);
				}
				return t;
			}
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
		public int id;
		
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
				I.photos[j].id = j;
				I.photos[j].h = split[0].equals("H");
				int m = Integer.parseInt(split[1]);
				for (int k = 0; k < m; k++) {
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

			bw.write(String.format("%d\n", S.slides.size()));
			for (int i = 0; i < S.slides.size(); i++) {
				if (S.slides.get(i).p2 == null) {
					bw.write(String.format("%d\n", S.slides.get(i).p1.id));
				} else {
					bw.write(String.format("%d %d\n", S.slides.get(i).p1.id, S.slides.get(i).p2.id));
				}
			}

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
