/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewerfx;

import java.io.*;
import java.util.logging.LogManager;

/**
 *
 * @author Pierrick Puimean-Chieze
 */
public class LogManagerTest extends LogManager {

    @Override
    public void readConfiguration() throws IOException, SecurityException {
        checkAccess();

        // if a configuration class is specified, load it and use it.
        String cname = System.getProperty("java.util.logging.config.class");
        if (cname != null) {
            try {
                // Instantiate the named class.  It is its constructor's
                // responsibility to initialize the logging configuration, by
                // calling readConfiguration(InputStream) with a suitable stream.
                try {
                    Class clz = ClassLoader.getSystemClassLoader().
                            loadClass(cname);
                    clz.newInstance();
                    return;
                } catch (ClassNotFoundException ex) {
                    Class clz = Thread.currentThread().getContextClassLoader().
                            loadClass(cname);
                    clz.newInstance();
                    return;
                }
            } catch (Exception ex) {
                System.err.
                        println("Logging configuration class \"" + cname + "\" failed");
                System.err.println("" + ex);
                // keep going and useful config file.
            }
        }

        String fname = System.getProperty("java.util.logging.config.file");
        if (fname == null) {
            fname = System.getProperty("java.home");
            if (fname == null) {
                throw new Error("Can't find java.home ??");
            }
            File f = new File(fname, "lib");
            f = new File(f, "logging.properties");
            fname = f.getCanonicalPath();
        }
        try {
            InputStream in = new FileInputStream(fname);
            BufferedInputStream bin = new BufferedInputStream(in);
            try {
                readConfiguration(bin);
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (IOException | SecurityException ex) {
            throw ex;
        }
    }
}
