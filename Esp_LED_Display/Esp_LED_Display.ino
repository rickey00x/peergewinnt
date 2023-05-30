#include <ArduinoJson.h>
#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <ESP8266mDNS.h>
#include <Adafruit_NeoPixel.h>

const char* ssid = "WIFI NAME HERE";
const char* password = "WIFI PASSWORD HERE";

ESP8266WebServer server(80);
Adafruit_NeoPixel strip(42, D5, NEO_GRB + NEO_KHZ800);

void setup() {
  
  // Initialize LED strip
  strip.begin();
  strip.clear();
  strip.show(); // Initialize all pixels to off

  //show that all leds work
  // Blink the first 7 LEDs 3 times
  for (int i = 0; i < 7 ; i++) {
      strip.setPixelColor(i, strip.Color(255, 255, 255));
      strip.show();
      delay(90);
      strip.clear();
      strip.show();
  }
  
  Serial.begin(115200);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }

  // Set unique hostname
  String hostname = "esp8266-" + String(ESP.getChipId());
  WiFi.hostname(hostname);

  Serial.println("Connected to WiFi");
  Serial.println("IP address: " + WiFi.localIP().toString());
  Serial.println("Hostname: " + hostname);
  

  // Initialize mDNS responder
  if (!MDNS.begin(hostname.c_str())) {
    Serial.println("Error setting up mDNS responder!");
  }

  // Add service to mDNS responder
  MDNS.addService("http", "tcp", 80);

   for (int i = 0; i < 2 ; i++) {
      strip.setPixelColor(0, strip.Color(0, 255, 0));
      strip.show();
      delay(100);
      strip.clear();
      strip.show();
      delay(50);
  }
  
  server.on("/", [](){
    server.send(200, "text/html", "<h1>Hello from ESP8266!</h1>");
  });

  server.on("/colors", HTTP_POST, [](){
    String payload = server.arg("plain");
    Serial.println("Received payload: " + payload);

    // Parse JSON payload
    const size_t capacity = JSON_ARRAY_SIZE(43) + 43*JSON_OBJECT_SIZE(3);
    DynamicJsonDocument doc(capacity);
    DeserializationError error = deserializeJson(doc, payload);
    if (error) {
      Serial.println("Error parsing JSON payload!");
      server.send(400, "text/plain", "Bad Request");
      return;
    }

    // Extract color values from JSON payload
    JsonArray colors = doc.as<JsonArray>();
    strip.clear();
    for (int i = 0; i < strip.numPixels() && i < colors.size(); i++) {
      Serial.println(i);
      JsonObject color = colors[i];
      uint8_t red = color["r"];
      uint8_t green = color["g"];
      uint8_t blue = color["b"];
      strip.setPixelColor(i, strip.Color(red, green, blue));
    }

    strip.show(); // Update LED strip
    server.send(200, "text/plain", "OK");
  });

  server.on("/blink", [](){
  // Generate a random color
  uint8_t red = random(256);
  uint8_t green = random(256);
  uint8_t blue = random(256);

  strip.clear();
  strip.show();

  // Blink the first 7 LEDs 3 times
  for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 7; j++) {
      strip.setPixelColor(j, strip.Color(red, green, blue));
    }
    strip.show();
    delay(500);
    strip.clear();
    strip.show();
    delay(500);
  }

  server.send(200, "text/plain", "OK");
  });



  server.begin();
}

void loop() {
  MDNS.update(); // Refresh mDNS responder
  server.handleClient(); // Handle incoming client 
}
