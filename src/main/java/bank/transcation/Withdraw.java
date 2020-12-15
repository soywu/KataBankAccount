package bank.transcation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import bank.TimeProvider;
import bank.account.BankAccount;
import bank.account.BankRecord;

public class Withdraw extends BankTransaction{

	public static final String OPERATION = "Withdraw";
	
	protected int withdrawFromAccountId;
	
	public static Withdraw of(BigDecimal amount,
			int withdrawFromAccountId) {
		return new Withdraw(TimeProvider.now(), amount, withdrawFromAccountId);
	}
	
	private Withdraw(LocalDateTime transactionTime, BigDecimal amount,
			int withdrawFromAccountId ) {
		super( transactionTime, amount);
		this.withdrawFromAccountId=withdrawFromAccountId;
	}

	public int getWithdrawFromAccountId() {
		return withdrawFromAccountId;
	}

	@Override
	public void valid(BankAccount account) throws InvalideTransactionException {
		if (this.getAmount().compareTo(BigDecimal.ZERO) < 0)
		{
			throw new InvalideTransactionException("Withdraw amount is not valid: " + this.getAmount());
		} else if (account.getBalance().compareTo(this.getAmount()) < 0) {

			throw new InvalideTransactionException("Withdraw amount is bigger than account balance, amount: "
					+ this.getAmount() + ", balance: " + account.getBalance());
		}
	}
	
	@Override
	public BankRecord process(BankAccount account) {
		account.updateBalance(this.getAmount().negate());
		return BankRecord.of(OPERATION, this.getTransactionTime(), this.getAmount(), account.getBalance());
	}
}
