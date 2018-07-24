/*
 *  Name        	: Andrew Hartwell
 *  cruzID       	: arhartwe
 *  Assignment   	: pa3
*/

import java.io.*;
import java.util.Scanner;

class Sparse
{
	public static void main(String[] args) throws IOException
	{
		Scanner in = null;
		PrintWriter out = null;
		int lineNumber = 0;
		String line = null;
		String[] token = null;
		int a;
		int b;
		double c;
		int aSize = 0;
		int bSize = 0;
		int aNNZ = 0;
		int bNNZ = 0;
		Matrix A;
		Matrix B;
		Matrix C;		

		if(args.length < 2) // User must specify input and output file
		{
			System.err.println("Usage: FileIO infile outfile");
			System.exit(1);
		}

		in = new Scanner(new File(args[0]));
		out = new PrintWriter(new FileWriter(args[1]));

		line = in.nextLine() + " ";		// Remove whitespace from input strings and grab ints
		token = line.split("\\s+");
		aSize = Integer.parseInt(token[0]);
		bSize = aSize;
		aNNZ = Integer.parseInt(token[1]);
		bNNZ = Integer.parseInt(token[2]);

		A = new Matrix(aSize);
		B = new Matrix(bSize);

		line = in.nextLine() + " ";

		for(int i = 0; i < aNNZ; i++)		// Insert values for first matrix
		{	
			line = in.nextLine() + " ";
			token = line.split("\\s+");
			a = Integer.parseInt(token[0]);
			b = Integer.parseInt(token[1]);
			c = Double.parseDouble(token[2]);
			A.changeEntry(a,b,c);
		}

		line = in.nextLine();

		for(int i = 0; i < bNNZ; i++)		// Insert values for second matrix
		{
			line = in.nextLine() + " ";
			token = line.split("\\s+");
			a = Integer.parseInt(token[0]);
			b = Integer.parseInt(token[1]);
			c = Double.parseDouble(token[2]);
			B.changeEntry(a,b,c);
		}

		out.print("A has " + A.getNNZ() + " non-zero entries:\n");		// Print out all matrix operations to output file
		out.print(A.toString());
		out.print("\n\n");
		out.print("B has " + B.getNNZ() + " non-zero entries:\n");
		out.print(B.toString());
		out.print("\n\n");
		C = A.scalarMult(1.5);
		out.print("(1.5)*A =\n");
		out.print(C.toString());
		out.print("\n\n");
		C = A.add(B);
		out.print("A+B =\n");
		out.print(C.toString());
		out.print("\n\n");
		C = A.add(A);
		out.print("A+A =\n");
		out.print(C.toString());
		out.print("\n\n");
		C = B.sub(A);
		out.print("B-A =\n");
		out.print(C.toString());
		out.print("\n\n");
		C = A.sub(A);
		out.print("A-A =\n");
		out.print(C.toString());
		out.print("\n\n");
		C = A.transpose();
		out.print("Transpose(A) =\n");
		out.print(C.toString());
		out.print("\n\n");
		C = A.mult(B);
		out.print("A*B =\n");
		out.print(C.toString());
		out.print("\n\n");
		C = B.mult(B);
		out.print("B*B = \n");
		out.print(C.toString());
		out.print("\n\n");

		in.close();
		out.close();

	}
}
