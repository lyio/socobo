package biz;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.mockito.Matchers;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Wraps Mockito's BaseMatcher to enable Lambda expressions as argument matchers.
 * @param <T>
 */
public class LambdaMatcher<T> extends BaseMatcher<T> {
    private final Predicate<T> matcher;
    private final Optional<String> description;

    public LambdaMatcher(Predicate<T> matcher) {
        this(matcher, null);
    }

    public LambdaMatcher(Predicate<T> matcher, String description) {
        this.matcher = matcher;
        this.description = Optional.ofNullable(description);
    }

    /**
     * Pass in a predicate and a description to create a Matcher.
     * @param predicate Condition to test for
     * @param description Describe the Matcher
     * @param <E>
     * @return
     */
    public static <E> E argThat(Predicate<E> predicate, String description) {
        return Matchers.argThat(new LambdaMatcher<E>(predicate, description));
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean matches(Object argument) {
        return matcher.test((T) argument);
    }

    @Override
    public void describeTo(Description description) {
        this.description.ifPresent(description::appendText);
    }
}