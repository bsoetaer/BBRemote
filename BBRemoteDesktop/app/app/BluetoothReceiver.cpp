/*
Requirements Covered: see associated header file, included below
*/

#include "BluetoothReceiver.hpp"

BluetoothReceiver::BluetoothReceiver()
{
	init(new ModeSwitcher());
}

BluetoothReceiver::BluetoothReceiver(ModeSwitcher *switcher)
{
	init(switcher);
}

BluetoothReceiver::BluetoothReceiver(ModeSwitcher *switcher, DriverProxy *initialProxy)
{
	init(switcher, initialProxy);
}

void BluetoothReceiver::init(ModeSwitcher *switcher)
{
	init(switcher, new KeyboardProxy());
}

void BluetoothReceiver::init(ModeSwitcher *switcher, DriverProxy *initialProxy)
{
	this->switcher = switcher;
	currentProxy = initialProxy;
}

BluetoothReceiver::~BluetoothReceiver()
{
}

void BluetoothReceiver::receiveData()
{
	SOCKET localSocket;
	SOCKET clientSocket;
	UINT lengthReceived = 0;
	char *dataBuffer = NULL;
	char *dataBufferIndex = NULL;
	wchar_t *instanceName = NULL;
	wchar_t szThisComputerName[MAX_COMPUTERNAME_LENGTH + 1];
	DWORD dwLenComputerName = MAX_COMPUTERNAME_LENGTH + 1;
	size_t instanceNameSize = 0;
	LPCSADDR_INFO lpCSAddrInfo = NULL;

	int addressLen = sizeof(SOCKADDR_BTH);
	WSADATA WSAData = { 0 };
	WSAQUERYSET wsaQuerySet = { 0 };

	SOCKADDR_BTH localAddress = { 0 };
	localAddress.addressFamily = AF_BTH;
	localAddress.btAddr = 0;
	localAddress.serviceClassId = GUID_NULL;
	localAddress.port = BT_PORT_ANY;

	if (WSAStartup(MAKEWORD(2, 2), &WSAData) != CXN_SUCCESS) {
		int i = 0;
	}

	localSocket = socket(AF_BTH, SOCK_STREAM, BTHPROTO_RFCOMM);

	if (localSocket == INVALID_SOCKET) {
		int i = 0;
	}

	if (bind(localSocket, (struct sockaddr *)&localAddress, sizeof(SOCKADDR_BTH)) == SOCKET_ERROR) {
		int i = 0;
	}

	if (getsockname(localSocket, (struct sockaddr *)&localAddress, &addressLen) == SOCKET_ERROR) {
		int i = 0;
	}

	lpCSAddrInfo = (LPCSADDR_INFO)HeapAlloc(GetProcessHeap(),
		HEAP_ZERO_MEMORY,
		sizeof(CSADDR_INFO));

	lpCSAddrInfo[0].LocalAddr.iSockaddrLength = sizeof(SOCKADDR_BTH);
	lpCSAddrInfo[0].LocalAddr.lpSockaddr = (LPSOCKADDR)&localAddress;
	lpCSAddrInfo[0].RemoteAddr.iSockaddrLength = sizeof(SOCKADDR_BTH);
	lpCSAddrInfo[0].RemoteAddr.lpSockaddr = (LPSOCKADDR)&localAddress;
	lpCSAddrInfo[0].iSocketType = SOCK_STREAM;
	lpCSAddrInfo[0].iProtocol = BTHPROTO_RFCOMM;

	GetComputerName(szThisComputerName, &dwLenComputerName);
	StringCchLength(szThisComputerName, sizeof(szThisComputerName), &instanceNameSize);
	instanceName = (LPWSTR)HeapAlloc(GetProcessHeap(), HEAP_ZERO_MEMORY, instanceNameSize);

	ZeroMemory(&wsaQuerySet, sizeof(WSAQUERYSET));
	wsaQuerySet.dwSize = sizeof(WSAQUERYSET);
	wsaQuerySet.lpServiceClassId = (LPGUID)&bbGUID;
	StringCbPrintf(instanceName, instanceNameSize, L"%s %s", szThisComputerName, CXN_INSTANCE_STRING);
	wsaQuerySet.lpszServiceInstanceName = instanceName;
	wsaQuerySet.dwNameSpace = NS_BTH;
	wsaQuerySet.dwNumberOfCsAddrs = 1;
	wsaQuerySet.lpcsaBuffer = lpCSAddrInfo;


	if (WSASetService(&wsaQuerySet, RNRSERVICE_REGISTER, 0) == SOCKET_ERROR) {
		int i = 0;
	}


	if (listen(localSocket, LISTEN_BACKLOG_SIZE) == SOCKET_ERROR) {
		int i = 0;
	}
	
	while (true)
	{
		clientSocket = accept(localSocket, NULL, NULL);

		if (clientSocket == INVALID_SOCKET) {
			continue;
		}
		
		dataBuffer = (char *)HeapAlloc(GetProcessHeap(),
			HEAP_ZERO_MEMORY,
			DEFAULT_DATA_LENGTH);
		dataBufferIndex = dataBuffer;
		
		while (true) {
			lengthReceived = recv(clientSocket,
				(char *)dataBufferIndex,
				DEFAULT_DATA_LENGTH,
				0);
			if (lengthReceived == SOCKET_ERROR) {
				break;
			}
			else if (lengthReceived == 0) {
				break;
			}
			handleData(dataBufferIndex, lengthReceived);
		}
	}

	if (clientSocket != INVALID_SOCKET) {
		closesocket(clientSocket);
	}

	if (localSocket != INVALID_SOCKET) {
		closesocket(clientSocket);
	}

	if (instanceName != NULL) {
		HeapFree(GetProcessHeap(), 0, instanceName);
	}

	if (dataBuffer != NULL) {
		HeapFree(GetProcessHeap(), 0, dataBuffer);
	}
}

void BluetoothReceiver::handleData(char* data, int bytesInData) {
	if (DriverProxy::DATA_TYPE_MODE_CHANGE == *data) { // Special code for switching modes
		currentProxy = switcher->switchMode(data + 1, currentProxy);
	} else {
		currentProxy->handleData(data, bytesInData);
	}
}

void BluetoothReceiver::cleanup() {}

DriverProxy *BluetoothReceiver::getCurrentProxy()
{
	return currentProxy;
}
