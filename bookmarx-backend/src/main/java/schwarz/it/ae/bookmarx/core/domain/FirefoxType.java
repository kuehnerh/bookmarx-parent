package schwarz.it.ae.bookmarx.core.domain;

import org.apache.commons.lang3.StringUtils;

public enum FirefoxType {


    PLACE("text/x-moz-place"),
    PLACE_CONTAINER("text/x-moz-place-container"),
    PLACE_SEPARATOR("text/x-moz-place-separator");

    private String type;

    FirefoxType(String type) {
        this.type = type;
    }


    public static boolean isValid(String typeToCheck) {
        for (FirefoxType t : FirefoxType.values()) {
            if (StringUtils.equals(t.type, typeToCheck)) {
                return true;
            }
        }

        return false;
    }


}
