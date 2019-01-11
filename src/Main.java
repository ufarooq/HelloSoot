import soot.PackManager;
import soot.Scene;
import soot.SootClass;
import soot.options.Options;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
        String outputPath = "analysis/out";
        String classPath = "analysis";
        configureSoot(classPath, outputPath);// configure soot
        Scene.v().loadNecessaryClasses(); // load all the library and dependencies for given program
        SootClass helloWorld = Scene.v().getMainClass(); // get main class
        System.out.println("Methods: "+helloWorld.getMethodCount()); // get methods count for class
        PackManager.v().runPacks();  // process and build call graph
        PackManager.v().writeOutput(); // write output
    }

    public static void configureSoot(String classpath, String outDirectory) {
        Options.v().set_whole_program(true);  // process whole program
        Options.v().set_allow_phantom_refs(true); // load phantom references
        Options.v().set_prepend_classpath(true); // prepend class path
        Options.v().set_src_prec(Options.src_prec_only_class); // process only .class files
        Options.v().set_output_format(Options.output_format_jimple); // output jimple format
        ArrayList<String> list = new ArrayList<>();
        list.add(classpath);
        Options.v().set_process_dir(list); // process all .class files in directory
        Options.v().set_output_dir(outDirectory); // directory to write output
        Options.v().setPhaseOption("cg.spark", "on"); // use spark for call graph
    }
}
