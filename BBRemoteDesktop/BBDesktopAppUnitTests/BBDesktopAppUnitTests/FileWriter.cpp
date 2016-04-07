#include "stdafx.h"
#include "FileWriter.hpp"

bool FileWriter::writeData(char *input, int numberOfBytes)
{
	SetFilePointer(
		GlobalFile::bufferFileHandle,
		0,
		NULL,
		FILE_BEGIN
		);

	return WriteFile(
		GlobalFile::bufferFileHandle,
		input,
		numberOfBytes,
		NULL,
		NULL
		);
}