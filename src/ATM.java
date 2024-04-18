public class ATM {
    public static void main(String[] args) {
        UtilCompany testCompany = new UtilCompany();
        Bank testBank = new Bank();
//        testCompany.createUserAccount();
//        System.out.println(testBank.getBalance(132772, testBank.getSAVINGSID()));
//        int testval = testCompany.userLogin("username","Tanner", "123");
//        System.out.println(testval);

        testBank.deposit(testBank.getFILENAME(), 132772, testBank.getCHECKINGID(), 3000);
        System.out.println("Hello world!");
    }
}