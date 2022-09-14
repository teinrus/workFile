

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basket {

    private  List<String> products;
    private  List<Integer> prices;
    private  Map<Integer, Integer> totalPrice = new HashMap<>();

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

    public void saveJSon(File textFile) {
        try (Writer writer= new FileWriter(textFile)){
            Gson gson = new Gson();
            Basket temp = new Basket(products, prices);
            temp.totalPrice=this.totalPrice;
            gson.toJson(temp,writer );
            System.out.println("Данные сохранены");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile);) {
            for (int i = 0; i < getProducts().size(); i++) {
                if (getTotalPrice().get(i) != null) {
                    String temp = getProducts().get(i) + " " + getTotalPrice().get(i) +
                            " " + getPrices().get(i);

                    out.print(temp);
                    out.print('\n');
                } else {
                    String temp = getProducts().get(i) + " " + 0 + " " + getPrices().get(i);
                    out.print(temp);
                    out.print('\n');

                }

            }
        }
        System.out.println("Данные сохранены");

    }



    public void  loadFromJSonFile(File textFile) throws RuntimeException {
    try (Reader reader = new FileReader(textFile)) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Basket temp = gson.fromJson(reader,Basket.class);
        this.totalPrice=temp.totalPrice;
        System.out.println(temp);
        System.out.println("Данные загружены");
    } catch (Exception e) {

        System.out.println("Файл не найден");;
    }

    }
    public Basket loadFromTxtFile(File textFile) {

        File file = new File(textFile.toURI());
        //создаем объект FileReader для объекта File
        try (FileReader fr = new FileReader(file);) {
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            int i = 0;
            while (line != null) {
                System.out.println(line);
                String[] pars = line.split(" ");
                this.totalPrice.put(i, Integer.valueOf(pars[1]));
                i++;
                // считываем остальные строки в цикле
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
