package com.khalekuzzanman.cse.just.peertopeer.data_layer.socket_programming.server

import android.util.Log
import com.khalekuzzanman.cse.just.peertopeer.data_layer.socket_programming.DataCommunicator
import com.khalekuzzanman.cse.just.peertopeer.data_layer.socket_programming.client.Peer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ServerSocket
import java.net.Socket

class Server : Peer {
    //select port 0 as a result OS will give a available port
    //which is a better solution,to overcome same port used by multiple processes

    companion object {
        private const val TAG = "ServerClass: "
         const val availablePort=45555
    }


    private val server = ServerSocket(availablePort)
    private var socket: Socket? = null
    private var dataCommunicator: DataCommunicator? = null



    private val _lastMessage = MutableStateFlow<String?>(null)

    init {

        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {

            withContext(Dispatchers.IO) {
                try {
                    Log.d(TAG, "running")
                    while (true) {
                        socket = server.accept()
                        socket?.let { socket ->
                            dataCommunicator = DataCommunicator(socket)
                            if (socket.isConnected) {
                                Log.d(TAG, "Connected to client")
                                //listening data continuously
                                listenContinuously()
                            }
                        }
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }


    override fun sendData(data: ByteArray) {
        dataCommunicator?.sendData(data)
    }

    private fun listenContinuously() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            while (true) {
                _lastMessage.value = dataCommunicator?.readReceivedData()
            }

        }
    }


    override fun readReceivedData(): StateFlow<String?> {
        // Log.d(TAG, "receivedData()")
        return _lastMessage.asStateFlow()
    }


}