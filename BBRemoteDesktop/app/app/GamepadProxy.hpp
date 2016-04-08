/*
Requirements covered:
3.2.7.2.1. Press Gamepad Button
3.2.7.2.2. Adjust Gamepad Axis
3.2.7.2.3. Press Toggle Button
3.2.7.2.7. Multiple Input Press
3.2.7.2.9. Rotate Phone
*/

#pragma once
#include <stdio.h>
#include <set>
#include "DriverProxy.hpp"
#include "HandleDataErrorCodes.hpp"
#include <map>
using namespace std;

class GamepadProxy : public DriverProxy
{
public:
	GamepadProxy();
	~GamepadProxy();
	int handleData(char *data, int bytes);
protected:
	const int packetSize = GAMEPAD_PACKET_SIZE;
private:
	static const map<char, char> bbRemoteButtonToHID;
	UCHAR lastButtonPresses[2] = { 0 };
	UCHAR lastMovement[4] = { 0 };
	int handleButton(_Out_ UCHAR *formattedData, char *data, int bytes);
	int handleAxis(_Out_ UCHAR formattedData[GAMEPAD_PACKET_SIZE], char *data, int bytes);
	int validateButtonData(char *data, int bytes);
	int validateAxisData(char *data, int bytes);
};