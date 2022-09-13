import java.io.*;
import java.util.*;

public class Basket implements Serializable {

    private static List<String> products;
    private static List<Integer> prices;
    private static Map<Integer,Integer> totalPrice=new HashMap<>();

    public Basket(List<String> products, List<Integer> prices) {
        this.products = products;
        this.prices = prices;
    }

    public Basket() {

    }

    public void addToCart(int productNum, int amount){
        if (totalPrice.containsKey(productNum)){
            totalPrice.put(productNum,totalPrice.get(productNum)+amount);
        }else {
            totalPrice.put(productNum,amount);
        }

    }
    public void printCart() {
        for (int i = 0; i < getProducts().size(); i++) {
            // Проверка на введенные продукты и печатаем только которые купили
            if (getTotalPrice().get(i) != null) {
                System.out.println(getProducts().get(i) + ": "
                        + (getTotalPrice().get(i))
                        + " шт " + getPrices().get(i) + " руб/шт "
                        + getTotalPrice().get(i)*getPrices().get(i) + " руб в сумме");
            }
        }
    }


    public void saveBin(File file) throws IOException {

        Basket saveBin =new Basket();
        try (FileOutputStream os = new FileOutputStream(file);
         ObjectOutputStream oos = new ObjectOutputStream(os)) {

             oos.writeObject(saveBin);

        } catch (Exception ex) {
            System.out.println("ошибка");
        }
        System.out.println("Данные сохранены");
        System.out.println(saveBin);
    }
    static Basket loadFromTxtFile(File textFile){
        try {
            File file = new File(textFile.toURI());
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            int i=0;
            while (line != null) {
                System.out.println(line);
                String[] pars = line.split(" ");
                totalPrice.put(i, Integer.valueOf(pars[1]));
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
    static void loadFromBinFile(File file) {
        Basket basket = new Basket();
        try (FileInputStream  fis = new FileInputStream(file);
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
