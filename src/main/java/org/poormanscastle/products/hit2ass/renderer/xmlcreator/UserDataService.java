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
     * @param clouBausteinName            the name of the CLOU Baustein to be processed.
     *                                    XML creation might infer business rules from the semantics of the given Baustein.
     * @param aenderungsUndFreigabeNummer is expected to be in the format ###/####, where #
     *                                    is in [0,9]
     * @return
     */
    InputStream getUserdataXml(InputStream dataInputStream, String clouBausteinName, String aenderungsUndFreigabeNummer);

    /**
     * infers the telephone direct inward dial (Telefondurchwahl) for the given baustein.
     * TODO provide a hook point on the generated XML and move this logic to an Interceptor or FilterChain.
     *
     * @param bausteinName
     * @param dataBlock    holds diverse DIDs, the method chooses the correct one given the bausteinName.
     * @return
     */
    String inferDid(String bausteinName, String dataBlock);

}
