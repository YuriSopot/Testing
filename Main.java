package com.max.idea.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
public  static int test=0;//количество тестировщиков
public  static int prog=0;//количество программистов
public  static int engin=0;//количество инженеров
public  static int total=0;//количество участноков команды
public  static int sum=0;//суммарная зарплата
public  static int Sen=0;//количество Senior
public  static int Jun=0;//количество Junior
public  static int Mid=0;//количество Middle

    public static void main(String[] args)  {
       Parser parser=new Parser();// создаем объект парсинга
       Root root=parser.parse();// создаем объект- преобразование из XML в JAVA
       Root afterFilterRoot=new Root();//создаем объект для новой команды
        afterFilterRoot=projectFilter(root);//фильтруем объект под новую команду
        System.out.println("Root: "+afterFilterRoot.toString());
        qualityAndQuantity(afterFilterRoot);//определяем качественный и количественный состав новой команды

//    Преобразование Java в XML
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            // добавляем rootElement в документ
            Element rootElement = doc.createElement("root");
            // добавляем root element в документ
            doc.appendChild(rootElement);

              // добавляем first child element to root element
            rootElement.appendChild(createUserElement(doc, afterFilterRoot.getName(), afterFilterRoot.getPeople().get(0).getFirstname(), afterFilterRoot.getPeople().get(0).getLastname(), afterFilterRoot.getPeople().get(0).getProfession(), afterFilterRoot.getPeople().get(0).getRange(),String.valueOf(afterFilterRoot.getPeople().get(0).getSalary())));
            // добавляем second child
            rootElement.appendChild(createUserElement(doc, afterFilterRoot.getName(), afterFilterRoot.getPeople().get(1).getFirstname(), afterFilterRoot.getPeople().get(1).getLastname(), afterFilterRoot.getPeople().get(1).getProfession(), afterFilterRoot.getPeople().get(1).getRange(),String.valueOf(afterFilterRoot.getPeople().get(1).getSalary())));
            // добавляем third child
            rootElement.appendChild(createUserElement(doc, afterFilterRoot.getName(), afterFilterRoot.getPeople().get(2).getFirstname(), afterFilterRoot.getPeople().get(2).getLastname(), afterFilterRoot.getPeople().get(2).getProfession(), afterFilterRoot.getPeople().get(2).getRange(),String.valueOf(afterFilterRoot.getPeople().get(2).getSalary())));
            // добавляем forth child
            rootElement.appendChild(createUserElement(doc, afterFilterRoot.getName(), afterFilterRoot.getPeople().get(3).getFirstname(), afterFilterRoot.getPeople().get(3).getLastname(), afterFilterRoot.getPeople().get(3).getProfession(), afterFilterRoot.getPeople().get(3).getRange(),String.valueOf(afterFilterRoot.getPeople().get(3).getSalary())));

            // вывод в файл
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            // запись в файл
            StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(new File("C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\com\\max\\idea\\XML\\new_bandIT.xml"));

            // запись данных
            transformer.transform(source, console);
            transformer.transform(source, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// определяем новую команду,фильтруем по зарплате, позиции, специальности
    private static Root projectFilter(Root root)
    {
        Root projectRoot=new Root();
        List<People> peopleListFilter=new ArrayList<>();
         root.getPeople().stream().filter(people -> {return people.getSalary()>=1600 && people.getRange().equals("Senior") && people.getProfession().equals("tester");}).forEach(people ->{
            peopleListFilter.add(people);
        });
        root.getPeople().stream().filter(people -> {return people.getSalary()>=1400 && people.getRange().equals("Middle") && people.getProfession().equals("programmer");}).forEach(people ->{
            peopleListFilter.add(people);
            });
        root.getPeople().stream().filter(people -> {return people.getSalary()>=1400 && (people.getRange().equals("Middle") || people.getRange().equals("Junior")) && people.getProfession().equals("Engineer");}).forEach(people ->{
            peopleListFilter.add(people);
        });

        projectRoot.setPeople(peopleListFilter);
        projectRoot.setName("New_MY_IT_Band");
        return projectRoot;
    }
    // определяем количественный состав и итоговую сумму
    private static void qualityAndQuantity(Root root)
    {
        /*int test=0;
        int prog=0;
        int engin=0;
        int total=0;
        int sum=0;
        int Sen=0;
        int Jun=0;
        int Mid=0;*/
        List<People> outfitter=root.getPeople();
     for(People elems:outfitter) {

         switch (elems.getProfession()) {

             case "tester": {
                 test++;

                 break;
             }
             case "programmer": {
                 prog++;

                 break;
             }
             case "Engineer": {
                 engin++;

                 break;
             }
         }
     }
        for(People el:outfitter) {
            total++;
            switch (el.getRange()) {

                case "Senior": {
                    Sen++;
                    break;
                }
                case "Junior": {
                    Jun++;
                    break;
                }
                case "Middle": {
                    Mid++;
                    break;
                }
            }
        }
         for(People elem:outfitter)
         {
             sum+=elem.getSalary();
         }
         System.out.println("Кол-во людей на проекте: "+total);
         System.out.println("Кол-во тестировщиков: "+test);
         System.out.println("Кол-во программистов: "+prog);
         System.out.println("Кол-во инженеров: "+engin);
         System.out.println("Кол-во Senior: "+Sen);
         System.out.println("Кол-во Middle: "+Mid);
         System.out.println("Кол-во Junior: "+Jun);
         System.out.println("Зарплата на проект: "+sum);
    }
//создаем UserElement
    private static Node createUserElement(Document doc, String id, String firstName, String lastName, String Profession,
                                          String Range, String salary) {
        Element user = doc.createElement("name");

        // set id attribute
        user.setAttribute("id", id);

        // create firstName element
        user.appendChild(createUserElements(doc, user, "firstName", firstName));

        // create lastName element
        user.appendChild(createUserElements(doc, user, "lastName", lastName));

        // create Profession element
        user.appendChild(createUserElements(doc, user, "Profession", Profession));

        // create Range element
        user.appendChild(createUserElements(doc, user, "Range", Range));
        // create salary element
        user.appendChild(createUserElements(doc, user, "salary", salary));

        return user;
    }

    // создание Node
    private static Node createUserElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }



}
