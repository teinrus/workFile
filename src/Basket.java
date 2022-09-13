import java.io.*;
import java.util.*;

public class Basket implements Serializable {

    private static List<String> products;
    private static List<Integer> prices;
    private static Map<Integer, Integer> totalPrice = new HashMap<>();

    public Basket(List<String> products, List<Integer> prices) {
        this.products = products;
        this.prices = prices;
    }

    public Basket() {

    }

    public void addToCart(int productNum, int amount) {
        if (totalPrice.containsKey(productNum)) {
            totalPrice.put(productNum, totalPrice.get(productNum) + amount);
        } else {
            totalPrice.put(productNum, amount);
        }

    }

    public void printCart() {
        for (int i = 0; i < getProducts().size(); i++) {
            // Проверка на введенные продукты и печатаем только которые купили
            if (getTotalPrice().get(i) != null) {
                System.out.println(getProducts().get(i) + ": "
                        + (getTotalPrice().get(i))
                        + " шт " + getPrices().get(i) + " руб/шт "
                        + getTotalPrice().get(i) * getPrices().get(i) + " руб в сумме");
            }
        }
    }


    public void saveBin(File file) throws IOException {

        Basket saveBin = new Basket();
        try (FileOutputStream os = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(os)) {

            oos.writeObject(saveBin);

        } catch (Exception ex) {
            System.out.println("ошибка");
        }
        System.out.println("Данные сохранены");
        System.out.println(saveBin);
    }

    static void loadFromBinFile(File file) {
        Basket basket = new Basket();
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            basket = (Basket) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(basket);
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public List<Integer> getPrices() {
        return prices;
    }



    public Map<Integer, Integer> getTotalPrice() {
        return totalPrice;
    }


    @Override
    public String toString() {
        return "Baske{" +
                "products=" + products +
                ", prices=" + prices +
                ", totalPrice=" + totalPrice +
                '}';
    }

}
