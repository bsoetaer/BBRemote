#pragma once
#include <Windows.h>
#include <stdio.h>
#include "Modes.hpp"
#include "GlobalFile.hpp"
#include "HandleDataErrorCodes.hpp"
using namespace std;

class DriverProxy
{
public:
	static const int DATA_TYPE_MODE_CHANGE;
	static const int DATA_TYPE_BUTTON;
	static const int DATA_TYPE_AXIS;

	DriverProxy();
	virtual int handleData(char *data, int bytes) = 0;
	void deactivateDevice();
	Mode getModeId();
protected:
	Mode modeId;
	int packetSize;
	void sendDataToDriver(UCHAR *data, int dataLen);
	void sendDataToDriver(UCHAR *data, int dataLen, Mode activeMode);
};