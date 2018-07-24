/*
 *  Name		: Andrew Hartwell
 *  cruzID       	: arhartwe
 *  Assignment   	: pa3
*/

class Matrix
{
	private int n;
	private List[] row;

	Matrix(int n) // Makes a new n x n zero Matrix. pre: n>=1
	{	
		if(n >= 1)
		{
			this.n = n;
		}
		row = new List[n+1];
		for(int i = 1; i < n+1; i++)
		{
			List list = new List();
			row[i] = list;
		}
	}

	private class Entry // Makes a new matrix entry.
	{
		private int col;
		private double val;

		Entry(int col, double val)
		{
			this.col = col;
			this.val = val;
		}
		public String toString()
		{
			return "(" + col + ", " + val + ")";
		}
	}

	// Access Functions
	int getSize() // Returns n, the number of rows and columns of this Matrix.
	{
		return n;
	}

	int getNNZ() // Returns the number of non-zero entries in this Matrix.
	{
		Entry E;	
		int NNZ = 0;

		for(int i = 1; i <= n; i++)
		{
			row[i].moveFront();
			while(row[i].length() > 0)
			{
				E = (Entry)row[i].get();
				NNZ++;
				row[i].moveNext();
				if(row[i].index() != -1)
				{
					E = (Entry)row[i].get();
				}
				else
				{
					break;
				}
			}

		}
		return NNZ;
	}

	public boolean equals(Object x) // overrides Object's equals() method.
	{
		Entry current = null;
		Entry compared = null;

		Matrix M = (Matrix)x;
		Matrix A = this;

		if(A.getSize() != M.getSize())
		{
			return false;
		}
		for(int i = 1; i <= n; i++)
		{
			A.row[i].moveFront();
			M.row[i].moveFront();
			while(A.row[i].length() > 0)
			{
				if(A.row[i].index() != -1)
				{
					current = (Entry)A.row[i].get();
				}
				else
				{
					break;
				}
				if(M.row[i].index() != -1)
				{
					compared = (Entry)M.row[i].get();
				}
				else
				{
					break;
				}
				if(current.col != compared.col || current.val != compared.val)
				{
					return false;
				}
				A.row[i].moveNext();
				M.row[i].moveNext();
				if(A.row[i].index() == -1 && M.row[i].index() != -1)
				{
					return false;
				}
				if(M.row[i].index() == -1 && A.row[i].index() != -1)
				{
					return false;
				}
				else
				{
					if(A.row[i].index() != -1)
					{
						current = (Entry)A.row[i].get();
					}
					if(M.row[i].index() != -1)
					{
						compared = (Entry)M.row[i].get();
					}
				}
			}
		}

		return true;
	}

	// Manipulation procedures
	void makeZero() // sets this Matrix to the zero state
	{
		Entry E;

		for(int i = 1; i <= n; i++)
		{
			row[i].clear();
		}
	}


	Matrix copy() // returns a new Matrix having the same entries as this Matrix
	{
		Entry E;
		Matrix M = new Matrix(n);

		for(int	i = 1; i <= n; i++)
		{
			row[i].moveFront();
			while(row[i].index() >= 0)
			{
				E = (Entry)row[i].get();
				Entry insert = new Entry(E.col, E.val);
				M.changeEntry(i, insert.col, insert.val);
				row[i].moveNext();
			} 
		}
		return M;
	}

	void changeEntry(int i, int j, double x) // changes ith row, jth column of this Matrix to x
		// pre: 1<=i<=getSize(), 1<=j<=getSize()
	{
		Entry E;

		if(1 <= i && i <= getSize() && 1 <= j && j <= getSize())
		{
			Entry entry = new Entry(j,x);
			if(row[i].length() == 0)
			{
				if (x == 0)
				{
					return;
				}
				row[i].append(entry);
			}
			else
			{
				row[i].moveFront();
				E = (Entry)row[i].get();
				while(j > E.col)
				{
					if(row[i].length() == 1)
					{
						if (x == 0)
						{
							return;
						}
						row[i].append(entry);
						return;
					}
					row[i].moveNext();
					if(row[i].index() != -1)
					{
						E = (Entry)row[i].get();
					}
					else
					{
						if(x != 0)
						{
							row[i].append(entry);
						}
						break;
					}
				}
				if(j == E.col)
				{
					if(E.val != 0 && x == 0)
					{
						row[i].delete();
					}
					else if(E.val != 0 && x != 0)
					{
						E.val = x;
					}
				}
				if(j < E.col)
				{
					if (x != 0)
					{
						row[i].insertBefore(entry);
					}
				}
			}

		}
	}

	Matrix scalarMult(double x) // returns a new Matrix that is the scalar product of this Matrix with x
	{
		Entry E;
		Matrix M = new Matrix(n);

		for(int i = 1; i <= n; i++)
		{
			row[i].moveFront();
			while(row[i].index() >= 0)
			{
				E = (Entry)row[i].get();
				Entry insert = new Entry(E.col, E.val);
				M.changeEntry(i, insert.col, x*insert.val);
				row[i].moveNext();
			}
		}
		return M;
	}

	Matrix add(Matrix M) // returns a new Matrix that is the sum of this Matrix with M
		// pre: getSize() == M.getSize()
	{
		Matrix C = new Matrix(n);
		Matrix A = this;

		if(getSize() == M.getSize())
		{
			for(int i = 1; i <= n; i++)
			{
				if(A != M)
				{
					C.row[i] = addHelp(A.row[i], M.row[i]);
				}							
				else
				{
					return A.scalarMult(2);
				}	
			}
		}
		return C;
	}

	List addHelp(List A, List M)
	{
		List added = new List();
		Entry aE;
		Entry mE;
		A.moveFront();
		M.moveFront();

		if(A.index() != -1)
		{
			aE = (Entry)A.get();
		}
		if(M.index() != -1)
		{
			mE = (Entry)M.get();
		}
		Entry insert;
		if(M.index() == -1)
		{
			while(A.index() != -1)
			{
				aE = (Entry)A.get();
				insert = new Entry(aE.col, aE.val);
				added.append(insert);
				A.moveNext();
			}
		}
		if(A.index() == -1)
		{
			while(M.index() != -1)
			{
				mE = (Entry)M.get();
				insert = new Entry(mE.col, mE.val);
				added.append(insert);
				M.moveNext();
			}
		}
		while(A.index() != -1 && M.index() != -1)
		{
			aE = (Entry)A.get();
			mE = (Entry)M.get();
			if(aE.col == mE.col)
			{
				double value = aE.val + mE.val;
				if(value != 0)
				{
					insert = new Entry(aE.col, value);
					added.append(insert);
				}
				A.moveNext();
				M.moveNext();		
			}
			else
			{
				while(aE.col < mE.col)
				{
					insert = new Entry(aE.col, aE.val);
					added.append(insert);
					A.moveNext();
					if(A.index() != -1)
					{
						aE = (Entry)A.get();
					}
					else
					{
						break;	
					}
				}
				while(aE.col > mE.col)
				{
					insert = new Entry(mE.col, mE.val);
					added.append(insert);
					M.moveNext();
					if(M.index() != -1)
					{
						mE = (Entry)M.get();
					}
					else
					{
						break;
					}
				}
			}
		}
		if(A.index() != -1 && M.index() != -1)
		{
			aE = (Entry)A.get();
			mE = (Entry)M.get();
			double value = aE.val + mE.val;
			insert = new Entry(aE.col, value);
			added.append(insert);
		}
		if(M.index() == -1)
		{
			while(A.index() != -1)
			{
				aE = (Entry)A.get();
				insert = new Entry(aE.col, aE.val);
				added.append(insert);
				A.moveNext();
			}
		}
		if(A.index() == -1)
		{
			while(M.index() != -1)
			{
				mE = (Entry)M.get();
				insert = new Entry(mE.col, mE.val);
				added.append(insert);
				M.moveNext();
			}
		}
		return added;
	}

	Matrix sub(Matrix M) // returns a new Matrix that is the difference of this Matrix with M
		// pre: getSize() == M.getSize
	{
		Entry aE;
		Entry mE;
		Matrix C = new Matrix(n);
		Matrix A = this;

		if(getSize() == M.getSize())
		{
			for(int i = 1; i <= n; i++)
			{
				C.row[i] = subHelp(A.row[i], M.row[i]);
			}
		}
		return C;
	}

	List subHelp(List A, List M)
	{
		List subtracted = new List();
		Entry aE;
		Entry mE;
		A.moveFront();
		M.moveFront();

		if(A.index() != -1)
		{
			aE = (Entry)A.get();
		}
		if(M.index() != -1)
		{
			mE = (Entry)M.get();
		}
		Entry insert;
		if(M.index() == -1)
		{
			while(A.index() != -1)
			{
				aE = (Entry)A.get();
				insert = new Entry(aE.col, aE.val);
				if(aE.val != 0)
				{
					subtracted.append(insert);
				}
				A.moveNext();
			}
		}
		if(A.index() == -1)
		{
			while(M.index() != -1)
			{
				mE = (Entry)M.get();
				insert = new Entry(mE.col, -mE.val);
				if(mE.val != 0)
				{
					subtracted.append(insert);
				}
				M.moveNext();
			}
		}
		while(A.index() != -1 && M.index() != -1)
		{
			aE = (Entry)A.get();
			mE = (Entry)M.get();

			if(aE.col == mE.col)
			{
				double value = aE.val - mE.val;
				insert = new Entry(aE.col, value);
				if(value != 0)
				{
					subtracted.append(insert);	
				}
				A.moveNext();
				M.moveNext();
			}
			else
			{
				while(aE.col < mE.col)
				{
					insert = new Entry(aE.col, aE.val);
					subtracted.append(insert);
					A.moveNext();
					if(A.index() != -1)
					{
						aE = (Entry)A.get();
					}
					else
					{
						break;
					}
				}
				while(aE.col > mE.col)
				{
					insert = new Entry(mE.col, -mE.val);
					subtracted.append(insert);
					M.moveNext();
					if(M.index() != -1)
					{
						mE = (Entry)M.get();

					}
					else
					{
						break;
					}
				}
			}
		}
		if(A.index() != -1 && M.index() != -1)
		{
			aE = (Entry)A.get();
			mE = (Entry)M.get();
			double value = aE.val - mE.val;
			insert = new Entry(aE.col, value);
			subtracted.append(insert);
		}
		if(M.index() == -1)
		{
			while(A.index() != -1)
			{
				aE = (Entry)A.get();
				insert = new Entry(aE.col, aE.val);
				if(aE.val != 0)
				{
					subtracted.append(insert);
				}
				A.moveNext();
			}
		}
		if(A.index() == -1)
		{
			while(M.index() != -1)
			{
				mE = (Entry)M.get();
				insert = new Entry(mE.col, -mE.val);
				if(mE.val != 0)
				{
					subtracted.append(insert);
				}
				M.moveNext();
			}
		}
		return subtracted;
	}

	Matrix transpose() // returns a new Matrix that is the transpose of this Matrix
	{
		Entry E;
		Matrix M = new Matrix(n);

		for(int i = 1; i <= n; i++)
		{
			row[i].moveFront();
			while(row[i].index() >= 0)
			{
				E = (Entry)row[i].get();
				Entry insert = new Entry(E.col, E.val);
				M.changeEntry(insert.col, i, insert.val);
				row[i].moveNext();
			}
		}
		return M;
	}

	Matrix mult(Matrix M) // returns a new Matrix that is the product of this Matrix with M
		// pre: getSize() == M.getSize()
	{
		Entry E;
		Matrix C = new Matrix(n);
		Matrix A = this;
		Matrix T = M.transpose();

		if(getSize() == M.getSize())
		{
			for(int i = 1; i <= n; i++)
			{
				int col = 1;
				for(int j = 1; j <= n; j++)
				{
					double value = dot(A.row[i], T.row[j]);
					Entry insert = new Entry(col, value);
					C.changeEntry(i, insert.col, insert.val);
					col++;
				}
			}
		}
		return C;
	}

	private static double dot(List P, List Q)
	{	
		double sum = 0;
		Entry pN;
		Entry qN;
		P.moveFront();
		Q.moveFront();

		if(P.index() != -1)
		{
			pN = (Entry)P.get();
		}
		if(Q.index() != -1)
		{
			qN = (Entry)Q.get();
		}
		while(P.index() != -1 && Q.index() != -1)
		{
			pN = (Entry)P.get();
			qN = (Entry)Q.get();

			if(pN.col == qN.col)
			{
				sum += pN.val * qN.val;
				P.moveNext();
				Q.moveNext();
			}
			else
			{
				while(pN.col < qN.col)
				{	
					P.moveNext();
					if(P.index() != -1)
					{
						pN = (Entry)P.get();
					}
					else
					{
						return sum;	
					}
				}
				while(pN.col > qN.col)
				{
					Q.moveNext();
					if(Q.index() != -1)
					{	
						qN = (Entry)Q.get();
					}
					else
					{
						return sum;	
					}
				}
			}
		}
		return sum;	
	}

	// Other functions
	public String toString() // overrrides Object's toString() method
	{
		Entry E;
		String s = "";

		for(int i = 1; i <= n; i++)
		{
			row[i].moveFront();
			if(row[i].index() != -1)
			{
				s += i + ": ";
				while(row[i].index() >= 0)
				{
					E = (Entry)row[i].get();
					s += "(" + E.col + ", " + E.val + ")";
					row[i].moveNext();
					if(row[i].index() != -1)
					{
						s += " ";
					}
				}
				if(i != n)
				{
					s += "\n";
				}
			}
		}		
		return s;	
	}
}

