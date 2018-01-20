package coinpurse;

/**
 * Coin represents coinage (money) with a fixed value and currency.
 * @author Kunyaruk Katebunlu
 */
public class Coin implements Comparable<Coin> {
	/** the value of the coin */
	private double value;
	/** the currency value of the coin */
	private String currency;
	
	/**
	 * Make sure that the value must not be negative,
	 * if it's negative change that to be 0.
	 * @param value is value of a coin
	 * @param currency is coin's currency
	 */
	public Coin (double value, String currency) {
		if(value >= 0) this.value = value;
		else this.value = 0;
		
		this.currency = currency;
	}
	
	/**
	 * Get value of the coin.
	 * @return the value of coin
	 */
	public double getValue () {
		return this.value;
	}
	
	/**
	 * Get currency of the coin.
	 * @return coin's currency
	 */
	public String getCurrency () {
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
		Coin coin = (Coin) arg;
		return this.getValue() == coin.getValue() && this.getCurrency().equals(coin.getCurrency());
	}
	
	/**
	 * Compares this object with the specified object for order.
	 * @param coin is the object that want to compare the value with this value
	 * @return a negative integer if value of this object is less than value of the specified object,
	 *			zero if value of this object equal to value of the specified object or
	 *			a positive integer if value of this object is greater than value of the specified object.
	 */
	@Override
	public int compareTo (Coin coin) {
		double compare = this.getValue() - coin.getValue();
		
		if (compare < 0) return -1;
		else if (compare > 0) return 1;
		else return 0;
	}
	
	/**
	 * toString show the coin's value and coin's currency.
	 * @return value with currency of the coin
	 */
	@Override
	public String toString () {
		if ((int) value - this.getValue() == 0) return String.format("%.0f-%s", this.getValue(), this.getCurrency());
		else return String.format("%.2f-%s", this.getValue(), this.getCurrency());
	}

}
