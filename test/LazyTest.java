import com.googlecode.totallylazy.*;
import com.googlecode.totallylazy.numbers.Numbers;
import org.junit.Test;

import static com.googlecode.totallylazy.Option.option;
import static com.googlecode.totallylazy.Pair.functions.first;
import static com.googlecode.totallylazy.Pair.pair;
import static com.googlecode.totallylazy.Sequences.iterate;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.Strings.reverse;
import static com.googlecode.totallylazy.Triple.triple;
import static com.googlecode.totallylazy.matchers.IterableMatcher.hasExactly;
import static com.googlecode.totallylazy.matchers.NumberMatcher.is;
import static com.googlecode.totallylazy.matchers.NumberMatcher.startsWith;
import static com.googlecode.totallylazy.numbers.Integers.range;
import static com.googlecode.totallylazy.predicates.LessThanOrEqualToPredicate.lessThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by jooyung.han on 2015-03-24.
 */
public class LazyTest {
    @Test
    public void testPartial() {
        Function2<Integer, Integer, Integer> add = new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer a, Integer b) throws Exception {
                return a + b;
            }
        };
        assertEquals(sequence(3, 4, 5), sequence(1, 2, 3).map(add.apply(2)));
    }

    @Test
    public void testPartial2UsingBuiltin() {
        assertEquals(sequence(3, 4, 5), sequence(1, 2, 3).map(Numbers.add(2)));
    }

    static <T extends Comparable<T>> Sequence<T> quickSort(Sequence<T> input) {
        if (input.size() <= 1) return input;
        final T pivot = input.head();
        final Pair<Sequence<T>, Sequence<T>> partition = input.tail().partition(lessThanOrEqualTo(pivot));
        return quickSort(partition.first()).append(pivot).join(quickSort(partition.second()));
    }

    @Test
    public void testQuickSort() {
        assertEquals(sequence(1, 2, 3, 4, 5, 6), quickSort(sequence(6, 4, 5, 1, 2, 3)));
    }

    @Test
    public void testOption() {
        Option<String> optString = option(null);
        final Option<String> map = optString.map(s -> s.toUpperCase());
        assertTrue(map.isEmpty());
        Option<String> optString2 = option("abc");
        final Option<String> map2 = optString2.map(s -> s.toUpperCase());
        assertEquals(Option.option("ABC"), map2);
    }

    @Test
    public void rangeEquals() {
        assertThat(range(1, 3), hasExactly(1, 2, 3));
    }

    @Test
    public void fibonacci() {
        final Sequence<Number> fibo = iterate((pair) -> pair(pair.second(), pair.first() + pair.second()), pair(1, 2)).map(first());
        final Sequence<Number> filtered = fibo.filter(Numbers.even()).takeWhile(Numbers.lessThanOrEqualTo(4_000_000));
        assertThat(filtered.reduce(Numbers.sum()), is(4613732));
    }

    boolean isPalindrom(int n) {
        final String s = Integer.toString(n);
        return s.equals(reverse(s));
    }
    @Test
    public void palindrom() {
        final Sequence<Integer> products = range(1, 999).flatMap(n -> range(1, 999).map(m -> n * m));
        final Number max = products.filter(n -> isPalindrom(n)).reduce(Numbers.maximum());
        assertThat(max, is(906609));
    }

    boolean isPythagorean(Sequence<Integer> t) {
        return t.third() > t.second() && t.first() * t.first() + t.second() * t.second() == t.third() * t.third();
    }
    @Test
    public void pythagorean() {
        final Sequence<Sequence<Integer>> combinations = range(1, 498).flatMap(a -> range(a + 1, 499).map(b -> sequence(a, b, 1000 - (a + b))));
        final Sequence<Integer> one = combinations.filter(t -> isPythagorean(t)).first();
        //assertThat(one, hasExactly(sequence(200, 375, 425)));
        assertThat(one.reduce(Numbers.multiply()), is(31875000));
    }
}
