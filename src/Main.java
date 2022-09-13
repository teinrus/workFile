import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException, ClassNotFoundException {
// создание статического массива
        List<String> products = List.of("Хлеб", "Яблоки", "Молоко");
        List<Integer> prices = List.of(100, 200, 300);
        int[] currentPrice = new int[3];
        int[] countProduct = new int[3];


        Basket baske = new Basket(products, prices);
        System.out.println(baske);


// переменные
        int sumProducts = 0;
// создаем сканер

        Scanner scan = new Scanner(System.in);
// Первое отображение списка покупок
        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < baske.getProducts().size(); i++) {
            System.out.println((i + 1) + "." + baske.getProducts().get(i) + " " + baske.getPrices().get(i) + "р/шт");
        }
// бесконечный цикл
        while (true) {

            System.out.println("Выберите товар и количество или введите `end`для завершения или " +
                    "'load' для загрузки файлов");
            String input = scan.nextLine();

            if ("end".equals(input)) {
                // Общая сумма


                for (int i = 0; i < baske.getTotalPrice().size(); i++) {
                    if (baske.getTotalPrice().get(i) != null){
                        sumProducts += baske.getTotalPrice().get(i)*baske.getPrices().get(i);
                    }
                }
                System.out.println(baske);

                // Вывод на печать
                    baske.printCart();
                    System.out.println("Итого: " + sumProducts + " руб");
                    break;


            }
            if ("load".equals(input)) {
                Basket.loadFromBinFile(new File("basket.bin"));
                System.out.println("Успешно загружены ");
                continue;
            }
                String[] pars = input.split(" ");
                int productNumber = Integer.parseInt(pars[0]) - 1;
                int productCount = Integer.parseInt(pars[1]);

//Стоимость продукта
                baske.addToCart(productNumber, productCount);

            baske.saveBin(new File("basket.bin"));
            }


        }




    }





