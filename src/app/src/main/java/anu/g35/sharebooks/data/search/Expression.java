package anu.g35.sharebooks.data.search;

import java.util.HashSet;

/**
 * It is an abstract class,
 * which is used to represent the expression of the search query
 *
 * @author u7722376 Di'ao Fu
 * @since 2024-04-20
 */
public abstract class Expression {

	public abstract String show();
	public abstract HashSet<Long> evaluate();

}
