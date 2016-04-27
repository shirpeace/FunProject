import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class MainClass {

	public static String docFileName = "tmg_docs_tf";
	public static String queriesFileName = "tmg_queries_tf";
	public static double k = 0.5; 
	public static double lambda = 0.4;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		System.out.println("hello");
		
		/*try {
			WordMatrix queries = getWordMatrixFromFile(docFileName);
			WordMatrix documents = getWordMatrixFromFile(queriesFileName);
			
			//double[] resultsBM25 = BM25(documents, queries, k);
			//double[] resultsLM = WordMatrix.LM(documents, queries, lambda);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}*/
		

//		double[] resultsVSM = WordMatrix.VSM(...);
		//double[] resultsBM25 = BM25(documents, queries, k);
		//double[] resultsLM = WordMatrix.LM(documents, queries, lambda);
		
		// calcVsm(f.getWordMatrix(), wordQuers, d, q)
	}

	public static WordMatrix getWordMatrixFromFile(String fileName) throws FileNotFoundException, IOException{
		HashSet<Record> records = new HashSet<Record>();
		HashSet<Integer> rows = new HashSet<Integer>();
		HashSet<Integer> columns = new HashSet<Integer>();

		try (BufferedReader br = new BufferedReader(new FileReader(fileName + ".txt"))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
				
				String[] arguments = line.split(" ");
				int word = Integer.valueOf(arguments[0]);
				int document = Integer.valueOf(arguments[1]);
				int frequency = Integer.valueOf(arguments[2]);
				rows.add(word);
				columns.add(document);
				records.add(new Record(word, document, frequency));
			}
			WordMatrix matrix = new WordMatrix(rows.size(), columns.size());
			for (Record r : records){
				matrix.insert(r.getWord(),r.getDocument(),r.getFrequency());
			}
			return matrix;
		}
	}
	
	public static double calcVsm(WordMatrix wordDocs, WordMatrix wordQuers,
			int d, int q) {

		double sumD = 0;

		for (int j = 0; j < wordDocs.getWordMatrix().length; j++) {
			sumD += wordDocs.tfIdf(j, d);
		}

		double sumQ = 0;
		for (int j = 0; j < wordQuers.getWordMatrix().length; j++) {
			sumQ += wordQuers.tfIdf(j, d);
		}
		
		double lngtD = wordDocs.calcLength( d);
		double lngtQ = wordQuers.calcLength( q);
		
		return sumD * sumQ / (lngtD * lngtQ);
	}
	
	/*	*//**
	 * Grades a document given a query
	 * 
	 * @param wordMatrix
	 *            the wordMatrix
	 * @param doc
	 *            doc id
	 * @param query
	 *            query Collection<Integer>
	 * @param
	 * @return
	 */
	private static double BM25(WordMatrix wordMatrix, int doc,
			Collection<Integer> query, double k) {
		double sum = 0;
		for (int q : query) {
			int f = wordMatrix.getf(q, doc);
			int docLength = wordMatrix.getDocLength(doc);
			int n = wordMatrix.getn(q);
			// TODO: check smoothing
			f += .5;
			n += .5;
			sum += (f / (k + docLength + f))
					* Math.log10((wordMatrix.getN() - n) / n);
		}
		return sum;
	}

	/**
	 * Grades all documents given a query
	 * 
	 * @param wordMatrix
	 * @param query
	 * @param k
	 * @return
	 */
	private static double[] BM25(WordMatrix wordMatrix,
			Collection<Integer> query, double k) {
		double[] documentScores = new double[wordMatrix.getN()];
		for (int doc = 0; doc < wordMatrix.getN(); doc++) {
			documentScores[doc] = BM25(wordMatrix, doc, query, k);
		}
		return documentScores;
	}

	/**
	 * Grades all documents given a all queries
	 * 
	 * @param wordMatrix
	 * @param queries
	 * @param k
	 * @return
	 */
	public static double[][] BM25(WordMatrix wordMatrix,
			WordMatrix queries, double k) {
		
		double[][] documentScores = new double[queries.getN()][wordMatrix .getN()];
		for (int q = 0; q < queries.getN(); q++) {
			ArrayList<Integer> query = new ArrayList<Integer>();
			for (int word = 0 ; word < wordMatrix.getWordMatrix().length ; word++){
				int frequency = wordMatrix.getWordMatrix()[word][q];
				if (frequency > 0){
					query.add(frequency);
				}
			}
			documentScores[q] = BM25(wordMatrix, query, k);
		}
		return documentScores;
	}
	
	public static List<Integer> getTop10BM25(WordMatrix wordMatrix,
			Collection<Integer> query, double k){
		double[] arr = BM25(wordMatrix, query, k);
		
		HashMap<Integer,Double> docs = new HashMap<Integer, Double>();
		for (int i = 0 ; i < arr.length ; i++){
			docs.put(i, arr[i]);
		}
		
		LinkedHashMap<Integer, Double> sortHashMap = sortHashMapByValues(docs);
		
		
		ArrayList<Integer> result = new ArrayList<Integer>(sortHashMap.keySet());
		
		//ArrayList<Double> grades = new ArrayList<Double>(docs.values());

	    /*Collections.sort(grades, new Comparator<Person>() {

	        public int compare(Person o1, Person o2) {
	            return o1.getAge() - o2.getAge();
	        }
	    });*/
	    
	    return result.subList(0, 10);		
	}
	
	public static LinkedHashMap<Integer, Double> sortHashMapByValues( HashMap<Integer, Double> passedMap) {
	    List<Integer> mapKeys = new ArrayList<Integer>(passedMap.keySet());
	    List<Double> mapValues = new ArrayList<Double>(passedMap.values());
	    Collections.sort(mapValues);
	    Collections.sort(mapKeys);

	    LinkedHashMap<Integer, Double> sortedMap =
	        new LinkedHashMap<Integer, Double>();

	    Iterator<Double> valueIt = mapValues.iterator();
	    while (valueIt.hasNext()) {
	    	Double val = valueIt.next();
	        Iterator<Integer> keyIt = mapKeys.iterator();

	        while (keyIt.hasNext()) {
	            Integer key = keyIt.next();
	            Double comp1 = passedMap.get(key);
	            Double comp2 = val;

	            if (comp1.equals(comp2)) {
	                keyIt.remove();
	                sortedMap.put(key, val);
	                break;
	            }
	        }
	    }
	    return sortedMap;
	}

	/**
	 * Grades a document given a query
	 * 
	 * @param wordMatrix
	 * @param doc
	 * @param query
	 * @param lambda
	 * @return
	 */
	private static double LM(WordMatrix wordMatrix, int doc,
			Collection<Integer> query, double lambda) {
		double sum = 0;
		if (lambda == 0) {
			return 0;
		}
		// TODO: check smoothing: what if the words does not exist in the
		// corpus?
		for (int q : query) {
			sum += (1 - lambda) * wordMatrix.getProbability(q, doc)
					/ wordMatrix.getDocLength(doc) + lambda
					* wordMatrix.getProbability(q);
		}
		return sum;
	}

	/**
	 * Grades all documents given a query
	 * 
	 * @param wordMatrix
	 * @param query
	 * @param lambda
	 * @return
	 */
	public static double[] LM(WordMatrix wordMatrix, Collection<Integer> query,
			double lambda) {
		double[] documentScores = new double[wordMatrix.getN()];
		for (int doc = 0; doc < wordMatrix.getN(); doc++) {
			documentScores[doc] = LM(wordMatrix, doc, query, lambda);
		}
		return documentScores;
	}

	/**
	 * Grades all documents given a all queries
	 * 
	 * @param wordMatrix
	 * @param queries
	 * @param k
	 * @return
	 */
	public static double[][] LM(WordMatrix wordMatrix,
			WordMatrix queries , double k) {
		double[][] documentScores = new double[queries.getN()][wordMatrix .getN()];
		for (int q = 0; q < queries.getN(); q++) {
			ArrayList<Integer> query = new ArrayList<Integer>();
			for (int word = 0 ; word < wordMatrix.getWordMatrix().length ; word++){
				int frequency = wordMatrix.getWordMatrix()[word][q];
				if (frequency > 0){
					query.add(frequency);
				}
			}
			documentScores[q] = LM(wordMatrix, query, k);
		}
		return documentScores;
	}
}
