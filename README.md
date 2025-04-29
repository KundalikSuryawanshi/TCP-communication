
# 📡 TCP Communication Demo (Android + Java Server)

This is a **demo application** that showcases **TCP communication** between an **Android client** and a **Java server**. The Android app sends messages to the server, and the server receives and echoes them back.

---

## 📁 Repository

Clone this project from GitHub:

```bash
git clone https://github.com/KundalikSuryawanshi/TCP-communication.git
```

---

## 🖥️ TCP Server Setup (Java)

To run the TCP server:

### 1. Create a file named `MyServiceServer.java`

Paste the following code into it:

```java
import java.io.*;
import java.net.*;

public class MyServiceServer {
    public static void main(String[] args) {
        int port = 1234;  // Add your port number here

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Service started. Listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected!");

                new Thread(new ClientHandler(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);

                // Echo the received message
                out.println("Echo: " + inputLine);

                if ("exit".equalsIgnoreCase(inputLine)) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

### 2. Compile and Run the Server

Open terminal and navigate to the folder where your `MyServiceServer.java` file is located. Then run:

```bash
# Compile the server code
javac MyServiceServer.java

# Run the server
java MyServiceServer
```

### ✅ Example Output

```
Service started. Listening on port 1234
Client connected!
Received: hi
Received: Gaurav here
Received: works.....
```

---

## 📱 Android Client Setup

### Requirements

- Android Studio
- Android device or emulator (API level 21+)
- Internet permission in `AndroidManifest.xml`

### Steps

1. Open the cloned project in Android Studio.
2. Update the IP address and port in the app’s socket connection code to match your local server setup.
3. Run the app on your device/emulator.
4. Send messages and check server output for responses.

---

## 🔐 Android Permission

Make sure to add the following permission in your `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

---

## 🖼️ Screenshot

Here’s the terminal output from the Java server:

Java Server Running

<img width="705" alt="Screenshot 2025-04-29 at 4 18 26 PM" src="https://github.com/user-attachments/assets/06b2ea19-b26f-4161-af7c-29d28c39fd03" />


---

## 📚 How It Works

- **Java Server**: Listens on a TCP port and spawns a new thread for each client connection.
- **Android Client**: Opens a TCP socket to the server, sends messages, and receives echoed responses.
- **Communication**: Simple text-based TCP over sockets.

---

## 📃 License

This project is open-source and available under the [MIT License](LICENSE).

---

## 🙌 Credits

Created by Kundalik Suryawanshi for educational and demo purposes.
