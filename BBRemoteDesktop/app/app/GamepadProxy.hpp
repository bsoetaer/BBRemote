#pragma once
#include <stdio.h>
#include <set>
#include "DriverProxy.hpp"
#include <map>
using namespace std;

class GamepadProxy : public DriverProxy
{
public:
	GamepadProxy();
	~GamepadProxy();
	void handleData(char *data, int bytes);
protected:
	const int packetSize = GAMEPAD_PACKET_SIZE;
private:
	static const map<char, char> bbRemoteButtonToHID;
	UCHAR lastButtonPresses[2] = { 0 };
	UCHAR lastMovement[4] = { 0 };
	void handleButton(_Out_ UCHAR *formattedData, char *data, int bytes);
	void handleAxis(_Out_ UCHAR formattedData[GAMEPAD_PACKET_SIZE], char *data, int bytes);
	bool validateButtonData(char *data, int bytes);
	bool validateAxisData(char *data, int bytes);
};