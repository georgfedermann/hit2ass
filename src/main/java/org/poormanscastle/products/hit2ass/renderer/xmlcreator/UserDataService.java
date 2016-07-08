package org.poormanscastle.products.hit2ass.renderer.xmlcreator;

import java.io.InputStream;

/**
 * Services related to the DocFamily Userdata concept.
 * <p>
 * Created by georg.federmann@poormanscastle.com on 7/8/16.
 */
public interface UserDataService {

    /**
     * accepts a text containing business data as used in HIT / CLOU solutions
     * and turns is into a generic XML document, holding a <line /> element for
     * each line in the input text file.
     *
     * @param dataInputStream
     * @return
     */
    public InputStream getUserdataXml(InputStream dataInputStream);

}
