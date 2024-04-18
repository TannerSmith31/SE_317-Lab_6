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

        //TODO: finish main atm functionality
        //start a while loop that runs until you tell it to quit
        //Things the user can do
        //> pay bills, check bill history, check next bill, make transfer between accounts, make withdrawls, make deposits, go to next day
        //All these functions have methods that can be used to accomplish them



//        UtilCompany testCompany = new UtilCompany();
//        Bank testBank = new Bank();
////        testCompany.createUserAccount();
////        System.out.println(testBank.getBalance(132772, testBank.getSAVINGSID()));
////        testCompany.createUserAccount();
////        int testval = testCompany.userLogin("username","Tanner", "123");
////        System.out.println(testval);
//
////        testBank.deposit(testBank.getFILENAME(), 132772, testBank.getCHECKINGID(), 3000);
//        testCompany.checkNextBillPayment(934493);
//        testBank.deposit(testBank.getFILENAME(), 934493, testBank.getCHECKINGID(), 500);
//        testCompany.makeBillPayment(934493, 10);
    }

    public void newDawn(){
        //TODO: implement this function
        //reset all daily values
        //add 1 to curDay
    }
}