package bank.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import bank.Utils;

public class BankRecord {

	private final String operation;
	private final String timeStamp;
	private final String amount;
	private final String balance;

	public static BankRecord of(String operation, LocalDateTime localDateTime, BigDecimal amount, BigDecimal balance) {
		return new BankRecord(operation, Utils.DTFormatter.format(localDateTime), amount.toString(),
				balance.toString());
	}

	private BankRecord(String operation, String timeStamp, String amount, String balance) {
		super();
		this.operation = operation;
		this.timeStamp = timeStamp;
		this.amount = amount;
		this.balance = balance;
	}

	public String print() {
		StringBuilder sb = new StringBuilder();
		sb.append("Operation : ");
		sb.append(this.operation);
		sb.append(", ");
		sb.append("TimeStamp : ");
		sb.append(this.timeStamp);
		sb.append(", ");
		sb.append("Amount : ");
		sb.append(amount);
		sb.append(", ");
		sb.append("Balance : ");
		sb.append(balance);
		return sb.toString();
	}

	public String getOperation() {
		return operation;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public String getAmount() {
		return amount;
	}

	public String getBalance() {
		return balance;
	}
}
