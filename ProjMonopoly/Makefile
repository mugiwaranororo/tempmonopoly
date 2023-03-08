JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java


server:
		$(JC) $(JFLAGS) Proj/Server.java
		java Proj.Server

player:
		$(JC) $(JFLAGS) Proj/Player.java


clean:
		$(RM) *.class