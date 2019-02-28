package hashcode2018;

public class test {
	public static void main(String[] args) {
		double x = Double.parseDouble("1.0000000e+00\n");
		System.out.println(x);
		x = Math.round(x);
		System.out.println(x);
	}
}
