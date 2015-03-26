import com.googlecode.totallylazy.Sequence;
import com.googlecode.totallylazy.Strings;
import com.googlecode.totallylazy.numbers.Integers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.totallylazy.Pair.functions.first;
import static com.googlecode.totallylazy.Pair.pair;
import static com.googlecode.totallylazy.Sequences.iterate;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.matchers.Matchers.is;
import static com.googlecode.totallylazy.numbers.Integers.maximum;
import static com.googlecode.totallylazy.numbers.Numbers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;


/**
 * Created by jooyung.han on 2015-03-26.
 */
public class SeminarTest {
    // 1. sum of fibonacci numbers which is even and < 4,000,000
    @Test
    public void fibonacciTest() {
        final Sequence<Integer> fibo = getFibo();
        final Number answer = fibo.takeWhile(lessThan(4000000))
                .filter(even())
                .reduce(sum());
        assertThat(answer, is(4613732));
    }
    // Iterable/Iterator
    private List<Integer> getFibosByLimit(int limit) {
        List<Integer> fibos = new ArrayList<>();
        int a = 1, b = 1;
        while (a < limit) {
            fibos.add(a);
            b = a + b;
            a = b - a; // old b
        }
        return fibos;
    }

    private List<Integer> getFibosByCount(int count) {
        List<Integer> fibos = new ArrayList<>();
        int a = 1, b = 1;
        while (fibos.size() < count) {
            fibos.add(a);
            b = a + b;
            a = b - a; // old b
        }
        return fibos;
    }

    private Sequence<Integer> getFibo() {
        // iterate(f, initial) = [initial, f(initial), f(f(initial)), f(f(f(initial))) ... ]
        // (1,1) (1,2) (2,3) (3,5) ...
        // map(first)
        return iterate(p -> pair(p.second(), p.second() + p.first()), pair(1, 1))
                .map(first());
    }


    // 2. maximum palindrome which is multiplication of two three-digits
    // palindrome : aba aaabbaaa , 101  12321 ...
    // 100 ~ 999

    // map :     Seq<A> -> (A -> B) -> Seq<B>
    // flatMap : Seq<A> -> (A -> Seq<B>) -> Seq<B>
    @Test
    public void palindromeTest() {
        final Sequence<Integer> integers =
                Integers.range(100, 999).flatMap(a ->
                        Integers.range(100, 999).map(b ->
                                a * b));

        int max = integers.filter(n -> isPalindrome(n)).reduce(maximum());
//        int max = -1;
//        for (int a = 100; a <= 999; a++) {
//            for (int b = 100; b <= 999; b++) {
//                if (isPalindrome(a * b)) {
//                    max = Math.max(max, a * b);
//                }
//            }
//        }
        //System.out.println(max);
        assertEquals(906609, max);
    }

    private boolean isPalindrome(int n) {
        final String s = Integer.toString(n);
        return reverse(s).equals(s);
    }

    // String -> String : pure function
    private String reverse(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    // 3. pythagorean triples which are a < b < c and a + b + c = 1000 and a^2 + b^2 = c^2
    @Test
    public void pythagoreanTriple() {
        final Sequence<Sequence<Integer>> sequences =
                Integers.range(1, 498).flatMap(a ->
                    Integers.range(a + 1, 499).map(b -> sequence(a, b, 1000 - a - b)));

        final Sequence<Integer> answer = sequences.filter(t -> isPythagorean(t)).first();
        assertEquals(31875000, answer.reduce(multiply()));
    }

    private boolean isPythagorean(Sequence<Integer> t) {
        return t.size() == 3
                && t.first()*t.first() + t.second()*t.second() == t.third()*t.third();
    }
    // 4. maximum path sum of number triangle

}
