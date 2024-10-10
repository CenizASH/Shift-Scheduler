Tabs not spaces for indentation

Space after the slashes for comments:
```java
// Explanatory comment
```
Begin comments with an upper-case:
```java
// Explanatory comment
```
Space before the bracket on control flow statements, but not functions:
```java
while (condition) {}
for (;;) {}
if (condition) {}
void function(args) {}
```
Braces on same line:
```java
if (condition) {
	// Code
}
```
`else`-cases follow on the same line:
```java
if (condition) {
	// Code
} else if (condition2) {
	// Code
} else {
	// Code
}
```
If there is code after a closing brace, add a new line in between:
```java
if (condition) {
	// Code
}
// Keep this line empty
// Code here
if (conditionA) {
	if (condition B) {
		// Code
	}
} // This is fine though
```
Classes should be PascalCase, variables should be camelCase, constants should be SCREAM\_CASE
```java
public class MyClass {
	private static final int IMPORTANT_VALUE = 69;
	private int changingVar = 420;
}
```
Spaces around binary operators, but not the unary increment and decrement:
```java
a + b;
b - c;
c * d;
d / e;
a += (b * (c / d));
b <<= 14;
a++;
--e;
```
No more than 1 blank line in a row, under any circumstances.

If code needs to be spread across multiple lines, indent it once instead of trying to align it:
```java
void func() {
	int variable =
		longlongvar1 +
		longlongvar2 +
		longlonglongstmnt;

	manyargs(
		longlongvar1,
		longlongvar2,
		longlonglongstmnt
	);
}
```