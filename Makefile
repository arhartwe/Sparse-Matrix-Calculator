#--------------------------------------------------------------------
# Makefile for CMPS101 pa3
# Creates an executable jar file called Sparse.
#
# Name			: Andrew Hartwell
# cruzID		: arhartwe
# Assignment		: pa3
#--------------------------------------------------------------------

MAINCLASS 	= Sparse
JAVAC 		= javac
JAVASRC 	= $(wildcard *.java)
SOURCES 	= $(JAVASRC) Makefile README
CLASSES 	= $(patsubst %.java, %.class, $(JAVASRC))
JARCLASSES 	= $(patsubst %.class, %*.class, $(CLASSES))
JARFILE 	= $(MAINCLASS)

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(JARCLASSES)
	chmod +x $(JARFILE)
	rm Manifest

%.class: %.java
	$(JAVAC) $<

clean:
	rm -f *.class $(JARFILE)
  	