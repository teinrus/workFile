package main.java;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basket {

    private static List<String> products;
    private static List<Integer> prices;
    private static Map<Integer, Integer> totalPrice = new HashMap<>();

    public Basket(List<String> products, List<Integer> prices) {
        this.products = products;
        this.prices = prices;
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

    public void saveTxt(File textFile) {
        try (Writer writer= new FileWriter(textFile)){
            Gson gson = new Gson();
            gson.toJson(new Basket(products, prices),writer );

            System.out.println("Данные сохранены");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromTxtFile(File textFile) throws RuntimeException {
    try (Writer writer= new FileWriter(textFile)){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Basket temp = gson.fromJson(String.valueOf(textFile), Basket.class);
        System.out.println(temp);
        System.out.println("Данные загружены");
    } catch (Exception e) {
        System.out.println("Ошибка");
    }
        return null;
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

    public void setPrices(List<Integer> prices) {
        this.prices = prices;
    }

    public Map<Integer, Integer> getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Map<Integer, Integer> totalPrice) {
        this.totalPrice = totalPrice;
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
