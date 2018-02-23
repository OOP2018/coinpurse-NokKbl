package coinpurse;

/**
 * A MalayMoneyFactory class is a subclass of MoneyFactory class.
 * This class use to create new money object with the local currency in Malaysia
 * and if it create a banknote, the money will have unique serial number.
 * @author Kunyaruk Katebunlu
 */
public class MalayMoneyFactory extends MoneyFactory{
	/** the serial number of Malay's banknote */
	private static long serialNumber = 1000000;
	
	/**
	 * Get unique serial number for Malay's banknote.
	 * @return money's serial number
	 */
	public long getSerial() {
		return this.serialNumber;
	}
	
	/**
	 * Create new money object in Malaysia's local currency. If the value 
	 * is not a valid currency amount, then throw IllegalArgumentException.
	 * @param value is the value that want to create.
	 * @return new money object with local currency
	 * @throws IllegalArgumentException if the value is not valid
	 */
	@Override
	public Valuable createMoney(double value) {
		double[] coins = {0.05, 0.10, 0.20, 0.50};
		double[] banknotes = {1, 2, 5, 10, 20, 50, 100};
		
		for (double val : coins) {
			if(value == val) return new Coin(value, "Ringgit");
		}
		
		for (double val : banknotes) {
			if(value == val) {
				Valuable banknote = new BankNote(value, "Ringgit", getSerial());
				serialNumber++;
				return banknote;
			}
		}
		
		throw new IllegalArgumentException(value + " is not a valid amount in Malaysia.");
	}
}
