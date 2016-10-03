package org.poormanscastle.products.hit2ass.renderer;

import static com.google.common.base.Preconditions.checkState;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMXMLBuilderFactory;
import org.apache.axiom.om.xpath.AXIOMXPath;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jaxen.JaxenException;
import org.poormanscastle.products.hit2ass.exceptions.BausteinMergerException;

/**
 * represents the DocFamily ModuleDeploymentLibrary to support modularization and re-use when mapping
 * HIT/CLOU (sub) Bausteins to DocFamily Workspaces.
 * Created by georg.federmann@poormanscastle.com on 29.09.16.
 */
public interface DeployedModuleLibrary {

    final static Logger logger = Logger.getLogger(DeployedModuleLibrary.class);

    static String HIT2ASS_DEPLOYED_MODULE_LIBRARY_NAME = "HitAssDeploymentPackageLibrary";

    static DeployedModuleLibrary library = null;

    static DeployedModuleLibrary createNewHitAssDeploymentPackageLibrary() {
        return new DeployedModuleLibraryImpl(DeployedModuleLibrary.HIT2ASS_DEPLOYED_MODULE_LIBRARY_NAME);
    }

    static DeployedModuleLibrary loadHitAssDeploymentPackageLibrary() {
        synchronized (DeployedModuleLibrary.class) {
            if (library == null)
                try {
                    String path = System.getProperty("hit2ass.clou.pathToDeployedModuleLibrary");
                    checkState(!StringUtils.isBlank(path), "Path to deployed module library not given. Please set system property hit2ass.clou.pathToDeployedModuleLibrary.");
                    byte[] dpLibData = Files.readAllBytes(Paths.get(path));

                    OMElement dpLibDocument = OMXMLBuilderFactory.createOMBuilder(
                            new ByteArrayInputStream(dpLibData)).getDocumentElement();
                    AXIOMXPath xPath = new AXIOMXPath(
                            "/Cockpit/Object[@type='com.assentis.cockpit.bo.BoWorkspace']/" +
                                    "Object[@type='com.assentis.cockpit.bo.BoModuleGroup']/" +
                                    "Object[@type='com.assentis.cockpit.bo.BoModuleDeploymentLibrary']/" +
                                    "Object[@type='com.assentis.cockpit.bo.BoModuleComposition']");
                    List<OMElement> elementList = xPath.selectNodes(dpLibDocument);
                    DeployedModuleLibrary dpLib = DeployedModuleLibrary.createNewHitAssDeploymentPackageLibrary();
                    for (OMElement element : elementList) {
                        DeployedModule module = DeployedModule.createNew(
                                element.getAttributeValue(new QName("name")),
                                element.toString(), element.getAttributeValue(new QName("id")));
                        dpLib.addDeployedModule(module);
                        logger.info(StringUtils.join("Found module ", module));
                    }
                } catch (IOException | JaxenException e) {
                    String errMsg = StringUtils.join("Could not load deployed module library, because: ",
                            e.getClass().getName(), " - ", e.getMessage());
                    logger.error(errMsg, e);
                    throw new BausteinMergerException(errMsg, e);
                }
        }

        return library;
    }

    DeployedModule addDeployedModule(DeployedModule deployedModule);

    boolean containsDeployedModule(DeployedModule deployedModule);

    DeployedModule getDeployedModuleByName(String name);

    String getName();

    String getElementId();

}
