/*
Requirements covered:
3.2.4.2.1. Send Mouse Movement
3.2.4.2.3. Left Click
3.2.4.2.4. Right Click
3.2.4.2.5. Scrolling
3.2.5.2.1. Send Mouse Movement
3.2.5.2.4. Left Click
3.2.5.2.5. Right Click
3.2.5.2.6. Scrolling
3.2.5.2.8. Middle Mouse Button
*/

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