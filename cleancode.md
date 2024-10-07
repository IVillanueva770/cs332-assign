## Chapter 1: Clean Code

- Small things matter
- Knowledge + work
- coding can bee understood as the hability to _specificate_ requirements
- Adding people to the team is probably not a solution. You will need to invest time giving context and etc.
- Its coders work to ensure **code quality**

## Chapter 2: Meaningful names

- Intention revealing names
- _why exist, what does, how's used_
- smart < professional
- class = noun
- method = verb
- try to use one word per concept

## Chapter 3: Functions

- small functions
- your functions should tell a story
- Functions should do only one thing, and do it well
- Dont mix levels of abstraction
- We want to read as the code is a set of "==**to do x**==, we do y" paragraphs
- OCP - SRP (SOLID Principles)
- Long names are ok and also useful, more than descriptive comments
- Arguments: try to avoid functions with 3 or more args
  - Don't pass booleans to a monadyc (1 arg) functions.
  - Dyadic function's arguments should have a natural cohesion between them
  - Try to reduce the number of arguments grouping them into classes when the cohesion exists
  - Variable ammount of the same type of arguments can be a List
  - Try to communicate order of args with the function's name
  - Output arguments should be replaced with class methods that changes some state of the object
  - Error handling functions should only do that
  - _First write down what comes to your mind, then massage what you wrote until is readable_.

## Chapter 4: Comments

- Code is the only source of truly accurate information.
- You should use as less comments as possible
- Sometimes is ok to use comments to explain your intention if you're not using the best or more natural approach for something
- Be careful with spreading misinformation on comments
- Explain code that appears near your comment, use local context.

## Chapter 7: Error Handling

- Its better to use exceptions rather than return codes (flags)
- Try to separate the error handlers and the functional code:

```
public void _upper_function_(){
	try{
		try_function_(); //this instead of a catch for every error possible flag
	} catch (Error e){
		logger(e)}
}

public void try_function_(){
	...
	//normal principal function behaviour
	...
	public getHandle() {
		throw _new_exception_ //this works with the try in the upper function
	}
}
```

- Create informative error messages. Mention the operation that failed
- Instead of writing repeated code for handling the type of the error, use a wrapper to centralize it.
- Dont return null. Return either Special Cases or use a wrap class.
- return Empty list > return Null.
- Dont pass Null neither.

## Chapter 9: Unit Tests

- TDD Laws:
  1.  You may not write production code until you have written a failing unit test.
  2.  You may not write more of a unit test than is sufficient to fail, and not compiling is failing.
  3.  You may not write more production code than is sufficient to pass the currently failing test.
- Tests should evolve with the code. Dirty tests = No tests because it takes more time to understand them.
- Without tests every change is a possible bug.
- Clean tests: readability. Clarity, simplicity and Density of expression
- BUILD-OPERATE-CHECK Pattern
- Minimice number of assets per test
- One concept per question.
- Fast-Independant-Repeteable-Self validating-Timely

## Chapter 10: Classes

- Private Functions should be after the public functions that uses it.
- Small classes: we count with responsibilities.
- SRP
- Cohesion: the methods should work with the variables of the class.
- When classes loses cohesion split them
