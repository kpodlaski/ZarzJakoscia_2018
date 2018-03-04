package vectoralgebra;

/**
 * Created by Krzysztof Podlaski on 04.03.2018.
 */
public class Vector {

    private double[] v;

    private  Vector(double[] v) {
        this.v=v;
    }

    public static Vector buildVector(double[] v) throws Exception {
        if (v.length > 3) throw new Exception("3D is maximal dimension ");
        return new Vector(v);
    }

    public double scalarProduct(Vector b) {
        double result = 0.0;
        int l = Math.min(v.length,b.v.length);
        for (int i = 0; i < l; i++) {
            result += v[i] * b.v[i];
        }
        return result;
    }

    public double length(){
        return Math.sqrt(scalarProduct(this));
    }

    public Vector vectorProduct(Vector b){
        return null;
    }

    public double[] getV() {
        return v;
    }
}
