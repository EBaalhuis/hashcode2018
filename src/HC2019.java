import java.io.*;
import java.util.*;

public class HC2019 {

	static String PATH = "/home/erik/git/hashcode2018/src/";
	static String id = "d";
	static int LIMIT = 6000;

	public static void main(String[] args) {
		Instance I = readInput(id);
		Solution S = solve2(I);
		// upperBound(I);

		System.out.printf("Score: %d\n", eval(I, S));
		writeToFile(S, PATH + "" + id + ".out");
	}

	public static void improve(Instance I, Solution S) {

	}

	public static void writeSet(HashSet<Slide> set, String file) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);

			bw.write(String.format("%d\n", set.size()));
			for (Slide sl : set) {
				if (sl.h) {
					bw.write(String.format("%d\n", sl.p1.id));
				} else {
					bw.write(String.format("%d %d\n", sl.p1.id, sl.p2.id));
				}
			}

			bw.close();
			fw.close();
			System.out.printf("Writing set to file %s complete\n", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Solution solve(Instance I) {
		Solution S = new Solution();

		HashSet<Slide> set = combineVerticals3(I);
		writeSet(set, PATH + "" + id + ".set");

		// first slide
		for (Slide sl : set) {
			S.slides.add(sl);
			break;
		}
		set.remove(S.slides.get(0));

		while (!set.isEmpty()) {
			long max = -1;
			Slide best = null;
			Random r = new Random();
			for (Slide sl : set) {
				if (best == null) {
					long score = score(sl, S.slides.get(S.slides.size() - 1));
					max = score;
					best = sl;
				}
				if (r.nextInt(10) == 0) {
					// System.out.println("ding");
					long score = score(sl, S.slides.get(S.slides.size() - 1));
					if (score > max) {
						max = score;
						best = sl;
					}
				}
			}
			S.slides.add(best);
			set.remove(best);

			if (S.slides.size() % 100 == 0) {
				System.out.printf("Did %d slides...\n", S.slides.size());
			}

		}

		return S;
	}

	public static Solution solve2(Instance I) {
		Solution S = new Solution();

		HashSet<Slide> set = combineVerticals3(I);
		writeSet(set, PATH + "" + id + ".set");
//		HashSet<Slide> set = readSet(I, PATH + "" + id + ".set3");

		// first slide
		Slide[] arr = new Slide[set.size()];
		arr = set.toArray(arr);
		Arrays.sort(arr);
		TreeSet<Slide> treeset = new TreeSet<>();
		for (Slide sl : arr) {
			treeset.add(sl);
		}
		System.out.println("sorted");
		
		S.slides.add(treeset.first());
		treeset.remove(treeset.first());
		
		long expected = 0;
		int x = treeset.size();
		for (int j = 0; j < x; j++) {
			
			long max = -1;
			Slide best = null;
			int ct = 0;
			for (Slide sl : treeset) {
				ct++;
				if (ct > LIMIT) break;
				long sc = score(S.slides.get(S.slides.size()-1), sl);
				if (sc > max) {
					max = sc;
					best = sl;
				}
			}
			
			expected += max;
			S.slides.add(best);
			treeset.remove(best);
			
			if (S.slides.size() % 100 == 0) {
				System.out.printf("Did %d slides, expected score: %d...\n", S.slides.size(), expected);
				System.out.printf("treeset size: %d\n", treeset.size());
			}
		}
			


		return S;
	}

	public static HashSet<Slide> readSet(Instance I, String file) {
		HashSet<Slide> res = new HashSet<Slide>();

		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			String[] nrs = line.split(" ");
			int nr = Integer.parseInt(nrs[0]);

			for (int j = 0; j < nr; j++) {
				line = br.readLine();
				String[] split = line.split(" ");
				Slide sl = new Slide();
				if (split.length == 1) {
					sl.h = true;
					sl.p1 = I.photos[Integer.parseInt(split[0])];
				} else {
					sl.h = false;
					sl.p1 = I.photos[Integer.parseInt(split[0])];
					sl.p2 = I.photos[Integer.parseInt(split[1])];
				}
				res.add(sl);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		return res;
	}

	// upgrade this!
	public static HashSet<Slide> combineVerticals(Instance I) {
		HashSet<Slide> res = new HashSet<>();
		boolean haveOne = false;
		Photo p = null;

		for (int j = 0; j < I.n; j++) {
			if (I.photos[j].h) {
				Slide sl = new Slide();
				sl.h = true;
				sl.p1 = I.photos[j];
				res.add(sl);
			} else {
				if (!haveOne) {
					p = I.photos[j];
					haveOne = true;
				} else {
					Slide sl = new Slide();
					sl.h = false;
					sl.p1 = p;
					sl.p2 = I.photos[j];
					res.add(sl);
					haveOne = false;
				}
			}
		}
		return res;
	}

	// combine biggest vert with smallest vert
	public static HashSet<Slide> combineVerticals2(Instance I) {
		HashSet<Slide> res = new HashSet<>();
		HashSet<Photo> verts = new HashSet<>();

		for (int j = 0; j < I.n; j++) {
			if (I.photos[j].h) {
				Slide sl = new Slide();
				sl.h = true;
				sl.p1 = I.photos[j];
				res.add(sl);
			} else {
				verts.add(I.photos[j]);
			}
		}

		// combine verticals somehow
		Photo[] arr = new Photo[verts.size()];
		arr = verts.toArray(arr);
		Arrays.sort(arr);

		for (int j = 0; j < arr.length / 2; j++) {
			Photo p1 = arr[j];
			Photo p2 = arr[arr.length - 1 - j];
			Slide sl = new Slide();
			sl.h = false;
			sl.p1 = p1;
			sl.p2 = p2;
			res.add(sl);
		}

		return res;
	}

	// avoid overlapping tags
	public static HashSet<Slide> combineVerticals3(Instance I) {
		HashSet<Slide> res = new HashSet<>();
		HashSet<Photo> verts = new HashSet<>();

		for (int j = 0; j < I.n; j++) {
			if (I.photos[j].h) {
				Slide sl = new Slide();
				sl.h = true;
				sl.p1 = I.photos[j];
				res.add(sl);
			} else {
				verts.add(I.photos[j]);
			}
		}

		// combine verticals somehow
		while (!verts.isEmpty()) {
			Photo p1 = verts.iterator().next();
			verts.remove(p1);

			Photo best = null;
			int min = p1.tags.size() + 1;
			for (Photo p : verts) {
				int overlap = 0;
				for (String t : p1.tags) {
					if (p.tags.contains(t)) {
						overlap++;
					}
				}
				if (overlap < min) {
					min = overlap;
					best = p;
				}
			}
			verts.remove(best);

			Slide sl = new Slide();
			sl.h = false;
			sl.p1 = p1;
			sl.p2 = best;
			res.add(sl);

			if (res.size() % 100 == 0) {
				System.out.printf("Made %d slides...\n", res.size());
			}
		}

		return res;
	}

	public static void upperBound(Instance I) {
		long res = 0;
		for (Photo p : I.photos) {
			res += p.tags.size();
		}
		System.out.println(res);
	}

	static class Solution {
		public ArrayList<Slide> slides;

		Solution() {
			slides = new ArrayList<Slide>();
		}
	}

	static class Slide implements Comparable<Slide> {
		public boolean h;
		public Photo p1;
		public Photo p2;
		public HashSet<String> t;

		public HashSet<String> tags() {
			if (this.h) {
				return p1.tags;
			} else {
				if (t == null) {
					t = new HashSet<>();
					t.addAll(p1.tags);
					t.addAll(p2.tags);
				}
				return t;
			}
		}

		public int compareTo(Slide o) {
			if (-Integer.compare(this.tags().size(), o.tags().size()) != 0) {
				return -Integer.compare(this.tags().size(), o.tags().size());
			} else {
				return Integer.compare(this.p1.id, o.p1.id);
			}
		}
	}

	static class Instance {
		public int n;
		public Photo[] photos;

		Instance() {
		}
	}

	static class Photo implements Comparable<Photo> {
		public boolean h;
		public HashSet<String> tags;
		public int id;

		Photo() {
			tags = new HashSet<>();
		}

		public int compareTo(Photo o) {
			if (this.tags.size() != o.tags.size()) {
				return Integer.compare(this.tags.size(), o.tags.size());
			} else {
				return Integer.compare(this.id, o.id);
			}
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
				String[] split = line.split(" ");
				I.photos[j] = new Photo();
				I.photos[j].id = j;
				I.photos[j].h = split[0].equals("H");
				int m = Integer.parseInt(split[1]);
				for (int k = 0; k < m; k++) {
					I.photos[j].tags.add(split[k + 2]);
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

		for (int j = 0; j < S.slides.size() - 1; j++) {
			Slide s1 = S.slides.get(j);
			Slide s2 = S.slides.get(j + 1);

			res += score(s1, s2);
		}

		return res;
	}

	public static long score(Slide s1, Slide s2) {
		long n1 = 0;
		long n2 = 0;

		for (String s : s1.tags()) {
			if (s2.tags().contains(s)) {
				n2++;
			} else {
				n1++;
			}
		}

		long sum = s1.tags().size() + s2.tags().size();
		long n3 = sum - n1 - n2 - n2;

		return Math.min(Math.min(n1, n2), n3);
	}
}
