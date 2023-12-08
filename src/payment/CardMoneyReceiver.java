package payment;

import java.util.Scanner;

public class CardMoneyReceiver implements Payment {
    private int cardNumber;
    private int currentAmount;

    @Override
    public void initialize() {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("Введите номер карты (4 цифры): ");
            if (sc.hasNextInt()) {
                cardNumber = sc.nextInt();
                sc.nextLine();
                if (cardNumber >= 1000 && cardNumber <= 9999) {
                    System.out.println("Номер карты принят. " + cardNumber);
                    break;
                } else {
                    System.out.println("Неверный формат номера карты. Повторите ввод.");
                }
            } else {
                sc.nextLine();
                System.out.println("Введите корректное число. Повторите ввод.");
            }
        } while (true);
    }

    @Override
    public void acceptMoney(int amount) {
        currentAmount += amount;
        if (amount >= 0) {
            System.out.println("Вы пополнили карту на " + amount);
        } else {
            System.out.println("У вас с карты снялось - " + amount);
        }
    }

    @Override
    public int getCurrentAmount() {
        return currentAmount;
    }
}
