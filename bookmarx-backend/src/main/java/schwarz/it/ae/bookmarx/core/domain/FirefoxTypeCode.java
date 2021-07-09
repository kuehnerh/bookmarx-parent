package schwarz.it.ae.bookmarx.core.domain;

public enum FirefoxTypeCode {

    URL(1),
    FOLDER(2),
    SEPARATOR(3);

    private int typeCode;

    private FirefoxTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }
    
    
    public static boolean isValid(int typeCodeToCheck) {
        for (FirefoxTypeCode typeCode : FirefoxTypeCode.values()) {
            if (typeCode.typeCode == typeCodeToCheck) {
                return true;
            }
        }

        return false;
    }

    public int asInt() {
        return typeCode;
    }

    public boolean isEqualTo(int typeCodeToCheck) {
        return this.typeCode == typeCodeToCheck ? true : false;
    }

}
