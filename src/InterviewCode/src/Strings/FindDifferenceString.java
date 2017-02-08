package Strings;

/*
 * Give two strings A and B. 
 * String B is String A with one more character.
 * Find the extra character.
 */
public class FindDifferenceString {

	public static void main(String[] args) {
		String s = "abcd";
		String t = "abcde";
		/*int sum = t.charAt(4);
		char found = (char) (sum);
		System.out.println(found + " " + sum );*/
		System.out.println(findDifferentChar(s, t));
	}
	
	public static char findDifferentChar(String a, String b) {
		// O(n) conversion + O(n) loops
		if(a == null || b == null || b.length() <= a.length()) {
			// Cannot be null or b cannot be less than a's length
			throw new IllegalArgumentException();
		}
		int sumB = 0;
		char found;
		// Get b's sum of chars
		for(char x : b.toCharArray()) {
			sumB += x;
		}
		// Subtract a' sum of chars
		for(char x: a.toCharArray()) {
			sumB -= x;
		}
		// Remaining is the Extra character
		found = (char)sumB;
		return found;
	}

}
