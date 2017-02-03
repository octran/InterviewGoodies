package BitManipulation;

public class AddingNumbers {

	public static void main(String[] args) {

		System.out.println(getSum(1,2));
	}

	
    public static int getSum(int a, int b) {
        // XOR = sum of two numbers without the carry
        // AND = carry of two numbers , 
    		// move it over by 1 with << 1 to put it in the appropriate place to add on next round
        while(b != 0) {
            int carry = (a & b);  // carry of two numbers
            a = (a ^ b);          // sum of two numbers
            b = (carry << 1);     // shift left the carry by 1
        }
        return a;
    }
}
