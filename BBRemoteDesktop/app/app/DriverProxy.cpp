/*
Requirements Covered: see associated header file, included below
*/

#include "DriverProxy.hpp"

const int DriverProxy::DATA_TYPE_MODE_CHANGE = -1;
const int DriverProxy::DATA_TYPE_BUTTON = 0;
const int DriverProxy::DATA_TYPE_AXIS = 1;

DriverProxy::DriverProxy() {}

void DriverProxy::sendDataToDriver(UCHAR *data, int dataLen, Mode activeMode)
{
	// make sure we are writing to the start of the file
	SetFilePointer(
		GlobalFile::bufferFileHandle,
		0,
		NULL,
		FILE_BEGIN
		);

	// include that this mode is active
	UCHAR *writeData;
	writeData = (UCHAR*)malloc(dataLen + 1);
	writeData[0] = (UCHAR)activeMode;
	memcpy_s(writeData + 1, dataLen, data, dataLen);

	// write data to the buffer file
	BOOL status = WriteFile(
		GlobalFile::bufferFileHandle,
		writeData,
		dataLen+1,
		NULL,
		NULL
		);
}

void DriverProxy::sendDataToDriver(UCHAR *data, int dataLen)
{
	sendDataToDriver(data, dataLen, modeId);
}

void DriverProxy::deactivateDevice()
{
	// To deactivate, we simply write the first byte as zero
	sendDataToDriver(0, 0, modeId);
}

Mode DriverProxy::getModeId()
{
	return modeId;
}