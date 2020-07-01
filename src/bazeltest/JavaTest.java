package bazeltest;

import org.junit.Test;


public class JavaTest {
    @Test
    public void test_fail() {
        throw new RuntimeException("Some exception");
    }
}
