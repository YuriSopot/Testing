package com.max.idea.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    // Вынос всех тэгов в константы
    public static final String TAG_name="name";
    public static final String TAG_people="people";
    public static final String TAG_firstname="firstname";
    public static final String TAG_Lastname="Lastname";
    public static final String TAG_Profession="Profession";
    public static final String TAG_Range="Range";
    public static final String TAG_salary="salary";
    public static final String TAG_element="element";
    public static Root root;
    public Root parse()
    {
        root=new Root();
        // Открытие файла
        Document doc;
        try{
            doc=buildDocument();
        }
        catch(Exception e)
        {
            System.out.println("Parsing error "+e.toString());
            return null;
        }
        //Получение Root элемента
        Node rootNode=doc.getFirstChild();
        //System.out.println("Как-то так: "+rootNode.getNodeName());
        //Массив внутри rootNode(от root до/root)
        NodeList rootChild=rootNode.getChildNodes();
        String mainName=null;
        Node peopleNode=null;
        //Движение по содержимому Root -<root>-</root>,Child(<name>IT Banda</name>, <people>-</people>)
        for(int i=0;i<rootChild.getLength();i++)
        {
            //System.out.println("Как-во Рут: "+rootChild.getLength());
            if(rootChild.item(i).getNodeType()!=Node.ELEMENT_NODE)
            {
                continue;
            }
            System.out.println(rootChild.item(i));
            switch(rootChild.item(i).getNodeName())
            {
                case TAG_name:
                {
                    mainName=rootChild.item(i).getTextContent();
                    //System.out.println("mainname: "+mainName);
                    break;
                }
                case TAG_people:
                {
                    peopleNode=rootChild.item(i);
                    break;
                }
            }
        }

        if(peopleNode==null)
        {
            return null;
        }
        List<People> peopleList=parsPeople(peopleNode);
        root.setName(mainName);
        root.setPeople(peopleList);
        System.out.println("Начинаем");
        System.out.println("Root: "+root.toString());
        return root;
    }
    private  Document buildDocument() throws Exception
    {
        File file=new File("C:\\Users\\User\\IdeaProjects\\HelloWorld\\src\\com\\max\\idea\\XML\\bandIT.xml");
        DocumentBuilderFactory dob=DocumentBuilderFactory.newInstance();
        return dob.newDocumentBuilder().parse(file);
    }
    //Движение по всем element и вызом метода parsElement()
    private  List<People> parsPeople(Node peopleNode)
    {
        List<People> peopleList=new ArrayList<>();
        NodeList peopleChilds=peopleNode.getChildNodes();
        for(int i=0;i<peopleChilds.getLength();i++)
        {
            if(peopleChilds.item(i).getNodeType()!=Node.ELEMENT_NODE)
            {
                continue;
            }
            if(!peopleChilds.item(i).getNodeName().equals(TAG_element))
            {continue;}
            //System.out.println("Авона"+peopleChilds.item(i));
            People people=parsElement(peopleChilds.item(i));
            peopleList.add(people);
        }
        return peopleList;
    }
    //Движение по данным element
    private  People parsElement (Node elementNode)
    {
        String  firstname="";
        String  Lastname="";
        String  Profession="";
        String  Range="";
        int  salary=0;
        NodeList elementChilds=elementNode.getChildNodes();
        for(int j=0;j<elementChilds.getLength();j++)
        {
            if(elementChilds.item(j).getNodeType()!=Node.ELEMENT_NODE)
            {
                continue;
            }
            switch (elementChilds.item(j).getNodeName())
            {
                case TAG_firstname:
                {
                    firstname=elementChilds.item(j).getTextContent();
                    break;
                }
                case TAG_Lastname:
                {
                    Lastname=elementChilds.item(j).getTextContent();
                    break;
                }
                case TAG_Profession:
                {
                    Profession=elementChilds.item(j).getTextContent();
                    break;
                }
                case TAG_Range:
                {
                    Range=elementChilds.item(j).getTextContent();
                    break;
                }
                case TAG_salary:
                {
                    salary=Integer.parseInt(elementChilds.item(j).getTextContent());
                    //System.out.println("УТИИ"+salary);
                    break;
                }
            }
        }
        People people=new People(firstname,Lastname,Profession,Range,salary);
        return people;
    }
}
