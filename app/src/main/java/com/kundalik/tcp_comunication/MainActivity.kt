package com.kundalik.tcp_comunication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.io.*
import java.net.Socket
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var messageInput: EditText
    private lateinit var sendButton: Button
    private lateinit var chatDisplay: TextView

    private var socket: Socket? = null
    private var writer: PrintWriter? = null
    private var reader: BufferedReader? = null

    private val serverIP = "192.168.1.41" // Replace with your server's IP
    private val serverPort = 6000         // Match server's port

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)
        chatDisplay = findViewById(R.id.chatDisplay)

        connectToServer()

        sendButton.setOnClickListener {
            val message = messageInput.text.toString()
            sendMessage(message)
            messageInput.text.clear()
        }
    }

    private fun connectToServer() {
        thread {
            try {
                socket = Socket(serverIP, serverPort)
                writer = PrintWriter(BufferedWriter(OutputStreamWriter(socket!!.getOutputStream())), true)
                reader = BufferedReader(InputStreamReader(socket!!.getInputStream()))

                listenForMessages()

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun sendMessage(message: String) {
        thread {
            try {
                writer?.println(message)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun listenForMessages() {
        try {
            var line: String?
            while (reader?.readLine().also { line = it } != null) {
                val received = line
                runOnUiThread {
                    chatDisplay.append("Server: $received\n")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            socket?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
