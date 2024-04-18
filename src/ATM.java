import java.util.Scanner;

public class ATM {
    static UtilCompany utilCompany = new UtilCompany();
    static Bank bank = new Bank();
    static JsonFileUtil jsonFileUtil = new JsonFileUtil();

    public static void main(String[] args) {
        int curDay = 10;  //start on day 10 for no reason
        int curUsrAccntNum;
        Scanner scanner = new Scanner(System.in); //scanner to take user input
        int usrInt;
        int accntType;  //value for storing account type in bank withdraws, deposits, and transfers
        int amount;     //value for storing amounts in bank withdraws, deposits, and transfers
        String usrString;
        boolean done = false;

        //Ask user to either login or create account
        System.out.println("Select an option: 1) Login with username   2) Login with accountNum    3) Create new Account");
        usrInt = scanner.nextInt();
        while(usrInt != 1 || usrInt != 2 || usrInt != 3){
            System.out.println("INVALID INPUT!");
            System.out.println("Select an option: 1) Login with username   2) Login with accountNum    3) Create new Account");
            usrInt = scanner.nextInt();
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
            System.out.print("Choose an option: (1: view bill history) (2: view next bill) (3: deposit) (4: withdraw) (5: transaction) (6: new dawn) (7:logout)\n > ");
            usrInt = scanner.nextInt();
            switch(usrInt) {
                case 1:
                    utilCompany.checkBillPaymentHistory(curUsrAccntNum);
                    break;

                case 2:
                    utilCompany.checkNextBill(curUsrAccntNum);
                    break;

                case 3:
                    System.out.print("enter an account to deposit [1=savings 2=checking]: ");
                    accntType = scanner.nextInt();
                    System.out.print("enter an ammount to deposit: ");
                    amount = scanner.nextInt();
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
                    accntType = scanner.nextInt();
                    System.out.print("enter an amount to withdraw: ");
                    amount = scanner.nextInt();
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
                    accntType = scanner.nextInt();
                    System.out.print("enter an amount to transfer: ");
                    amount = scanner.nextInt();
                    if(accntType == 1){
                        bank.transfer(curUsrAccntNum, bank.getSAVINGSID(), amount);
                    }else if(accntType == 2){
                        bank.transfer(curUsrAccntNum, bank.getCHECKINGID(), amount);
                    }else{
                        System.out.println("ERROR: invalid input");
                    }

                    break;
                case 6:
                    //TODO: call newDawn() when it is finished

                    break;

                case 7:
                    done = true;
                    System.out.println("Good Bye Cruel World.");
                    break;

                default:
                    System.out.println("ERROR: invalid input");
            }
        }
        return;
    }

    /*
     * function to advance to the next day
     */
    public void newDawn(){
        //TODO: implement this function
        //reset all daily values
        //add 1 to curDay
    }
}