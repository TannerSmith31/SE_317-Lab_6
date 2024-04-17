public class Bank {
    //instance variables
    private String savingsFilename = "savings.json";  //text file to store savings account information for users //TODO: maybe use JSON file
    private String checkingFilename = "checking.json"; //text file to store checking account information for users //TODO: maybe use JSON file

    public Bank(){
        //constructor
    }

    /*
     * function to create a new savings or checking account for a user and store the info in the savings account text file
     * Pass in the savingsFilename as the filename parameter to add a savings account
     * Pass in the checkingFilename as the filename parameter to add a checking account
     * throws an error if the account you are trying to create already exists
     */
    public void createAccount(int accountNumber, String filename){
        //TODO: implement this function
        //open the account file (pass in savings filename or checking filename)
        //if the account number already exists throw an error
        //otherwise create an entry for that account number
        //initialize the balance as 0
    }

    /*
     * Function to get the balance in a users account based on their account number
     * Pass in the savingsFilename as the filename parameter to read from savings
     * Pass in the checkingFilename as the filename parameter to read from savings
     * Throws an error if the user doesn't have a savings account set up
     */
    public int getSavingsBalance(int accountNumber, String filename){
        //TODO: implement this function
        //open account file
        //see if an account exists with the given account number
        //if not throw an error
        //if so, return the balance

        return 0;
    }

    /*
     * Function to update the value in the savings account. Amount will be added (or subtracted if negative) to existing balance
     * Throws an error if user doesn't have a savings account
     * Throws error if will cause the account balance to go negative
     * TODO: look at how else this can throw errors (from PDF)
     */
    public void updateSavingsBalance(int accountNumber, int ammount) {
        //TODO: implement this function
        //open savings account file
        //see if savings account exists with given number
        //if not throw an error
        //if so, add the given ammount to the balance and save it to the file
    }

    /*
     * Function to update the value in the savings account. Ammount will be added (or subtracted if negative) to existing balance
     * Throws an error if user doesn't have a savings account
     * Throws error if will cause the account balance to go negative
     * TODO: look at how else this can throw errors (from PDF)
     */
    public void updateCheckingBalance(int accountNumber, int amount){
        //TODO: implement this function
        //open checking account file
        //see if checking account exists with given number
        //if not throw an error
        //Check for other error conditions
        //if all is good, add the given amount to the balance and save it to the file
    }




}
