package model;

public class PaymentByCard {
    String cardNumber;
    String password;
    int amount;

    public PaymentByCard(String cardNumber, String password){
        this.cardNumber = cardNumber;
        this.password = password;
        this.amount = 0;
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
