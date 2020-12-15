package bank.transcation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import bank.account.BankAccount;
import bank.account.BankRecord;

public abstract class BankTransaction {
	protected LocalDateTime transactionTime;
	protected BigDecimal amount;

	public BankTransaction(LocalDateTime transactionTime, BigDecimal amount) {
		super();
		this.transactionTime = transactionTime;
		this.amount = amount;
	}

	public abstract void valid(BankAccount account) throws InvalideTransactionException;

	public abstract BankRecord process(BankAccount account);

	public LocalDateTime getTransactionTime() {
		return transactionTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}
}
