package org.poormanscastle.products.hit2ass.renderer;

import static com.google.common.base.Preconditions.checkState;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    static DeployedModuleLibraryWrapper libraryWrapper = new DeployedModuleLibraryWrapper();

    static DeployedModuleLibrary createNewHitAssDeploymentPackageLibrary() {
        if (libraryWrapper.library != null) {
            logger.warn("Overwriting existing deployed module library. I do hope you know what you're doing.");
        }
        return libraryWrapper.library = new DeployedModuleLibraryImpl();
    }

    static void storeHitAssDeployedModuleLibrary() {
        synchronized (DeployedModuleLibrary.class) {
            try {
                String path = System.getProperty("hit2ass.clou.pathToDeployedModuleLibrary");
                checkState(!StringUtils.isBlank(path), "Path to deployed module library is not defined. Please set the system property hit2ass.clou.pathToDeployedModuleLibrary accordingly.");
                Files.write(Paths.get(path), libraryWrapper.library.renderToDocFamilyWorkspace());
            } catch (IOException e) {
                String errMsg = StringUtils.join("Could not store deployed module library because: ",
                        e.getClass().getName(), " - ", e.getMessage());
                logger.error(errMsg, e);
                throw new BausteinMergerException(errMsg, e);
            }
        }
    }

    static DeployedModuleLibrary loadHitAssDeployedModuleLibrary() {
        synchronized (DeployedModuleLibrary.class) {
            if (libraryWrapper.library == null) {
                // by calling createNewHitAssDeploymentPackageLibrary, dpLib is a reference to the real thing.
                DeployedModuleLibrary dpLib = DeployedModuleLibrary.createNewHitAssDeploymentPackageLibrary();
                try {
                    checkState(!StringUtils.isBlank(System.getProperty("hit2ass.clou.pathToDeployedModuleLibrary")),
                            "Path to deployed module library not defined. Please set system property hit2ass.clou.pathToDeployedModuleLibrary.");
                    Path path = Paths.get(System.getProperty("hit2ass.clou.pathToDeployedModuleLibrary"));

                    if (!Files.exists(path)) {
                        logger.warn(StringUtils.join("No deployed module library found. Creating new one."));
                    } else {
                        byte[] dpLibData = Files.readAllBytes(path);

                        OMElement dpLibDocument = OMXMLBuilderFactory.createOMBuilder(
                                new ByteArrayInputStream(dpLibData)).getDocumentElement();
                        AXIOMXPath xPath = new AXIOMXPath(
                                "/Cockpit/Object[@type='com.assentis.cockpit.bo.BoWorkspace']/" +
                                        "Object[@type='com.assentis.cockpit.bo.BoModuleGroup']/" +
                                        "Object[@type='com.assentis.cockpit.bo.BoModuleDeploymentLibrary']/" +
                                        "Object[@type='com.assentis.cockpit.bo.BoModuleComposition']");
                        List<OMElement> elementList = xPath.selectNodes(dpLibDocument);

                        for (OMElement element : elementList) {
                            DeployedModule module = DeployedModule.createNew(
                                    element.getAttributeValue(new QName("name")),
                                    element.toString(), element.getAttributeValue(new QName("id")));
                            dpLib.addDeployedModule(module);
                            logger.info(StringUtils.join("Found module ", module));
                        }
                    }
                } catch (IOException | JaxenException e) {
                    String errMsg = StringUtils.join("Could not load deployed module library, because: ",
                            e.getClass().getName(), " - ", e.getMessage());
                    logger.error(errMsg, e);
                    throw new BausteinMergerException(errMsg, e);
                }
            }
        }
        return libraryWrapper.library;
    }

    DeployedModule addDeployedModule(DeployedModule deployedModule);

    boolean containsDeployedModule(DeployedModule deployedModule);

    boolean containsDeployedModule(String deployedModuleName);

    DeployedModule getDeployedModuleByName(String name);

    String getElementId();

    byte[] renderToDocFamilyWorkspace();

    class DeployedModuleLibraryWrapper {
        DeployedModuleLibrary library;
    }

}
