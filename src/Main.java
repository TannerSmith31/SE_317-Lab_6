import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        UtilCompany testCompany = new UtilCompany();
        Bank testBank = new Bank();
        System.out.println(testBank.getAccountBalance(333233, testBank.getSAVINGS_FILENAME()));
//        int testval = testCompany.userLogin("tanner1","123455");
//        System.out.println(testval);
//        testCompany.createUserAccount();
        System.out.println("Hello world!");
    }
}