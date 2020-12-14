package bank;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import bank.account.BankAccount;
import bank.account.BankAccountNotFoundException;
import bank.account.BankRecord;
import bank.transcation.Deposit;
import bank.transcation.InvalideTransactionException;
import bank.transcation.Withdraw;

public class Main {
	
	private static class Holder {
		private static final Main INSTANCE = new Main();
	}
	
	public static Main getInstance() {
		return Holder.INSTANCE;
	}
	
	Map<Integer, BankAccount> accounts;

	private Main() {
		init();
	}
	
	public void init() {
		accounts = new HashMap<>();
	}
	
	public BankAccount createNewAccount(String firstName, String familyName) {
		BankAccount ac = BankAccount.of(firstName, familyName);
		accounts.put(ac.getId(), ac);
		return ac;
	}
	
	public BankAccount getAccount(int accountId) throws BankAccountNotFoundException {
		BankAccount ac = accounts.get(accountId);
		if(ac==null) throw new BankAccountNotFoundException();
		return ac;
	}
	
	public BankRecord takeDeposit(int accountId, BigDecimal amount) throws BankAccountNotFoundException, InvalideTransactionException {
		BankAccount ac = getAccount(accountId);
		Deposit de = Deposit.of(amount, accountId);
		return ac.acceptTransaction(de);
	}
	
	public BankRecord makeWithdraw(int accountId, BigDecimal amount) throws BankAccountNotFoundException, InvalideTransactionException {
		BankAccount ac = getAccount(accountId);
		Withdraw wd = Withdraw.of(amount, accountId);
		return ac.acceptTransaction(wd);
	}
}
