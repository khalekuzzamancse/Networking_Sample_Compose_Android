package nsd.common.endpoint

/**
 * - [Connecting] will be helpful to update ui till it connecting,and prevent user to click multiple time connect
 */
enum class EndPointStatus{
    Discovered,Connected,Initiated,Disconnected,Connecting
}