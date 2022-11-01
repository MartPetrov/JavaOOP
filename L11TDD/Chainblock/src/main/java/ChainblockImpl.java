import java.util.*;
import java.util.stream.Collectors;

public class ChainblockImpl implements Chainblock{

    private Map<Integer,Transaction> transactionsMap;

    public ChainblockImpl() {
        this.transactionsMap = new HashMap<>();
    }

    public int getCount() {
        return transactionsMap.size();
    }

    public void add(Transaction transaction) {
        int id = transaction.getId();
        if (!transactionsMap.containsKey(id)) {
            this.transactionsMap.put(id,transaction);
        }
    }

    public boolean contains(Transaction transaction) {
        return this.transactionsMap.containsValue(transaction);
    }

    public boolean contains(int id) {
        return this.transactionsMap.containsKey(id);
    }

    public void changeTransactionStatus(int id, TransactionStatus newStatus) {
        if (!transactionsMap.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        Transaction transactionForChange = this.transactionsMap.get(id);
        transactionForChange.setStatus(newStatus);
    }

    public void removeTransactionById(int id) {
        if (!transactionsMap.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        this.transactionsMap.remove(id);
    }

    public Transaction getById(int id) {
        if (!transactionsMap.containsKey(id)) {
            throw new IllegalArgumentException();
        }
       return this.transactionsMap.get(id);
    }

    public Iterable<Transaction> getByTransactionStatus(TransactionStatus status) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction: this.transactionsMap.values()) {
            if (transaction.getStatus() == status) {
                filteredTransactions.add(transaction);
            }
        }
        if (filteredTransactions.isEmpty()) {
            throw new IllegalArgumentException();
        }
        filteredTransactions.sort(Comparator.comparing(Transaction::getAmount).reversed());
        return filteredTransactions;
    }

    public Iterable<String> getAllSendersWithTransactionStatus(TransactionStatus status) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        getByTransactionStatus(status).forEach(filteredTransactions::add);

        if (filteredTransactions.isEmpty()) {
            throw new IllegalArgumentException();
        }
        filteredTransactions.stream().sorted(Comparator.comparing(Transaction::getAmount).reversed()).collect(Collectors.toList());
        List<String> senders = filteredTransactions.stream().map(Transaction::getFrom).collect(Collectors.toList());

        return senders;
    }

    public Iterable<String> getAllReceiversWithTransactionStatus(TransactionStatus status) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        getByTransactionStatus(status).forEach(filteredTransactions::add);

        if (filteredTransactions.isEmpty()) {
            throw new IllegalArgumentException();
        }
        filteredTransactions.stream().sorted(Comparator.comparing(Transaction::getAmount).reversed()).collect(Collectors.toList());
        List<String> receivers = filteredTransactions.stream().map(Transaction::getTo).collect(Collectors.toList());

        return receivers;
    }

    public Iterable<Transaction> getAllOrderedByAmountDescendingThenById() {
        return this.transactionsMap.values().stream().sorted(Comparator.comparing(Transaction::getAmount).reversed()
                .thenComparing(Transaction::getId))
                .collect(Collectors.toList());
    }

    public Iterable<Transaction> getBySenderOrderedByAmountDescending(String sender) {
        return null;
    }

    public Iterable<Transaction> getByReceiverOrderedByAmountThenById(String receiver) {
        return null;
    }

    public Iterable<Transaction> getByTransactionStatusAndMaximumAmount(TransactionStatus status, double amount) {
        return null;
    }

    public Iterable<Transaction> getBySenderAndMinimumAmountDescending(String sender, double amount) {
        return null;
    }

    public Iterable<Transaction> getByReceiverAndAmountRange(String receiver, double lo, double hi) {
        return null;
    }

    public Iterable<Transaction> getAllInAmountRange(double lo, double hi) {
        return null;
    }

    public Iterator<Transaction> iterator() {
        return null;
    }
}
