# Phase 1 writeup
## Q1
The Additional Tasks that you would like graded for this phase (**1.1 - 1.3, 1.7 - 1.9**).

## Q2
1. **Task 1.1 adds more unit tests to DataManager_createFund_Tests.java to prevent any invalid inputs or the circumstances that would lead to the failure of creating funds. It also adds two files named DataManager_getContributorName_Test.java and DataManager_attemptLogin_Test.java. These are the unit tests for the getContributorName and attemptLogin method in the DataManager.java file.**
2. **Task 1.2 changes the attemptLogin() method in DataManager class. It changes “descrption” to “description” . It also changes the createFund() method. It adds if conditions to handle input invalidation.**
3. **Task 1.3 adds more codes in the displayFund function in the UserInterface.java. When iterate through the donations, totalDonation variable and percentage variable are created to record the total donation amount and percentage of target that has been achieved. Then print out the total donation and percentage.**
4. **Task 1.7 changes the start method and createFund method in UserInterface.java. The start method now tests if the entered fund number is valid, and will throw a NumberFormatException if not. The createFund method now tests if the entered fund target is valid, and will throw a NumberFormatException as well if it’s not. Overall, all the changes are made so that the code will have better error handling.**
5. **Task 1.8 changes the attemptLogin() method in DataManager class. The method now throws IllegalStateException "an error occurs in communicating with the server" if it is not a failed login. For example, if the response is null, instead of showing failed login, it throws an exception. The task also changes the main() method in user interface class to handle failed login and exceptions.**
6. **Task 1.9 changes data formats for organization app. Adds a dataTransfer(String month) methind and changes getDate() method in Donation.java. getData() method uses substring() to extract corresponding day, month, and year from the original date from a Donation object, then dataTransfer(String month) is used to Convert the numerical representation of the month into a textual description.**

## Q3
A description of any bugs that you found and fixed in Task 1.2 (and also in 1.4 if you chose to do it)
1. **CreateFund() method: In testInvalidInputCreationWithNull(), when inputs are null, it should return null. To deal with this, at the beginning of the method body, add three if conditions to check if they are null.**
2. **In testInvalidInputCreationWithEmptyString() and In testInvalidInputCreationWithNegativeNumber(), when input strings are empty or input target number is negative , it should return null. To deal with this, at the beginning of the method body, add three if conditions to check these.**
3. **attemptLogin() method: In testSuccessfulLogin(), it fails when checking assertEquals("This is a test organization", org.getDescription()). There is a typo in the original code. Changing “descrption” to “description” when using get() method.**

## Q4
Any known bugs or other issues with the tasks you attempted in this phase.
1. **attemptLogin() method: In testSuccessfulLogin(), it fails when checking assertEquals("This is a test organization", org.getDescription()). There is a typo in the original code. Changing “descrption” to “description” when using get() method.**
2. **When calculating the percentage of target that has been achieved, a long type variable is created, but when printing out the result, 0.6 will be cast into 0.0 automatically, so I changed it to double type and use NumberFormat to print it out in % format.** 

## Q5
A brief but specific description of each team member’s contributions, including the task numbers that they worked on. Please do not simply write “all members contributed equally to all tasks” since we know that’s not really the case. 😁
**Yao Jiang did tasks 1.1 and 1.7.
Renyu did tasks 1.2 and 1.8. 
Yuqi Wang did tasks 1.3 and 1.9**
