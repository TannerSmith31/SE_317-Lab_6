import com.google.gson.JsonObject;

public class BankAccount {
    private int accountNumber;
    private int savingsBalance;
    private int checkingBalance;
    private int dailySavingsDeposit;
    private int dailyCheckingDeposit;
    private int dailySavingsTransfer;
    private int dailyCheckingWithdraw;

    public BankAccount(int accountNumber){
        this.accountNumber = accountNumber;
        this.savingsBalance = 0;
        this.checkingBalance = 0;
        this.dailyCheckingDeposit = 0;
        this.dailySavingsDeposit = 0;
        this.dailyCheckingWithdraw = 0;
        this.dailySavingsTransfer = 0;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getSavingsBalance() {
        return savingsBalance;
    }

    public void setSavingsBalance(int savingsBalance) {
        this.savingsBalance = savingsBalance;
    }

    public int getCheckingBalance() {
        return checkingBalance;
    }

    public void setCheckingBalance(int checkingBalance) {
        this.checkingBalance = checkingBalance;
    }

    public int getDailySavingsDeposit() {
        return dailySavingsDeposit;
    }

    public void setDailySavingsDeposit(int dailySavingsDeposit) {
        this.dailySavingsDeposit = dailySavingsDeposit;
    }

    public int getDailyCheckingDeposit() {
        return dailyCheckingDeposit;
    }

    public void setDailyCheckingDeposit(int dailyCheckingDeposit) {
        this.dailyCheckingDeposit = dailyCheckingDeposit;
    }

    public int getDailyCheckingWithdraw() {
        return dailyCheckingWithdraw;
    }

    public void setDailyCheckingWithdraw(int dailyCheckingWithdraw) {
        this.dailyCheckingWithdraw = dailyCheckingWithdraw;
    }

    public int getDailySavingsTransfer() {
        return dailySavingsTransfer;
    }

    public void setDailySavingsTransfer(int dailySavingsTransfer) {
        this.dailySavingsTransfer = dailySavingsTransfer;
    }

    public JsonObject toJsonObject(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("accountNumber", accountNumber);
        jsonObject.addProperty("savingsBalance", savingsBalance);
        jsonObject.addProperty("checkingBalance", checkingBalance);
        jsonObject.addProperty("dailySavingsDeposit", dailySavingsDeposit);
        jsonObject.addProperty("dailyCheckingDeposit", dailyCheckingDeposit);
        jsonObject.addProperty("dailyCheckingWithdraw", dailyCheckingWithdraw);
        jsonObject.addProperty("dailySavingsTransfer", dailySavingsTransfer);
        return jsonObject;
    }

}
