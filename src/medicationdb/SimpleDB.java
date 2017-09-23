
package medicationdb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

public class SimpleDB {

    private String name, company;
    private float price, quantity;
    public static List<SimpleDB> ALLObject = new ArrayList<SimpleDB>();
    public static PrintWriter write;
    public BufferedReader read;

    public SimpleDB() {
    }

    public SimpleDB(String name, float price, String company, float quantity){ 
        setname(name);
        setCompany(company);
        setPrice(price);
        setQuantity(quantity);
    }

    public void setname(String name) {
        this.name = name;
    }
    public String getname() {
        return this.name;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getCompany() {
        return this.company;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public float getPrice() {
        return this.price;
    }
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
    public float getQuantity() {
        return this.quantity;
    }
    public static boolean validation(String id) {
        for (Object o : ALLObject) 
            if (((SimpleDB) o).getname().equals(id)) 
                return true;
            return false;
            }
    public static boolean validation(String name,int id) {
          SimpleDB o =ALLObject.get(id);
        if (o.getname().equals(name)) 
                return true;
        return false;
    }
    public static void writeInFile() {
        try {
            String SaveData = null;
            write = new PrintWriter("DataFile.txt");
            for (Object o : ALLObject) {
                SaveData = ((SimpleDB) o).getname() + ";"
                        + ((SimpleDB) o).getPrice() + ";"
                        + ((SimpleDB) o).getCompany() + ";"
                        + ((SimpleDB) o).getQuantity() + ";";
                write.print(SaveData);
            }
            write.close();
        } catch (FileNotFoundException ex) {
//            JOptionPane.showMessageDialog(null, ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public static void readFromFile(){
        try {
            BufferedReader read = new BufferedReader
                                 (new InputStreamReader
                                 (new FileInputStream("DataFile.txt"), "UTF-8"));
            String GetData = "";
            int i = 0,j = 0;
            while ((i = read.read()) != -1) {
                if ((char) i == ';')   j++;
                GetData += (char) i;
                if (j == 4) {
                    String[] SplitData = GetData.split(";");
                    ALLObject.add(new SimpleDB(SplitData[0],
                            Float.parseFloat(SplitData[1]),
                            SplitData[2],
                            Float.parseFloat(SplitData[3]))
                    );
                    j = 0;
                    GetData = "";
                }
            }
            read.close();
        }catch (FileNotFoundException ex) {
//           JOptionPane.showMessageDialog(null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
            
        }
    }
}

