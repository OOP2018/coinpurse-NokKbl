package coinpurse;

/**
 * MoneyFactoryDemo use to test that MoneyFactory, ThaiMoneyFactory and MalayMoneyFactory are work.
 * @author Kunyaruk Katebunlu
 */
public class MoneyFactoryDemo {
	
	/**
	 * Test singleton in MoneyFactory class.
	 */
	public static void testMoneyFactory() {
		MoneyFactory mf1 = MoneyFactory.getInstance();
		System.out.println("mf1 is a " + mf1.getClass().getName());
		MoneyFactory mf2 = MoneyFactory.getInstance();
		System.out.println("mf2 is a " + mf2.getClass().getName());
		System.out.println("mf1 == mf2 (same)? " + (mf1 == mf2));
		
		//create some money
		String[] values = {"0.05", "0.5", "1.0", "2.0", "5", "20", "100", "500", "1000"};
		for (String val : values) {
			try {
				Valuable v = mf1.createMoney(val);
				System.out.println(v.toString());
			} catch(IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
		}
		System.out.println();
	}
	
	/**
     * Configure and start the application.
     * @param args not used
     */
	public static void main(String[] args) {
		testMoneyFactory();
		MoneyFactory.getInstance().setFactory(new MalayMoneyFactory());
		testMoneyFactory();
	}
}
