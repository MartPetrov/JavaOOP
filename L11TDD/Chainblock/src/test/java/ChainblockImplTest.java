import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ChainblockImplTest {

    private Chainblock database;
    private List<Transaction> transactionList;

    @Before
    public void setUp() {
        this.database = new ChainblockImpl();
        this.transactionList = new ArrayList<>();
        prepareTransactionForTest();
    }

    private void prepareTransactionForTest() {
        Transaction transaction1 = new TransactionImpl(1, TransactionStatus.SUCCESSFUL, "Martin", "Yana", 150.50);
        Transaction transaction2 = new TransactionImpl(2, TransactionStatus.SUCCESSFUL, "Martin", "Emil", 120.50);
        Transaction transaction3 = new TransactionImpl(3, TransactionStatus.FAILED, "Ivan", "Stoyan", 100.50);
        this.transactionList.add(transaction1);
        this.transactionList.add(transaction2);
        this.transactionList.add(transaction3);
    }


    //1. Транзакция, която я няма
    //2. Транзакция, която вече я има
    @Test
    public void testAddCorrectTransaction() {
        Assert.assertEquals(0, database.getCount());
        this.database.add(transactionList.get(0));
        Assert.assertEquals(1, database.getCount());

    }

    @Test
    public void testAddExistingTransaction() {
        Assert.assertEquals(0, database.getCount());
        this.database.add(transactionList.get(0));
        Assert.assertEquals(1, database.getCount());
        this.database.add(transactionList.get(0));
        Assert.assertEquals(1, database.getCount());
    }


    @Test
    public void testContains() {
        Assert.assertFalse(this.database.contains(transactionList.get(0)));
        Assert.assertFalse(this.database.contains(transactionList.get(0).getId()));

        this.database.add(transactionList.get(0));

        Assert.assertTrue(this.database.contains(transactionList.get(0)));
        Assert.assertTrue(this.database.contains(transactionList.get(0).getId()));
    }

    //1. Намираме транзакцията и сменяме статуса
    //2. Не намираме транзакция в нашия мап
    @Test
    public void testChangeTransactionStatusSuccessful() {
        this.database.add(transactionList.get(0));
        Assert.assertEquals(1, database.getCount());

        this.database.changeTransactionStatus(transactionList.get(0).getId(), TransactionStatus.ABORTED);
        Assert.assertEquals(TransactionStatus.ABORTED, transactionList.get(0).getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeTransactionStatusInvalidId() {
        this.database.add(transactionList.get(0));
        Assert.assertEquals(1, database.getCount());
        this.database.changeTransactionStatus(2, TransactionStatus.ABORTED);

    }


    @Test
    public void testRemoveTransactionByValidId() {
        this.database.add(this.transactionList.get(0));
        this.database.add(this.transactionList.get(1));
        Assert.assertEquals(2, this.database.getCount());

        int id = this.transactionList.get(0).getId();
        this.database.removeTransactionById(id);
        Assert.assertEquals(1, this.database.getCount());
        Assert.assertFalse(this.database.contains(id));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveTransactionByInvalidId() {
        this.database.add(this.transactionList.get(0));
        this.database.removeTransactionById(5);
    }

    @Test
    public void testGetByValidId() {
        Transaction transaction = this.transactionList.get(0);
        this.database.add(transaction);
        Transaction returnedTransaction = this.database.getById(transaction.getId());

        Assert.assertEquals(returnedTransaction, transaction);

        Assert.assertEquals(transaction.getId(), returnedTransaction.getId());
        Assert.assertEquals(transaction.getStatus(), returnedTransaction.getStatus());
        Assert.assertEquals(transaction.getFrom(), returnedTransaction.getFrom());
        Assert.assertEquals(transaction.getTo(), returnedTransaction.getTo());
        Assert.assertEquals(transaction.getAmount(), returnedTransaction.getAmount(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByInvalidId() {
        Transaction transaction = this.transactionList.get(0);
        this.database.add(transaction);
        this.database.getById(6);
    }

    @Test
    public void testGetByTransactionValidStatus() {
        List<Transaction> expectedTransactions = this.transactionList.stream()
                .filter(t -> t.getStatus() == TransactionStatus.SUCCESSFUL).collect(Collectors.toList());
        fillDatabaseWithTransactions();

        Iterable<Transaction> result = this.database.getByTransactionStatus(TransactionStatus.SUCCESSFUL);

        List<Transaction> returnedTransactions = new ArrayList<>();
        result.forEach(returnedTransactions::add);
        Assert.assertEquals(expectedTransactions.size(), returnedTransactions.size());

        returnedTransactions.forEach(tr -> Assert.assertEquals(TransactionStatus.SUCCESSFUL, tr.getStatus()));

        Assert.assertEquals(expectedTransactions, returnedTransactions);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testGetByTransactionInvalidStatus() {
        fillDatabaseWithTransactions();
        this.database.getByTransactionStatus(TransactionStatus.UNAUTHORIZED);
    }

    private void fillDatabaseWithTransactions() {
        this.database.add(this.transactionList.get(2));
        this.database.add(this.transactionList.get(0));
        this.database.add(this.transactionList.get(1));
    }

    @Test
    public void testGetAllSenderWithTransactionStatus() {
        fillDatabaseWithTransactions();
        this.database.add(new TransactionImpl(4, TransactionStatus.SUCCESSFUL, "Ivan", "Desi", 980.40));
        this.database.add(new TransactionImpl(5, TransactionStatus.ABORTED, "Ivan", "Desi", 980.40));
        Iterable<String> result = this.database.getAllSendersWithTransactionStatus(TransactionStatus.SUCCESSFUL);

        List<String> returnedSenders = new ArrayList<>();
        result.forEach(returnedSenders::add);

        Assert.assertEquals(3, returnedSenders.size());
        Assert.assertEquals("Ivan", returnedSenders.get(0));
        Assert.assertEquals("Martin", returnedSenders.get(1));
        Assert.assertEquals("Martin", returnedSenders.get(2));

    }
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllSenderWithTransactionStatusThrowInvalidStatus() {
        fillDatabaseWithTransactions();
        this.database.getAllSendersWithTransactionStatus(TransactionStatus.UNAUTHORIZED);
    }

    @Test
    public void testGetAllReceiversWithTransactionStatus() {
        fillDatabaseWithTransactions();
        this.database.add(new TransactionImpl(4, TransactionStatus.SUCCESSFUL, "Ivan", "Desi", 980.40));
        this.database.add(new TransactionImpl(5, TransactionStatus.ABORTED, "Ivan", "Desi", 980.40));
        Iterable<String> result = this.database.getAllReceiversWithTransactionStatus(TransactionStatus.SUCCESSFUL);

        List<String> returnedSenders = new ArrayList<>();
        result.forEach(returnedSenders::add);

        Assert.assertEquals(3, returnedSenders.size());
        Assert.assertEquals("Desi", returnedSenders.get(0));
        Assert.assertEquals("Yana", returnedSenders.get(1));
        Assert.assertEquals("Emil", returnedSenders.get(2));

    }
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllReceiversWithTransactionStatusThrowInvalidStatus() {
        fillDatabaseWithTransactions();
        this.database.getAllSendersWithTransactionStatus(TransactionStatus.UNAUTHORIZED);
    }

    @Test
    public void testGetAllOrderedByAmountDescendingThenById() {
        fillDatabaseWithTransactions();
        Iterable<Transaction> result = this.database.getAllOrderedByAmountDescendingThenById();
        List<Transaction> returned = new ArrayList<>();
        result.forEach(returned::add);

        List<Transaction> expected = this.transactionList.stream()
                .sorted(Comparator.comparing(Transaction::getAmount).reversed()
                        .thenComparing(Transaction::getId)).collect(Collectors.toList());
        Assert.assertEquals(expected,returned);
    }
}