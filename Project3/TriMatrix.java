/*
 * PROJECT III: TriMatrix.java
 *
 * This file contains a template for the class TriMatrix. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file. You will also need to have completed
 * the Matrix class.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */

public class TriMatrix extends Matrix {
    /**
     * An array holding the diagonal elements of the matrix.
     */
    private double[] diag;

    /**
     * An array holding the upper-diagonal elements of the matrix.
     */
    private double[] upper;

    /**
     * An array holding the lower-diagonal elements of the matrix.
     */
    private double[] lower;
    
    /**
     * Constructor function: should initialise m and n through the Matrix
     * constructor and set up the data array.
     *
     * @param N  The dimension of the array.
     */
    public TriMatrix(int N) {
	super(N,N);
        if (m<1) 
        {
            throw new MatrixException("Matrix must have positive dimensions");
        }
        this.upper = new double[N-1];
        this.diag = new double[N];
        this.lower = new double[N-1];
    }
    
    /**
     * Getter function: return the (i,j)'th entry of the matrix.
     *
     * @param i  The location in the first co-ordinate.
     * @param j  The location in the second co-ordinate.
     * @return   The (i,j)'th entry of the matrix.
     */
    public double getIJ(int i, int j) {
        if( ((i<0) || (i > this.m-1)) || ((j<0) || (j > this.n-1))) 
        {
            throw new MatrixException("Trying to get non-existent entry.");
        }
        else if (i==j) 
        {
            return this.diag[i];    
        }
        else if (i==j+1) 
        {
        return this.upper[j];    
        }
        else if (j==i+1) 
        {
            return this.lower[i];   
        }
        else return 0;
    }
    
    /**
     * Setter function: set the (i,j)'th entry of the data array.
     *
     * @param i    The location in the first co-ordinate.
     * @param j    The location in the second co-ordinate.
     * @param val  The value to set the (i,j)'th entry to.
     */
    public void setIJ(int i, int j, double val) {
        if ( ((i<0) || (i > this.m-1)) || ((j<0) || (j > this.n-1))) 
        {
            throw new MatrixException("Trying to set non-existent matrix element.");
        }      
        else if ( Math.abs(i-j) > 1 ) 
        {
            throw new MatrixException("Setting not valid for Trimatrix");
        }
        else if (i==j) 
        {
            this.diag[i] = val;    
        }
        else if (i==j+1) 
        {
            this.upper[j] = val;    
        }
        else if (j==i+1) 
        {
            this.lower[i] = val;   
        }
    }
    
    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        Matrix A = decomp();
        int i;
        double det=1.0;
        for(i = 0; i< A.m;i++)
        {
            det *= A.getIJ(i,i);
        }
        return det;		
    }
    
    /**
     * Returns the LU decomposition of this matrix. See the formulation for a
     * more detailed description.
     * 
     * @return The LU decomposition of this matrix.
     */
    public TriMatrix decomp() {
        if (n != m)
            throw new MatrixException("Matrix is not square");
        TriMatrix decomp = new TriMatrix(n);
	decomp.diag[0] = this.diag[0];
	for( int i=0; i<n-1; i++)
        {
            decomp.setIJ(i,i+1, this.upper[i]);
            decomp.setIJ(i+1,i, this.lower[i]/decomp.diag[i]);
	    decomp.setIJ(i+1,i+1, diag[i+1]-decomp.lower[i]*upper[i]);
	}
	return decomp;
    }

    /**
     * Add the matrix to another matrix A.
     *
     * @param A  The Matrix to add to this matrix.
     * @return   The sum of this matrix with the matrix A.
     */
    public Matrix add(Matrix A){
        if( (this.m != A.m) || (this.n != A.n) ) 
        {
            throw new MatrixException("Matrix dimensions differ");
        }
        TriMatrix Sum = new TriMatrix(m);
        int i, j;
        for(i=0; i< this.m ; i++)
        {
            for(j=0; j<this.n ; j++)
            {
                if ( Math.abs(i-j) <= 1 )
                {
                Sum.setIJ(i,j, (this.getIJ(i,j) + A.getIJ(i,j)) );
                }
            }
        }
        return Sum;       
    }
    
    /**
     * Multiply the matrix by another matrix A. This is a _left_ product,
     * i.e. if this matrix is called B then it calculates the product BA.
     *
     * @param A  The Matrix to multiply by.
     * @return   The product of this matrix with the matrix A.
     */
    public Matrix multiply(Matrix A) {
        if( this.n != A.m ) 
        {
            throw new MatrixException("The matrices have the wrong dimensions to be multipled.");
        }
        TriMatrix Product = new TriMatrix(A.n);     
        int i,j,k; 
        double ProductEntry = 0;       
        for(i = 0; i<this.m; i++)
        {
            for(k=0;k<A.n;k++)
            {
                ProductEntry = 0;
                for(j=0; j<this.n;j++)
                {
                    ProductEntry += (this.getIJ(i,j)* A.getIJ(j,k));
                }
                if ( Math.abs(i-k) <= 1 ) {
                    Product.setIJ(i,k,ProductEntry);
                }
            }
        }       
        return Product;
    }
    
    /**
     * Multiply the matrix by a scalar.
     *
     * @param a  The scalar to multiply the matrix by.
     * @return   The product of this matrix with the scalar a.
     */
    public Matrix multiply(double a) {
        TriMatrix ScalarMultiple = new TriMatrix(this.m);
        int i,j;
        for(i = 0; i<this.m;i++)
        {
            for(j = 0; j<this.n;j++)
            {
                if ( Math.abs(i-j) <= 1 ) 
                {
                    ScalarMultiple.setIJ(i,j, (this.getIJ(i,j)*a));
                }
            }
        }
        return ScalarMultiple;
    }

    /**
     * Returns a matrix containing random numbers which are uniformly
     * distributed between 0 and 1.
     *
     * @param n  The first dimension of the matrix.
     * @param m  The second dimension of the matrix.
     */
    public void random() {
        int i = 0;
        int j=0;
        for(i=0; i< this.m ; i++)
        {
            for(j=0; j<this.n ; j++)
            {
                if ( Math.abs(i-j) <= 1 ) 
                {
                    this.setIJ(i,j, Math.random());
                }
            }
        }
    }
    
    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {
            //create test matrices
        try {Matrix A = new TriMatrix(-1);}
        catch (MatrixException e)
        {
            System.out.println(e.getMessage());
        }
        Matrix A = new TriMatrix(3);
        Matrix B = new TriMatrix(3);
        Matrix C = new TriMatrix(3);
        Matrix D = new TriMatrix(3);
        Matrix E = new TriMatrix(3);
        Matrix F = new TriMatrix(2);
        Matrix G = new TriMatrix(1);
        Matrix H = new TriMatrix(4);
        //create entries for test matrices
        A.random();
        B.random();
        C.random();
        D.random();
        E.random();
        F.random();
        G.random();
        H.random();
        //Print test matrices
        System.out.println("Print Matrices\n");
        System.out.println(A.toString());
        System.out.println(B.toString());
        System.out.println(C.toString());
        System.out.println(D.toString());
        System.out.println(E.toString());
        System.out.println(F.toString());
        System.out.println(G.toString());
        System.out.println(H.toString());
        //Test Determinant
        System.out.println("Print Determinants\n");
        System.out.println(D.determinant());
        System.out.println(G.determinant());
        //Test Addition
        System.out.println("Test Addition");
        System.out.println("A+B:\n");
        System.out.println(A.add(B).toString());
        //Test Multiplication
        System.out.println("Test Multiplication");
        System.out.println("A*E; E*A\n");
        System.out.println(A.multiply(E).toString());
        System.out.println(E.multiply(A).toString());
        //Test Scalar Multiplication
        System.out.println("Test Scalar Multiplication");
        System.out.println("H*4");
        System.out.println(H.multiply(4));
        //Test Getter and setter
        System.out.println("Test getter and setter");
        System.out.println(A.getIJ(1, 0));
        A.setIJ(1,0, 4.0);
        System.out.println(A.getIJ(1, 0)); 
    }
}