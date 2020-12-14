package bank;

import org.junit.Before;
import org.junit.Test;

import bank.account.BankAccount;
import bank.account.BankAccountNotFoundException;
import bank.account.BankRecord;
import bank.transcation.InvalideTransactionException;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public class TddMainTest {

	private static final LocalDateTime NOW = LocalDateTime.now();
	private String timestamp ;
	
	@Before
	public void setup() {
		IdProvider.initCount();
		Main.getInstance().init();
		TimeProvider.useFixedClockAt(NOW);
		timestamp  = Utils.DTFormatter.format(NOW);
	}
	
	@Test
	public void createAccountTest() throws BankAccountNotFoundException {

		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.firstName1, TestHelper.familyName1);
		BankAccount ac2 = Main.getInstance().createNewAccount(TestHelper.firstName1, TestHelper.familyName1);
		
		assertThat(ac != null).isTrue();
		assertThat(ac.getBalance().compareTo(BigDecimal.ZERO) == 0).isTrue();
		assertThat(TestHelper.firstName1.equals(ac.getFirstName())).isTrue();
		assertThat(TestHelper.familyName1.equals(ac.getFamilyName())).isTrue();
		assertThat(ac.getId() == 0).isTrue();
		
		assertThat(ac2 != null).isTrue();
		assertThat(ac2.getBalance().compareTo(BigDecimal.ZERO) == 0).isTrue();
		assertThat(TestHelper.firstName1.equals(ac2.getFirstName())).isTrue();
		assertThat(TestHelper.familyName1.equals(ac2.getFamilyName())).isTrue();
		assertThat(ac2.getId() == 1).isTrue();
		
		assertThat( ac == Main.getInstance().getAccount(ac.getId()) ).isTrue();
		assertThat( ac2 == Main.getInstance().getAccount(ac2.getId()) ).isTrue();
	}
	
	@Test(expected = BankAccountNotFoundException.class)
	public void AccountNotFoundTest() throws BankAccountNotFoundException {

		Main.getInstance().createNewAccount(TestHelper.firstName1, TestHelper.familyName1);
		
	    Main.getInstance().getAccount(100);
	    
	}
	
	@Test
	public void takeDepositTest() throws BankAccountNotFoundException, InvalideTransactionException {
		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.firstName1, TestHelper.familyName1);
		
		BankRecord br = Main.getInstance().takeDeposit(ac.getId(), BigDecimal.ONE);
		
		assertThat( ac.getBalance().compareTo(BigDecimal.ONE)==0 ).isTrue();
		assertThat( br!=null ).isTrue();
		assertThat( "1".equals( br.getAmount() ) ).isTrue();
		assertThat( "1".equals( br.getBalance() ) ).isTrue();
		assertThat( "Deposit".equals( br.getOperation() ) ).isTrue();
		assertThat( timestamp.equals( br.getTimeStamp() ) ).isTrue();
	}
	
	@Test(expected = InvalideTransactionException.class)
	public void InvalidDepositAmountTest() throws BankAccountNotFoundException, InvalideTransactionException {
		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.firstName1, TestHelper.familyName1);
		
		Main.getInstance().takeDeposit(ac.getId(), BigDecimal.ONE.negate());
	}
	
	@Test
	public void makeWithdrawTest() throws BankAccountNotFoundException, InvalideTransactionException {
		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.firstName1, TestHelper.familyName1);
		
		Main.getInstance().takeDeposit(ac.getId(), BigDecimal.ONE);
		BankRecord br = Main.getInstance().makeWithdraw(ac.getId(), BigDecimal.ONE);
		
		assertThat( ac.getBalance().compareTo(BigDecimal.ZERO)==0 ).isTrue();
		assertThat( br!=null ).isTrue();
		assertThat( "1".equals( br.getAmount() ) ).isTrue();
		assertThat( "0".equals( br.getBalance() ) ).isTrue();
		assertThat( "Withdraw".equals( br.getOperation() ) ).isTrue();
		assertThat( timestamp.equals( br.getTimeStamp() ) ).isTrue();
	}
	
	@Test(expected = InvalideTransactionException.class)
	public void InvalidWithDrawNotEnoughFundTest() throws BankAccountNotFoundException, InvalideTransactionException {
		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.firstName1, TestHelper.familyName1);
		
		Main.getInstance().takeDeposit(ac.getId(), BigDecimal.ONE);
		Main.getInstance().makeWithdraw(ac.getId(), BigDecimal.TEN);
	}
	
	@Test(expected = InvalideTransactionException.class)
	public void InvalidWithDrawAmountTest() throws BankAccountNotFoundException, InvalideTransactionException {
		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.firstName1, TestHelper.familyName1);
		
		Main.getInstance().takeDeposit(ac.getId(), BigDecimal.ONE);
		Main.getInstance().makeWithdraw(ac.getId(), BigDecimal.ONE.negate());
	}
	
	@Test
	public void getStatementTest() throws BankAccountNotFoundException, InvalideTransactionException {
		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.firstName1, TestHelper.familyName1);
		
		Main.getInstance().takeDeposit(ac.getId(), BigDecimal.TEN);
		Main.getInstance().makeWithdraw(ac.getId(), BigDecimal.ONE);
		
		List<BankRecord> brs = ac.getRecords();
		
		assertThat( brs !=null ).isTrue();
		assertThat(brs.size()==2 ).isTrue();
		
		assertThat( "10".equals( brs.get(0).getAmount() ) ).isTrue();
		assertThat( "10".equals( brs.get(0).getBalance() ) ).isTrue();
		assertThat( "Deposit".equals( brs.get(0).getOperation() ) ).isTrue();
		assertThat( timestamp.equals( brs.get(0).getTimeStamp() ) ).isTrue();
		
		assertThat( "1".equals( brs.get(1).getAmount() ) ).isTrue();
		assertThat( "9".equals( brs.get(1).getBalance() ) ).isTrue();
		assertThat( "Withdraw".equals( brs.get(1).getOperation() ) ).isTrue();
		assertThat( timestamp.equals( brs.get(1).getTimeStamp() ) ).isTrue();
	}
	
	@Test
	public void getEmptyStatementTest() throws BankAccountNotFoundException, InvalideTransactionException {
		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.firstName1, TestHelper.familyName1);
	
		List<BankRecord> brs = ac.getRecords();
		
		assertThat( brs !=null ).isTrue();
		assertThat(brs.size()==0 ).isTrue();
	}
	
	@Test
	public void printStatementTest() throws BankAccountNotFoundException, InvalideTransactionException {
		BankAccount ac = Main.getInstance().createNewAccount(TestHelper.firstName1, TestHelper.familyName1);
	
		
		Main.getInstance().takeDeposit(ac.getId(), BigDecimal.TEN);
		Main.getInstance().makeWithdraw(ac.getId(), BigDecimal.ONE);
		
		List<String> res =ac.printStatement();
		
		assertThat( res !=null ).isTrue();
		assertThat(res.size()==3 ).isTrue();
		

		assertThat("Bank Statement, Name: Alice Nike, Account id: 0".equals( res.get(0) ) ).isTrue();

		assertThat(("Operation : Deposit, TimeStamp : "+timestamp+", Amount : 10, Balance : 10").equals( res.get(1) ) ).isTrue();

		assertThat(("Operation : Withdraw, TimeStamp : "+timestamp+", Amount : 1, Balance : 9").equals( res.get(2) ) ).isTrue();
	}
}
