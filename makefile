JFLAGS = -cp
JC = javac
RM = rm
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	src/Abilities/*.java/ \
	src/Dictionaries/*.java/ \
	src/Classes/*.java/ \
	src/Equipment/*.java/ \
	src/Items/*.java/ \
	src/Places/*.java/ \
	src/Misc/*.java/ \
	src/Combat/*.java/ \
	src/driver.java/ \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.MF 