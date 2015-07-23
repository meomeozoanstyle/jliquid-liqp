package org.jliquid.liqp.filters;

class Blank extends Filter {

	/*
	 * blank(input)
	 * 
	 * return passed value.
	 */
	@Override
	public Object apply(Object value, Object... params) {
		if(super.isNumber(value) || super.isInteger(value) || super.isString(value))
			return value;
		
		return "";
	}
}
