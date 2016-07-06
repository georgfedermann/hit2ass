package org.poormanscastle.products.hit2ass.renderer;

import org.apache.velocity.VelocityContext;

/**
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public final class VelocityHelper {

    public final static VelocityContext getVelocityContext(){
        VelocityContext context = new VelocityContext();
        context.put("docFamUtils", new DocFamUtils());
        return context;
    }

}
