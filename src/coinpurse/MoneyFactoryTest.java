package coinpurse;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Test the MoneyFactory, ThaiMoneyFactory and MalayMoneyFactory using JUnit.
 * This is a JUnit 4 test suite.  
 * 
 * IDEs (Eclipse, Netbeans, IntelliJ, BlueJ) include JUnit 4,
 * but you have to tell the IDE to add it to your project as a "Library".
 * To run these tests, right click on this file (in Project panel)
 * and choose Run As -> JUnit test
 * 
 * @author Kunyaruk Katebunlu
 * @version 2018.02.16
 */
public class MoneyFactoryTest {
	/** tolerance for comparing two double values */
	private static final double TOL = 1.0E-6;
	
	@Test
	public void testMoneyFactory() {
		MoneyFactory dmf = MoneyFactory.getInstance();
		MoneyFactory exmf = new ThaiMoneyFactory();
		//test getInstance() method
		assertEquals(exmf.getClass().getName(), dmf.getClass().getName());
		
		//test createMoney() method with String
		Valuable value = dmf.createMoney("5");
		assertEquals(new Coin(5, "Baht"), value);
		
		//test setFactory() method
		MoneyFactory.getInstance().setFactory(new MalayMoneyFactory());
		dmf = MoneyFactory.getInstance();
		assertEquals("coinpurse.MalayMoneyFactory", dmf.getClass().getName());
	}
	
	@Test
	public void testMalayMoneyFactory() {
		//test createMoney() method in MalayMoneyFactory
		MoneyFactory mfM = MoneyFactory.getInstance();
		double[] ableCoins = {0.05, 0.10, 0.20, 0.50};
		double[] ableBankNotes = {1, 2, 5, 10, 20, 50, 100};
		
		//test create all Malay's valid coins
		for (double val : ableCoins) {
			Valuable mf = mfM.createMoney(val);
			assertEquals(val*100, mf.getValue(), TOL);
			assertEquals("Sen", mf.getCurrency());
		}
		
		//test create all Malay's valid banknotes
		for (double val : ableBankNotes) {
			Valuable mf = mfM.createMoney(val);
			assertEquals(val, mf.getValue(), TOL);
			assertEquals("Ringgit", mf.getCurrency());
		}
	}
	
	@Test
	public void testThaiMoneyFactory() {
		//test createMoney() method in ThaiMoneyFactory
		MoneyFactory.getInstance().setFactory(new ThaiMoneyFactory());
		MoneyFactory mfTH = MoneyFactory.getInstance();
		double[] ableCoins = {1, 2, 5, 10};
		double[] ableBankNotes = {20, 50, 100, 500, 1000};
		
		//test create all Thai's valid coins
		for (double val : ableCoins) {
			Valuable mf = mfTH.createMoney(val);
			assertEquals(val, mf.getValue(), TOL);
			assertEquals("Baht", mf.getCurrency());
		}
		
		//test create all Thai's valid banknotes
		for (double val : ableBankNotes) {
			Valuable mf = mfTH.createMoney(val);
			assertEquals(val, mf.getValue(), TOL);
			assertEquals("Baht", mf.getCurrency());
		}
	}
}
