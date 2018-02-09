package coinpurse;

/**
 * Money represents money with a fixed value and currency.
 * @author Kunyaruk Katebunlu
 */
public class Money implements Valuable{
	/** the value of money */
	protected double value;
	/** the currency value of money */
	protected String currency;
	
	/**
	 * Make sure that the value must not be negative, if it's negative it'll
	 * constructs an IllegalArgumentException with the specified detail message.
	 * @param value is value of money
	 * @param currency is money's currency
	 */
	public Money(double value, String currency) {
		if(value < 0) throw new IllegalArgumentException("Money must be more than 0.");
		
		this.value = value;
		this.currency = currency;
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
	public boolean equals (Object arg) {
		if (arg == null) return false;
		if (arg.getClass() != this.getClass()) return false;
		Money money = (Money) arg;
		return this.getValue() == money.getValue() && this.getCurrency().equalsIgnoreCase(money.getCurrency());
	}
	
	/**
	 * Compares this object with the specified object for order.
	 * If both objects have the same currency, order them by value.
	 * @param val is the object that want to compare the value with this value
	 * @return a negative integer if value of this object is less than value of the specified object,
	 *			zero if value of this object equal to value of the specified object or
	 *			a positive integer if value of this object is greater than value of the specified object.
	 */
	@Override
	public int compareTo(Valuable val) {
		if(this.getCurrency().equalsIgnoreCase(val.getCurrency())) return Double.compare(this.getValue(), val.getValue());
		return this.getCurrency().compareToIgnoreCase(val.getCurrency());
	}
}
