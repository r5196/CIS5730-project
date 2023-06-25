# Phase 3 writeup
## Q1
The Additional Tasks that you would like graded for this phase (**3.1-3.4**).

## Q2
For each task you completed in this phase, a brief description of the changes you made to the provided code, e.g. the names of any classes or methods that were changed, new methods that were created, etc.
1. **Task 3.1 adds createOrg() method in data manager class. The task also changes main() method in userinterface class. The task finally adds /creatOrg endpoint in api.js. Task 3.1 checks if any field is blank in createOrg() method in data manager classs. In the case, the method will throw exception and indicate the field is invalid. The task checks if the org login already exists using /createOrg endpoint in api.js.**
2. **Task 3.2 adds updateOrg method in data manager class. The task also changes main() method in user interface by adding change password method. Finally the task adds /updateOrg endpoint in api.js.**



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
**Yao Jiang did tasks 3.3.
Renyu did tasks 3.1 and 3.2. 
Yuqi Wang did tasks 3.4. All supports are in the github project as comments and linked pull requests in issues.**
