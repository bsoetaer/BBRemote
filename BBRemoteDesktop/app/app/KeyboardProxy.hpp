#pragma once
#include <stdio.h>
#include <set>
#include "DriverProxy.hpp"
using namespace std;

class KeyboardProxy : public DriverProxy
{
public:
	KeyboardProxy();
	~KeyboardProxy();
	void handleData(char *data, int bytes);
protected:
	const int packetSize = KEYBOARD_PACKET_SIZE;
private:
	set<UCHAR> lastKeysDown;
	void handleRawData(char *data, int bytes);
	void copySet(set<UCHAR> *to, set<UCHAR> *from);
	bool validateData(char *data, int bytes);
};