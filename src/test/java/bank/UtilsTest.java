package bank;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;


public class UtilsTest {
	  @Test
	  public void dTFormatterTest() {
		LocalDateTime ldt = LocalDateTime.of(2020, 01, 01, 12, 12,12);
		String expected = "2020-01-01 12:12:12";
	    assertThat(expected.equals(Utils.DTFormatter.format(ldt))).isTrue();
	  }
	 
}
