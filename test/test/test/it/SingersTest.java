package test.test.it;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import test.it.Bas;
import test.it.Singer;
import test.it.Soprano;
import test.it.Tenor;

public class SingersTest {

	@Spy Soprano soprano = new Soprano();
	@Spy Bas bas = new Bas();
	@Spy Tenor tenor = new Tenor();
	
	@Before	
	public void init(){
		MockitoAnnotations.initMocks(this);
	}

	
	@Test
	public void test() {
		Mockito.doThrow(new RuntimeException("just to see the sing method of soprano is invoked")).when(soprano).sing();
		Mockito.doThrow(new RuntimeException("just to see the sing method of bas is invoked")).when(bas).sing();
		Mockito.doThrow(new RuntimeException("just to see the sing method of tenor is invoked")).when(tenor).sing();
		List<Singer> singerList = new ArrayList<>();
		singerList.add(tenor);
		singerList.add(bas);
		singerList.add(soprano);
		singerList.forEach(
				singer -> {
					try {
						singer.sing();
						Assert.assertTrue(false); //test unreachable code
					} catch (Exception e) {
						Assert.assertTrue("", e.getMessage().indexOf("just to see the sing method")==0);
					}
				});
	}

}
