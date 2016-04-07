#pragma once
#include <stdio.h>
#include <Windows.h>
#include "../../app/app/GlobalFile.hpp"

class FileWriter
{
public:
	static bool writeData(char *input, int numberOfBytes);
};