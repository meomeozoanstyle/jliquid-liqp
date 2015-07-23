package org.jliquid.liqp.filters;

import org.jliquid.liqp.LValue;

import com.google.common.base.CaseFormat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Output markup takes filters. Filters are simple methods. The first parameter
 * is always the output of the left side of the filter. The return value of the
 * filter will be the new left value when the next filter is run. When there are
 * no more filters, the template will receive the resulting string.
 * <p/>
 * -- https://github.com/Shopify/liquid/wiki/Liquid-for-Designers
 */
public abstract class Filter extends LValue {
	public static final String UNOVERRIDE = "un_override";
	private static final Map<String, Filter> FILTERS = new HashMap<String, Filter>();

	static {
		// Initialize all standard filters.
		registerFilter(new Append());
		registerFilter(new Capitalize());
		registerFilter(new Date());
		registerFilter(new Divided_By());
		registerFilter(new Downcase());
		registerFilter(new Escape());
		registerFilter(new EscapeOnce());
		registerFilter(new First());
		registerFilter(new H());
		registerFilter(new Join());
		registerFilter(new Last());
		registerFilter(new org.jliquid.liqp.filters.Map());
		registerFilter(new Minus());
		registerFilter(new Modulo());
		registerFilter(new Plus());
		registerFilter(new Prepend());
		registerFilter(new Remove());
		registerFilter(new RemoveFirst());
		registerFilter(new Replace());
		registerFilter(new ReplaceFirst());
		registerFilter(new Size());
		registerFilter(new Sort());
		registerFilter(new Split());
		registerFilter(new StripHTML());
		registerFilter(new StripNewlines());
		registerFilter(new Times());
		registerFilter(new Truncate());
		registerFilter(new Truncatewords());
		registerFilter(new Upcase());
		registerFilter(new Money());
		registerFilter(new MoneyWithCurrency());
	}
	/**
	 * The name of the filter.
	 */
	public final String name;

	/**
	 * Used for all package protected filters in the liqp.filters-package whose
	 * name is their class name lower cased.
	 */
	protected Filter() {
		this.name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this
				.getClass().getSimpleName());
	}

	/**
	 * Creates a new instance of a Filter.
	 *
	 * @param name
	 *            the name of the filter.
	 */
	public Filter(String name) {
		this.name = name;
	}

	/**
	 * Applies the filter on the 'value'.
	 *
	 * @param value
	 *            the string value `AAA` in: `{{ 'AAA' | f:1,2,3 }}`
	 * @param params
	 *            the values [1, 2, 3] in: `{{ 'AAA' | f:1,2,3 }}`
	 *
	 * @return the result of the filter.
	 */
	public Object apply(Object value, Object... params) {
		return UNOVERRIDE;
	}

	/**
	 * Applies the filter on the 'value'.
	 *
	 * @param value
	 *            the string value `AAA` in: `{{ 'AAA' | f:1,2,3 }}`
	 * @param params
	 *            the values [1, 2, 3] in: `{{ 'AAA' | f:1,2,3 }}`
	 *
	 * @return the result of the filter.
	 */
	public Object apply(Map<String, Object> context, Object value,
			Object... params) {
		return UNOVERRIDE;
	}

	/**
	 * Check the number of parameters and throws an exception if needed.
	 *
	 * @param params
	 *            the parameters to check.
	 * @param expected
	 *            the expected number of parameters.
	 */
	public final void checkParams(Object[] params, int expected) {
		if (params == null || params.length != expected) {
			throw new RuntimeException(
					"Liquid error: wrong number of arguments ("
							+ (params.length + 1) + " for " + (expected + 1)
							+ ")");
		}
	}

	/**
	 * Returns a value at a specific index from an array of parameters. If no
	 * such index exists, a RuntimeException is thrown.
	 *
	 * @param index
	 *            the index of the value to be retrieved.
	 * @param params
	 *            the values.
	 *
	 * @return a value at a specific index from an array of parameters.
	 */
	protected Object get(int index, Object... params) {

		if (index >= params.length) {
			throw new RuntimeException("error in filter '" + name
					+ "': cannot get param index: " + index + " from: "
					+ Arrays.toString(params));
		}

		return params[index];
	}

	/**
	 * Retrieves a filter with a specific name.
	 *
	 * @param name
	 *            the name of the filter to retrieve.
	 *
	 * @return a filter with a specific name.
	 */
	public static Filter getFilter(String name) {

		Filter filter = FILTERS.get(name);

		if (filter == null) {
			filter = new Blank();
		}

		return filter;
	}

	/**
	 * Registers a new filter.
	 *
	 * @param filter
	 *            the filter to be registered.
	 */
	public static void registerFilter(Filter filter) {
		FILTERS.put(filter.name, filter);
	}
}
