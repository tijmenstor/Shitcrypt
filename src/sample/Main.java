package sample;

import com.bulenkov.darcula.DarculaLaf;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private int pqValue = 0;
    private int pValue = 0;
    private int qValue = 0;
    private int eValue = 0;
    private long time = 0;
    private String encryptedMsg = "";

    private final JFrame frame;

    private final JLabel inputLabel;
    private final JLabel pqValueLabel;
    private final JLabel pqTimeLabel;
    private final JLabel eValueLabel;
    private final JLabel encryptedMsgLabel;
    private final JLabel decryptedValueLabel;
    private final JLabel decryptedMessageLabel;

    private final JButton step1Button;
    private final JButton step2Button;
    private final JButton step3Button;
    private final JButton step1DecButton;
    private final JButton step2DecButton;

    private final JTextField step1Field;
    private final JTextField step3Field;
    private final JTextField step1pbKeyN;
    private final JTextField step1pbkeyE;
    private final JTextField step2EncryptedText;


    private Main() {
        try {
            UIManager.setLookAndFeel(new DarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame = new JFrame("Discrete Mathematics PA3");
        frame.setLayout(new MigLayout());
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.getInsets().set(10, 10, 10, 10);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 400));

        inputLabel = new JLabel("Input value n:");
        pqValueLabel = new JLabel("p is " + pValue + ", q is " + qValue);
        pqTimeLabel = new JLabel("Amount of time busy finding p and q: " + time + " ms");
        eValueLabel = new JLabel("e is ");
        encryptedMsgLabel = new JLabel("Message after encryption is: " + encryptedMsg);

        step1Button = new JButton("Step 1");
        step2Button = new JButton("Step 2");
        step3Button = new JButton("Step 3");

        step1Field = new JTextField();
        step3Field = new JTextField();

        step1pbkeyE = new JTextField();
        step1pbKeyN = new JTextField();

        step2EncryptedText = new JTextField();

        step1DecButton = new JButton("Step 1");
        step2DecButton = new JButton("Step 2");

        decryptedValueLabel = new JLabel("d is ");
        decryptedMessageLabel = new JLabel("Message after decryption is: ");

        JComponent encryptionTab = createPanel();
        tabs.addTab("Encryption", null, encryptionTab, "Encryption");
        JPanel step1 = new JPanel(new MigLayout());
        step1.setBorder(new TitledBorder("Step 1"));

        //step 1
        step1.add(inputLabel, "wrap, growx");
        step1.add(step1Field, "growx");
        step1.add(step1Button, "wrap, growx");
        step1.add(pqValueLabel, "wrap");
        step1.add(pqTimeLabel, "wrap, growx");
        encryptionTab.add(step1, "grow");

        //step 2
        JPanel step2 = new JPanel(new MigLayout());
        step2.setBorder(new TitledBorder("Step 2"));
        step2.add(step2Button, "wrap, growx");
        step2.add(eValueLabel, "growx");
        encryptionTab.add(step2, "wrap, grow");

        //step 3
        JPanel step3 = new JPanel(new MigLayout());
        step3.setBorder(new TitledBorder("Step 3"));
        step3.add(step3Field, "growx");
        step3.add(step3Button, "wrap");
        step3.add(encryptedMsgLabel, "growx");
        encryptionTab.add(step3, "grow");

        JComponent decryptionTab = createPanel();
        tabs.addTab("Decryption", null, decryptionTab, "Decryption");

        //step 1
        JPanel step1Decryption = new JPanel(new MigLayout());
        step1Decryption.setBorder(new TitledBorder("Step 1"));
        step1Decryption.add(step1pbKeyN, "growx, wrap");
        step1Decryption.add(step1pbkeyE, "growx, wrap");
        step1Decryption.add(step1DecButton, "growx, wrap");
        step1Decryption.add(decryptedValueLabel, "growx");
        decryptionTab.add(step1Decryption);

        //step 2
        JPanel step2Decryption = new JPanel(new MigLayout());
        step2Decryption.setBorder(new TitledBorder("Step 2"));
        step2Decryption.add(step2EncryptedText, "growx, wrap");
        step2Decryption.add(step2DecButton, "growx, wrap");
        step2Decryption.add(decryptedMessageLabel, "growx");
        decryptionTab.add(step2Decryption);


        frame.add(tabs, "wrap, grow");

        step1Button.addActionListener(this::step1Handler);
        step2Button.addActionListener(this::step2Handler);
        step3Button.addActionListener(this::step3Handler);
        step1DecButton.addActionListener(this::step1DecHandler);
        step2DecButton.addActionListener(this::step2DecHandler);

        display();
    }

    private void display() {
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    private void step1Handler(ActionEvent event) {
        long startTime = System.currentTimeMillis();
        if (Encryption.isSemiPrime(Integer.parseInt(step1Field.getText()))) {
            List<Integer> primeNumbers = Encryption.calculatePrimeNumbers(Integer.parseInt(step1Field.getText()));
            time = System.currentTimeMillis() - startTime;
            pValue = primeNumbers.get(0);
            qValue = primeNumbers.get(1);
            pqValue = (pValue - 1) * (qValue - 1);
            pqValueLabel.setText("p is " + pValue + ", q is " + qValue);
            pqTimeLabel.setText("Amount of time busy finding p and q: " + time + " ms");
            return;
        }

        pqValueLabel.setText("Number is not a semiprime!");
    }

    private void step2Handler(ActionEvent event) {
        if (pqValue != 0) {
            eValue = Encryption.calculatePublicKeyPart2(pqValue);
            eValueLabel.setText("e is " + eValue);
            return;
        }

        eValueLabel.setText("First do step 1");
    }

    private void step3Handler(ActionEvent event) {
        encryptedMsg = Encryption.calculateCipher(step3Field.getText(), pValue, qValue, eValue).toString();
        encryptedMsgLabel.setText("Message after encryption is: " + encryptedMsg);
    }

    private void step1DecHandler(ActionEvent event) {
        int n = Integer.parseInt(step1pbKeyN.getText());
        int e = Integer.parseInt(step1pbkeyE.getText());
        decryptedValueLabel.setText("d is " + Decryption.calculateDecriptionKey(n, e));
    }

    private void step2DecHandler(ActionEvent event) {
        String[] encryptedList = step2EncryptedText.getText().split(",");
        List<Integer> encryptedNums = new ArrayList<>();
        for (String encryptedNum : encryptedList) {
            encryptedNums.add(Integer.parseInt(encryptedNum));
        }
        decryptedMessageLabel.setText("Message after decryption is: " + Decryption.calculateDecryptedText(encryptedNums));
    }

    private JComponent createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout(""));
        return panel;
    }


}
