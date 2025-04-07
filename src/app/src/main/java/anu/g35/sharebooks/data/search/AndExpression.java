package anu.g35.sharebooks.data.search;

import java.util.HashSet;

/**
 * This class is used to represent the expression of AND operation
 *
 * @author u7722376 Di'ao Fu
 * @since 2024-04-20
 */
public class AndExpression extends Expression {

	// The left and right expression
	private Expression key;
	private Expression expression;

	/**
	 * Constructor
	 * @param key
	 * @param expression
	 */
	public AndExpression(Expression key, Expression expression) {
		this.key = key;
		this.expression = expression;
	}

	/**
	 * Show the AND operation
	 * @return the string representation of the AND operation
	 */
	@Override
	public String show() {
		return "(" + key.show() + " AND " + expression.show() + ")";
	}

	/**
	 * Evaluate the AND operation
	 * @return the intersection of the two sets
	 */
	@Override
	public HashSet<Long> evaluate() {

		HashSet<Long> keySet = key.evaluate();
		HashSet<Long> expSet = expression.evaluate();

		// Find the intersection of the two sets
		keySet.retainAll(expSet);
		return (keySet);
	}
}