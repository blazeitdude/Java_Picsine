mkdir target

find . -type f -name "*.java" > classes

javac -sourcepath src/java -d target @classes

rm -rf classes

java -classpath target edu.school21.printer.app.Program . 0 ./it.bmp