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
	void handleData(char *data, int bytes);

private:
	static const map<char, char> bbRemoteButtonToHID;
	UCHAR lastMovement[2] = { 0 };
	UCHAR lastButtonPresses = 0;
	UCHAR * handleButton(char *data, int bytes);
	UCHAR * handleAxis(char *data, int bytes);
};