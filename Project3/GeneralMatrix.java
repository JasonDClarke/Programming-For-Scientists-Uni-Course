/*
 * PROJECT III: GeneralMatrix.java
 *
 * This file contains a template for the class GeneralMatrix. Not all methods
 * are implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file. You will also need to have completed
 * the Matrix class.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */

import java.util.Arrays;

public class GeneralMatrix extends Matrix {
    /**
     * This instance variable stores the elements of the matrix.
     */
    private double[][] data;

    /**
     * Constructor function: should initialise m and n through the Matrix
     * constructor and set up the data array.
     *
     * @param m  The first dimension of the array.
     * @param n  The second dimension of the array.
     */
    public GeneralMatrix(int m, int n) throws MatrixException {
        super(m,n);
        if (m<1 || n<1)
        {
            throw new MatrixException("Matrix must have positive dimensions");
        }
        this.data = new double[m][n];
    }

    /**
     * Constructor function. This is a copy constructor; it should create a
     * copy of the matrix A.
     *
     * @param A  The matrix to create a copy of.
     */
    public GeneralMatrix(GeneralMatrix A) {
	super(A.m,A.n);
        int i,j;
        data = new double[A.m][A.n];
        for (i=0;i<m;i++) 
        {
            for (j=0;j<n;j++)
            {
                this.data[i][j] = A.data[i][j];
            }
        }
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
        return this.data[i][j];
    }
    
    /**
     * Setter function: set the (i,j)'th entry of the data array.
     *
     * @param i    The location in the first co-ordinate.
     * @param j    The location in the second co-ordinate.
     * @param val  The value to set the (i,j)'th entry to.
     */
    public void setIJ(int i, int j, double val) {
        if( ((i<0) || (i > this.m-1)) || ((j<0) || (j > this.n-1))) 
        {
            throw new MatrixException("Trying to set non-existent matrix element.");
        }      
        this.data[i][j] = val;
    }
    
    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        double[] d = new double[1];
        Matrix A = decomp(d);
        int i;
        double det=d[0];
        for(i = 0; i< A.m;i++)
        {
            det = det * A.getIJ(i,i);
        }
        return det;
    }

    /**
     * Add the matrix to another matrix A.
     *
     * @param A  The Matrix to add to this matrix.
     * @return   The sum of this matrix with the matrix A.
     */
    public Matrix add(Matrix A) {               
        if( (this.m != A.m) || (this.n != A.n) ) 
        {
            throw new MatrixException("Matrix dimensions differ");
        }
        GeneralMatrix Sum = new GeneralMatrix(m,n);
        int i, j;
        for(i=0; i< this.m ; i++)
        {
            for(j=0; j<this.n ; j++)
            {
                Sum.setIJ(i,j, (this.getIJ(i,j) + A.getIJ(i,j)) );
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
        GeneralMatrix Product = new GeneralMatrix(A.n,this.m);     
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
                Product.setIJ(i,k,ProductEntry);
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
        GeneralMatrix ScalarMultiple = new GeneralMatrix(this.m,this.n);
        int i,j;
        for(i = 0; i<this.m;i++)
        {
            for(j = 0; j<this.n;j++)
            {
                ScalarMultiple.setIJ(i,j, (this.getIJ(i,j)*a));
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
                this.setIJ(i,j, Math.random());
            }
        }
    }

    /**
     * Returns the LU decomposition of this matrix; i.e. two matrices L and U
     * so that A = LU, where L is lower-diagonal and U is upper-diagonal.
     * 
     * On exit, decomp returns the two matrices in a single matrix by packing
     * both matrices as follows:
     *
     * [ u_11 u_12 u_13 u_14 ]
     * [ l_21 u_22 u_23 u_24 ]
     * [ l_31 l_32 u_33 u_34 ]
     * [ l_41 l_42 l_43 l_44 ]
     *
     * where u_ij are the elements of U and l_ij are the elements of l. When
     * calculating the determinant you will need to multiply by the value of
     * d[0] calculated by the function.
     * 
     * If the matrix is singular, then the routine throws a MatrixException.
     *
     * This method is an adaptation of the one found in the book "Numerical
     * Recipies in C" (see online for more details).
     * 
     * @param d  An array of length 1. On exit, the value contained in here
     *           will either be 1 or -1, which you can use to calculate the
     *           correct sign on the determinant.
     * @return   The LU decomposition of the matrix.
     */
    public GeneralMatrix decomp(double[] d) {
        // This method is complete. You should not even attempt to change it!!
        if (n != m)
            throw new MatrixException("Matrix is not square");
        if (d.length != 1)
            throw new MatrixException("d should be of length 1");
        
        int           i, imax = -10, j, k; 
        double        big, dum, sum, temp;
        double[]      vv   = new double[n];
        GeneralMatrix a    = new GeneralMatrix(this);
        
        d[0] = 1.0;
        
        for (i = 1; i <= n; i++) {
            big = 0.0;
            for (j = 1; j <= n; j++)
                if ((temp = Math.abs(a.data[i-1][j-1])) > big)
                    big = temp;
            if (big == 0.0)
                throw new MatrixException("Matrix is singular");
            vv[i-1] = 1.0/big;
        }
        
        for (j = 1; j <= n; j++) {
            for (i = 1; i < j; i++) {
                sum = a.data[i-1][j-1];
                for (k = 1; k < i; k++)
                    sum -= a.data[i-1][k-1]*a.data[k-1][j-1];
                a.data[i-1][j-1] = sum;
            }
            big = 0.0;
            for (i = j; i <= n; i++) {
                sum = a.data[i-1][j-1];
                for (k = 1; k < j; k++)
                    sum -= a.data[i-1][k-1]*a.data[k-1][j-1];
                a.data[i-1][j-1] = sum;
                if ((dum = vv[i-1]*Math.abs(sum)) >= big) {
                    big  = dum;
                    imax = i;
                }
            }
            if (j != imax) {
                for (k = 1; k <= n; k++) {
                    dum = a.data[imax-1][k-1];
                    a.data[imax-1][k-1] = a.data[j-1][k-1];
                    a.data[j-1][k-1] = dum;
                }
                d[0] = -d[0];
                vv[imax-1] = vv[j-1];
            }
            if (a.data[j-1][j-1] == 0.0)
                a.data[j-1][j-1] = 1.0e-20;
            if (j != n) {
                dum = 1.0/a.data[j-1][j-1];
                for (i = j+1; i <= n; i++)
                    a.data[i-1][j-1] *= dum;
            }
        }
        
        return a;
    }

    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {
        //create test matrices
        try {Matrix A = new GeneralMatrix(-1,2);}
        catch (MatrixException e) 
        {
            System.out.println(e.getMessage());
        }
        Matrix A = new GeneralMatrix(3,2);
        Matrix B = new GeneralMatrix(3,2);
        Matrix C = new GeneralMatrix(3,3);
        Matrix D = new GeneralMatrix(3,3);
        Matrix E = new GeneralMatrix(2,3);
        Matrix F = new GeneralMatrix(2,3);
        Matrix G = new GeneralMatrix(1,1);
        Matrix H = new GeneralMatrix(4,4);
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