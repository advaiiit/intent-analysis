package example;

import java.util.Properties;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
public class nlpPipeline {
    static StanfordCoreNLP pipeline;
    public static void init()
    {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, parse");
        pipeline = new StanfordCoreNLP(props);
    }
    public static String findIntent(String text)
    {
        Annotation annotation = pipeline.process(text);
        String intent = "It does not seem that the sentence expresses an explicit intent.";
        for(CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class))
        {
            SemanticGraph sg = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
            for (SemanticGraphEdge edge : sg.edgeIterable()) {
                if (edge.getRelation().getLongName() == "direct object"){
                    String tverb = edge.getGovernor().originalText();
                    String dobj = edge.getDependent().originalText();
                    //dobj = dobj.substring(0,1).toUpperCase() + dobj.substring(1).toLowerCase();
                    intent = tverb + " " + dobj;
                }
            }
        }
        return intent;
    }
}
