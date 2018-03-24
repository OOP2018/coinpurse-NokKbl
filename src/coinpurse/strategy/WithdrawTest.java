package coinpurse.strategy;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import coinpurse.Money;
import coinpurse.Valuable;

/**
 * Test any WithdrawStrategy using JUnit.
 * This is a JUnit 4 test suite.
 * @author  Kunyaruk Katebunlu
 */
public class WithdrawTest {
	/** tolerance for comparing two double values */
	private static final double TOL = 1.0E-6;
	private WithdrawStrategy strategy;
	private List<Valuable> money = null;
	
	@Before
	public void setUp() throws Exception {
		strategy = new RecursiveWithdraw();
		money = new ArrayList<>();
	}
	
	@Test (timeout = 1000)
	public void testWithdrawNothing() {
		money.clear();
		assertNull(strategy.withdraw(new Money(0, "Baht"), money));
		
		money.add(new Money(2.0, "Baht"));
		assertNull(strategy.withdraw(new Money(0, "Baht"), money));
	}
	
	@Test (timeout = 1000)
	public void testWithdrawOneThing() {
		money.add(new Money(1.0, "Baht"));
		assertEquals(1, strategy.withdraw(new Money(1.0, "Baht"), money).size(), TOL);
		
		money.add(new Money(2.0, "Baht"));
		money.add(new Money(5.0, "Baht"));
		money.add(new Money(10.0, "Baht"));
		assertEquals(1, strategy.withdraw(new Money(5.0, "Baht"), money).size(), TOL);
		assertEquals(1, strategy.withdraw(new Money(10.0, "Baht"), money).size(), TOL);
	}
	
	@Test (timeout = 1000)
	public void testWithdrawMultipleThings() {
		money.add(new Money(1.0, "Baht"));
		money.add(new Money(2.0, "Baht"));
		money.add(new Money(4.0, "Baht"));
		money.add(new Money(5.0, "Baht"));
		money.add(new Money(10.0, "Baht"));
		money.add(new Money(50.0, "Baht"));
		money.add(new Money(100.0, "Baht"));
		assertEquals(2, strategy.withdraw(new Money(150.00, "Baht"), money).size(), TOL);
		
		//Greedy won't work
		money.clear();
		money.add(new Money(3.0, "Baht"));
		money.add(new Money(4.0, "Baht"));
		money.add(new Money(5.0, "Baht"));
		assertEquals(2, strategy.withdraw(new Money(7.0, "Baht"), money).size(), TOL);
	}
	
	@Test (timeout = 1000)
	public void testWithdrawEverything() {
		money.add(new Money(20.0, "Baht"));
		money.add(new Money(30.0, "Baht"));
		money.add(new Money(50.0, "Baht"));
		money.add(new Money(100.0, "Baht"));
		assertEquals(4, strategy.withdraw(new Money(200.0,"Baht"), money).size(), TOL);
		
		money.add(new Money(5.0, "Baht"));
		money.add(new Money(10.0, "Baht"));
		money.add(new Money(50.0, "Baht"));
		money.add(new Money(100.0, "Baht"));
		assertEquals(1, strategy.withdraw(new Money(5.0, "Baht"), money).size(), TOL);
		assertEquals(1, strategy.withdraw(new Money(50.0, "Baht"), money).size(), TOL);
		assertEquals(2, strategy.withdraw(new Money(110.0, "Baht"), money).size(), TOL);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testException() {
		money.add(new Money(1.0, "Baht"));
		money.add(new Money(50.0, "Baht"));
		// less than 0
		strategy.withdraw(new Money(-1.0, "Baht"), money);
	}
	
	@Test (timeout = 1000)
	public void testImpossibleWithdraw() {
		money.add(new Money(1.0, "Baht"));
		money.add(new Money(2.0, "Baht"));
		money.add(new Money(30.0, "Baht"));
		money.add(new Money(50.0, "Baht"));
		assertNull(strategy.withdraw(new Money(100.0, "Baht"), money));
		assertNull(strategy.withdraw(new Money(25.0, "Baht"), money));
	}
	
	@Test (timeout = 1000)
	public void testWithdrawDifferenceCurrency() {
		money.add(new Money(10, "Cent"));
		money.add(new Money(20, "Dollar"));
		money.add(new Money(30, "Cent"));
		
		assertNull(strategy.withdraw(new Money(30,"Dollar"), money));
		assertEquals(2, strategy.withdraw(new Money(40,"Cent"), money).size(),TOL);
		assertEquals(1, strategy.withdraw(new Money(20,"Dollar"), money).size(),TOL);
	}

}
