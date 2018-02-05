package coinpurse;

/**
 * BankNote represents banknote (money) with a fixed value and currency.
 * @author Kunyaruk Katebunlu
 */
public class BankNote implements Valuable {
	/** the value of the banknote */
	private double value;
	/** the currency value of the banknote */
	private String currency;
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
		this.value = value;
		this.currency = currency;
		serialNumber = nextSerialNumber;
	}
	
	/**
	 * Get unique serial number of the banknote.
	 * @return Banknote's serial number
	 */
	public long getSerial() {
		nextSerialNumber++;
		return this.serialNumber;
	}
	
	/**
	 * Get value of the money.
	 * @return the value of money
	 */
	public double getValue() {
		return this.value;
	}

	/**
	 * Get currency of the money.
	 * @return money's currency
	 */
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * Compares this object to the specified object.
	 * @param arg is the object that use to compare with this object
	 * @return true if the value and the currency of the objects are the same
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(this.getClass() != obj.getClass()) return false;
		BankNote banknote = (BankNote) obj;
		return this.currency.equals(banknote.currency) && this.value == banknote.value;
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
