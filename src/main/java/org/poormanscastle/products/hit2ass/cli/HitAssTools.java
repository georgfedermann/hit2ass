package org.poormanscastle.products.hit2ass.cli;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.hit2ass.ast.domain.ClouBaustein;
import org.poormanscastle.products.hit2ass.exceptions.HitAssTransformerException;
import org.poormanscastle.products.hit2ass.parser.javacc.HitAssAstParser;
import org.poormanscastle.products.hit2ass.parser.javacc.ParseException;
import org.poormanscastle.products.hit2ass.prettyprint.PrettyPrintVisitor;
import org.poormanscastle.products.hit2ass.renderer.DeployedModuleLibrary;
import org.poormanscastle.products.hit2ass.renderer.IRTransformer;
import org.poormanscastle.products.hit2ass.renderer.xmlcreator.UserDataServiceBean;
import org.poormanscastle.products.hit2ass.transformer.ClouBausteinDependencyResolverVisitor;
import org.poormanscastle.products.hit2ass.transformer.EraseBlanksVisitor;

/**
 * HitAssTools is meant to be used as cmd line tool to work with grammars.
 * Created by georg.federmann@poormanscastle.com on 17.02.2016.
 */
public final class HitAssTools {

    private final static Logger logger = Logger.getLogger(HitAssTools.class);

    public static void main(String[] args) {
        logger.info("hitAssTools.sh was called with these arguments:");
        Arrays.stream(args).forEach(logger::info);
        try {
            if (args.length == 0) {
                printHelp();
                return;
            }

            int counter = 0;
            do {
                String arg = args[counter++];
                if ("a".equals(arg)) {
                    System.out.println(HitAssTools.createAstVisualization());
                } else if ("h".equals(arg)) {
                    HitAssTools.printHelp();
                } else if ("v".equals(arg)) {
                    HitAssTools.printVersion();
                } else if ("w".equals(arg)) {
                    System.out.println(HitAssTools.createDocDesignWorkspace());
                } else if ("x".equals(arg)) {
                    try {
                        System.out.println(HitAssTools.createUserDataXml(args[counter++], args[counter++]));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new HitAssTransformerException(StringUtils.join("Could not process input text file since Baustein name (e.g. UE108) was missing. Please read help using hitAssTools -h."));
                    }
                }
            } while (counter < args.length);
        } catch (Exception exception) {
            String errMsg = StringUtils.join("An exception occurred during processing: ",
                    exception.toString(), " - Please see log file hitass.log for details.");
            System.err.println(errMsg);
            logger.error(errMsg, exception);
        }
    }

    private static String createDocDesignWorkspace() throws ParseException {
        logger.info(StringUtils.join("Running parser with encoding hit2ass.clou.encoding=",
                System.getProperty("hit2ass.clou.encoding")));
        // check if deployed module library exists. if not, create a new one
        DeployedModuleLibrary library = DeployedModuleLibrary.loadHitAssDeployedModuleLibrary();

        ClouBaustein baustein = new HitAssAstParser(System.in, System.getProperty("hit2ass.clou.encoding")).CB();
        baustein.setClouBausteinName("Test Baustein");
        baustein.accept(new ClouBausteinDependencyResolverVisitor());
        baustein.accept(new EraseBlanksVisitor());
        IRTransformer irTransformer = new IRTransformer();
        baustein.accept(irTransformer);
        // during processing the DeployedModuleLibrary was loaded and maybe extended.
        // store the current version of the DeployedModuleLibrary
        DeployedModuleLibrary.storeHitAssDeployedModuleLibrary();
        return irTransformer.getWorkspace().getContent();
    }

    private static String createUserDataXml(String clouBausteinName, String aenderungsFreigabeNummer) throws IOException, ParseException {
        return IOUtils.toString(new UserDataServiceBean().getUserdataXml(System.in, clouBausteinName, aenderungsFreigabeNummer));
    }

    private static String createAstVisualization() throws IOException, ParseException {
        logger.info(StringUtils.join("Running parser with encoding hit2ass.clou.encoding=",
                System.getProperty("hit2ass.clou.encoding")));
        ClouBaustein clouBaustein = new HitAssAstParser(System.in, System.getProperty("hit2ass.clou.encoding")).CB();
        clouBaustein.accept(new ClouBausteinDependencyResolverVisitor());
        clouBaustein.accept(new EraseBlanksVisitor());
        PrettyPrintVisitor prettyPrinter = new PrettyPrintVisitor();
        clouBaustein.accept(prettyPrinter);
        return prettyPrinter.serialize();
    }

    private static void printHelp() {
        logger.info(StringUtils.join("Printing help and version information. Configured encoding is ", System.getProperty("hit2ass.clou.encoding"), "."));
        printVersion();
        System.out.println("Usage: hitAssTools ");
        System.out.println("  Default action is to read data from standard input stream, process it, and write data to standard output stream.");
        System.out.println("  Data can be written to stdin and stdout can be redirected as seems fit.");
        System.out.println();
        System.out.println("  -h  print this help.");
        System.out.println("  -v  print version information.");
        System.out.println("  -a  create AST diagram for the Clou component in dot format. Input data is read from the std input.");
        System.out.println("      usage: cat B.al003 | hitAssTools.sh -a | dot -Gcharset=latin1 -Tsvg -o B_al003.svg");
        System.out.println("  -x  create DocFamily userdata XML from HIT/CLOU input text file.");
        System.out.println("      Additionally, the script needs to be configured with the Bausteinname and the AeFrNr.");
        System.out.println("      usage: cat hitclou_Order60071.txt | hitAssTools -x \"EK015 130/0001\" > userdata_Order60071.xml");
        System.out.println("      To get intented XML output you can use xmllint with the pipe redirect symbol - :");
        System.out.println("      cat OrderData.dat | hitAssTools.sh -x UE108 | xmllint --format -");
        System.out.println("  -w  create DocFamily workspace from HIT/CLOU Baustein.");
    }

    private static void printVersion() {
        System.out.println("HitAssTools v0.3 of 2016-10-23, brought to you by Poor Man's Castle.");
    }

}
