import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        UtilCompany testCompany = new UtilCompany();
        int testval = testCompany.userLogin("tanner1","123455");
        System.out.println(testval);
//        testCompany.createUserAccount();
        System.out.println("Hello world!");
    }
}