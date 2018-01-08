package org.poormanscastle.products.hit2ass.renderer;

import static com.google.common.base.Preconditions.checkState;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.junit.Test;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBaustein;
import org.poormanscastle.products.hit2ass.parser.javacc.HitAssAstParser;
import org.poormanscastle.products.hit2ass.transformer.ClouBausteinDependencyResolverVisitor;

/**
 * Created by georg on 03.10.16.
 */
public class DeployedModuleLibraryTest {

    private final static Logger logger = Logger.getLogger(DeployedModuleLibraryTest.class);

    @Test
    public void createNewHitAssDeploymentPackageLibrary() throws Exception {
        Velocity.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");
        Velocity.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        Velocity.init();

        checkState(!StringUtils.isBlank(System.getProperty("hit2ass.clou.encoding")), "Please set system property hit2ass.clou.encoding");
        checkState(!StringUtils.isBlank(System.getProperty("hit2ass.clou.pathToDeployedModuleLibrary")), "Please set system property hit2ass.clou.pathToDeployedModuleLibrary");
        logger.info(StringUtils.join("Using encoding ", System.getProperty("hit2ass.xml.encoding"), " for XMLs."));
        logger.info(StringUtils.join("Using encoding ", System.getProperty("hit2ass.clou.encoding"), " for HIT/CLOU bausteins."));
        logger.info(StringUtils.join("Using deployed module library workspace ", System.getProperty("hit2ass.clou.pathToDeployedModuleLibrary")));
        String encoding = System.getProperty("hit2ass.clou.encoding");

        // workingBausteine.txt contains a white list of bausteine known to be working with the HIT/CLOU parser. Or the other way round.
        // this white list is read into an array and used as a filter for the bausteine that shall be processed into DocFamily.
        List<String> whiteList = new ArrayList<>();
        Files.lines(Paths.get("/Users/georg/poormanscastle/products/hit2assprod/hit2ass/src/test/resources/workingBausteine.txt"))
                .forEach(line -> whiteList.add(line));

/*
        whiteList.clear();
        whiteList.add("B.al001");
*/

        // Process CLOU Bausteine
        Arrays.stream(Paths.get(
                StringUtils.defaultString(System.getProperty("hit2ass.clou.path"),
                        "/Users/georg/vms/UbuntuWork/shared/hitass/reverseEngineering/hit2assentis_reworked")).toFile().
                listFiles((dir, name) -> whiteList.contains(name))).forEach(bausteinFile -> {
            System.out.println(StringUtils.join("Processing Baustein ", bausteinFile));

            // Try to create workspace and save it to DocRepo
            try {
                ClouBaustein baustein = new HitAssAstParser(new ByteArrayInputStream(Files.readAllBytes(bausteinFile.toPath())), encoding).CB();
                baustein.setClouBausteinName(bausteinFile.getName());
                baustein.accept(new ClouBausteinDependencyResolverVisitor());
            } catch (Error | Exception e) {
                // javacc parser throws Errors ...
                String errorMessage = StringUtils.join("Could not process HIT/CLOU Baustein ", bausteinFile.getName(), ", because:",
                        e.getClass().getName(), " - ", e.getMessage());
                logger.error(errorMessage, e);
            }
            // Try to create deployment package and import it to DocRepo
        });

        // Store deployed module library
        DeployedModuleLibrary.storeHitAssDeployedModuleLibrary();
    }

    @Test
    public void getHitAssDeploymentPackageLibrary() throws Exception {
        DeployedModuleLibrary library = DeployedModuleLibrary.loadHitAssDeployedModuleLibrary();
    }

}