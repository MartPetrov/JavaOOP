package p01_Database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import javax.naming.OperationNotSupportedException;

public class DatabaseTest {
    public Database database;
    private static final Integer[] NUMBERS = {7, 45, 34, 12, 98, 23};
    private static final Integer NUMBER_TO_ADD = 67;

    @Before
    public void prepareDatabase() throws OperationNotSupportedException {
        database = new Database(NUMBERS);
    }
    //1. Конструктор
    //1.1 Дали създава правилен обект

    @Test
    public void testConstructorHasCreateValidObject() {
        Integer[] elements = database.getElements();
        Assert.assertArrayEquals(elements, NUMBERS);
    }

    //1.2 Дали дава ексепшан ако елементите са > 16
    @Test(expected = OperationNotSupportedException.class)
    public void testConstructorThrowWhenMoreThanSixteenElements() throws OperationNotSupportedException {
        Integer[] numbers = new Integer[17];
        new Database(numbers);
    }

    //1.3 дали дава ексепшан ако елементите са < 1
    @Test(expected = OperationNotSupportedException.class)
    public void testConstructorThrowWhenLessThanOneElements() throws OperationNotSupportedException {
        Integer[] numbers = new Integer[0];
        new Database(numbers);
    }

    //2. Add
    //2.1 Успешно добавяме елемент
    @Test
    public void testAddShouldAddElement() throws OperationNotSupportedException {
        database.add(NUMBER_TO_ADD);
        Integer[] elements = database.getElements();
        Assert.assertEquals("Invalid add operation!", database.getElements().length, NUMBERS.length + 1);
        Assert.assertEquals(elements[elements.length - 1], NUMBER_TO_ADD);
    }

    //2.2 Добавяне на null елемент
    @Test(expected = OperationNotSupportedException.class)
    public void testAddShouldThrowNullParam() throws OperationNotSupportedException {
        database.add(null);
    }

    //3. Remove

    //3.1 Успешно премахване на елемент
    @Test
    public void testRemoveSuccessful() throws OperationNotSupportedException {
        database.remove();
        Integer[] elements = database.getElements();
        Assert.assertEquals(database.getElements().length, NUMBERS.length - 1);
        Assert.assertEquals(elements[elements.length - 1], NUMBERS[NUMBERS.length - 2]);
    }

    //3.2 Премахване на елемент от празна база данни
    @Test(expected = OperationNotSupportedException.class)
    public void testRemoveFromEmptyArray() throws OperationNotSupportedException {
        for (int i = 0; i < NUMBERS.length; i++) {
            database.remove();
        }
        database.remove();
    }
}
