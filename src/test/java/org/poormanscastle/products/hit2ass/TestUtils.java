package org.poormanscastle.products.hit2ass;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by georg.federmann@poormanscastle.com on 29.03.2016.
 */
public class TestUtils {
    
    public static InputStream getClouChunkAsInputStream(String clouChunkName) throws Exception{
        return TestUtils.getClouBausteinAsInputStream("/clouChunks/", clouChunkName, ".clou");
    }

    public static InputStream getClouBausteinAsInputStream(String path, String bausteinName, String suffix) throws Exception {
        return new ByteArrayInputStream(IOUtils.toByteArray(TestUtils.class.getResourceAsStream(
                StringUtils.join(path.startsWith("/") ? "" : "/",
                        path, path.endsWith("/") ? "" : "/", bausteinName,
                        suffix.startsWith(".") ? "" : ".", suffix))));
    }

    public static InputStream getClouBausteinAsInputStream(String bausteinName) throws Exception {
        return TestUtils.getClouBausteinAsInputStream("/clou/", bausteinName, ".clou");
    }

}
