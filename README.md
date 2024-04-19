HOW IT WORKS:
Our application has implemented all the features detailed in the lab document. We have achieved persistent data by the use of two JSON files. One is called “bankAccounts.json” which stores all the information about a users bank account including checking account balance, savings account balance, etc. The other is called “userInfo.json” that stores the users utilCompany information including their username, password, and account number. This information in the user file is how users can login to their account (they are given the option to login with username OR account number). If the user fails to enter a valid username or password, the program will print out a message saying their info was invalid and then end. After logging in, the user has 9 options:

1: View Bill History
If the user enters 1, they will be displayed with a screen that shows the previous 3 bills paid including information about how much they cost, when they were due, and when they were paid. Per the instructions given in lecture, we have hardcoded information for the transaction history.

2: View Next Bill
If the user enters a 2 they will be shown information about the next bill due, including its amount, when it is due, and how much they have already paid. This way, the user can make multiple payments to the bill rather than paying it all at once. Additionally there is a check in the code to ensure the user doesn’t overpay for a bill. All money that goes towards paying a bill is considered a withdraw (and withdrawn from the users checking account).

3: Deposit
If the user enters 3 they are able to deposit money to an account. They are first prompted to enter which account they would like to deposit to (savings or checking) and then prompted to enter an amount. There is a check to ensure the daily limit isn’t reached before successfully adding the money to their bank account information in the bankAccounts.json file. If the deposit fails, an error message is displayed and the account info in the json file doesn’t change.

4: Withdraw
If the user enters 4 they are able to withdraw money from an account. They are first prompted to enter which account they would like to withdraw from, however, they are only allowed to withdraw from the checking account so if they try to withdraw from savings, it will display an error message. Withdraws are done by checking to make sure that the users account doesn’t go negative and that they don’t exceed the daily limit. If either occurs, corresponding error messages are displayed.

5: Transaction
If the user enters 5 they are able to transfer money between their accounts (savings and checking). They are first prompted to tell which direction they want the transfer to flow (savings->checking or vice versa) and then to enter an amount. There is a check to ensure that neither account will be negative after the transaction and that the daily limits haven’t been exceeded (if they have, it displays an error and will not move the funds). It then prints out “deposit successful” if it works because the deposit function is used for transfers.

6:Check Balance
If the user enters 6 they can check the balance in either of their accounts. The function prompts the user to tell which account they want to read (savings or checking) and then reads the information from the bankAccounts.json file for their account number.

7:new Dawn
If the user enters 7 they call upon a new dawn, and the day count increases. Basically this means a day has passed and all their tallys towards their daily bank account limits reset to 0.

8: logout
If the user enters 8 they will log out of the system, and the program will end.

9: pay bill
If the user enters 9 they can pay part or all of the next bill. The function prompts the user to enter how much they will pay towards the bill, and then, if they have enough money in their checking account and they haven't hit their daily withdraw limit, it will take that money from their account and then add that much onto the “paid” position of the bill. If the bill payment runs into either of those problems, a corresponding message will be displayed to the user, and the transaction won’t go through.

Invalid input handling:
There are many places in the code that take user input, and many of these places require the user to enter an integer. In order to handle improper input, I made a function called getUserInt() which will take the next string from the user and then try to parse it to an integer. If it cannot, it will catch the error and ask them to re-enter the number until they enter a valid input.



Test Cases (Automated Testing): Testing was designed particularly for the Bank class, which contains the methods in order to complete transactions, opening accounts, depositing, withdrawing, and transactions all found within this class.  The test cases were designed to handle exceptions, such as null information, invalid types, exceeding transaction limits, and functionality testing. The referred to accounts can be found in the bankAccounts.json file, which hold’s all respective information for a bank account. 
	
The second test class, JsonFileUtilTest is designed to handle the writing, reading, and data manipulation of the JSON file. These tests were designed with writing, looking for the data, and comparing it to how the JSON file should be displayed. Each method within the JsonFileUtil class was used to create test classes that could adequately test each exception case with null data, data sets, and so on. 

You may run either of these tests by pressing the play button on either the side of the chosen IDE or on the top navigation bar. 

UI testing was done manually, as we did not understand how to create automated test cases from command line prompts and as account numbers were randomly generated. Instead, we documented the errors that were displayed within the prompt, and if the prompt was given invalid information. We also showed when the prompt is given valid information and the respective valid return. The functionality has been tested by the ATM interface working as expected and company util, as it calls sub-methods which were tested with automated tests. 
