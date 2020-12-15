package bank.transcation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import bank.TimeProvider;
import bank.account.BankAccount;
import bank.account.BankRecord;

public class Deposit extends BankTransaction {

	public static final String OPERATION = "Deposit";

	protected int depositToAccountId;

	public static Deposit of(BigDecimal amount, int depositToAccountId) {
		return new Deposit(TimeProvider.now(), amount, depositToAccountId);
	}

	private Deposit(LocalDateTime transactionTime, BigDecimal amount, int depositToAccountId) {
		super(transactionTime, amount);
		this.depositToAccountId = depositToAccountId;
	}

	public int getDepositToAccountId() {
		return depositToAccountId;
	}

	@Override
	public void valid(BankAccount account) throws InvalideTransactionException {
		if (this.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalideTransactionException("Deposit amount is invalid: " + this.getAmount());
		}
	}

	@Override
	public BankRecord process(BankAccount account) {
		account.updateBalance(this.getAmount());
		return BankRecord.of(OPERATION, this.getTransactionTime(), this.getAmount(), account.getBalance());
	}

}
