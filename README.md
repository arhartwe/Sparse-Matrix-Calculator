# Sparse-Matrix-Calculator

This program takes a text file input in order to build two NxN matrices
that have matrix operations performed on them that are printed in 
a specified output file. These operations include printing the matrices,
scalar multiplying them, adding them, sutracting them, transposing them,
and multipling them. 

The necessary formatting of the input file is as follows:

3 9 5  
  
1 1 1.0  
1 2 2.0  
1 3 3.0  
2 1 4.0  
2 2 5.0  
2 3 6.0  
3 1 7.0  
3 2 8.0  
3 3 9.0  
  
1 1 1.0  
1 3 1.0  
3 1 1.0  
3 2 1.0  
3 3 1.0  
  
Where the first line specifies the matrices size, then the first matrix's number of non-zero entries,
followed by the second matrix's number of non-zero entries.

The following lines specify the row, column, and value to be stored.
An empty line seperates the first matrix's entries from the second's.

Files Included:

1) Sparse.java			- Retrieves input file and prints matrix operations to output file.
2) Matrix.java			- Constructs and performs matrix operations on matrix of given size.
3) List.java			  - Creates a list that stores the Entries (column and matrix values) of Matrix.
4) Makefile			    - Compiles all necessary executables
5) README           - Specifies programs function, included files, and purpose of files.
