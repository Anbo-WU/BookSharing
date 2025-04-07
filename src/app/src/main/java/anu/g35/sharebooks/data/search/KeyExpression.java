package anu.g35.sharebooks.data.search;

import java.util.HashSet;

/**
 * This class is used to represented the expression of key
 *
 * @author u7722376 Di'ao Fu
 * @since 2024-04-20
 */
public class KeyExpression extends Expression {
	
	private Token token;

	private Books books = Books.getInstance();

	/**
	 * Constructor
	 * @param token the token

	 */
	public KeyExpression(Token token) {
		this.token = token;
	}

	/**
	 * Show the key expression
	 * @return the string representation of the key expression
	 */
	@Override
	public String show() {
		String result;
		switch (token.getType()) {
			case ISBN:
				result = "ISBN:" + token.getToken();
				break;
			case TITLE:
				result = "TITLE:" + token.getToken();
				break;
			case AUTHORS:
				result = "AUTHORS:" + token.getToken();
				break;
			case CATEGORY:
				result = "CATEGORY:" + token.getToken();
				break;
			case YEAR:
				result = "YEAR:" + token.getToken();
				break;
			default:
				result = "UNKNOWN:" + token.getToken();
				break;
		}
		return result;
	}

	/**
	 * Evaluate the key expression
	 * @return the set of ISBNs
	 */
	@Override
	public HashSet<Long> evaluate() {
		HashSet<Long> result = new HashSet<Long>();

		switch (token.getType()) {
			case ISBN:
				result.add(Long.valueOf(token.getToken()));
				break;
			case TITLE:
				result.addAll(books.getISBNSetByTitle(token.getToken()));
				break;
			case AUTHORS:
				result.addAll(books.getISBNSetByAuthors(token.getToken()));
				break;
			case CATEGORY:
				result.addAll(books.getISBNSetByCategory(token.getToken()));
				break;
			case YEAR:
				result.addAll(books.getISBNSetByPublishedYear(Integer.parseInt(token.getToken())));
				break;
			default:
				// Search-Invalid, give meaningful results
				// Ignore unrecognized token
		}
		return result;
	}
}
