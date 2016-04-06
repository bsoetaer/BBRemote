#include "GlobalFile.hpp"

HANDLE GlobalFile::bufferFileHandle = NULL;

void GlobalFile::createBufferFileHandle()
{
		wchar_t filePath[MAX_PATH];
		wchar_t fullFilePath[MAX_PATH];

		DWORD pathLength = GetWindowsDirectory(
			filePath,
			MAX_PATH
			);

		swprintf_s(fullFilePath, MAX_PATH, L"%s\\Temp\\%s", filePath, L"bbRemoteBuffer");

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