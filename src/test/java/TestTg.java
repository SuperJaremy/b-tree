import functions.Tg;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestTg {

    @ParameterizedTest
    @CsvSource(value = {"0,1", "1,-0.5", "2,0.16667", "3,0", "4,-0.03333", "5,0", "6,0.02381", "7,0", "8,-0.03333", "9,0",
            "10,0.07576", "11,0", "12,-0.25311", "13,0", "14,1.16667", "15,0", "16,-7.09216", "17,0", "18,54.97118",
            "19,0", "20,-529.12424"})
    public void testB(int number, double expected) {
        Assertions.assertEquals(expected, Tg.bernoulli(number).doubleValue(), 0.00001);
    }

    @ParameterizedTest
    @CsvSource(value = {"0, 0", "1, 1.557", "-1, -1.557", "-1.3, -3.602", "1.3, 3.602", "-0.5, -0.546", "0.5, 0.546",
            "-1.49, -12.35", "1.495, 13.168"})
    public void testTg(double value, double expected) {
        double delta;
        if (Math.abs(expected) < 1)
            delta = 0.001;
        else if (Math.abs(expected) < 10)
            delta = 0.1;
        else
            delta = 2;
        Assertions.assertEquals(expected, Tg.solve(value, delta), delta);
    }
}
