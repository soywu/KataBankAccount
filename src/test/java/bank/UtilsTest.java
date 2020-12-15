package bank;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class UtilsTest {
	  @Test
	  public void dTFormatterTest() {
	    assertThat(TestHelper.LDT_STRING.equals(Utils.DTFormatter.format(TestHelper.LDT))).isTrue();
	  }
	 
}
