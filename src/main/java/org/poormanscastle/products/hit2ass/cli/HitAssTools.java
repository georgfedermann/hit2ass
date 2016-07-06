package org.poormanscastle.products.hit2ass.cli;

import org.poormanscastle.products.hit2ass.ast.domain.ClouBaustein;
import org.poormanscastle.products.hit2ass.parser.javacc.HitAssAstParser;
import org.poormanscastle.products.hit2ass.parser.javacc.ParseException;
import org.poormanscastle.products.hit2ass.parser.javacc.TokenMgrError;
import org.poormanscastle.products.hit2ass.prettyprint.PrettyPrintVisitor;
import org.poormanscastle.products.hit2ass.renderer.IRTransformer;
import org.poormanscastle.products.hit2ass.renderer.xmlcreator.XmlCreator;
import org.poormanscastle.products.hit2ass.renderer.xmlcreator.XmlDataMerger;
import org.poormanscastle.products.hit2ass.transformer.ClouBausteinMergerVisitor;
import org.poormanscastle.products.hit2ass.transformer.FixedTextMerger;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * HitAssTools is meant to be used as cmd line tool to work with grammars.
 * <p/>
 * Created by georg.federmann@poormanscastle.com on 17.02.2016.
 */
public final class HitAssTools {

    private final static Logger logger = Logger.getLogger(HitAssTools.class);

    public static void main(String[] args) {
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
                } else if ("m".equals(arg)) {
                    System.out.println(HitAssTools.createDataXml(Arrays.asList(args)));
                } else if ("v".equals(arg)) {
                    HitAssTools.printVersion();
                } else if ("w".equals(arg)) {
                    System.out.println(HitAssTools.createDocDesignWorkspace());
                } else if ("x".equals(arg)) {
                    System.out.println(HitAssTools.createXmlTemplate());
                }
            } while (counter < args.length);
        } catch (Exception exception) {
            System.err.println(StringUtils.join("An exception occurred during processing: ",
                    exception.toString(), " - Please see log file hit2ass.log for details."));
            logger.error(exception, exception);
        }
    }

    private static String createDocDesignWorkspace() throws IOException {
        try {
            ClouBaustein baustein = new HitAssAstParser(System.in, "UTF-8").CB();
            baustein.accept(new ClouBausteinMergerVisitor());
            baustein.accept(new FixedTextMerger());
            IRTransformer irTransformer = new IRTransformer();
            baustein.accept(irTransformer);
            return irTransformer.getWorkspace().getContent();
//            SymbolTableCreatorVisitor symbolTableCreator = new SymbolTableCreatorVisitor();
//            program.accept(symbolTableCreator);
        } catch (ParseException | TokenMgrError e) {
            return StringUtils.join("Parser error: ", e.getMessage());
        }
    }

    private static String createXmlTemplate() throws IOException, ParseException {
        ClouBaustein clouBaustein = new HitAssAstParser(System.in, "UTF-8").CB();
        clouBaustein.accept(new ClouBausteinMergerVisitor());
        clouBaustein.accept(new FixedTextMerger());
        XmlCreator xmlTemplateCreator = new XmlCreator();
        clouBaustein.accept(xmlTemplateCreator);
        return xmlTemplateCreator.serialize();
    }

    private static String createAstVisualization() throws IOException, ParseException {
        ClouBaustein clouBaustein = new HitAssAstParser(System.in, "UTF-8").CB();
        clouBaustein.accept(new ClouBausteinMergerVisitor());
        clouBaustein.accept(new FixedTextMerger());
        PrettyPrintVisitor prettyPrinter = new PrettyPrintVisitor();
        clouBaustein.accept(prettyPrinter);
        return prettyPrinter.serialize();
    }

    private static String createDataXml(List<String> args) {
        // search args for file names of velocity template and data file.
        try {
            checkArgument(args.contains("m"));
            checkArgument(args.contains("t"));
            checkArgument(args.contains("d"));
            checkArgument(args.size() == 5);

            String dataFileName = args.get(1);
            String templateFileName = args.get(3);
            return new XmlDataMerger().merge(new BufferedInputStream(new FileInputStream(args.get(3))),
                    new BufferedInputStream(new FileInputStream(args.get(1))));
        } catch (IllegalArgumentException e) {
            return "Usage: hitAssTools -m -t templateFileName -d dataFileName";
        } catch (IOException e) {
            return StringUtils.join("Could not process input files, because: ", e.getMessage());
        }
    }

    private static void printHelp() {
        printVersion();
        System.out.println("Usage: hitAssTools ");
        System.out.println("  Default action is to read data from standard input stream, process it, and write data to standard output stream.");
        System.out.println("  Data can be written to stdin and stdout can be redirected as seems fit.");
        System.out.println();
        System.out.println("  -h  print this help.");
        System.out.println("  -v  print version information.");
        System.out.println("  -a  create AST diagram for the Clou component in dot format. Input data is read from the std input.");
        System.out.println("  -x  create DocFamily data XML-template for the given CLOU component.");
        System.out.println("  -m  merge HIT/CLOU input data with XML-template to create DocFamily data XML.");
        System.out.println("      hitAssTools -m -t xmlTemplate -d dataFile");
        System.out.println("  -w  create DocFamily workspace from HIT/CLOU Baustein.");
    }

    private static void printVersion() {
        System.out.println("HitAssTools v0.1 of 2016-04-06, by Poor Man's Castle.");
    }

}
