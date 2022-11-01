package p04_BubbleSortTest;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class BubbleTest {

    @Test
    public void isSortedArray() {
        int[] numbers = {3, 5, 6, 89, 7, 8,1,20,34};
        Bubble.sort(numbers);
        int[] expected = {1,3, 5, 6, 7, 8,20,34, 89};
        Assert.assertArrayEquals(expected, numbers);
    }
}