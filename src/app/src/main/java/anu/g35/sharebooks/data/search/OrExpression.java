package anu.g35.sharebooks.data.search;

import java.util.HashSet;

/**
 * This class is used to represent the expression of OR operation
 *
 * @author u7722376 Di'ao Fu
 * @since 2024-04-20
 */
public class OrExpression extends Expression {

	private Expression key;
	private Expression expression;

	/**
	 * Constructor
	 * @param key the key
	 * @param expression the expression
	 */
	public OrExpression(Expression key, Expression expression) {
		this.key = key;
		this.expression = expression;
	}

	/**
	 * Show the OR operation
	 * @return the string representation of the OR operation
	 */
	@Override
	public String show() {
		return "(" + key.show() + " OR " + expression.show() + ")";
	}

	/**
	 * Evaluate the OR operation
	 * @return the union of the two sets
	 */
	@Override
	public HashSet<Long> evaluate() {

		HashSet<Long> keySet = key.evaluate();
		HashSet<Long> expSet = expression.evaluate();

		// Calculate the union of the two sets
		keySet.addAll(expSet);
		return (keySet);
	}
}