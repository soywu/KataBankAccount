package bank;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import bank.account.BankRecord;

public class TestHelper {

	public static final String FIRST_NAME_1 = "Alice";
	public static final String FAMILY_NAME_1 = "Nike";
	public static final String DEPOSIT = "Deposit";
	public static final String WITHDRAW = "Withdraw";
	

	public static final String PRINT_STATEMENT_TEST_EXPERTED_0 = "Bank Statement, Name: Alice Nike, Account id: 0";
	public static final String PRINT_STATEMENT_TEST_EXPERTED_1 = "Operation : Deposit, TimeStamp : "+TestHelper.LDT_STRING+", Amount : 10, Balance : 10";
	public static final String PRINT_STATEMENT_TEST_EXPERTED_2 = "Operation : Withdraw, TimeStamp : "+TestHelper.LDT_STRING+", Amount : 1, Balance : 9";
	
	
	public static final LocalDateTime LDT = LocalDateTime.of(2020, 01, 01, 12, 12, 12);
	public static final String LDT_STRING = "2020-01-01 12:12:12";
	
	public static final  BigDecimal NI = BigDecimal.TEN.subtract(BigDecimal.ONE);
	
	public static void testBankRecord(BankRecord br, 
			String operation, String amount, String balance, String timestamp) {
		assertThat(br.getAmount().equals(amount));
		assertThat(br.getOperation().equals(operation));
		assertThat(br.getBalance().equals(balance));
		assertThat(br.getTimeStamp().equals(timestamp));
	}
}
