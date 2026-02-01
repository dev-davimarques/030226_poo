package entities;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

import entities.enums.MenuOption;

public class CreditInquiry {

    private final static MenuOption[] choices = MenuOption.values();

    public static void main(String[] args) {
        MenuOption accountType = getRequest();

        while (accountType != MenuOption.END) {
            switch (accountType) {
                case ZERO_BALANCE:
                    System.out.printf("%nAccounts with zero balances:%n");
                    break;
                case CREDIT_BALANCE:
                    System.out.printf("%nAccounts with credit balances:%n");
                    break;
                case DEBIT_BALANCE:
                    System.out.printf("%nAccounts with debit balances:%n");
                    break;
            }
            readRecords(accountType);
            accountType = getRequest();
        }
    }

    // obtém a solicitação do usuário
    private static MenuOption getRequest(){
        int request = 4;

        System.out.printf("%nEnter request%n%s%n%s%n%s%n%s%n",
            " 1 - List accounts with zero balances",
            " 2 - List accounts with credit balances",
            " 3 - List accounts with debit balances",
            " 4 - Terminate program"
        );

        try {
            Scanner input = new Scanner(System.in);
            do {
                System.out.printf("%n? ");
                request = input.nextInt();
            } while ((request < 1) || (request > 4));
        } catch (NoSuchElementException noSuchElementException) {
            System.err.println("Invalid input. Terminating.");
        }
        return choices[request - 1];
    }

    // lê registros de arquivo e exibe somente os registros do tipo apropriado
    private static void readRecords (MenuOption accountType){
        try (Scanner input = new Scanner(Paths.get("/home/davimarques/github-projects/030226_poo/src/entities/Clients.txt"))) {
            while (input.hasNext()){
                int accountNumber = input.nextInt();
                String firstName = input.next();
                String lastName = input.next();
                double balance = input.nextDouble();

                if(shouldDisplay(accountType, balance)){
                    System.out.printf("%-10d%-12s%-12s%10.2f%n",accountNumber, firstName, lastName, balance);
                } else {
                    input.nextLine();
                }
            }
        } catch (NoSuchElementException | IllegalStateException | IOException e) {
            System.err.println("Erro processing file. Terminating");
            System.exit(1);
        }
    }
    
    // utiliza o tipo de registro para determinar se registro deve ser exibido
    private static boolean shouldDisplay(MenuOption accountType, double balance) {

    if (accountType == MenuOption.CREDIT_BALANCE && balance > 0) {
        return true;
    }

    if (accountType == MenuOption.DEBIT_BALANCE && balance < 0) {
        return true;
    }

    if (accountType == MenuOption.ZERO_BALANCE && Math.abs(balance) < 0.0001) {
        return true;
    }

    return false;
}



}
