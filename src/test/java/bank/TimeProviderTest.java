package bank;

import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;



public class TimeProviderTest {

	@Test
	public void testLocalDateTime() {
		TimeProvider.useFixedClockAt(TestHelper.LDT);
		assertThat(TimeProvider.now().compareTo(TestHelper.LDT)==0).isTrue();
		
		LocalDateTime now = LocalDateTime.now();
		TimeProvider.useFixedClockAt(now);
		assertThat(TimeProvider.now().compareTo(now)==0).isTrue();
	}
}
