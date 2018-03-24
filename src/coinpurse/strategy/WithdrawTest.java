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
	/** default currency */
	private final String CURRENCY = "Baht";
	private WithdrawStrategy strategy;
	private List<Valuable> money = null;
	
	@Before
	public void setUp() throws Exception {
		//strategy = new GreedyWithdraw();
		strategy = new RecursiveWithdraw();
		money = new ArrayList<>();
	}
	
	@Test (timeout = 1000)
	public void testWithdrawNothing() {
		money.clear();
		assertEquals(0, strategy.withdraw(makeMoney(0), money).size(), TOL);
		money.add(makeMoney(2.0));
		assertEquals(0, strategy.withdraw(makeMoney(0), money).size(), TOL);
	}
	
	@Test (timeout = 1000)
	public void testWithdrawOneThing() {
		money.add(makeMoney(1.0));
		assertEquals(1, strategy.withdraw(makeMoney(1.0), money).size(), TOL);
		money.clear();
		
		money.add(makeMoney(2.0));
		money.add(makeMoney(5.0));
		money.add(makeMoney(10.0));
		assertEquals(1, strategy.withdraw(makeMoney(5.0), money).size(), TOL);
		assertEquals(1, strategy.withdraw(makeMoney(10.0), money).size(), TOL);
	}
	
	@Test (timeout = 1000)
	public void testWithdrawMultipleThings() {
		money.add(makeMoney(1.0));
		money.add(makeMoney(2.0));
		money.add(makeMoney(4.0));
		money.add(makeMoney(5.0));
		money.add(makeMoney(10.0));
		money.add(makeMoney(50.0));
		money.add(makeMoney(100.0));
		assertEquals(2, strategy.withdraw(makeMoney(150.0), money).size(), TOL);
		
		//Greedy strategy won't work but recursive strategy will work
		money.clear();
		money.add(makeMoney(5.0));
		money.add(makeMoney(2.0));
		money.add(makeMoney(2.0));
		money.add(makeMoney(2.0));
		assertEquals(3, strategy.withdraw(makeMoney(6.0), money).size(), TOL); // result if use recursive
		//assertNull(strategy.withdraw(new Money(7.0, "Baht"), money)); // result if use greedy
		
	}
	
	@Test (timeout = 1000)
	public void testWithdrawEverything() {
		money.add(makeMoney(20.0));
		money.add(makeMoney(30.0));
		money.add(makeMoney(50.0));
		money.add(makeMoney(100.0));
		assertEquals(4, strategy.withdraw(makeMoney(200.0), money).size(), TOL);
		money.clear();
		
		money.add(makeMoney(5.0));
		money.add(makeMoney(10.0));
		money.add(makeMoney(50.0));
		money.add(makeMoney(100.0));
		assertEquals(1, strategy.withdraw(makeMoney(5.0), money).size(), TOL);
		assertEquals(1, strategy.withdraw(makeMoney(50.0), money).size(), TOL);
		assertEquals(2, strategy.withdraw(makeMoney(110.0), money).size(), TOL);
	}
	
	@Test (timeout = 1000)
	public void testImpossibleWithdraw() {
		money.add(makeMoney(1.0));
		money.add(makeMoney(2.0));
		money.add(makeMoney(30.0));
		money.add(makeMoney(50.0));
		assertNull(strategy.withdraw(makeMoney(100.0), money));
		assertNull(strategy.withdraw(makeMoney(25.0), money));
	}
	
	@Test (timeout = 1000)
	public void testWithdrawDifferenceCurrency() {
		money.add(makeMoney(10));
		money.add(makeMoney(5));
		assertEquals(0, strategy.withdraw(new Money(5, "Cent"), money).size(), TOL);
		assertEquals(2, strategy.withdraw(makeMoney(15), money).size(),TOL);
	}
	
	public Valuable makeMoney(double amount) {
		Money m = new Money(amount, CURRENCY);
		return m;
	}
}
