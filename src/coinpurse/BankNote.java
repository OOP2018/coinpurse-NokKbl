package coinpurse;

/**
 * BankNote represents banknote (money) with a fixed value and currency.
 * @author Kunyaruk Katebunlu
 */
public class BankNote extends Money {
	/** the serial number of the purse */
	private long serialNumber;
	private static long nextSerialNumber = 1000000;
	
	/**
	 * Make sure that the value must not be negative,
	 * if it's negative change that to be 0.
	 * @param value is value of banknote
	 * @param currency is money's currency
	 */
	public BankNote(double value, String currency) {
		super(value, currency);
		this.serialNumber = nextSerialNumber++;
	}
	
	/**
	 * Get unique serial number of the banknote.
	 * @return Banknote's serial number
	 */
	public long getSerial() {
		return this.serialNumber;
	}
	
	/**
	 * toString show the money's value and money's currency.
	 * @return value with currency of the money
	 */
	@Override
	public String toString() {
		return String.format("%.2f-%s note [%d]", this.getValue(), this.getCurrency(), this.getSerial());
	}
}
