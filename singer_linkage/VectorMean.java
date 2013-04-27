package singer_linkage;


public class VectorMean {

  private double[] mean;
	private int Number;
	public VectorMean(int N){
		setMean(new double[N]);
		Number = 0;
	}
	
	public void add(double[] data){
		for(int i = 0; i < data.length; ++i ){
			mean[i] = mean[i] + data[i];
		}
		++Number;
	}

	public double[] getMean() {
		for(int i = 0; i < mean.length; ++i)
			mean[i] = mean[i] / Number;
		
		return mean;
	}

	public void setMean(double[] mean) {
		this.mean = mean;
	}

}
