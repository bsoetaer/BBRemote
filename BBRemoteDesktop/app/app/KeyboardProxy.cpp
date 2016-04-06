#include "KeyboardProxy.hpp"

KeyboardProxy::KeyboardProxy() : DriverProxy() 
{
	modeId = Mode::KEYBOARD;
}

KeyboardProxy::~KeyboardProxy() {}

int KeyboardProxy::handleData(char *data, int bytes)
{
	// Should always be a button press
	if (data[0] == DATA_TYPE_AXIS)
		return ERROR_NOT_AXIS; // TODO: Error handling
	return handleRawData(data + 1, bytes - 1);
}

int KeyboardProxy::handleRawData(char *data, int bytes)
{
	int error = validateData(data, bytes);
	if (error != SUCCESS)
		return error;

	UCHAR inputData[KEYBOARD_PACKET_SIZE] = { 0 };
	set<UCHAR> currentKeysDown;
	copySet(&currentKeysDown, &lastKeysDown);

	// start with the keys that had no state change
	for (int i = 0; i < bytes; i += 2)
		if (lastKeysDown.find(data[i]) != lastKeysDown.end())
			currentKeysDown.erase(data[i]);

	// Do the rest of the data; there is a 6 key cap
	for (int i = 0; i < bytes && currentKeysDown.size() < 6; i += 2)
		if (data[i + 1])
			currentKeysDown.insert(data[i]);

	// create the data to send
	int index = 2;
	set<UCHAR>::iterator it;
	for (it = currentKeysDown.begin(); it != currentKeysDown.end(); ++it)
	{
		if (*it == 226) // left alt
			inputData[0] |= 0b00000100;
		if (*it == 224) // left control
			inputData[0] |= 0b00000001;
		if (*it == 225) // left shift
			inputData[0] |= 0b00000010;
		if (*it == 231) // left GUI
			inputData[0] |= 0b00001000;
		inputData[index] = *it;
		index++;
	}

	sendDataToDriver(inputData, KEYBOARD_PACKET_SIZE);

	copySet(&lastKeysDown, &currentKeysDown);

	return SUCCESS;
}

int KeyboardProxy::validateData(char *data, int bytes)
{
	if (bytes % 2 != 0)
		return ERROR_UNEVEN_BYTE_COUNT;
	for (int i = 1; i < bytes; i+=2)
		if (data[i] != 0 && data[i] != -1)
			return ERROR_BUTTON_VALUE_INVALID;
	return SUCCESS;
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