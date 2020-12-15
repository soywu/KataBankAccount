package bank;

import org.junit.Before;
import org.junit.Test;

import bank.account.BankAccount;
import bank.account.BankRecord;
import bank.transcation.InvalideTransactionException;
import bank.transcation.Withdraw;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

public class BankAccountTest {
	@Before
	public void setup() {
		IdProvider.initCount();
		Main.getInstance().init();
		TimeProvider.useFixedClockAt(TestHelper.LDT);
	}

	@Test
	public void bankAccountTest() throws InvalideTransactionException {
		BankAccount ac = BankAccount.of(TestHelper.FIRST_NAME_1, TestHelper.FAMILY_NAME_1);

		assertThat(ac.getBalance().equals(BigDecimal.ZERO));
		assertThat(ac.getFirstName().equals(TestHelper.FIRST_NAME_1));
		assertThat(ac.getFamilyName().equals(TestHelper.FAMILY_NAME_1));
		assertThat(ac.getId() == 0);
		assertThat(ac.getRecords().size() == 0);

		ac.updateBalance(BigDecimal.TEN);
		assertThat(ac.getBalance().equals(BigDecimal.TEN));

		Withdraw wd = Withdraw.of(BigDecimal.ONE, 0);
		BankRecord br = ac.acceptTransaction(wd);
		assertThat(ac.getBalance().equals(TestHelper.NI));
		assertThat(ac.getRecords().size() == 1);
		TestHelper.testBankRecord(br, TestHelper.WITHDRAW, BigDecimal.ONE.toString(), TestHelper.NI.toString(),
				TestHelper.LDT_STRING);
		TestHelper.testBankRecord(ac.getRecords().get(0), TestHelper.WITHDRAW, BigDecimal.ONE.toString(),
				TestHelper.NI.toString(), TestHelper.LDT_STRING);
	}
}
