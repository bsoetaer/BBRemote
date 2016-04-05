#pragma once
#include <stdio.h>
#include <map>
#include "DriverProxy.hpp"

using namespace std;

class MouseProxy : public DriverProxy
{
public:
	MouseProxy();
	~MouseProxy();
	int handleData(char *data, int bytes);

private:
	static const map<char, char> bbRemoteButtonToHID;
	UCHAR lastMovement[3] = { 0 };
	UCHAR lastButtonPresses = 0;
	int handleButton(_Out_ UCHAR *formattedData, char *data, int bytes);
	int handleAxis(_Out_ UCHAR formattedData[MOUSE_PACKET_SIZE], char *data, int bytes);
	int validateButtonData(char *data, int bytes);
	int validateAxisData(char *data, int bytes);
protected:
	const int packetSize = MOUSE_PACKET_SIZE;
};