package bank;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import bank.account.BankAccount;
import bank.account.BankAccountNotFoundException;
import bank.account.BankRecord;
import bank.transcation.InvalideTransactionException;

public class TddMainTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setup() {
		IdProvider.initCount();
		Main.getInstance().init();
		TimeProvider.useFixedClockAt(TestHelper.LDT);
	}

	@Test
	public void createAccountTest() throws BankAccountNotFoundException {

		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.FIRST_NAME_1, TestHelper.FAMILY_NAME_1);
		BankAccount ac2 = Main.getInstance().createNewAccount(TestHelper.FIRST_NAME_1, TestHelper.FAMILY_NAME_1);

		assertThat(ac != null).isTrue();
		assertThat(ac.getBalance().compareTo(BigDecimal.ZERO) == 0).isTrue();
		assertThat(TestHelper.FIRST_NAME_1.equals(ac.getFirstName())).isTrue();
		assertThat(TestHelper.FAMILY_NAME_1.equals(ac.getFamilyName())).isTrue();
		assertThat(ac.getId() == 0).isTrue();

		assertThat(ac2 != null).isTrue();
		assertThat(ac2.getBalance().compareTo(BigDecimal.ZERO) == 0).isTrue();
		assertThat(TestHelper.FIRST_NAME_1.equals(ac2.getFirstName())).isTrue();
		assertThat(TestHelper.FAMILY_NAME_1.equals(ac2.getFamilyName())).isTrue();
		assertThat(ac2.getId() == 1).isTrue();

		assertThat(ac == Main.getInstance().getAccount(ac.getId())).isTrue();
		assertThat(ac2 == Main.getInstance().getAccount(ac2.getId())).isTrue();
	}

	@Test
	public void AccountNotFoundTest() throws BankAccountNotFoundException {
		expectedEx.expect(BankAccountNotFoundException.class);
		expectedEx.expectMessage("Account not found for Id: 100");

		Main.getInstance().createNewAccount(TestHelper.FIRST_NAME_1, TestHelper.FAMILY_NAME_1);
		Main.getInstance().getAccount(100);
	}

	@Test
	public void takeDepositTest() throws BankAccountNotFoundException, InvalideTransactionException {
		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.FIRST_NAME_1, TestHelper.FAMILY_NAME_1);

		BankRecord br = Main.getInstance().takeDeposit(ac.getId(), BigDecimal.ONE);

		assertThat(ac.getBalance().compareTo(BigDecimal.ONE) == 0).isTrue();

		TestHelper.testBankRecord(br, TestHelper.DEPOSIT, BigDecimal.ONE.toString(), BigDecimal.ONE.toString(),
				TestHelper.LDT_STRING);
	}

	@Test
	public void InvalidDepositAmountTest() throws BankAccountNotFoundException, InvalideTransactionException {
		expectedEx.expect(InvalideTransactionException.class);
		expectedEx.expectMessage("Deposit amount is invalid: -1");

		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.FIRST_NAME_1, TestHelper.FAMILY_NAME_1);

		Main.getInstance().takeDeposit(ac.getId(), BigDecimal.ONE.negate());
	}

	@Test
	public void makeWithdrawTest() throws BankAccountNotFoundException, InvalideTransactionException {
		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.FIRST_NAME_1, TestHelper.FAMILY_NAME_1);

		Main.getInstance().takeDeposit(ac.getId(), BigDecimal.ONE);
		BankRecord br = Main.getInstance().makeWithdraw(ac.getId(), BigDecimal.ONE);

		assertThat(ac.getBalance().compareTo(BigDecimal.ZERO) == 0).isTrue();

		TestHelper.testBankRecord(br, TestHelper.WITHDRAW, BigDecimal.ONE.toString(), BigDecimal.ZERO.toString(),
				TestHelper.LDT_STRING);
	}

	@Test
	public void InvalidWithDrawNotEnoughFundTest() throws BankAccountNotFoundException, InvalideTransactionException {
		expectedEx.expect(InvalideTransactionException.class);
		expectedEx.expectMessage("Withdraw amount is bigger than account balance, amount: 10, balance: 1");

		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.FIRST_NAME_1, TestHelper.FAMILY_NAME_1);

		Main.getInstance().takeDeposit(ac.getId(), BigDecimal.ONE);
		Main.getInstance().makeWithdraw(ac.getId(), BigDecimal.TEN);
	}

	@Test
	public void InvalidWithDrawAmountTest() throws BankAccountNotFoundException, InvalideTransactionException {
		expectedEx.expect(InvalideTransactionException.class);
		expectedEx.expectMessage("Withdraw amount is not valid: -1");

		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.FIRST_NAME_1, TestHelper.FAMILY_NAME_1);

		Main.getInstance().takeDeposit(ac.getId(), BigDecimal.ONE);
		Main.getInstance().makeWithdraw(ac.getId(), BigDecimal.ONE.negate());
	}

	@Test
	public void getStatementTest() throws BankAccountNotFoundException, InvalideTransactionException {
		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.FIRST_NAME_1, TestHelper.FAMILY_NAME_1);

		Main.getInstance().takeDeposit(ac.getId(), BigDecimal.TEN);
		Main.getInstance().makeWithdraw(ac.getId(), BigDecimal.ONE);

		List<BankRecord> brs = ac.getRecords();

		assertThat(brs != null).isTrue();
		assertThat(brs.size() == 2).isTrue();

		TestHelper.testBankRecord(brs.get(0), TestHelper.DEPOSIT, BigDecimal.TEN.toString(), BigDecimal.TEN.toString(),
				TestHelper.LDT_STRING);
		TestHelper.testBankRecord(brs.get(1), TestHelper.WITHDRAW, BigDecimal.ONE.toString(), TestHelper.NI.toString(),
				TestHelper.LDT_STRING);
	}

	@Test
	public void getEmptyStatementTest() throws BankAccountNotFoundException, InvalideTransactionException {
		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.FIRST_NAME_1, TestHelper.FAMILY_NAME_1);

		List<BankRecord> brs = ac.getRecords();

		assertThat(brs != null).isTrue();
		assertThat(brs.size() == 0).isTrue();
	}

	@Test
	public void printStatementTest() throws BankAccountNotFoundException, InvalideTransactionException {
		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.FIRST_NAME_1, TestHelper.FAMILY_NAME_1);

		Main.getInstance().takeDeposit(ac.getId(), BigDecimal.TEN);
		Main.getInstance().makeWithdraw(ac.getId(), BigDecimal.ONE);

		List<String> res = ac.printStatement();

		assertThat(res != null).isTrue();
		assertThat(res.size() == 3).isTrue();

		assertThat(TestHelper.PRINT_STATEMENT_TEST_EXPERTED_0.equals(res.get(0))).isTrue();

		assertThat(TestHelper.PRINT_STATEMENT_TEST_EXPERTED_1.equals(res.get(1))).isTrue();

		assertThat(TestHelper.PRINT_STATEMENT_TEST_EXPERTED_2.equals(res.get(2))).isTrue();
	}
}
