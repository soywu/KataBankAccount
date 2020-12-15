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


public class WithdrawTest {
	@Before
	public void setup() {
		TimeProvider.useFixedClockAt(TestHelper.LDT);
		IdProvider.initCount();
		Main.getInstance().init();
	}
	
	@Test
	public void createWithdrawTest() {
		int accountId = 0;
		
		Withdraw test = Withdraw.of(BigDecimal.TEN, accountId);
		
		assertThat(test.getAmount().compareTo(BigDecimal.TEN)==0).isTrue();
		assertThat(test.getWithdrawFromAccountId()==accountId).isTrue();
		assertThat(test.getTransactionTime().compareTo(TestHelper.LDT)==0).isTrue();
	}
	
	@Test
	public void processWithdrawTest() {
		
		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.FIRST_NAME_1, TestHelper.FAMILY_NAME_1);
		
		Deposit de = Deposit.of(BigDecimal.TEN, ac.getId());

		Withdraw wd = Withdraw.of(BigDecimal.ONE, ac.getId());
		
		de.process(ac);
		
		BankRecord br = wd.process(ac);
		
		assertThat(ac.getBalance().equals(TestHelper.NI)).isTrue();
		
		TestHelper.testBankRecord(br, TestHelper.WITHDRAW, BigDecimal.ONE.toString(),
				TestHelper.NI.toString(), TestHelper.LDT_STRING);
	}
}
