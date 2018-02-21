package coinpurse;

/**
 * A MoneyFactory class is a singleton class which is an abstract class.
 * This class use to get and also set an instance of MoneyFactory and 
 * create new money object with the local currency of MoneyFactory.
 * @author Kunyaruk Katebunlu
 */
public abstract class MoneyFactory {
	/** singleton instance of MoneyFactory. */
	private static MoneyFactory factory;
	
	protected MoneyFactory() { }
	
	/**
	 * Get an instance of MoneyFactory. if factory is null,
	 * the default MoneyFactory will be ThaiMoneyFactory.
	 * @return an object of a subclass (such as ThaiMoneyFactor)
	 */
	public static MoneyFactory getInstance() {
		if(factory == null) factory = new ThaiMoneyFactory();
		return factory;
	}
	
	/**
	 * Create new money object in the local currency. If the value 
	 * is not a valid currency amount, then throw IllegalArgumentException.
	 * @param value is a double value that want to create.
	 * @return new money object with local currency
	 * @throws IllegalArgumentException if the value is not valid
	 */
	public abstract Valuable createMoney(double value);
	
	/**
	 * Accepts money value as a String and convert the value to a double
	 * and calls createMoney(double) and create new money.
	 * @param value is a String value that want to create.
	 * @return new money object with local currency
	 */
	public Valuable createMoney(String value) {
		double val = 0;
		 try {
		 val = Double.parseDouble(value);
		 } catch (NumberFormatException ex) {
			 throw new IllegalArgumentException(ex.getMessage());
		 }
		 return createMoney(val);
	}
	
	/**
	 * Static method to set the MoneyFactory object that is used.
	 * @param mf is the MoneyFactory object that is used.
	 */
	public static void setFactory(MoneyFactory mf) {
		factory = mf;
	}
}
