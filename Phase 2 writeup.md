# Phase 2 writeup
## Q1
The Additional Tasks that you would like graded for this phase (**2.1 - 2.3, 2.7 - 2.9**).

## Q2
1. **Task 2.2 change each method in data manager file to pass robustness test cases. The task also requirs changes to orignial tests in phase 1. Finally the userinterface is changed to make UI display meaningful error message. After message, the user has the option of retrying the operation. This involes changes of each operation in userinterface class.**
2. **Task 2.8 changes the start() method in user interface class. It has the option of logging out when variable option is set to -1. It also changes the way of starting app in main() method. This is in Q4. The change asks you to log back in.**


## Q3
Any known bugs or other issues with the tasks you attempted in this phase.

## Q4
Instructions on how to start each app, if you changed anything from the original version of the code, e.g. the name of the Java main class or JavaScript entry point, arguments to the programs, etc. If you did not change anything, you may omit this.

- **Before, to run the program, you should run the UserInterface class, and pass the login ID and password that you created for an organization via the Admin app as the runtime arguments to the main method.**
```
	public static void main(String[] args) {
		
		DataManager ds = new DataManager(new WebClient("localhost", 3001));
		
		String login = args[0];
		String password = args[1];
```

- **Now, to run the program, you should run the UserInterface class, but do not need to pass the login ID and password as the runtime arguments to the main method. The program will now ask you to enter the login ID and password that you created for an organization via the Admin app after the program runs.**
```
	public static void main(String[] args) {
		
		DataManager ds = new DataManager(new WebClient("localhost", 3001));
		Scanner in = new Scanner(System.in);

		while (true) {
			System.out.print("Enter your login: ");
			String login = in.nextLine().trim();
			System.out.print("Enter your password: ");
			String password = in.nextLine().trim();
```


## Q5
A brief but specific description of each team member’s contributions, including the task numbers that they worked on. Please do not simply write “all members contributed equally to all tasks” since we know that’s not really the case. 😁
**Yao Jiang did tasks 2.1 and 2.7.
Renyu did tasks 2.2 and 2.8. 
Yuqi Wang did tasks 2.3 and 2.9**
