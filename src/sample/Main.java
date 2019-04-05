package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    private int pqValue = 0;
    private int pValue = 0;
    private int qValue = 0;
    private int eValue = 0;

    public Encryption encryption;

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = createGrid();
        createFields(grid);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(grid, 900, 550));
        primaryStage.show();
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(40, 40,40, 40));
        return grid;
    }

    private void createFields(GridPane grid) {
        Text scenetitle = new Text("Welcome to this amazing encrypter.");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label step1Label = new Label("Input Value n:");
        grid.add(step1Label, 0, 8);

        TextField step1TextField = new TextField();
        grid.add(step1TextField, 0, 9);

        Button step1Button = new Button("Step 1");
        grid.add(step1Button, 1, 9);

        Button step2Button = new Button("Step 2");
        grid.add(step2Button, 2, 9);

        Label pqValueLabel = new Label();
        grid.add(pqValueLabel, 0, 10);


        Label pqTimelabel = new Label();
        grid.add(pqTimelabel, 0, 11);

        Label eValueLabel = new Label();
        grid.add(eValueLabel, 0, 12);

        TextField step3TextField = new TextField();
        grid.add(step3TextField, 0, 14);

        Button step3Button = new Button("Step 3");
        grid.add(step3Button, 1, 14);

        Label encryptedMsgLabel = new Label();
        grid.add(encryptedMsgLabel, 0, 15);

        step1Button.setOnAction(event -> {
            long startTime = System.nanoTime();
            pqValueLabel.setText("");
            pqTimelabel.setText("");
            eValueLabel.setText("");
            if (encryption.isSemiPrime(Integer.parseInt(step1TextField.getText()))) {
                List<Integer> primeNumbers = encryption.calculatePrimeNumbers(Integer.parseInt(step1TextField.getText()));
                pqValueLabel.setText("p is "+ primeNumbers.get(0) + ", q is "+ primeNumbers.get(1));
                long busyTime = System.nanoTime() - startTime;
                pqTimelabel.setText("Amount of time busy finding p and q: " + busyTime);
                pValue = primeNumbers.get(0);
                qValue = primeNumbers.get(1);
                pqValue = (pValue-1) * (qValue -1);

            } else {
                pqValueLabel.setText("Number is not a semiprime!");
            }
        });

        step2Button.setOnAction(event -> {
            if (pqValue != 0) {
                eValue = encryption.calculatePublicKeyPart2(pqValue);
                eValueLabel.setText("e is " + eValue);
            } else {
                eValueLabel .setText("First do step 1");
            }
        });

        step3Button.setOnAction(event -> encryptedMsgLabel.setText(encryption.calculateCipher(step3TextField.getText(), pValue, qValue, eValue).toString()));

    }


    public static void main(String[] args) {
        launch(args);
    }
}
