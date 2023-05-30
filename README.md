# peergewinnt
This is the coding project which is part of the lecture "Distributed Systems" at the Duale Hochschule Baden Württemberg.

## User Guide

To play the Connect 4 game using this program, please follow the instructions below:

1. **Requirements**: Make sure you have two devices (computers, laptops, or mobile devices) connected to the same local network. This is necessary for the game to function properly.

2. **Start the Program**: Execute the `Program.java` class in `Peergewinnt_Java_Application/src/main/java/` folder to begin the game.

3. **Game Modes**: Upon starting the program, you will be presented with two game modes:
   - **Host a New Game**: Choose this option if you want to host a new game.
   - **Join an Existing Game**: Select this option if you want to join a game hosted by another player.

4. **Wireless Display Setup**: If you have an ESP8266 with an addressable RGB LED matrix as a wireless display, follow the steps below to set it up:
   - Change the Wi-Fi SSID and password in the code provided in the `Esp_LED_Display` folder to match your own network credentials.
   - Flash the modified code onto your ESP8266 board.
   - Power up the ESP8266 and the addressable RGB LED matrix.
   - The program running on the ESP8266 will automatically connect to the Wi-Fi network indicated by a green flashing LED on and the matrix.
   - Select "Connect to Wireless Display" in the Server start process, and it will search the network for all available ESP8266 devices. A list of devices will be displayed for you to choose from.
   - Once you select an ESP8266 device, the LED matrix will blink to signal that it is connected to the game server. From this point onwards, all game updates will be displayed on the matrix as well.

5. **Hosting a Game**: If you choose to host a new game, you may be asked if you want to add a wireless display to the server. Wether you choose to add a wireless display or not, the server IP address will be displayed. Share this IP address with the second player.

6. **Starting the Game Server**: After confirming the IP address display the game server will start and wait for the other player to connect.

7. **Joining a Game**: If you choose to join a game, enter the IP address of the game server provided by the hosting player.

8. **Both Players Connected**: Once both players are successfully connected to the game server, the Connect 4 gameplay can begin. The objective of the game is to be the first to form a horizontal, vertical, or diagonal line of four discs of your color on the game board.

9. **Play Multiple Rounds**: The game allows you to play any number of rounds of Connect 4. Enjoy playing as many rounds as you like!

Please ensure that both players are connected to the same local network for smooth communication between the devices. If you are using the ESP8266 with an addressable RGB LED matrix as a wireless display, make sure it is properly set up by changing the Wi-Fi credentials and flashing the code before powering up the ESP8266 and the matrix.

## Additonal information to the Project

- **Using mDNS for Discovery**

   For the Discovery function of the esp we used mDNS (Multicast DNS), this is a protocol that allows devices on a local network to discover and communicate with each other using domain names. It enables devices to advertise their services and hostnames on the local network, making it easier for other devices to locate and connect to them without relying on a centralized DNS (Domain Name System) server. mDNS uses multicast UDP (User Datagram Protocol) packets to broadcast and receive information, making it a convenient solution for service discovery in small-scale networks without a dedicated DNS infrastructure.

- **Separation of Game/Domain Logic using Clean Architecture**:
    - In this project, the game/domain logic has been separated using the Clean Architecture approach. The Clean Architecture is a software architectural pattern that promotes separation of concerns and modular design. It divides the software into different layers, each with a specific responsibility and clear boundaries.
    - The specific benefits of using the Clean Architecture in this project, especially for Connect 4, are as follows:
        1. **Easy Creation of Multiple User Interfaces and Applications**:
            - By separating the game/domain logic from the user interface, it becomes incredibly easy to create different user interfaces and applications for Connect 4 without having to change any of the game logic code.
            - This means that the core game rules and mechanics can remain untouched while developing new interfaces, such as a desktop application, web application, mobile app, or even voice-based interfaces like a chatbot or voice assistant integration.
        2. **Flexibility for Future Enhancements and Adaptability**:
            - The Clean Architecture allows for future enhancements and adaptability of the Connect 4 game.
            - As new features or modifications are required, it becomes straightforward to extend the existing architecture without disrupting the core game logic.
            - Whether it's adding new gameplay modes, implementing AI opponents, or integrating with external systems, the modular structure of the Clean Architecture makes it easier to introduce these changes while preserving the integrity of the game/domain logic.
        3. **Maintainability and Testability of the Game Logic**:
            - With the game/domain logic isolated in its own layer, it becomes easier to maintain and test the Connect 4 game logic.
            - The separation from other components, such as the user interface or external dependencies, allows for focused unit testing of the core game rules.
            - Additionally, any modifications or enhancements made to the game logic can be done with confidence, knowing that it won't introduce unexpected side effects to other parts of the system.
        4. **Code Reusability and Portability**:
            - By adhering to the Clean Architecture principles, the game logic code becomes highly reusable and portable.
            - It can be extracted and used in different contexts or projects, allowing for easy integration with other applications or frameworks.
            - This reusability enhances code efficiency and saves development time when building similar games or applications that require the same game logic foundation.

