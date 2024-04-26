package peers.ui.devices_list

data class NearByDevice(
    val name: String,
    val isConnected: Boolean = false,
    val deviceAddress: String
)
