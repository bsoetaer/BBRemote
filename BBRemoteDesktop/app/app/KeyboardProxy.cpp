#include "KeyboardProxy.hpp"

KeyboardProxy::KeyboardProxy() : DriverProxy(L"bbKbdBuffer")
{
	//lastKeysDown = new set<UCHAR>();
}

KeyboardProxy::~KeyboardProxy() {}

void KeyboardProxy::handleData(char *data, int bytes)
{
	// Should always be a button press
	if (data[0] == DATA_TYPE_AXIS)
		return; // TODO: Error handling
	handleRawData(data + 1, bytes - 1);
}

void KeyboardProxy::handleRawData(char *data, int bytes)
{
	UCHAR inputData[KEYBOARD_PACKET_SIZE] = { 0 };
	set<UCHAR> currentKeysDown;
	copySet(&currentKeysDown, &lastKeysDown);

	// start with the keys that had no state change
	for (int i = 0; i < bytes; i += 2)
		if (lastKeysDown.find(data[i]) != lastKeysDown.end())
			currentKeysDown.erase(data[i]);

	// Do the rest of the data; there is a 6 key cap
	for (int i = 0; i < bytes && currentKeysDown.size() < 6; i += 2)
		if (!data[i + 1])
			currentKeysDown.insert(data[i]);

	// create the data to send
	int index = 2;
	set<UCHAR>::iterator it;
	for (it = currentKeysDown.begin(); it != currentKeysDown.end(); ++it)
	{
		inputData[index] = *it;
		index++;
	}

	sendDataToDriver(inputData, KEYBOARD_PACKET_SIZE);


	copySet(&lastKeysDown, &currentKeysDown);
}

void KeyboardProxy::copySet(set<UCHAR> *to, set<UCHAR> *from)
{
	to->clear();
	set<UCHAR>::iterator it;
	for (it = from->begin(); it != from->end(); ++it)
	{
		to->insert(*it);
	}
}