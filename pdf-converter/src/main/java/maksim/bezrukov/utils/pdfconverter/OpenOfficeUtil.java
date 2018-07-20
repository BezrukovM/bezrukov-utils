package maksim.bezrukov.utils.pdfconverter;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XDesktop;
import com.sun.star.frame.XStorable;
import com.sun.star.io.IOException;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import ooo.connector.BootstrapSocketConnector;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class OpenOfficeUtil {

    private static final Set<String> EXTENSIONS = Arrays.stream(new String[]{"doc", "docx", "xls", "xlsx", "ppt", "pptx",
            "ods", "odt", "odp", "rtf"}).collect(Collectors.toSet());

    public static String officeDirectory = "";

    public static boolean isSupportedExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        String extension = index > 0 && index < fileName.length()-1 ? fileName.substring(index + 1) : "";
        return EXTENSIONS.contains(extension.toLowerCase());
    }

    public static void convert(File wordFile, File pdfFile) throws BootstrapException, Exception, MalformedURLException, InterruptedException {
        XDesktop xDesktop = initialize();
        XComponent xComp = loadDocument(wordFile, xDesktop);
        saveToPDF(pdfFile, xComp);

        xDesktop.terminate();
        Thread.sleep(1000);
    }

    private static void saveToPDF(File pdfFile, XComponent xComp) throws MalformedURLException, IOException {
        PropertyValue[] propertyValues;

        XStorable xStorable = UnoRuntime.queryInterface(XStorable.class, xComp);

        PropertyValue[] aMediaDescriptor = new PropertyValue[2];

        aMediaDescriptor[0] = new PropertyValue();
        aMediaDescriptor[0].Name = "FilterName";
        aMediaDescriptor[0].Value = "writer_pdf_Export";

        propertyValues = new PropertyValue[3];
        propertyValues[0] = new PropertyValue();
        propertyValues[0].Name = "Overwrite";
        propertyValues[0].Value = Boolean.TRUE;
        propertyValues[1] = new PropertyValue();
        propertyValues[1].Name = "SelectPdfVersion";
        propertyValues[1].Value = 1;
        propertyValues[2] = new PropertyValue();
        propertyValues[2].Name = "UseTaggedPDF";
        propertyValues[2].Value = Boolean.TRUE;

        aMediaDescriptor[1] = new PropertyValue();
        aMediaDescriptor[1].Name = "FilterData";
        aMediaDescriptor[1].Value = propertyValues;

        // Appending the favoured extension to the origin document name
        xStorable.storeToURL(pdfFile.toURI().toURL().toString(), aMediaDescriptor);
    }

    private static XComponent loadDocument(File wordFile, XDesktop xDesktop) throws MalformedURLException, IOException, IllegalArgumentException {
        if (!wordFile.canRead()) {
            throw new RuntimeException("Cannot load template:" + wordFile);
        }

        XComponentLoader xCompLoader = UnoRuntime.queryInterface(XComponentLoader.class, xDesktop);

        PropertyValue[] propertyValues = new PropertyValue[1];
        propertyValues[0] = new PropertyValue();
        propertyValues[0].Name = "Hidden";
        propertyValues[0].Value = Boolean.TRUE;

        return xCompLoader.loadComponentFromURL(wordFile.toURI().toURL().toString(), "_blank", 0, propertyValues);
    }

    private static XDesktop initialize() throws BootstrapException, Exception {
        XComponentContext xContext;
        xContext = BootstrapSocketConnector.bootstrap(officeDirectory);
        XMultiComponentFactory xMCF = xContext.getServiceManager();
        Object oDesktop = xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xContext);
        return UnoRuntime.queryInterface(XDesktop.class, oDesktop);
    }
}
