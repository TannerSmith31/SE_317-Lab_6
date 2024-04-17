public class UtilCompany {
    //instance variables
    String loginFilename = "utilityFile.json"; //TODO: maybe use a json file

    public UtilCompany(){
        //constructor
    }

    /*
     * Function to create a new user account for the utility company / bank.
     * Takes a username and a password for the new account and stores them in the utility file
     */
    public void createUserAccount(String username, String password){
        //TODO: implement function
        //see if username already exists in utility file
        //if so, return error
        //otherwise add the new username and password to the utility file
        //create a random 6 digit number to be their account number and store it with the username and password
        //return
    }

    /*
     * Takes a username and password and checks the utility File to see if the data matches
     * returns 0 if info matches, 1 if username exists but password doesn't match, 2 if username doesn't exist, 3 otherwise
     */
    public void userLogin(String username, String password){
        //TODO: implement function
        //open utility file
        //see if the username exists
        //if not return 3
        //else see if the password matches the username
        //if not return 2
        //otherwise if password matches username return 0 (success)
    }

    /*
     * function to check a users bill payment history
     */
    public void checkBillPaymentHistory(int accountNumber){
        //TODO: implement function
        //open utility file
        //read the bill payment history stored and display it to the user
    }

    /*
     * function to check a users next bill payment
     */
    public void checkNextBillPayment(int accountNumber){
        //TODO: implement function
        //open up the utility file
        //read what the next bill payment is
    }

    /*
     * makes a bill payment from the users checking account
     * TODO: see if you have to pay the bill all at once or if there is an option to pay part of it
     */
    public void makeBillPayment(int accountNumber, int amount){
        //TODO: implement function
        //get the next bill that the user has to pay
        //try to pay the amount to it
        //if the amount is too much, send message to user?
        //if not enough money in account, throw error (the transaction function will do this and maybe I catch it here and display to user?
        //if bill gets fully paid, generate a new next bill payment? or are all of them generated?
    }
}
