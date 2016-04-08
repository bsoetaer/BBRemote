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
3.2.7.2.9. Rotate Phone
3.2.9.2.1. Send Sound
3.2.10.2.1. Send Video
3.2.10.2.2. Send Audio
3.2.11.2.1. Use Mic and a touchscreen input together
*/

#pragma once
#include <Windows.h>
#include <stdio.h>

class GlobalFile
{
public:
	static void createBufferFileHandle();
	static void closeBufferFileHandle();
	static void createBufferFileHandle(wchar_t *fileName);
	static HANDLE bufferFileHandle;
};