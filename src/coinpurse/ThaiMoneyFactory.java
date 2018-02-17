package coinpurse;

/**
 * A ThaiMoneyFactory class is a subclass of MoneyFactory class.
 * This class use to create new money object with the local currency in Thailand
 * and if it create a banknote, the money will have unique serial number.
 * @author Kunyaruk Katebunlu
 */
public class ThaiMoneyFactory extends MoneyFactory{
	/** the serial number of Thai's banknote */
	private static long serialNumber = 1000000;
	
	/**
	 * Get unique serial number for Thai's banknote.
	 * @return money's serial number
	 */
	public long getSerial() {
		return this.serialNumber;
	}
	
	/**
	 * Create new money object in Thailand's local currency. If the value 
	 * is not a valid currency amount, then throw IllegalArgumentException.
	 * @param value is the value that want to create.
	 * @return new money object with local currency
	 * @throws IllegalArgumentException if the value is not valid
	 */
	@Override
	public Valuable createMoney(double value) {
		double[] coins = {1, 2, 5, 10};
		double[] banknotes = {20, 50, 100, 500, 1000};
		
		for (double val : coins) {
			if(value == val) return new Coin(value, "Baht");
		}
		
		for (double val : banknotes) {
			if(value == val) {
				Valuable banknote = new BankNote(value, "Baht", getSerial());
				serialNumber++;
				return banknote;
			}
		}
		
		throw new IllegalArgumentException(value + " is not a valid amount in Thailand.");
	}
}
