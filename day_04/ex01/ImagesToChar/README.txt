# making directory
rm -rf target
mkdir target

# compiling files
javac -d target src/java/edu/school21/printer/*/*.java

# copying resources
cp -R src/resources target

# making jar archive
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

# starting program
java -jar target/images-to-chars-printer.jar . 0
