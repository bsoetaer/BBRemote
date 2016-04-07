#pragma once
#include <stdio.h>
#include <Windows.h>
#include "../../app/app/GlobalFile.hpp"

class FileLoader
{
public:
	static bool readData(char *output, wchar_t *fileName, int numberOfBytes);
};