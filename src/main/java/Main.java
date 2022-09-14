import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;



public class Main {


    public static void main(String[] args) throws IOException {
        Boolean loadCon=true;
        String fileNameLoad = null;
        String fileFormatLoad=null;

        Boolean saveCon=true;
        String fileNameSave = null;
        String fileFormatSave=null;

        Boolean logCon=true;
        String fileNameLog = null;

        try {
            File fXmlFile = new File("shop.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("load");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    loadCon= Boolean.valueOf(eElement.getElementsByTagName("enabled")
                            .item(0).getTextContent());
                    fileNameLoad=eElement.getElementsByTagName("fileName")
                            .item(0).getTextContent();
                    fileFormatLoad= eElement.getElementsByTagName("format")
                            .item(0).getTextContent();
                }
            }
            NodeList nList2 = doc.getElementsByTagName("save");
            for (int temp = 0; temp < nList2.getLength(); temp++) {
                Node nNode = nList2.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    saveCon= Boolean.valueOf(eElement.getElementsByTagName("enabled")
                            .item(0).getTextContent());
                    fileNameSave=eElement.getElementsByTagName("fileName")
                            .item(0).getTextContent();
                    fileFormatSave=eElement.getElementsByTagName("format")
                            .item(0).getTextContent();
                }
            }
            NodeList nList3 = doc.getElementsByTagName("log");

            for (int temp = 0; temp < nList2.getLength(); temp++) {
                Node nNode = nList3.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Load enabled : " +
                            eElement.getElementsByTagName("enabled").item(0).getTextContent());
                    System.out.println("fileName  : " +
                            eElement.getElementsByTagName("fileName").item(0).getTextContent());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

// создание статического массива
        List<String> products = List.of("Хлеб", "Яблоки", "Молоко");
        List<Integer> prices = List.of(100, 200, 300);
        ClientLog clientLog = new ClientLog();

        Basket baske = new Basket(products, prices);
        System.out.println(baske);
        if (fileFormatLoad.equals("json") && loadCon){
            baske.loadFromJSonFile(new File(fileNameLoad));
        }else if(loadCon){
            baske.loadFromTxtFile(new File(fileNameLoad));
        }


       //
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
                    if (baske.getTotalPrice().get(i) != null) {
                        sumProducts += baske.getTotalPrice().get(i) * baske.getPrices().get(i);
                    }
                }
                System.out.println(baske);

                // Вывод на печать
                baske.printCart();
                System.out.println("Итого: " + sumProducts + " руб");

                if (fileFormatSave.equals("json")){
                    baske.saveJSon(new File(fileNameSave));
                }else{
                    baske.saveTxt(new File(fileNameSave));
                }

                if (logCon){
                    clientLog.exportAsCSV(new File(fileNameLog));
                }
                break;


            }
            if ("load".equals(input)) {
                if (fileFormatLoad.equals("json")){
                    baske.loadFromJSonFile(new File(fileNameLoad));
                }else{
                    baske.loadFromTxtFile(new File(fileNameLoad));
                }

                continue;
            }
            String[] pars = input.split(" ");
            int productNumber = Integer.parseInt(pars[0]) - 1;
            int productCount = Integer.parseInt(pars[1]);


                clientLog.log(productNumber,productCount);


//Стоимость продукта
            baske.addToCart(productNumber, productCount);

            if (fileFormatSave.equals("json") && saveCon){
                baske.saveJSon(new File(fileNameSave));
            }else if(saveCon){
                baske.saveTxt(new File(fileNameSave));
            }


        }


    }


}





