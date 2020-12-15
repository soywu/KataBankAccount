package bank;


import org.junit.Before;
import org.junit.Test;

import bank.account.BankRecord;
import bank.transcation.Deposit;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

public class BankRecordTest {
	
	@Before
	public void setup() {
		IdProvider.initCount();
		Main.getInstance().init();
		TimeProvider.useFixedClockAt(TestHelper.LDT);
	}
	
	@Test
	public void bankRecordTest() {
		BankRecord br = BankRecord.of(Deposit.OPERATION, TestHelper.LDT, BigDecimal.TEN, BigDecimal.TEN);

		TestHelper.testBankRecord(br, TestHelper.DEPOSIT, BigDecimal.TEN.toString(),
				BigDecimal.TEN.toString(), TestHelper.LDT_STRING);
		assertThat( br.print().equals(TestHelper.PRINT_STATEMENT_TEST_EXPERTED_1) ).isTrue();
	}
}
