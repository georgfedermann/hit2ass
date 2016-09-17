package org.poormanscastle.products.hit2ass.renderer;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by georg.federmann@poormanscastle.com on 5/9/16.
 */
public class DocFamUtils {

    private DateFormat dateFormatObjectId = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

    private DateFormat dateFormatExportDate = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

    /**
     * DocFamily Cockpit Elemente haben eine ID, die sich aus dem Datum und der Uhrzeit der Erzeugung des
     * Elements, und einem nachfolgenden, nicht näher spezfizierten Teil zusammen setzt.
     *
     * @return creates a cockpit element id which tries to be similar to IDs created by DocFamily DocRepo.
     */
    public String getCockpitElementId() {
        return StringUtils.join(dateFormatObjectId.format(new Date()), ".",
                String.valueOf(100 + Math.random() * 100), "-",
                String.valueOf(1000 + Math.random() * 9000), ".",
                String.valueOf(1000000 + Math.random() * 9000000), ".",
                String.valueOf(1000000 + Math.random() * 9000000), ".",
                String.valueOf(100 + Math.random() * 900)
        );
    }

    public String getCockpitExportDate() {
        return dateFormatExportDate.format(new Date());
    }

    public String truncate(String text, int maxWidth) {
        return StringUtils.abbreviate(text.replaceAll("[\"\\/*?:|@&.!'<>-]", "_"), maxWidth).replaceAll("[.]", "").trim();
    }

}
