# Check if exactly 1 argument is passed
if [ $# -ne 1 ]; then
  echo "Error: Exactly 1 argument is required. Got $#"
  exit 1
fi

echo "Check if maven is installed"
mvn --versiont
if [ $? -eq 0 ]; then
    echo "maven is installed"
else
    echo "Maven is not installed. Exiting script"
    exit 1
fi

echo ""
echo ""
mvn clean

echo ""
echo ""
mvn compile
if [ $? -eq 0 ]; then
    buildOutput="Build successful"
else
    buildOutput="Build failed"
fi

echo ""
echo ""
mvn test
if [ $? -eq 0 ]; then
    testOutput="Tests passed"
else
    testOutput="Tests failed"
fi

echo ""
echo ""
python email-sender.py "$1" "$buildOutput" "$testOutput"