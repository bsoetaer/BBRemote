#include "stdafx.h"
#include "FileLoader.hpp"

bool FileLoader::readData(char *output, wchar_t *fileName, int numberOfBytes)
{
	SetFilePointer(
		GlobalFile::bufferFileHandle,
		0,
		NULL,
		FILE_BEGIN
		);

	return ReadFile(
		GlobalFile::bufferFileHandle,
		output,
		numberOfBytes,
		NULL,
		NULL);
}