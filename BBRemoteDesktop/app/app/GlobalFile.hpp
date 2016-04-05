#pragma once
#include <Windows.h>
#include <stdio.h>

class GlobalFile
{
public:
	static void createBufferFileHandle();
	static void createBufferFileHandle(wchar_t *fileName);
	static HANDLE bufferFileHandle;
};