/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vpc_conf;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  This is for sphinx buissnes
 * @author hash
 */
public class Sphinx_stf {

    private static Sphinx_stf stf = null;
    private ConfigurationManager cm;
    private Recognizer recognizer;
    private Microphone microphone;

    private Sphinx_stf() throws Exception{
        cm = new ConfigurationManager(Vpc_confView.class.getResource(
                "vpc_conf.config.xml"));
        recognizer = (Recognizer)cm.lookup("recognizer");
        recognizer.allocate();

        microphone = (Microphone)cm.lookup("microphone");
        if (!microphone.startRecording()) {
            recognizer.deallocate();
            throw new Exception("Can't start microphone");
        }
        microphone.stopRecording();
    }

    public static Sphinx_stf get(){
        if (stf == null) {
            try {
                stf = new Sphinx_stf();
            } catch (Exception ex) {
                Logger.getLogger(Sphinx_stf.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }
        return stf;
    }

    public String GetCommand(){
        Result result = recognizer.recognize();
        if (result != null) {
            return result.getBestFinalResultNoFiller();
        } else {
            return "";
        }
    }
}
