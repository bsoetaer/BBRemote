/*
Requirements Covered: see associated header file, included below
*/

#include "BluetoothReceiver.hpp"
#include "GlobalFile.hpp"

int wmain() {
	GlobalFile::createBufferFileHandle();
	BluetoothReceiver receiver = BluetoothReceiver();
	receiver.receiveData();
}