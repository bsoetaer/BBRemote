#include "DriverProxy.hpp"

const int DriverProxy::DATA_TYPE_MODE_CHANGE = -1;
const int DriverProxy::DATA_TYPE_BUTTON = 0;
const int DriverProxy::DATA_TYPE_AXIS = 1;

DriverProxy::DriverProxy(wchar_t *bufferFileName)
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
	BOOL status = WriteFile(
		bufferFileHandle,
		data,
		dataSize,
		NULL,
		NULL
		);
}

void DriverProxy::createBufferFile(wchar_t *bufferFileName)
{
	wchar_t filePath[MAX_PATH];
	wchar_t fullFilePath[MAX_PATH];

	DWORD pathLength = GetWindowsDirectory(
		filePath,
		MAX_PATH
		);

	swprintf_s(fullFilePath,MAX_PATH, L"%s\\Temp\\%s", filePath, bufferFileName);

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