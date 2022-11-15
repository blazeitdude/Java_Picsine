# making directory
rm -rf target lib
mkdir target lib

# downloading library
cd lib
curl -O https://repo1.maven.org/maven2/com/beust/jcommander/1.78/jcommander-1.78.jar
curl -O https://repo1.maven.org/maven2/com/diogonunes/JCDP/2.0.3.1/JCDP-2.0.3.1.jar
cd ..

# compiling files
javac -d target -sourcepath src/java -cp lib/JCDP-2.0.3.1.jar:lib/jcommander-1.78.jar:. src/java/edu/school21/printer/*/*.java

# copying resources
cp -R src/resources target

# Copy class-files from jar
jar -xf lib/JCDP-2.0.3.1.jar
jar -xf lib/jcommander-1.78.jar
rm -rf META-INF
mv com target

# making jar archive
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target/ .

# starting program
java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN