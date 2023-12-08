import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private final CoinAcceptor coinAcceptor;
    private final Payment payment;

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        payment = new Payment();
        choosePaymentMethod();
        coinAcceptor = new CoinAcceptor(payment.getAmount());
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        print("Монет на сумму: " + coinAcceptor.getAmount());

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);

        if (coinAcceptor.getAmount() > 0) {
            print("Монет на сумму: " + coinAcceptor.getAmount());
            print("Желаете добавить еще деньги? (y/n)");
            String input = fromConsole().trim().toLowerCase();
            if (input.equals("y")) {
                choosePaymentMethod();
                coinAcceptor.setAmount(coinAcceptor.getAmount() + payment.getAmount());
            } else if (input.equals("n")) {
                isExit = true;
            } else {
                print("Недопустимая буква. Попрбуйте еще раз.");
                startSimulation();
            }
        }
    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (coinAcceptor.getAmount() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    coinAcceptor.setAmount(coinAcceptor.getAmount() - products.get(i).getPrice());
                    print("Вы купили " + products.get(i).getName());
                    break;
                } else if ("h".equalsIgnoreCase(action)) {
                    isExit = true;
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            print("Недопустимая буква. Попрбуйте еще раз.");
            chooseAction(products);
        }


    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void choosePaymentMethod() {
        choosePaymentMethodAction();
        String method = fromConsole().trim();

        try {
            if (method.equals("k")) {
                payByCard();
            } else if (method.equals("m")) {
                payByCash();
            } else {
                print("Недопустимая буква. Попробуйте еще раз.");
                choosePaymentMethod();
            }
        } catch (NumberFormatException e) {
            print("Недопустимая сумма. Попробуйте еще раз.");
            choosePaymentMethod();
        }
    }
    private void choosePaymentMethodAction(){
        print("Выберите метнод оплаты: ");
        print("k - карта | m - монетки");
    }

    private void payByCash(){
        try {
            print("Закиньте монетки: ");
            int amount = Integer.parseInt(fromConsole().trim());
            payment.setAmount(payment.getAmount() + amount);
        } catch (NumberFormatException e) {
            print("Недопустимая сумма. Попробуйте еще раз.");
            payByCash();
        }
    }

    private void payByCard(){
        try {
            print("Введите номер карты:");
            String cardNumber = fromConsole().trim();
            payment.setCardNumber(cardNumber);
            print("Введите пароль:");
            String password = fromConsole().trim();
            payment.setPassword(password);
            print("Выберите нужную сумму для пополнения :");
            String input = fromConsole().trim();
            int amount = Integer.parseInt(input);
            payment.setAmount(payment.getAmount() + amount);
        }catch (NumberFormatException e) {
            print("Недопустимая сумма. Попробуйте еще раз.");
            payByCard();
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
