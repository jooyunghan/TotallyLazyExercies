import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * User: jooyunghan
 * Date: 3/26/15 9:03 AM
 */
public class ImperativeStyleTest {
    @Test
    public void fibonacciEvenSum() throws Exception {
        int a = 0, b = 1;
        int sum = 0;
        while (a < 4_000_000) {
            // use a
            if (a % 2 == 0)
                sum += a;
            // next fib
            b = a + b;
            a = b - a;
        }
        assertEquals(4613732, sum);
    }

    @Test
    public void palindromeMax() throws Exception {
        int max = -1;
        for (int a = 100; a < 999; a++) {
            for (int b = 100; b < 999; b++) {
                if (isPalindrome(a * b)) {
                    max = Math.max(max, a * b);
                }
            }
        }
        assertEquals(906609, max);
    }

    private static boolean isPalindrome(int n) {
        String s = Integer.toString(n);
        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void pythagoreanTriple() throws Exception {
        for (int a = 1; a < 498; a++) {
            for (int b = a + 1; b < 499; b++) {
                int c = 1000 - a - b;
                if (a * a + b * b == c * c) {
                    assertEquals(31875000, a * b * c);
                    return;
                }
            }
        }
        fail("not found");
    }

    @Test
    public void maxiumPathSum() throws Exception {
        String input = "75\n" +
                "95 64\n" +
                "17 47 82\n" +
                "18 35 87 10\n" +
                "20 04 82 47 65\n" +
                "19 01 23 75 03 34\n" +
                "88 02 77 73 07 63 67\n" +
                "99 65 04 28 06 16 70 92\n" +
                "41 41 26 56 83 40 80 70 33\n" +
                "41 48 72 33 47 32 37 16 94 29\n" +
                "53 71 44 65 25 43 91 52 97 51 14\n" +
                "70 11 33 28 77 73 17 78 39 68 17 57\n" +
                "91 71 52 38 17 14 91 43 58 50 27 29 48\n" +
                "63 66 04 68 89 53 67 30 73 16 69 87 40 31\n" +
                "04 62 98 27 23 09 70 98 73 93 38 53 60 04 23\n";
        int[][] data = parseInput(input);
        for (int line = data.length - 2; line >= 0; line--) {
            for (int i = 0; i < data[line].length; i++) {
                data[line][i] += Math.max(data[line + 1][i], data[line + 1][i + 1]);
            }
        }
        assertEquals(1074, data[0][0]);
    }

    private int[][] parseInput(String input) {
        String[] lines = input.split("\n");
        int[][] data = new int[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            String[] nums = lines[i].split(" ");
            data[i] = new int[nums.length];
            for (int j = 0; j < nums.length; j++) {
                data[i][j] = Integer.parseInt(nums[j]);
            }
        }
        return data;
    }
}
