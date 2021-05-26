package TBoxScanner;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;

import java.io.IOException;
import java.io.PrintWriter;

public class Pattern8 extends BasePattern implements IPattern{

    public void generatePattern() {
        //the eighth pattern (find all asymmetric properties...)
        try {
            this.GetPrintWriter("8");
            for (OWLAsymmetricObjectPropertyAxiom oba8 : ont.getAxioms(AxiomType.ASYMMETRIC_OBJECT_PROPERTY)) {
                pw.println(oba8.getProperty().toString());
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }
}
