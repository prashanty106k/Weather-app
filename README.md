# # WeatherApp

## Overview
WeatherApp is a Java-based desktop application that fetches and displays real-time weather data and a 5-day forecast using the OpenWeatherMap API. It features a graphical user interface (GUI) built with Swing and retrieves weather information such as temperature, humidity, wind speed, visibility, and more.

## Features
- Fetches current weather conditions for any city.
- Displays temperature, humidity, wind speed, visibility, and weather conditions.
- Provides a 5-day weather forecast.
- Displays sunrise and sunset timings.
- Air quality estimation.
- Simple and interactive user interface using Java Swing.

## Technologies Used
- **Java** (Core Logic and UI)
- **Swing** (Graphical User Interface)
- **OpenWeatherMap API** (Weather Data Source)
- **HttpURLConnection** (For API calls)
- **JSON Parsing** (Using `org.json.JSONObject`)

## Installation & Setup
1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/WeatherApp.git
   cd WeatherApp
   ```
2. Obtain an API key from [OpenWeatherMap](https://openweathermap.org/api).
3. Open the `WeatherApp.java` file and replace the `API_KEY` variable with your API key:
   ```java
   private static final String API_KEY = "your_api_key_here";
   ```
4. Compile and run the application:
   ```sh
   javac WeatherApp.java
   java WeatherApp
   ```

## How It Works
- The user enters a city name and clicks the "Get Weather" button.
- The application sends an API request to OpenWeatherMap and retrieves the weather data.
- JSON data is parsed, and weather details are displayed in the GUI.

## Screenshots
*(Include relevant screenshots of your application's UI here)*

## Future Enhancements
- Implement caching to reduce redundant API calls.
- Add graphical weather visualizations using charts.
- Improve UI with JavaFX.
- Integrate geolocation support to fetch weather data based on the user's location.

## License
This project is open-source and available under the MIT License.

## Author
Developed by **[Your Name]**

## Contributing
Contributions are welcome! Feel free to open issues and submit pull requests.

