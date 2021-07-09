package schwarz.it.ae.bookmarx.entries.api;

import org.apache.commons.lang3.StringUtils;

public class RestControllerUtils {
    public static String extractId(String idInPath, String idInBody) {
        // Either ID in path or in body is required
        if (StringUtils.isAllEmpty(idInPath, idInBody)) {
            throw new IllegalArgumentException("Either ID in path or in body is required");
        }
        //
        if (StringUtils.isNoneEmpty(idInPath, idInBody) && !StringUtils.equals(idInPath, idInBody)) {
            throw new IllegalArgumentException("ID in path has to be equal to ID in body");
        }
        if (StringUtils.isNoneEmpty(idInPath)) {
            return idInPath;
        }
        return idInBody;
    }
}
