package peers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import peers.ui.devices_list.DeviceDetails
import peers.ui.devices_list.NearByDevice
import kotlin.test.Test

class DeviceDetailsTestAndroid {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun disConnectedDeviceInfoTest() = runComposeUiTest {
        val device = NearByDevice(name = "Md Abdul", isConnected = false, ip = "1234")
        setContent {
            DeviceDetails(
                device = device,
                onClose = {},
                onDisconnectRequest = {}
            )
        }
        onNodeWithTag(":DeviceNameText").assertTextEquals("Device Name: Md Abdul")
        onNodeWithTag(":IPAddressText").assertTextEquals("IP Address: 1234")
        //on connected "Cancel" Button will be will be ,"Disconnect" will be shown
        onNodeWithTag(":Dialog:DisconnectButton").assertDoesNotExist()
        onNodeWithTag(":Dialog:CancelButton").assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun dismissDialog() = runComposeUiTest {
        val device = NearByDevice(name = "Md Abdul", isConnected = false, ip = "1234")
        var showDialog by mutableStateOf(true)

        setContent {
            if (showDialog) {
                DeviceDetails(
                    modifier = Modifier.testTag(":Dialog"),
                    device = device,
                    onClose = {
                        showDialog = false
                    },
                    onDisconnectRequest = {
                        showDialog = false
                    }
                )
            }

        }
        onNodeWithTag(":Dialog").assertExists()
        //Click for dismiss dialog
        onNodeWithTag(":Dialog:CancelButton").performClick()
        onNodeWithTag(":Dialog").assertDoesNotExist()
    }
}