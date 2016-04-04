#pragma once
#include <Windows.h>
#include <stdio.h>

class GlobalFile
{
public:
	static void createBufferFileHandle();
	static HANDLE bufferFileHandle;
};