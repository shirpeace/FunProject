import java.lang.Math;
public class Vsm {
 final static int N=1398; 
 
 
 	/**
 	 * 
 	 * @param mtx
 	 * @param w
 	 * @param d
 	 * @return
 	 */
	public static double tfIdf(int[][] mtx, int w, int d){
		int f = getf(mtx,  w,  d);
		int nj = gtNj(mtx,w);
		return (Math.log(f)+1)*(Math.log(N/nj));

	}
	private static int gtNj(int[][] mtx, int w) {
		int count=0;
		for (int j = 0; j < mtx.length; j++) {
			if(mtx[j][1]==w)
				count++;
		}
		return count;
	}
	private static int getf(int[][] mtx, int w, int d) {
		for (int j = 0; j < mtx.length; j++) {
			if(mtx[j][0]==d && mtx[j][1]==w)
				return mtx[j][2];
		}
		return 0;
	}
	public static double calcLength(int[][] mtx, int d)
	{
		double sum=0;
		for (int j = 0; j < mtx.length; j++) {
			if(mtx[j][0]==d)
				sum+= Math.pow(tfIdf(mtx, mtx[j][1], d),2);
		}
		return Math.sqrt(sum);
		
	}
	public static double getMulitipalSumDQ(int[][] mtx, int d)
	{
	
		return 0;
		
	}
	
	public static double calcVsm(int[][] mtx, int d, int[][] qry, int q){
		System.out.println("testing 1..2..1..2");
		double sumD=0;
		for (int j = 0; j < mtx.length; j++) {
			if(mtx[j][0]==d)
				sumD+= tfIdf(mtx, mtx[j][1], d);
		}
		double sumQ=0;
		for (int j = 0; j < qry.length; j++) {
			if(mtx[j][0]==d)
				sumQ+= tfIdf(mtx, mtx[j][1], d);
		}
		double lngtD = calcLength(mtx, d);
		double lngtQ = calcLength(qry, q);
		return sumD*sumQ/(lngtD*lngtQ);
	}
	
}
