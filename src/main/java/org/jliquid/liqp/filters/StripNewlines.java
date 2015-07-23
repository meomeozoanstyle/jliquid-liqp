package org.jliquid.liqp.filters;

class StripNewlines extends Filter {

    /*
     * strip_newlines(input) click to toggle source
     *
     * Remove all newlines from the string
     */
    @Override
    public Object apply(Object value, Object... params) {

        return super.asString(value).replaceAll("[\r\n]++", "");
    }
}
