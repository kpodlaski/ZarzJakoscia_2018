package vectoralgebra;

public class Vector {
	public double[] v;
	
	public double lenght(){
		return Math.sqrt( this.scalarProduct(this) );
	}

	public double scalarProduct(Vector b){
		double result =0;
		int min_length =Math.min(v.length,b.v.length); 
		for (int i=0; i<min_length; i++){
			result+=v[i]*b.v[i];
		}
		return result;
	}
	
	public Vector crossProduct(Vector b){
		return new Vector();
	}
	
}
