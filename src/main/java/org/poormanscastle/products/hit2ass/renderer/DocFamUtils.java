package org.poormanscastle.products.hit2ass.renderer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public class DocFamUtils {

    private static DateFormat dateFormatObjectId = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

    private static DateFormat dateFormatExportDate = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

    /**
     * DocFamily Cockpit Elemente haben eine ID, die sich aus dem Datum und der Uhrzeit der Erzeugung des
     * Elements, und einem nachfolgenden, nicht näher spezfizierten Teil zusammen setzt.
     *
     * @return creates a cockpit element id which tries to be similar to IDs created by DocFamily DocRepo.
     */
    public static String getCockpitElementId() {
        return StringUtils.join(dateFormatObjectId.format(new Date()), ".",
                String.valueOf(100 + Math.random() * 100), "-",
                String.valueOf(1000 + Math.random() * 9000), ".",
                String.valueOf(1000000 + Math.random() * 9000000), ".",
                String.valueOf(1000000 + Math.random() * 9000000), ".",
                String.valueOf(100 + Math.random() * 900)
        );
    }

    public static String getCockpitExportDate() {
        return dateFormatExportDate.format(new Date());
    }

    /**
     * trying to adapt the StringUtils.abbreviate() method to work with Strings that might be corrupted when being
     * cut at arbritary locations.
     * CAVEAT: this method turned out to still corrupt strings. fix it before using.
     *
     * @param text     the text to be abbreviated
     * @param maxWidth the maximal width allowed for the result
     * @return a shorter version of the input text
     */
    public static String truncate(String text, int maxWidth) {
        return StringUtils.abbreviate(text.replaceAll("[\"\\/*?:|@&.!'<>-]", "_"), maxWidth).replaceAll("[.]", "").trim();
    }

}
