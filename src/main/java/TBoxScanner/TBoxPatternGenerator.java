package TBoxScanner;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import uk.ac.manchester.cs.jfact.JFactFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/*
     Script that do TBoxScanner.

      */
public class TBoxPatternGenerator {
    String ontology_file;
    OWLOntology ont;
    String out_dir;

    public TBoxPatternGenerator(String ontoloty_file, String output_dir) {
        this.ontology_file = ontoloty_file;
        this.out_dir = output_dir;
    }

    public void getAllClasses() throws IOException {
        File all_classes = new File(out_dir + "/AllClasses.txt");
        FileWriter FW_classes = new FileWriter(all_classes);
        PrintWriter all_classes_w = new PrintWriter(FW_classes);
        ont.classesInSignature().forEach(element -> {
            all_classes_w.print(element.getIRI().getShortForm() + '\n');
        });
        all_classes_w.close();
    }

    public void GeneratePatterns() {
        try {
            long startTime = System.nanoTime();
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();
            InputStream is = this.readFileAsStream(ontology_file);
            ont = man.loadOntologyFromOntologyDocument(is);
            OWLReasonerFactory rf = new JFactFactory();
            OWLReasoner reasoner = rf.createReasoner(ont);
            OWLDataFactory factory = man.getOWLDataFactory();
            ArrayList<Supplier<BasePattern>> patternConsumers = RegesterPatterns();
            for (Supplier<BasePattern> p : patternConsumers) {
                p.get().SetOWLAPIContext(ont, reasoner, factory, out_dir).generatePattern();
            }
        } catch (OWLOntologyCreationException | IllegalArgumentException | FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

    private ArrayList<Supplier<BasePattern>> RegesterPatterns() {
        ArrayList<Supplier<BasePattern>> patternConsumers = new ArrayList<>();
        patternConsumers.add(() -> new Pattern1());
        patternConsumers.add(() -> new Pattern2());
        patternConsumers.add(() -> new Pattern3());
        patternConsumers.add(() -> new Pattern4());
        patternConsumers.add(() -> new Pattern5());
        patternConsumers.add(() -> new Pattern6());
        patternConsumers.add(() -> new Pattern7());
        patternConsumers.add(() -> new Pattern8());
        patternConsumers.add(() -> new Pattern9());
        patternConsumers.add(() -> new Pattern10());
        patternConsumers.add(() -> new Pattern11());
        return patternConsumers;
    }

    private InputStream readFileAsStream(String fileName) throws FileNotFoundException {
        // The class loader that loaded the class
//        ClassLoader classLoader = getClass().getClassLoader();
//        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        File initialFile = new File(fileName);
        InputStream inputStream = new FileInputStream(initialFile);
        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }
}
