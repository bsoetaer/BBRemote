#include "MouseProxy.hpp"

const map<char,char> MouseProxy::bbRemoteButtonToHID = {
	{ 0, 0b00000001 },
	{ 1, 0b00000010 },
	{ 2, 0b00000100 }
};

MouseProxy::MouseProxy() : DriverProxy("bbMouseBuffer") {}

MouseProxy::~MouseProxy() {}

void MouseProxy::handleData(char *data, int bytes)
{
	UCHAR *inputData;
	if (data[0] == DATA_TYPE_BUTTON)
		inputData = handleButton(data + 1, bytes - 1);
	else if (data[0] == DATA_TYPE_AXIS)
		inputData = handleAxis(data + 1, bytes - 1);
	else
		return;
	sendDataToDriver(inputData, 3);
}

UCHAR * MouseProxy::handleAxis(char *data, int bytes)
{
	UCHAR inputData[3] = { lastButtonPresses, lastMovement[0], lastMovement[1] };
	for (int i = 0; i < bytes; i += 2)
		inputData[data[i]] = data[i + 1];

	lastMovement[0] = inputData[0];
	lastMovement[1] = inputData[1];

	return inputData;
}

UCHAR * MouseProxy::handleButton(char *data, int bytes)
{
	UCHAR inputData[3] = { lastButtonPresses, lastMovement[0], lastMovement[1] };

	// For each button, if the button-pressed value is 1, include it in the inputData; else, exclude it.
	// Since button-up is 0b00000000, and button-down is 0b11111111, we can "and" it with the HID mask to do this.
	for (int i = 0; i < bytes; i += 2)
		inputData[0] |= (bbRemoteButtonToHID.at(data[i]) & data[i+1]);

	lastButtonPresses = inputData[0];

	return inputData;
}