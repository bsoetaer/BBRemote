#include "BluetoothReceiver.hpp"
#include "GlobalFile.hpp"

int wmain() {
	GlobalFile::createBufferFileHandle();
	BluetoothReceiver receiver = BluetoothReceiver();
	receiver.receiveData();
}