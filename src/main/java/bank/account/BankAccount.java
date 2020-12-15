package bank.account;

import java.math.BigDecimal;
import java.util.*;

import bank.IdProvider;
import bank.transcation.BankTransaction;
import bank.transcation.InvalideTransactionException;

public class BankAccount {
	private int id;
	private String firstName;
	private String familyName;
	private BigDecimal balance;
	
	List<BankRecord> records;

	public static BankAccount of(String firstName, String familyName) {
		return new BankAccount(firstName, familyName, IdProvider.getCountAndPlusOne());
	}
	
	private BankAccount(String firstName, String familyName, int id) {
		this.firstName = firstName;
		this.familyName = familyName;
		this.id = id;
		this.balance = BigDecimal.ZERO;
		records = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public BigDecimal getBalance() {
		return balance;
	}
	
	public void updateBalance(BigDecimal update) {
		balance = balance.add(update);
	}

	public List<BankRecord> getRecords() {
		return records;
	}
	
	public BankRecord acceptTransaction(BankTransaction transaction) throws InvalideTransactionException {
		transaction.valid(this);
		BankRecord record = transaction.process(this);
		getRecords().add(record);
		return record;
	}

	public List<String> printStatement() {
		List<String> res = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder();
		sb.append("Bank Statement, ");
		sb.append("Name: ");
		sb.append(this.getFirstName());
		sb.append(" ");
		sb.append(this.getFamilyName());
		sb.append(", ");
		sb.append("Account id: ");
		sb.append(this.getId());
		
		res.add(sb.toString());
		for(BankRecord record : records) {
			res.add(record.print());
		}
		return res;
	}
	
}
