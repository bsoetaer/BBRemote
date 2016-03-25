#include "DriverProxy.hpp"

const int DriverProxy::DATA_TYPE_MODE_CHANGE = -1;
const int DriverProxy::DATA_TYPE_BUTTON = 0;
const int DriverProxy::DATA_TYPE_AXIS = 1;

DriverProxy::DriverProxy(char *bufferFileName)
{
	createBufferFile(bufferFileName);
}

void DriverProxy::sendDataToDriver(UCHAR *data, int dataSize)
{
	// make sure we are writing to the start of the file
	SetFilePointer(
		bufferFileHandle,
		0,
		NULL,
		FILE_BEGIN
		);

	// write data to the buffer file
	WriteFile(
		bufferFileHandle,
		data,
		dataSize,
		NULL,
		NULL
		);
}

void DriverProxy::createBufferFile(char *bufferFileName)
{
	wchar_t filePath[MAX_PATH];
	wchar_t fullFilePath[MAX_PATH];

	DWORD pathLength = GetWindowsDirectory(
		filePath,
		MAX_PATH
		);

	wprintf_s(fullFilePath, MAX_PATH, "%s\\Temp\\%s", filePath, bufferFileName);

	bufferFileHandle = CreateFile(
		fullFilePath,
		FILE_WRITE_DATA,
		FILE_SHARE_READ | FILE_SHARE_WRITE,
		NULL,
		CREATE_ALWAYS,
		FILE_ATTRIBUTE_NORMAL,
		NULL
		);

	// TODO: error handling
}