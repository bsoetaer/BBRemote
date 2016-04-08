/*
Requirements covered:
3.2.3.2.1. Send Key Input
3.2.4.2.1. Send Mouse Movement
3.2.4.2.3. Left Click
3.2.4.2.4. Right Click
3.2.4.2.5. Scrolling
3.2.5.2.1. Send Mouse Movement
3.2.5.2.4. Left Click
3.2.5.2.5. Right Click
3.2.5.2.6. Scrolling
3.2.5.2.8. Middle Mouse Button
3.2.7.2.1. Press Gamepad Button
3.2.7.2.2. Adjust Gamepad Axis
3.2.7.2.3. Press Toggle Button
3.2.7.2.7. Multiple Input Press
*/

#pragma once
#include <stdio.h>
#include <Windows.h>
#include "../../app/app/GlobalFile.hpp"

class FileLoader
{
public:
	static bool readData(char *output, wchar_t *fileName, int numberOfBytes);
};