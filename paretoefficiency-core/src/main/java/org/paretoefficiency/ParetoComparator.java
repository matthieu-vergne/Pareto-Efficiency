package org.paretoefficiency;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * A pareto comparator allows to compare multidimensional individuals in a
 * pareto way :
 * <ul>
 * <li>if A is better than B on all the dimensions (some can be equivalent), A
 * is considered as the best one</li>
 * <li>if A is equivalent to B on all the dimensions, A and B are considered as
 * equivalent</li>
 * <li>if A is better than B on at least one dimension and worst on at least one
 * another, A and B are considered as equivalent, as we cannot decide wich one
 * is better</li>
 * </ul>
 * <p>
 * An individual is considered better than another regarding the comparators
 * given to the pareto comparator, for each dimension. There is no constraint
 * about which order (positive or negative comparison) tell which one is the
 * best (so you can decide for the most natural way), but <b>all the comparators
 * have to be consistent</b> : if one comparator uses a positive value to say A
 * is better than B, the others must use the same convention.
 * </p>
 * <p>
 * <b>BE CAREFUL :</b> two individuals said equivalent through this comparator
 * can be different !
 * </p>
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 * @param <Dimension>
 *            the dimensions considered, it can be a basic {@link Integer} or an
 *            {@link Enum} type for more advanced management.
 * @param <Individual>
 *            The individuals to compare.
 */
public class ParetoComparator<Dimension, Individual> implements
		Comparator<Individual> {
	/**
	 * The comparators to use on each dimension.
	 */
	private final Map<Dimension, Comparator<Individual>> comparators = new HashMap<Dimension, Comparator<Individual>>();

	/**
	 * Set a specific comparator for a given dimension. There can be only one
	 * comparator per dimension.
	 * 
	 * @param dimension
	 *            the dimension to consider
	 * @param comparator
	 *            the comparator to use for the given dimension
	 */
	public void setDimensionComparator(Dimension dimension,
			Comparator<Individual> comparator) {
		comparators.put(dimension, comparator);
	}

	/**
	 * 
	 * @param dimension
	 *            the dimension to consider
	 * @return the comparator set for this dimension, null if there is not
	 */
	public Comparator<Individual> getDimensionComparator(Dimension dimension) {
		return comparators.get(dimension);
	}

	/**
	 * Compare multidimensional individuals in a pareto way :
	 * <ul>
	 * <li>if A is better than B on all the dimensions (some can be equivalent),
	 * A is considered as the best one</li>
	 * <li>if A is equivalent to B on all the dimensions, A and B are considered
	 * as equivalent</li>
	 * <li>if A is better than B on at least one dimension and worst on at least
	 * one another, A and B are considered as equivalent, as we cannot decide
	 * which one is better</li>
	 * </ul>
	 */
	public int compare(Individual a, Individual b) {
		int reference = 0;
		for (Comparator<Individual> comparator : comparators.values()) {
			if (reference == 0) {
				reference = (int) Math.signum(comparator.compare(a, b));
			} else {
				int comparison = (int) Math.signum(comparator.compare(a, b));
				if (comparison * reference < 0) {
					// one better, another worst : cannot decide
					return 0;
				}
			}
		}
		return reference;
	}

}
