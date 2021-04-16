# OOP
* **/lab2*** -refactored and improved calculator from [P2P course](https://github.com/ikripaka/P2P-course/tree/main/Calculator/src/com/shpp/p2p/cs/ikripaka/assignment11 "P2Pcourse RPN calculator") 

**Main changes:**
1. add test class CalculatorTest.java
2. delete Communicator.java
3. move functions with what Calculator interacted in Assignment11.java to Parser.java and Calculator.java
4. delete Assignment11.java 

Lab 2 is directly connected with implementing OOP patterns in code
Here represented **Adapter**, **Factory method**

**Adapter** - class _Calculator.java_ 
	this class like "adapter" between user and calculator functions 
**Factory method** - interface _FunctionInterface.java_ and class _FunctionCalculator.java_
	this class holds functions different from ordinary like ("log2","log10","cos","sin","tan","atan") that implements functions that every function has to contain
	
**How to run program**
1. JDK 11
2. junit.jupiter-5.7.0