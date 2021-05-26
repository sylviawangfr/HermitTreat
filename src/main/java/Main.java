import TBoxScanner.TBoxPatternGenerator;

import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        if(args.length == 0) {
            System.out.println("Usage: -Dtask Materialize/Materialize.Pattern10.TBoxScanner\n-Dontology ontology_file\n-Doutput output_dir");
            System.exit(-1);
        }
        String task = System.getProperty("task", "TBoxScanner");
        String ontoloty_file = System.getProperty("ontology", "data/NELL.ontology.ttl");
        String output_dir = System.getProperty("output", "output");
        java.net.URL url = Main.class.getProtectionDomain().getCodeSource()
                .getLocation();

        String filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
        String rootPath;
        if (filePath.endsWith(".jar")) {
            rootPath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        } else{
            rootPath = filePath.substring(0, filePath.lastIndexOf("target"));
        }
        System.out.println(rootPath);
        File outputFull = new File(rootPath + output_dir);
        if(!outputFull.exists() || !outputFull.isDirectory()) {
            if(outputFull.mkdirs()) {
                System.out.println("created output dir: " + outputFull.getAbsolutePath());
            } else {
                System.out.println("failed to make dir for: " + outputFull.getAbsolutePath());
            }
        } else {
            System.out.println(outputFull.getAbsolutePath() + " exists, skip creating dir");
        }
        String outputFullPath = outputFull.getAbsolutePath();
        String ontologyFullPath = rootPath + ontoloty_file;
        TBoxPatternGenerator tboxScanner = new TBoxPatternGenerator(ontologyFullPath, outputFullPath);
        switch (task) {
            case "Materialize":
                Materialize.materialize(ontologyFullPath, outputFullPath + "/materialize_triples.txt");
            case "TBoxScanner":
                tboxScanner.GeneratePatterns();
            case "AllClass":
                tboxScanner.getAllClasses();
            default:
        }
    }

}
