public class ATM {
    public static void main(String[] args) {
        UtilCompany testCompany = new UtilCompany();
        Bank testBank = new Bank();
//        testCompany.createUserAccount();
//        System.out.println(testBank.getBalance(470569, testBank.getSAVINGSID()));
        int testval = testCompany.userLogin("username","Tanner", "123");
        System.out.println(testval);
        System.out.println("Hello world!");
    }
}