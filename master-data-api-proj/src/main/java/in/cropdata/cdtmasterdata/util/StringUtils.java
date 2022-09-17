package in.cropdata.cdtmasterdata.util;

public interface StringUtils
{
    /**
     * check for the string null or empty.
     * @param string
     * @return If string is empty or null it sends TRUE
     */
    static boolean isBlankOrNull(String string)
    {
        return string == null || string.isEmpty();
    }

    /**
     * check for the string null or empty.
     * @param string
     * @return If string is empty or null it sends FALSE
     */
    static boolean isNotBlankOrNull(String string)
    {
        return string != null && !string.isEmpty();
    }
}
