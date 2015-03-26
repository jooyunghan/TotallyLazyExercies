import com.googlecode.totallylazy.Sequence;
import com.googlecode.totallylazy.Triple;
import com.googlecode.totallylazy.numbers.Integers;
import com.googlecode.totallylazy.numbers.Numbers;
import org.junit.Test;

import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.Strings.reverse;
import static com.googlecode.totallylazy.Triple.triple;
import static com.googlecode.totallylazy.matchers.Matchers.is;
import static com.googlecode.totallylazy.matchers.NumberMatcher.startsWith;
import static com.googlecode.totallylazy.numbers.Numbers.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by jooyung.han on 2015-03-25.
 */
public class EulerTest {
    @Test
    public void no2() {
        final Sequence<Number> fibonacci = fibonacci();
        assertThat(fibonacci, startsWith(0, 1, 1, 2, 3, 5));

        final Number sum = fibonacci.takeWhile(lessThan(4_000_000)).filter(even()).reduce(sum());
        assertThat(sum, is(4613732));
    }

    @Test
    public void no4() {
        assertEquals(3, sequence(1, 2, 3).reduce(maximum()));
        final Number biggestPalindrome = range(100, 999).cartesianProduct(range(100, 999))
                .map(p -> multiply(p.first(), p.second()))
                .filter(n -> isPalindrome(n))
                .reduce(maximum());
        assertThat(biggestPalindrome, is(906609));
    }

    private boolean isPalindrome(Number n) {
        final String s = n.toString();
        return reverse(s).equals(s);
    }

    @Test
    public void no9() {
        final Sequence<Sequence<Integer>> triples =
                Integers.range(1, 500).flatMap(a ->
                        Integers.range(a + 1, 500).map(b ->
                                sequence(a, b, 1000 - (a + b))));
        final Sequence<Sequence<Integer>> answers =
                triples.filter(t -> isPythagorean(t));
        final Number answer = answers.first().reduce(multiply());
        assertThat(answer, is(31875000));
    }

    private boolean isPythagorean(Sequence<Integer> t) {
        return t.size() == 3
                && t.second() < t.third()
                && t.first() * t.first() + t.second() * t.second() == t.third() * t.third();
    }

}