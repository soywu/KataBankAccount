package bank;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class IdProviderTest {
	  @Test
	  public void getCountAndPlusOneTest() {
		  IdProvider.initCount();
		  
		  int count1 = IdProvider.getCountAndPlusOne();
		  int count2 = IdProvider.getCountAndPlusOne();
		  int count3 = IdProvider.getCountAndPlusOne();

		  assertThat(count1==0).isTrue();
		  assertThat(count2==1).isTrue();
		  assertThat(count3==2).isTrue();
	  }
}
