package org.jliquid.liqp.nodes;

import org.jliquid.liqp.filters.Filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilterNode implements LNode {

	private Filter filter;
	private List<LNode> params;

	public FilterNode(String id) {
		filter = Filter.getFilter(id);
		params = new ArrayList<LNode>();
	}

	public void add(LNode param) {
		params.add(param);
	}

	public Object apply(Object value, Map<String, Object> variables) {
		List<Object> paramValues = new ArrayList<Object>();

		for (LNode node : params) {
			paramValues.add(node.render(variables));
		}

		Object result = filter.apply(variables, value,
				paramValues.toArray(new Object[paramValues.size()]));
		if (result != null && result instanceof CharSequence
				&& result.toString().equals(Filter.UNOVERRIDE)) {
			result = filter.apply(value,
					paramValues.toArray(new Object[paramValues.size()]));
		}

		return result;
	}

	@Override
	public Object render(Map<String, Object> context) {
		throw new RuntimeException("cannot render a filter");
	}
}
