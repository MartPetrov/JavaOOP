package p02_ExtendedDatabase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.Assert.*;

public class DatabaseTest {
    public p02_ExtendedDatabase.Database database;
    private static final Person[] PEOPLE = {new Person(1, "Boris"), new Person(2, "Petar"), new Person(3, "Miroslav")};
    private static final Person PERSON_TO_ADD = new Person(4, "Ivan");
    private static final Person PERSON_WITH_INVALID_NAME = new Person(5, "Pesho");

    @Before
    public void prepareDatabase() throws OperationNotSupportedException {
        database = new p02_ExtendedDatabase.Database(PEOPLE);
    }
    //1. Конструктор
    //1.1 Дали създава правилен обект

    @Test
    public void testConstructorHasCreateValidObject() {
        Person[] elements = database.getElements();
        Assert.assertArrayEquals(elements, PEOPLE);
    }

    //1.2 Дали дава ексепшан ако елементите са > 16
    @Test(expected = OperationNotSupportedException.class)
    public void testConstructorThrowWhenMoreThanSixteenElements() throws OperationNotSupportedException {
        Person[] peoples = new Person[17];
        new p02_ExtendedDatabase.Database(peoples);
    }

    //1.3 дали дава ексепшан ако елементите са < 1
    @Test(expected = OperationNotSupportedException.class)
    public void testConstructorThrowWhenLessThanOneElements() throws OperationNotSupportedException {
        Person[] peoples = new Person[0];
        new p02_ExtendedDatabase.Database(peoples);
    }

    //2. Add
    //2.1 Успешно добавяме елемент
    @Test
    public void testAddShouldAddElement() throws OperationNotSupportedException {
        database.add(PERSON_TO_ADD);
        Person[] peoples = database.getElements();
        Assert.assertEquals("Invalid add operation!", database.getElements().length, PEOPLE.length + 1);
        Assert.assertEquals(peoples[peoples.length - 1], PERSON_TO_ADD);
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
        Person[] elements = database.getElements();
        Assert.assertEquals(database.getElements().length, PEOPLE.length - 1);
        Assert.assertEquals(elements[elements.length - 1], PEOPLE[PEOPLE.length - 2]);
    }

    //3.2 Премахване на елемент от празна база данни
    @Test(expected = OperationNotSupportedException.class)
    public void testRemoveFromEmptyArray() throws OperationNotSupportedException {
        database = new Database();
        database.remove();
    }

    //findByUsername
    // 4.1 Ако подаваме null;
    @Test(expected = OperationNotSupportedException.class)
    public void testFindByUsernameThrowNullParam() throws OperationNotSupportedException {
        database.findByUsername(null);
    }

    //4.2 Ако нямаме хора в базата данни
    @Test(expected = OperationNotSupportedException.class)
    public void testFindByUsernameThrowEmptyDatabase() throws OperationNotSupportedException {
        database = new Database();
        database.findByUsername(PERSON_TO_ADD.getUsername());
    }
    //4.3 Ако подадем валиден Username
    @Test
    public void testFindByUsername() throws OperationNotSupportedException {
        Person person = database.findByUsername(PEOPLE[1].getUsername());
        Assert.assertEquals(2, person.getId());
        Assert.assertEquals(PEOPLE[1].getUsername(), person.getUsername());
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testFindByUsernameMoreThanOnePerson() throws OperationNotSupportedException {
        database.add(PERSON_TO_ADD);
        database.add(PERSON_TO_ADD);
        database.findByUsername(PERSON_TO_ADD.getUsername());
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testFindByNameInvalidName() throws OperationNotSupportedException {
        database.findByUsername(PERSON_WITH_INVALID_NAME.getUsername());
    }

    //findById
    @Test(expected = OperationNotSupportedException.class)
    public void testFindByIdThrowException() throws OperationNotSupportedException {
        database.findById(PERSON_TO_ADD.getId());
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testFindByIdThrowEmptyDatabase() throws OperationNotSupportedException {
        database = new Database();
        database.findById(PERSON_TO_ADD.getId());
    }

    @Test
    public void testFindById() throws OperationNotSupportedException {
        Person person = database.findById(PEOPLE[1].getId());
        Assert.assertEquals(person.getId(), PEOPLE[1].getId());
        Assert.assertEquals(person.getUsername(), PEOPLE[1].getUsername());
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testFindByIdMoreThanOnePerson() throws OperationNotSupportedException {
        database.add(PERSON_TO_ADD);
        database.add(PERSON_TO_ADD);
        database.findById(PERSON_TO_ADD.getId());
    }

}