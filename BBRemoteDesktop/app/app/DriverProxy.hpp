#pragma once
#include <Windows.h>
#include <stdio.h>
#include "Modes.h"
using namespace std;

class DriverProxy
{
public:
	static const int DATA_TYPE_MODE_CHANGE;
	static const int DATA_TYPE_BUTTON;
	static const int DATA_TYPE_AXIS;

	DriverProxy(wchar_t *bufferFileName);
	virtual void handleData(char *data, int bytes) = 0;
	void deactivateDevice();
protected:
	Mode modeId;
	int packetSize;
	void sendDataToDriver(UCHAR *data, int dataLen);
	void sendDataToDriver(UCHAR *data, int dataLen, BOOL active);
private:
	HANDLE bufferFileHandle;
	void createBufferFile(wchar_t *bufferFileName);
};