
package medicationdb;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class MedicationDB extends Application {

    int index = 0;
    String getprice;
    String getquantity;
    static boolean ChangData = false;
    private TextField Name = new TextField();
    private Label Namelb = new Label("Medication Name:");
    private TextField Price = new TextField();
    private Label Pricelb = new Label("Medication Price:");
    private TextField Company = new TextField();
    private Label Companylb = new Label("Medication Company:");
    private TextField Quantity = new TextField();
    private Label Quantitylb = new Label("Medication Quantity:");
    private Button Save = new Button("Save");
    private Button Modify = new Button("Modify");
    private Button Delete = new Button("Delete");
    private Label SelectMedicationlb = new Label("Select Medication:");
    static ComboBox<String> Select = new ComboBox<>();

    @Override
    public void start(Stage primaryStage){
        try {
            Name.setMinHeight(30);
            Price.setMinHeight(30);
            Company.setMinHeight(30);
            Quantity.setMinHeight(30);
            Save.setMinHeight(30);
            Modify.setMinHeight(30);
            Delete.setMinHeight(30);
            HBox ManipulationData = new HBox(25);
            fillSelect();
            Save.setMinWidth(100);
            Modify.setMinWidth(100);
            Delete.setMinWidth(100);
            ManipulationData.getChildren().addAll(Save, Modify, Delete);
            ManipulationData.setAlignment(Pos.CENTER);
            HBox SelectData = new HBox(25);
            SelectData.getChildren().addAll(SelectMedicationlb, Select);
            Select.setMinWidth(200);
            Select.setMinHeight(30);
            Select.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
            SelectData.setAlignment(Pos.CENTER);
            GridPane pane = new GridPane();
            pane.setVgap(20);
            pane.setHgap(10);
            pane.add(Namelb, 0, 0);
            pane.add(Name, 1, 0);
            pane.add(Pricelb, 0, 1);
            pane.add(Price, 1, 1);
            pane.add(Companylb, 0, 2);
            pane.add(Company, 1, 2);
            pane.add(Quantitylb, 0, 3);
            pane.add(Quantity, 1, 3);
            pane.add(ManipulationData, 0, 4);
            pane.setColumnSpan(ManipulationData, 2);
            pane.add(SelectData, 0, 5);
            pane.setColumnSpan(SelectData, 2);
            pane.setPadding(new Insets(30));
            pane.setAlignment(Pos.CENTER);
            Save.setOnMouseClicked(e -> Save());
            Modify.setOnMouseClicked(e -> Modify());
            Delete.setOnMouseClicked(e -> Delete());
             Image image = new Image("Imge/background.jpg");
        // new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        // new BackgroundImage(image, repeatX, repeatY, position, size)
        BackgroundImage backgroundImage = new BackgroundImage(
                image, BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
                BackgroundSize.DEFAULT);
        // new Background(images...)
        Background background = new Background(backgroundImage);
//        root.setBackground(background);
            pane.setBackground(background);
            Scene scene = new Scene(pane, 450, 500);
            primaryStage.setOnCloseRequest(e -> exitPrograme());
            primaryStage.setTitle("Hello in Medication DataBase");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void Save() {
        try {
            if (!SimpleDB.validation(Name.getText().trim())) {
                SimpleDB.ALLObject.add(new SimpleDB(
                        Name.getText().trim(),
                        Float.parseFloat(Price.getText().trim()),
                        Company.getText().trim(),
                        Float.parseFloat(Quantity.getText().trim()))
                );
                Select.getItems().add(Name.getText().trim());
                clearFields();
                if (!ChangData) {
                    ChangData = true;
                }
            } else {
                JOptionPane.showMessageDialog(null,"Name Exist...!!!","Wrong Input",JOptionPane.NO_OPTION);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            clearFields();
        }catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,"Illegal Data "+ex.getMessage());
        }  
        catch (Exception ex) {
            clearFields();
        }
    }

    public void Modify() {
        try {
            if (SimpleDB.validation(Name.getText().trim(), index)) {
                SimpleDB.ALLObject.set(index, new SimpleDB(Name.getText().trim(),
                        Float.parseFloat(Price.getText().trim()),
                        Company.getText().trim(),
                        Float.parseFloat(Quantity.getText().trim()))
                );
                clearFields();
                if (!ChangData) {
                    ChangData = true;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cannot ModifY Medication Name"
                        + "you may be change Medicarion name");
                clearFields();
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            clearFields();
        }catch (NumberFormatException ex) {
            
            JOptionPane.showMessageDialog(null, ex);
        } catch (Exception ex) {
            clearFields();
        }
    }

    public void Delete() {
        try {
            SimpleDB.ALLObject.remove(index);
            Select.getItems().remove(index);
            clearFields();
            if (!ChangData) {
                ChangData = true;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            clearFields();
        } catch (Exception ex) {
            clearFields();
        }
    }

    public void fillSelect() {
        try {
            SimpleDB.readFromFile();
            for (SimpleDB o : SimpleDB.ALLObject) {
                Select.getItems().add(o.getname());
            }
        } catch (Exception ex) {
            clearFields();
        }
    }

    public void indexChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        index = newValue.intValue();
        SimpleDB o = SimpleDB.ALLObject.get(index);
        Name.setText(o.getname());
        Price.setText(Float.toString(o.getPrice()));
        Quantity.setText(Float.toString(o.getQuantity()));
        Company.setText(o.getCompany());
    }

    public void exitPrograme() {
        if (ChangData) {
            SimpleDB.writeInFile();
            JOptionPane.showMessageDialog(null, "Saving Data Is Done  !!!");
        }
    }

    public void clearFields() {
        Name.setText("");
        Price.setText("");
        Quantity.setText("");
        Company.setText("");
    }
}
