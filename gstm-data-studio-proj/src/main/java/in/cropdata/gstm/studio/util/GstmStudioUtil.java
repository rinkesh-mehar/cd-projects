package in.cropdata.gstm.studio.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Utility class for performing common operations.
 * 
 * @since 1.0
 * @author PranaySK
 */

@Component
public class GstmStudioUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstmStudioUtil.class);

	/**
	 * This method is used to round off the <b>Double</b> value to the given places.
	 * 
	 * @param value  the value to be rounded off in <code>Double</code>
	 * @param places the number of digits for the given value to be rounded off
	 * 
	 * @return the rounded off value in String
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public String roundToPlaces(final Double value, final int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.CEILING);
		return bd.toString();
	}

	/**
	 * This method is used to get the rounded Integer value from the given double
	 * value.
	 * 
	 * @param value the value in <code>double</code> to be converted
	 * 
	 * @return the rounded off value in Integer
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public Integer doubleToInt(final double value) {
		return (int) Math.round(value);
	}

	/**
	 * This method is used to get the given production value with unit.
	 * 
	 * @param zoomLevel       the zoom level to control the value unit
	 * @param productionValue the value of production to get with unit
	 * 
	 * @return the response value with unit
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public String getProductionWithUnit(String zoomLevel, BigDecimal productionValue) {
		String productionValueWithUnit = "";

		if (Integer.valueOf(zoomLevel) < 11) {
			productionValueWithUnit = this.getFormattedValue(
					productionValue.divide(BigDecimal.valueOf(1000000l), 2, RoundingMode.CEILING)) + " MT";

		} else if (Integer.valueOf(zoomLevel) > 10 && Integer.valueOf(zoomLevel) < 13) {
			productionValueWithUnit = this.getFormattedValue(
					productionValue.divide(BigDecimal.valueOf(1000l), 2, RoundingMode.CEILING)) + " KT";

		} else {
			productionValueWithUnit = this.getFormattedValue(productionValue.setScale(2, RoundingMode.CEILING)) + " T";
		}
		LOGGER.info("production value -> {}", productionValueWithUnit);
		return productionValueWithUnit;
	}

	/**
	 * This method is used to get formatted value.
	 * 
	 * @param value the value to be formatted
	 * 
	 * @return the formatted value
	 * 
	 * @since 1.0
	 * @author PranaySK
	 * @apiNote If input value is <code>2835.99</code> then it will return the value
	 *          as <code>2,835.99</code>
	 */
	private String getFormattedValue(final BigDecimal value) {
		NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
		formatter.setMinimumFractionDigits(2);
		return formatter.format(value);
	}

}
