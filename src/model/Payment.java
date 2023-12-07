package model;

import java.util.Scanner;

public class Payment {
    private static final String pinCode = "0000";

    public void paymentCard(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите PIN-код для оплаты: ");
        String pin = scanner.nextLine();

        if (pin.equals(pinCode)) {
            System.out.println("Оплата прошла успешно!");
        } else {
            System.out.println("Неверный PIN-код!");
            paymentCard();
        }
    }









}



