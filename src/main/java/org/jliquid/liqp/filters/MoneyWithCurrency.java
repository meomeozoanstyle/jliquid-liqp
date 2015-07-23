package org.jliquid.liqp.filters;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

class MoneyWithCurrency extends Filter {
	public static final String AMOUNT_NO_DECIMALS_WITH_COMMA_SEPARATOR = "{{amount_no_decimals_with_comma_separator}}";
	public static final String AMOUNT = "{{amount}}";
	public static final String AMOUNT_NO_DECIMAL = "{{amount_no_decimals}}";
	public static final String MONEY_FORMAT = "money_with_currency_format";

	/*
	 * money(input)
	 * 
	 * Formats the price based on the shop's HTML without currency setting.
	 */
	@Override
	public Object apply(Map<String, Object> context, Object value,
			Object... params) {
		String moneyFormat = AMOUNT_NO_DECIMALS_WITH_COMMA_SEPARATOR;
		BigDecimal amount = new BigDecimal(0);

		if (super.isInteger(value))
			amount = new BigDecimal(super.asNumber(value).longValue());
		else if (super.isNumber(value))
			amount = new BigDecimal(super.asNumber(value).doubleValue());

		if (context.containsKey(MONEY_FORMAT))
			moneyFormat = context.get(MONEY_FORMAT).toString();

		return _money(amount, moneyFormat);
	}

	private static String _money(BigDecimal money, String moneyFormat) {
		String moneyFormatType = "";

		if (moneyFormat.contains(AMOUNT))
			moneyFormatType = AMOUNT;
		else if (moneyFormat.contains(AMOUNT_NO_DECIMAL))
			moneyFormatType = AMOUNT_NO_DECIMAL;
		else if (moneyFormat.contains(AMOUNT_NO_DECIMALS_WITH_COMMA_SEPARATOR))
			moneyFormatType = AMOUNT_NO_DECIMALS_WITH_COMMA_SEPARATOR;

		if (moneyFormatType != null && !moneyFormatType.isEmpty()) {
			String moneyFormatString = moneyFormat.replace(moneyFormatType,
					"%s");
			String moneyText = _convertDecimalToMoneyString(money,
					moneyFormatType);
			return String.format(moneyFormatString, moneyText);
		}

		return money.toString();
	}

	private static String _convertDecimalToMoneyString(BigDecimal input,
			String type) {
		NumberFormat amountFormat = DecimalFormat.getNumberInstance(Locale.US);
		amountFormat.setMinimumFractionDigits(2);
		NumberFormat amountNoDecimalFormat = DecimalFormat
				.getNumberInstance(Locale.US);
		amountNoDecimalFormat.setMaximumFractionDigits(0);
		NumberFormat amountNoDecimalWithCommaSeparatorFormat = DecimalFormat
				.getNumberInstance(Locale.US);
		amountNoDecimalWithCommaSeparatorFormat.setMaximumFractionDigits(0);

		if (type.equalsIgnoreCase(AMOUNT))
			return amountFormat.format(input);
		if (type.equalsIgnoreCase(AMOUNT_NO_DECIMAL))
			return amountNoDecimalFormat.format(input);
		if (type.equalsIgnoreCase(AMOUNT_NO_DECIMALS_WITH_COMMA_SEPARATOR))
			return amountNoDecimalWithCommaSeparatorFormat.format(input)
					.replace(',', '.');

		return input.toString();
	}
}
