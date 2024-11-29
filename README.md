# Java-Battleship

The game, Battleship, written in Java.

## Installation

1. Make sure you've installed all requirements
2. Clone this repository:
  `git clone https://github.com/nisotiya/Java-Battleship`
3. Change into the directory of the project
4. Use `javac *.java` to compile the source code
5. To run the program: `java Battleship`

## Unit Tests
1. Download Maven to run unit tests
2. Verify the download by running `mvn --version`
3. Run the unit tests using `mvn test`

## Build Test Suite and get email
1. Create a `.credentials` file in the repo
	1. Add gmail username & gmail app-password.
       1. You will have to create an "app password". The regular google account password won't work because of security.
   ```
   myemail@gmail.com
   app-passowrd
   ``` 
2. Run `bash buildTestSuite.sh <to-email>` to get the email status

## Usage

Here's a short explanation on how to use **Java-Battleship**:

* Use `java Battleship` to run the code, after following the installation steps.
* When the application starts, you, the player, sets up your board by typing in the on-screen coordinates.
* Then, the computer makes its own board and you play against it by guessing its ships' locations.
	* NOTE: The computer's board is printed on screen, just so the game can go faster.
* The program ends when one side beats the other.

## Contributing

1. Fork it
2. Create your feature branch: `git checkout -b feature/my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin feature/my-new-feature`
5. Submit a pull request

## Requirements / Dependencies

* JDK
	* javac
* Maven

## Version

1.0.0

## License

The MIT License (MIT)

Copyright (c) 2015 Yuval Marcus

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.