import java.util.Scanner;

public class ATM {
    static UtilCompany utilCompany = new UtilCompany();
    static Bank bank = new Bank();
    static JsonFileUtil jsonFileUtil = new JsonFileUtil();

    static int curDay;

    public static void main(String[] args) {
        curDay = 10;  //start on day 10 for no reason
        int curUsrAccntNum;
        Scanner scanner = new Scanner(System.in); //scanner to take user input
        int usrInt;
        int accntType;  //value for storing account type in bank withdraws, deposits, and transfers
        int amount;     //value for storing amounts in bank withdraws, deposits, and transfers
        String usrString;
        boolean done = false;

        //Ask user to either login or create account
        System.out.print("Select an option: 1) Login with username   2) Login with accountNum    3) Create new Account \n> ");
        usrInt = getUsrInt(scanner);
        while(usrInt != 1 && usrInt != 2 && usrInt != 3){
            System.out.println("INVALID INPUT!");
            System.out.print("Select an option: 1) Login with username   2) Login with accountNum    3) Create new Account \n> ");
            usrInt = getUsrInt(scanner);
        }

        //NOTE: you only get one shot to input username/accountNum and password correctly
        if(usrInt == 1){
            System.out.print("Please enter Username: ");
            String username = scanner.nextLine();
            System.out.print("Please enter Password: ");
            String password = scanner.nextLine();
            int loginResult = utilCompany.userLogin("username", username, password);
            if(loginResult == -2){
                System.out.println("Username doesn't exist");
                return;
            }else if(loginResult == -1){
                System.out.println("Incorrect Password for username");
                return;
            }else if(loginResult != 0){
                System.out.println("ERROR IN LOGIN");
                return;
            }
            curUsrAccntNum = Integer.parseInt(jsonFileUtil.getJsonMember(utilCompany.getFILENAME(), "username", username, "accountNumber"));
            System.out.println("Login Successful");

        }else if(usrInt == 2){
            System.out.print("Please enter accountNumber: ");
            String accountNumber = scanner.nextLine();
            System.out.print("Please enter Password: ");
            String password = scanner.nextLine();
            int loginResult = utilCompany.userLogin("accountNumber", accountNumber, password);
            if(loginResult == -2){
                System.out.println("accountNumber doesn't exist");
                return;
            }else if(loginResult == -1){
                System.out.println("Incorrect Password for accountNumber");
                return;
            }else if(loginResult != 0){
                System.out.println("ERROR IN LOGIN");
                return;
            }
            curUsrAccntNum = Integer.parseInt(accountNumber);
            System.out.println("Login Successful");
        }else if(usrInt ==3){
            curUsrAccntNum = utilCompany.createUserAccount();
        }else{
            System.out.println("ERROR invalid user input to first option selection");
            return;
        }

        done = false;

        //loop for main ATM functionality
        while(!done){
            System.out.print("\nChoose an option: (1: view bill history) (2: view next bill) (3: deposit) (4: withdraw) (5: transaction) (6: check Balance) (7: new dawn) (8:logout)\n> ");
            usrInt = getUsrInt(scanner);
            switch(usrInt) {
                case 1:
                    utilCompany.checkBillPaymentHistory(curUsrAccntNum);
                    break;

                case 2:
                    utilCompany.checkNextBill(curUsrAccntNum);
                    break;

                case 3:
                    System.out.print("enter an account to deposit [1=savings 2=checking]: ");
                    accntType = getUsrInt(scanner);
                    System.out.print("enter an ammount to deposit: ");
                    amount = getUsrInt(scanner);
                    if(accntType == 1){
                        bank.deposit(curUsrAccntNum, bank.getSAVINGSID(), amount);
                    }else if(accntType == 2){
                        bank.deposit(curUsrAccntNum, bank.getCHECKINGID(), amount);
                    }else{
                        System.out.println("ERROR: invalid input");
                    }
                    break;

                case 4:
                    System.out.print("enter an account to withdraw [1=savings 2=checking]: ");
                    accntType = getUsrInt(scanner);
                    System.out.print("enter an amount to withdraw: ");
                    amount = getUsrInt(scanner);
                    if(accntType == 1){
                        bank.withdraw(curUsrAccntNum, bank.getSAVINGSID(), amount);
                    }else if(accntType == 2){
                        bank.withdraw(curUsrAccntNum, bank.getCHECKINGID(), amount);
                    }else{
                        System.out.println("ERROR: invalid input");
                    }
                    break;

                case 5:
                    System.out.print("enter an transfer type [1=savings->checking 2=checking->savings]: ");
                    accntType = getUsrInt(scanner);
                    System.out.print("enter an amount to transfer: ");
                    amount = getUsrInt(scanner);
                    if(accntType == 1){
                        bank.transfer(curUsrAccntNum, bank.getSAVINGSID(), amount);
                    }else if(accntType == 2){
                        bank.transfer(curUsrAccntNum, bank.getCHECKINGID(), amount);
                    }else{
                        System.out.println("ERROR: invalid input");
                    }
                    break;

                case 6:
                    System.out.print("enter account to check balance [1=savings  2=checking]: ");
                    accntType = getUsrInt(scanner);
                    if(accntType == 1){
                        amount = bank.getBalance(curUsrAccntNum, bank.getSAVINGSID());
                        System.out.println("savings balance: " + amount);
                    }else if(accntType == 2){
                        amount = bank.getBalance(curUsrAccntNum, bank.getCHECKINGID());
                        System.out.println("checking balance: " + amount);
                    }else{
                        System.out.println("ERROR: invalid input");
                    }
                    break;

                case 7:
                    newDawn();
                    break;

                case 8:
                    done = true;
                    System.out.println("Good Bye Cruel World.");
                    break;

                default:
                    System.out.println("ERROR: invalid input");
            }
        }
    }

    /*
     * function to advance to the next day
     */
    public static void newDawn(){
        bank.resetDailyValues(); //reset daily values for all bank accounts
        curDay+= 1; //add 1 to day count
        System.out.println("current day: " + curDay);
    }

    /*
     * function to take user integer input and handle exceptions
     */
    public static int getUsrInt(Scanner scanner){
        int returnVal = 0;
        String usrIn;
        boolean inputValid = false;

        while(!inputValid) {
            usrIn = scanner.nextLine();

            try {
                returnVal = Integer.parseInt(usrIn);
                inputValid = true;
            } catch (Exception e) {
                System.out.print("INVALID INPUT Try again: ");
            }
        }
        return returnVal;
    }
}