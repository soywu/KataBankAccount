package bank.transcation;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import bank.IdProvider;
import bank.Main;
import bank.TestHelper;
import bank.TimeProvider;
import bank.account.BankAccount;
import bank.account.BankRecord;


public class DepositTest {

	@Before
	public void setup() {
		TimeProvider.useFixedClockAt(TestHelper.LDT);
		IdProvider.initCount();
		Main.getInstance().init();
	}
	
	@Test
	public void createDepositTest() {
		int accountId = 0;
		
		Deposit test = Deposit.of(BigDecimal.TEN, accountId);
		
		assertThat(test.getAmount().compareTo(BigDecimal.TEN)==0).isTrue();
		assertThat(test.getDepositToAccountId()==accountId).isTrue();
		assertThat(test.getTransactionTime().compareTo(TestHelper.LDT)==0).isTrue();
	}
	
	@Test
	public void processDepositTest() {
		
		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.FIRST_NAME_1, TestHelper.FAMILY_NAME_1);
		
		Deposit test = Deposit.of(BigDecimal.TEN, ac.getId());
		
		BankRecord br = test.process(ac);
		
		
		assertThat(ac.getBalance().compareTo(BigDecimal.TEN)==0).isTrue();
		
		TestHelper.testBankRecord(br, TestHelper.DEPOSIT, BigDecimal.TEN.toString(),
				BigDecimal.TEN.toString(), TestHelper.LDT_STRING);
	}
	
}
