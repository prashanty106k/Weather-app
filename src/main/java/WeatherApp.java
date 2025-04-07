import javax.print.event.PrintJobAttributeListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class WeatherApp {
    private static final String API_KEY = "Your-Openweathermap-API-Key";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String FORECAST_URL = "https://api.openweathermap.org/data/2.5/forecast?q=";
    static String cityName;
    static String iconpath;
    static double temperature, feelsLike, windSpeed, cloudCover;
    static int humidity1, pressure, visibility;
    static long sunrise, sunset;
    static String description;
    static String date1[]=new String[5];
    static double temp1[]=new double[5];
    static String desc1[]=new String[5];
    static String iconcode1[]=new String[5];




    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }



    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Weather App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 800);

        JPanel panel = new JPanel();


        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        Font font = new Font("Nunito Sans", Font.BOLD, 20);
        leftPanel.setBackground(Color.WHITE);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(Color.lightGray);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.lightGray);

        JTextField cityField = new JTextField("Search for places",12);
        cityField.setFont(font);
        JButton fetchButton = new JButton("Get Weather");
        JLabel img= new JLabel("-",JLabel.CENTER);
        img.setFont(font);
        img.setHorizontalTextPosition(JLabel.CENTER);
        img.setVerticalTextPosition(JLabel.BOTTOM);
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\prash\\IdeaProjects\\untitled\\icons\\hot.png"); // Replace with your image path
        Image image = imageIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(image);
        JLabel temp = new JLabel("-°C",resizedIcon,JLabel.CENTER);
        temp.setFont(font);
        ImageIcon imageIcon1 = new ImageIcon("C:\\Users\\prash\\IdeaProjects\\untitled\\icons\\humidity.png"); // Replace with your image path
        Image image1 = imageIcon1.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon1 = new ImageIcon(image1);
        JLabel humidity = new JLabel("-",resizedIcon1,JLabel.CENTER);
        humidity.setHorizontalTextPosition(JLabel.CENTER);
        humidity.setVerticalTextPosition(JLabel.BOTTOM);
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3,1));
        centerPanel.setBackground(Color.white);
        centerPanel.add(img);
        centerPanel.add(temp);
        centerPanel.add(humidity);
        JLabel weatherLabel = new JLabel("-",JLabel.CENTER);

         JPanel rightside = new JPanel();
         rightside.setLayout(new GridLayout(3,1));

        JPanel rightTopPanel = new JPanel();

        rightTopPanel.setLayout(new GridLayout(1, 5, 10, 0)); // Horizontal layout with spacing
        rightside.add(rightTopPanel);

        for (int i = 1; i <= 5; i++) {
            JPanel panelright = new JPanel();
            panelright.setLayout(new GridLayout(3, 1)); // 3 JLabels stacked vertically
            panelright.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            for (int j = 1; j <= 3; j++) {
                panelright.add(new JLabel("Label " + j + " in Panel " + i, SwingConstants.CENTER));
            }

            rightTopPanel.add(panelright);
        }











        fetchButton.addActionListener(e -> {
            String city = cityField.getText().trim();
            if (!city.isEmpty()) {
                String weatherInfo = getWeather(city);
                String forecastInfo = getForecast(city);
                weatherLabel.setText("<html>" + weatherInfo.replaceAll("\n", "<br>") + "<br><br>" + forecastInfo.replaceAll("\n", "<br>") + "</html>");
                temp.setText(temperature+"°C");


                ImageIcon newIcon = new ImageIcon(iconpath);
                Image scaledImage = newIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                img.setIcon(new ImageIcon(scaledImage));
                img.setText(description);
                humidity.setText(humidity1+"%");





            }
        });



        temp.setHorizontalTextPosition(JLabel.CENTER);
        temp.setVerticalTextPosition(JLabel.BOTTOM);

        JPanel inputPanel = new JPanel();
        inputPanel.add(cityField);
        inputPanel.add(fetchButton);
        inputPanel.setBackground(Color.WHITE);


        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(inputPanel, BorderLayout.NORTH);
        leftPanel.add(centerPanel, BorderLayout.CENTER);




        rightPanel.add(weatherLabel, BorderLayout.CENTER);

//        frame.add(panel);
        frame.setVisible(true);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(350); // Set initial divider position
        splitPane.setEnabled(false);
        splitPane.setDividerSize(0);
        mainPanel.add(splitPane, BorderLayout.CENTER);
        splitPane.setBorder(new EmptyBorder(25, 25, 25, 25));






        frame.add(mainPanel);
    }

    private static String getWeather(String city) {
        String urlString = BASE_URL + city + "&appid=" + API_KEY + "&units=metric";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                Scanner scanner = new Scanner(url.openStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();
                return parseWeatherData(response.toString());


            } else {
                return "Error: Unable to fetch weather data";
            }
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    private static String parseWeatherData(String jsonResponse) {
        JSONObject obj = new JSONObject(jsonResponse);
        String cityName = obj.getString("name");
        double temperature = obj.getJSONObject("main").getDouble("temp");
        double feelsLike = obj.getJSONObject("main").getDouble("feels_like");
        int humidity = obj.getJSONObject("main").getInt("humidity");
        int pressure = obj.getJSONObject("main").getInt("pressure");
        double windSpeed = obj.getJSONObject("wind").getDouble("speed");
        int visibility = obj.getInt("visibility") / 1000;
        long sunrise = obj.getJSONObject("sys").getLong("sunrise");
        long sunset = obj.getJSONObject("sys").getLong("sunset");
        String description = obj.getJSONArray("weather").getJSONObject(0).getString("description");
        double cloudCover = obj.getJSONObject("clouds").getDouble("all");
        String iconCode = obj.getJSONArray("weather").getJSONObject(0).getString("icon");
        WeatherApp app = new WeatherApp();
        app.cityName = cityName;
        app.temperature = temperature;
        app.description = description;
        app.humidity1 = humidity;
        app.iconpath ="C:\\Users\\prash\\IdeaProjects\\untitled\\icons\\"+iconCode+".png";



        return "Weather in " + cityName + ":\n\n" +
                "Temperature: " + temperature + "°C\n" +
                "Feels Like: " + feelsLike + "°C\n" +
                "Humidity: " + humidity + "%\n" +
                "Pressure: " + pressure + " hPa\n" +
                "Wind Speed: " + windSpeed + " m/s\n" +
                "Visibility: " + visibility + " km\n" +
                "Cloud Cover: " + cloudCover + "%\n" +
                "Sunrise: " + convertUnixToTime(sunrise) + "\n" +
                "Sunset: " + convertUnixToTime(sunset) + "\n" +
                "Conditions: " + description;
    }

    private static String convertUnixToTime(long unixTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(unixTime * 1000));
    }

    private static String getForecast(String city) {
        String urlString = FORECAST_URL + city + "&appid=" + API_KEY + "&units=metric";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                Scanner scanner = new Scanner(url.openStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();
                return parseForecastData(response.toString());
            } else {
                return "Error: Unable to fetch forecast data";
            }
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    private static String parseForecastData(String jsonResponse) {
        JSONObject obj = new JSONObject(jsonResponse);
        StringBuilder forecast = new StringBuilder("Next Week Forecast:\n\n");
        for (int i = 0; i < 5; i++) {
            JSONObject dailyForecast = obj.getJSONArray("list").getJSONObject(i * 8);
            String date = dailyForecast.getString("dt_txt").split(" ")[0];
            double temp = dailyForecast.getJSONObject("main").getDouble("temp");
            String desc = dailyForecast.getJSONArray("weather").getJSONObject(0).getString("description");
            String iconCode = dailyForecast.getJSONArray("weather").getJSONObject(0).getString("icon");
            WeatherApp app = new WeatherApp();
            LocalDate date3 = LocalDate.parse(date);
            DayOfWeek dayOfWeek = date3.getDayOfWeek();
            app.date1[i] = String.valueOf(dayOfWeek);
            app.temp1[i] = temp;
            forecast.append(date).append(" - Temp: ").append(temp).append("°C, ").append(desc).append(iconCode).append("\n");
        }
        return forecast.toString();
    }


}
