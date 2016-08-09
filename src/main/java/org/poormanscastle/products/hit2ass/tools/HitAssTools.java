package org.poormanscastle.products.hit2ass.tools;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.poormanscastle.products.hit2ass.ast.domain.ExpressionList;
import org.poormanscastle.products.hit2ass.exceptions.ClouBausteinIoException;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * provides access to HIT / CLOU Bausteine etc.
 * Nota bene: for this to work, the system property hit2ass.clou.path has to be set.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 4/27/16.
 */
public final class HitAssTools {

    /**
     * This field has to be made configurable.
     */
//    private static String pathToClouBausteinFolder = "/home/georg/pmc/projects/AT/products/HitAss/reverseEngineering/hit/Bau-Zentral/ba/hit2assentis";

    /**
     * load a HIT / CLOU text component from the given path.
     *
     * @param path
     * @param bausteinName
     * @return
     * @throws ClouBausteinIoException
     */
    public final static InputStream getClouBausteinAsInputStream(String path, String bausteinName) throws ClouBausteinIoException {
        try {
            return new ByteArrayInputStream(IOUtils.toByteArray(new FileInputStream((
                    StringUtils.join(path, path.endsWith("/") ? "" : "/", bausteinName)))));
        } catch (IOException e) {
            throw new ClouBausteinIoException(StringUtils.join("Could not open baustein ", path, bausteinName, ", because: ", e.getMessage(), e));
        }
    }

    /**
     * load a HIT / CLOU text component given its name.
     *
     * @param bausteinName
     * @return
     * @throws ClouBausteinIoException
     */
    public static InputStream getClouBausteinAsInputStream(String bausteinName) throws ClouBausteinIoException {
        String path = System.getProperty("hit2ass.clou.path");
        if (StringUtils.isBlank(path)) {
            throw new ClouBausteinIoException(StringUtils.join(
                    "No path to HIT/CLOU Baustein library defined. Please set Java system property hit2ass.clou.path."));
        }
        return HitAssTools.getClouBausteinAsInputStream(System.getProperty("hit2ass.clou.path"), bausteinName);
    }

    public static String getExpressionListAsString(ExpressionList expressionList) {
        StringBuilder output = new StringBuilder();
        boolean firstArg = true;
        while (expressionList != null) {
            if (firstArg) {
                firstArg = false;
            } else {
                output.append(", ");
            }
            output.append(expressionList.getHead().toXPathString());
            expressionList = expressionList.getTail();
        }
        return output.toString();
    }

}
