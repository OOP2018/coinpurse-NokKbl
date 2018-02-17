package coinpurse;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the Purse using JUnit.
 * This is a JUnit 4 test suite.  
 * 
 * IDEs (Eclipse, Netbeans, IntelliJ, BlueJ) include JUnit 4,
 * but you have to tell the IDE to add it to your project as a "Library".
 * To run these tests, right click on this file (in Project panel)
 * and choose Run As -> JUnit test
 * 
 * @author  Resident Evil
 * @version 2018.01.19
 */
public class PurseTest {
	/** tolerance for comparing two double values */
	private static final double TOL = 1.0E-6;
	/** default currency */
	private static final String CURRENCY = "Baht";
	private MoneyFactory moneyFactory = MoneyFactory.getInstance();
	
    /**
     * Sets up the test fixture.
     * Called before every test method.
     */
    @Before
    public void setUp() {
    	// nothing to initialize
    }
    
    /** Make a coin with the default currency. To save typing "new Coin(...)" */
    private Valuable makeMoney(double value) {
		if(value < 20) return new Coin(value,CURRENCY);
		else {
    			Valuable valuable = null;
    			try {
    				valuable = moneyFactory.createMoney(value);
    			} catch(IllegalArgumentException ex) {
    				System.out.println("Sorry, "+ value + " is not a valid amount.");
    			}
    		return valuable;
		}
	}

    /** Easy test that the Purse constructor is working. */
    @Test
    public void testConstructor()
    {
        Purse purse = new Purse(3);
        assertEquals(3, purse.getCapacity());
        assertEquals(false, purse.isFull());
        assertEquals(0, purse.count());
    }

    /** Insert some coins. Easy test. */
    @Test
    public void testInsert()
    {
        Purse purse = new Purse(3);
        Valuable coin1 = makeMoney(5);
        Valuable coin2 = makeMoney(10);
        Valuable coin3 = makeMoney(1);
        assertTrue( purse.insert(coin1));
        assertTrue( purse.insert(coin3));
        assertTrue( purse.insert(coin2));
        assertEquals( 3, purse.count() );
        // purse is full so insert should fail
        assertFalse( purse.insert(makeMoney(1)) );
    }

	/** Insert should reject coin with no value. */
    @Test
    public void testInsertNoValue()
    {
        Purse purse = new Purse(3);
        Coin fakeCoin = new Coin(0, CURRENCY);
        assertFalse( purse.insert(fakeCoin) );
    }

    @Test(timeout=1000)
    public void testIsFull()
    {   // borderline case (capacity 1)
        Purse purse = new Purse(1);
        assertFalse( purse.isFull() );
        purse.insert( makeMoney(1) );
        assertTrue( purse.isFull() );
        // real test
        int capacity = 4;
        purse = new Purse(capacity);
        for(int k=1; k<=capacity; k++) {
            assertFalse(purse.isFull());
            purse.insert( makeMoney(k) );
        }
        // should be full now
        assertTrue( purse.isFull() );
        assertFalse( purse.insert( makeMoney(5) ) );
    }

	/** Should be able to insert same coin many times,
	 *  since spec doesn't say anything about this.
	 */
	@Test(timeout=1000)
	public void testInsertSameCoin()
	{
		int capacity = 5;
		double value = 10.0;
		Purse purse = new Purse(capacity);
		Coin coin = new Coin(value, "THB");
		assertTrue( purse.insert(coin) );
		assertTrue( purse.insert(coin) ); // should be allowed
		assertTrue( purse.insert(coin) ); // should be allowed
		assertTrue( purse.insert(coin) ); // should be allowed
		assertTrue( purse.insert(coin) ); // should be allowed
		assertEquals( purse.getBalance(), 5*value, TOL);
	}

	/** Add one coin and remove it. */
	@Test(timeout=1000)
	public void testEasyWithdraw() {
		Purse purse = new Purse(10);
		double [] values = {1, 20, 0.5, 10}; // values of coins we will insert
		
		for(double value : values) {
			Valuable coin = makeMoney(value);
			assertTrue(purse.insert(coin));
			assertEquals(value,  purse.getBalance(), TOL);
			Valuable [] result = purse.withdraw(value);
			assertTrue( result != null );
			assertEquals( 1, result.length );
			assertSame(  coin, result[0] ); // should be same object
			assertEquals( 0, purse.getBalance(), TOL );
		}
	}
	
	/** Add 4 coins and then withdraw in pairs, but not in same order. */
	@Test(timeout=1000)
	public void testMultiWithdraw() {
		Purse purse = new Purse(10);
		Valuable[] value = { makeMoney(5.0), makeMoney(10.0), makeMoney(1.0), makeMoney(5.0) };
		// insert them all
		for(Valuable v: value) assertTrue( purse.insert(v) );
		
		double amount1 = value[1].getValue() + value[3].getValue();
		double amount2 = value[0].getValue() + value[2].getValue();
		assertEquals(amount1+amount2, purse.getBalance(), TOL );
		
		Valuable [] wd1 = purse.withdraw(amount1);
		assertEquals(amount1, sum(wd1), TOL );
		
		assertEquals(amount2, purse.getBalance(), TOL );
		Valuable [] wd2 = purse.withdraw(amount2);
		
		// should be empty now
		assertEquals(0, purse.getBalance(), TOL );
	}
	
	/** Withdraw full amount in purse, using varying numbers of objects. */
	@Test(timeout=1000)
	public void testWithdrawEverything() {
		Purse purse = new Purse(10);
		// Coins we want to insert and then withdraw.
		// Use values such that greedy will succeed, but not monotonic
		List<Valuable> coins = Arrays.asList(
				makeMoney(1.0), makeMoney(0.5), makeMoney(10.0), makeMoney(0.25), makeMoney(5.0)
				);
		// num = number of coins to insert and then withdraw
		for(int num=1; num <= coins.size(); num++) {
			double amount = 0.0;
			List<Valuable> subList = coins.subList(0, num);
			for(Valuable c: subList) {
				purse.insert(c);
				amount += c.getValue();
			}
			// balance should be exactly what we just inserted
			assertEquals( amount, purse.getBalance(), TOL);
			// can we withdraw it all?
			Valuable[] result = purse.withdraw(amount);
			String errmsg = String.format("couldn't withdraw %.2f but purse has %s",
					amount, Arrays.toString(subList.toArray()) );
			assertNotNull( errmsg, result );
			// is the amount correct?
			assertEquals("Withdraw wrong amount", amount, sum(result), TOL);
			// should not be anything left in the purse
			assertEquals( 0.0, purse.getBalance(), TOL);
		}
	}

	@Test(timeout=1000)
	public void testImpossibleWithdraw() {
		Purse purse = new Purse(10);
		assertNull( purse.withdraw(1) );
		purse.insert( makeMoney(20) );
		assertNull( purse.withdraw(1) );
		assertNull( purse.withdraw(19) );
		assertNull( purse.withdraw(21) );
		purse.insert( makeMoney(20) ); // now it has 20 + 20
		assertNull( purse.withdraw(30) );
	}
	
	@Test(timeout=1000)
	public void testValuableWithdraw() {
		// withdraw easy one
		Purse purse = new Purse(10);
		purse.insert(new Money(5, "Baht"));
		assertEquals(5, purse.getBalance(), TOL);
		purse.withdraw(new Money(5, "Dollar"));
		assertEquals(5, purse.getBalance(), TOL);
		purse.withdraw(new Money(5, "Baht"));
		assertEquals(0, purse.getBalance(), TOL);
		
		// withdraw multiple money
		Purse newPurse = new Purse(5);
		newPurse.insert(new Money(5, "Baht"));
		newPurse.insert(new Money(10, "Baht"));
		newPurse.insert(new Money(1, "Baht"));
		newPurse.insert(new Money(20, "Baht"));
		newPurse.insert(new Money(45, "Baht"));
		// this value will not insert
		newPurse.insert(new Money(15, "Baht"));
		
		assertEquals(81, newPurse.getBalance(), TOL);
		newPurse.withdraw(new Money(6, "Baht"));
		assertEquals(75, newPurse.getBalance(), TOL);
		newPurse.withdraw(new Money(100, "Baht"));
		assertEquals(75, newPurse.getBalance(), TOL);
		newPurse.withdraw(new Money(10, "Yen"));
		assertEquals(75, newPurse.getBalance(), TOL);
		newPurse.withdraw(new Money(75, "Baht"));
		assertEquals(0, newPurse.getBalance(), TOL);
	}
	
	@Test(timeout=100)
	public void testMultipleCurrency() {		
		Purse purse = new Purse(5);
		purse.insert(new Money(10, "Cent"));
		purse.insert(new Money(20, "Baht"));
		purse.insert(new Money(20, "Dollar"));
		purse.insert(new Money(30, "Cent"));
		purse.insert(new Money(35,"Baht"));
		//must not insert these two money
		purse.insert(new Money(40, "Baht"));
		purse.insert(new Money(20, "Dollar"));
		
		assertEquals(115, purse.getBalance(), TOL);
		purse.withdraw(new Money(55,"Baht"));
		assertEquals(60, purse.getBalance(), TOL);
		purse.withdraw(new Money(30,"Dollar"));
		assertEquals(60, purse.getBalance(), TOL);
		purse.withdraw(new Money(40,"Cent"));
		assertEquals(20, purse.getBalance(),TOL);
		purse.withdraw(new Money(20,"Dollar"));
		assertEquals(0, purse.getBalance(),TOL);
		
		purse = new Purse(6);
	    purse.insert(new Money(20, "Baht"));
	    purse.insert(new Money(30, "Baht"));
	    purse.insert(new Money(40, "Baht"));
	    purse.insert(new Money(30, "Yen"));
	    purse.insert(new Money(50, "Baht"));
	    
	    purse.withdraw(new Money(50,"Yen"));
	    assertEquals(170, purse.getBalance(), TOL);
	    purse.withdraw(new Money(90,"Baht"));
	    assertEquals(80, purse.getBalance(), TOL);
	    
		purse = new Purse(8);
        purse.insert(new Money(40, "Baht"));
        purse.insert(new Money(20, "baht"));
        purse.insert(new Money(20, "baht"));
        purse.insert(new Money(50, "Baht"));
        purse.insert(new Money(10, "yen"));
        purse.insert(new Money(20, "Yen"));
        //Withdraw exceed money.
        purse.withdraw(new Money(100, "Baht"));
        assertEquals(160, purse.getBalance(), TOL);
        //Withdraw test capital alphabets.
        purse.withdraw(new Money(30, "Yen"));
        assertEquals(130, purse.getBalance(), TOL);
	}
	
	@Test(timeout=1000)
	public void testEquals() {
		Valuable c = new Coin(5, "Baht");
		Valuable c1 = new Coin(5, "Baht");
		Valuable c2 = new Coin(10, "Baht");
		Valuable c3 = new Coin(5, "Dollar");
		
		Valuable bk = makeMoney(5);
		Valuable bk1 = makeMoney(20);
		Valuable bk2 = makeMoney(20);
		
		assertTrue(c.equals(c1));
		assertFalse(c.equals(c2));
		assertFalse(c.equals(c3));
		assertTrue(bk1.equals(bk2));
		assertTrue(c1.equals(bk));
	}
	
	/**
	 * Sum the value of some coins.
	 * @param wd1 array of coins
	 * @return sum of values of the coins
	 */
	private double sum(Valuable[] wd1)  {
		if (wd1 == null) return 0.0;
		double sum = 0;
		for(Valuable v: wd1) if (v != null) sum += v.getValue();
		return sum;
	}
}