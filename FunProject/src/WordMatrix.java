import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class WordMatrix {
	/*
	 * i = word, j = document
	 */
	int[][] wordMatrix;
	

	/**
	 * words total number of words document = total number of documents
	 * @param words
	 * @param documents
	 */
	public WordMatrix(int words, int documents) {
		super();
		wordMatrix = new int[words][documents];
	}

	public int[][] getWordMatrix() {
		return wordMatrix;
	}

	public void setWordMatrix(int[][] wordMatrix) {
		this.wordMatrix = wordMatrix;
	}

	/**
	 * Inserts a word frequency in a document to the word matrix word = the id
	 * of the word document = the id of the document f = the frequency of the
	 * word in the document
	 * 
	 * @param word
	 *            - the id of the word
	 * @param document
	 *            - the id of the document
	 * @param f
	 *            - the frequency of the word in the document
	 */
	public void insert(int word, int document, int f) {
		wordMatrix[word][document] = f;
	}

	/**
	 * Returns the frequency of the word among all documents
	 * 
	 * @param word
	 *            the id of the word
	 * @return - the frequency of the word among all documents
	 */
	public int getn(int word) {
		int sum = 0;
		for (int doc = 0; doc < getN(); doc++) {
			sum += wordMatrix[word][doc];
		}
		return sum;
	}

	/**
	 * 
	 * @return Returns the number of documents
	 */
	public int getN() {
		return wordMatrix[0].length;
	}

	/**
	 * Returns the length (number of words) of the document
	 * 
	 * @param document
	 *            id
	 * @return
	 */
	public int getDocLength(int document) {
		int sum = 0;
		for (int word = 0; word < wordMatrix.length; word++) {
			sum += getf(word, document);
		}
		return sum;
	}

	/**
	 * Returns the frequency of the word in the document
	 * 
	 * @param word
	 *            id
	 * @param document
	 *            id
	 * @return
	 */
	public int getf(int word, int document) {
		return wordMatrix[word][document];
	}

	/**
	 * Returns the probability of a word in a document for LM
	 * 
	 * @param word
	 *            id
	 * @param doc
	 *            id
	 * @return
	 */
	public double getProbability(int word, int doc) {
		return getf(word, doc) / getDocLength(doc);
	}

	/**
	 * Returns the probability of a word in all documents for LM
	 * 
	 * @param word
	 *            id
	 * @return Returns the probability of a word in all documents for LM
	 */
	public double getProbability(int word) {
		return getWordFrequency(word) / getTotalNumOfWords();
	}

	/**
	 * Returns the word frequency in all documents for LM
	 * 
	 * @param word
	 *            id
	 * @return Returns the word frequency in all documents for LM
	 */
	public int getWordFrequency(int word) {
		int sum = 0;
		for (int doc = 0; doc < getN(); doc++) {
			sum += wordMatrix[word][doc];
		}
		return sum;
	}

	/**
	 * Returns the total number of words in all documents for LM
	 * 
	 * @return Returns the total number of words in all documents for LM
	 */
	public int getTotalNumOfWords() {
		int sum = 0;
		for (int word = 0; word < wordMatrix.length; word++) {
			for (int doc = 0; doc < getN(); doc++) {
				sum += wordMatrix[word][doc];
			}
		}
		return sum;
	}

	
	
	/**
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
	/*private static double BM25(WordMatrix wordMatrix, int doc,
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
	}*/

	/**
	 * Grades all documents given a query
	 * 
	 * @param wordMatrix
	 * @param query
	 * @param k
	 * @return
	 */
	/*private static double[] BM25(WordMatrix wordMatrix,
			Collection<Integer> query, double k) {
		double[] documentScores = new double[wordMatrix.getN()];
		for (int doc = 0; doc < wordMatrix.getN(); doc++) {
			documentScores[doc] = BM25(wordMatrix, doc, query, k);
		}
		return documentScores;
	}*/

	/**
	 * Grades all documents given a all queries
	 * 
	 * @param wordMatrix
	 * @param queries
	 * @param k
	 * @return
	 */
	/*public static double[][] BM25(WordMatrix wordMatrix,
			WordMatrix queries, double k) {
		
		double[][] documentScores = new double[queries.getN()][wordMatrix
				.getN()];
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
	}*/
	
	
	/*public static List<Integer> getTop10BM25(WordMatrix wordMatrix,
			Collection<Integer> query, double k){
		double[] arr = BM25(wordMatrix, query, k);
		
		HashMap<Integer,Double> docs = new HashMap<Integer, Double>();
		for (int i = 0 ; i < arr.length ; i++){
			docs.put(i, arr[i]);
		}
		
		LinkedHashMap<Integer, Double> sortHashMap = sortHashMapByValues(docs);
		
		
		ArrayList<Integer> result = new ArrayList<Integer>(sortHashMap.keySet());
		
		//ArrayList<Double> grades = new ArrayList<Double>(docs.values());

	    Collections.sort(grades, new Comparator<Person>() {

	        public int compare(Person o1, Person o2) {
	            return o1.getAge() - o2.getAge();
	        }
	    });
	    
	    return result.subList(0, 10);		
	}*/
	


	/**
	 * Grades a document given a query
	 * 
	 * @param wordMatrix
	 * @param doc
	 * @param query
	 * @param lambda
	 * @return
	 */
	/*private static double LM(WordMatrix wordMatrix, int doc,
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
	}*/

	/**
	 * Grades all documents given a query
	 * 
	 * @param wordMatrix
	 * @param query
	 * @param lambda
	 * @return
	 */
	/*public static double[] LM(WordMatrix wordMatrix, Collection<Integer> query,
			double lambda) {
		double[] documentScores = new double[wordMatrix.getN()];
		for (int doc = 0; doc < wordMatrix.getN(); doc++) {
			documentScores[doc] = LM(wordMatrix, doc, query, lambda);
		}
		return documentScores;
	}*/

	/**
	 * Grades all documents given a all queries
	 * 
	 * @param wordMatrix
	 * @param queries
	 * @param k
	 * @return
	 */
	/*public static double[][] LM(WordMatrix wordMatrix,
			ArrayList<ArrayList<Integer>> queries, double k) {
		double[][] documentScores = new double[queries.size()][wordMatrix
				.getN()];
		for (int query = 0; query < queries.size(); query++) {
			documentScores[query] = LM(wordMatrix, queries.get(query), k);
		}
		return documentScores;
	}*/

	/**
	 * calculate the wight of the 
	 * @param mtx
	 * @param word
	 * @param d
	 * @return
	 */
	public double tfIdf(int word, int d) {
		int f = getf(word, d);
		int nj = getNj(word);
		return (Math.log(f) + 1) * (Math.log(getN() / nj));

	}

	public double calcLength(int d) {
		double sum = 0;
		for (int j = 0; j < wordMatrix.length; j++) {
			sum += Math.pow(tfIdf(j, d), 2);
		}
		return Math.sqrt(sum);

	}



	/**
	 * get the number of docs that the word appears in
	 * 
	 * @param word
	 * @return
	 */
	private int getNj(int word) {
		int count = 0;
		for (int doc = 0; doc < getN(); doc++) {
			if (wordMatrix[word][doc] > 0) {
				count++;
			}

		}

		return count;
	}

}
