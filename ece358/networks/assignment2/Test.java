import java.lang.Math;
import java.util.Random;

public class Test {
	public static void main(String args[]) {
		for (int i = 0; i < 100; i++) {
			//int randomVal = 1 + (int) (Math.random() * (Math.pow(2,3) - 1));
			//System.out.println(randomVal);
			Random generator = new Random();
			double number = generator.nextDouble();
			System.out.println(number);
		}
	}
}