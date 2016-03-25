#pragma once
#include <Windows.h>
#include <stdio.h>
using namespace std;

class DriverProxy
{
public:
	static const int DATA_TYPE_MODE_CHANGE;
	static const int DATA_TYPE_BUTTON;
	static const int DATA_TYPE_AXIS;

	DriverProxy(char *bufferFileName);
	virtual void handleData(char *data, int bytes) = 0;
protected:
	void sendDataToDriver(UCHAR *data, int dataSize);
private:
	HANDLE bufferFileHandle;
	void createBufferFile(char *bufferFileName);
};