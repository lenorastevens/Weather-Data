import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Map;


public class MainWeather extends JFrame {
    // Set font and zipcode fields
    final private Font mainFont = new Font("", Font.BOLD, 18);
    JTextField zipcode;
    JLabel lbData;

    // code to create window panel
    public void initalize() {

        /********** Data Display *********/
        lbData = new JLabel();
        lbData.setFont(mainFont);
    

        /********** Submit & Clear Buttons *********/
        JButton getWeather = new JButton("Get Weather");
        getWeather.setFont(mainFont);

        getWeather.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve data from the API using entered zipcode
                String enteredZipcode = zipcode.getText();
                WeatherFetch weatherFetch = new WeatherFetch(enteredZipcode);
                Map<String, String> weatherData = weatherFetch.fetchWeatherData();

                // Update the lbData with the weather information
                StringBuilder displayText = new StringBuilder("<html>");
                for (Map.Entry<String, String> entry : weatherData.entrySet()) {
                    displayText.append(entry.getKey()).append(": ").append(entry.getValue()).append("<br/>");
                }
                displayText.append("</html>");

                lbData.setText(displayText.toString());
                lbData.setHorizontalAlignment(SwingConstants.CENTER);
            }
        });

        JButton btnClear = new JButton("Clear");
        btnClear.setFont(mainFont);
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Clear the zipcode field
                zipcode.setText("");
                // Clear the Weathe Data
                lbData.setText("");
            }
        });


        /********** Form Panel *********/
        JLabel lbZipcode = new JLabel("Zipcode");
        lbZipcode.setFont(mainFont);

        zipcode = new JTextField();
        zipcode.setFont(mainFont);

        JPanel zipcodePanel = new JPanel();
        zipcodePanel.setLayout(new GridLayout(5, 1, 5, 5));
        zipcodePanel.setOpaque(false);
        zipcodePanel.add(lbZipcode);
        zipcodePanel.add(zipcode);

        /********** Data Panel *********/
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 5, 5));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(getWeather);
        buttonsPanel.add(btnClear);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(125, 125, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(zipcodePanel, BorderLayout.NORTH);
        mainPanel.add(lbData, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setTitle("Welcome to Weather Data");
        setSize(500, 500);
        setMinimumSize(new Dimension(300, 300));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
