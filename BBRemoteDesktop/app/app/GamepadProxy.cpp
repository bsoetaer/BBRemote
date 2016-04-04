#include "GamepadProxy.hpp"

GamepadProxy::GamepadProxy() : DriverProxy()
{
	modeId = Mode::GAMEPAD;
}

GamepadProxy::~GamepadProxy() {}

void GamepadProxy::handleData(char *data, int bytes)
{
	UCHAR inputData[GAMEPAD_PACKET_SIZE] = { 0 };
	if (data[0] == DATA_TYPE_BUTTON)
		handleButton(inputData, data + 1, bytes - 1);
	else if (data[0] == DATA_TYPE_AXIS)
		handleAxis(inputData, data + 1, bytes - 1);
	else
		return;
	sendDataToDriver(inputData, GAMEPAD_PACKET_SIZE);
}

void GamepadProxy::handleAxis(_Out_ UCHAR formattedData[GAMEPAD_PACKET_SIZE], char *data, int bytes)
{
	UCHAR inputData[GAMEPAD_PACKET_SIZE] = { lastButtonPresses[0], lastButtonPresses[1], lastMovement[0], lastMovement[1], lastMovement[2], lastMovement[3] };

	for (int i = 0; i < bytes; i += 2)
		inputData[data[i] + 2] = data[i + 1];

	lastMovement[0] = inputData[2];
	lastMovement[1] = inputData[3];
	lastMovement[2] = inputData[4];
	lastMovement[3] = inputData[5];

	memcpy(formattedData, inputData, GAMEPAD_PACKET_SIZE);
}

void GamepadProxy::handleButton(_Out_ UCHAR *formattedData, char *data, int bytes)
{
	UCHAR inputData[GAMEPAD_PACKET_SIZE] = { lastButtonPresses[0], lastButtonPresses[1], lastMovement[0], lastMovement[1], lastMovement[2], lastMovement[3] };

	// For each button, if the button-pressed value is 1, include it in the inputData; else, exclude it.
	// Since button-up is 0b00000000, and button-down is 0b11111111, we can "and" it with the HID mask to do this.

	for (int i = 0; i < bytes; i += 2)
	{
		int firstOrSecondIndex = (int)data[i] > 7;
		inputData[firstOrSecondIndex] = ((inputData[firstOrSecondIndex] | bbRemoteButtonToHID.at(data[i])) ^ bbRemoteButtonToHID.at(data[i])) | (bbRemoteButtonToHID.at(data[i]) & data[i + 1]); // the first or and xor clear the bit that was requested, and the last or writes the new bit
	}

	lastButtonPresses[0] = inputData[0];
	lastButtonPresses[1] = inputData[1];

	memcpy(formattedData, inputData, GAMEPAD_PACKET_SIZE);
}

bool GamepadProxy::validateButtonData(char *data, int bytes)
{
	if (bytes % 2 != 0)
		return false;
	for (int i = 1; i < bytes; i += 2)
		if (data[i] != 0 && data[i] != -1)
			return false;
	for (int i = 0; i < bytes; i += 2)
		if (bbRemoteButtonToHID.find(data[i]) == bbRemoteButtonToHID.end())
			return false;
	return true;
}

bool GamepadProxy::validateAxisData(char *data, int bytes)
{
	if (bytes % 2 != 0)
		return false;
	for (int i = 0; i < bytes; i += 2)
		if (data[i] < 0 || data[i] > 15)
			return false;
	return true;
}

const map<char, char> GamepadProxy::bbRemoteButtonToHID = {
	{ 0, 0b00000001 },
	{ 1, 0b00000010 },
	{ 2, 0b00000100 },
	{ 3, 0b00001000 },
	{ 4, 0b00010000 },
	{ 5, 0b00100000 },
	{ 6, 0b01000000 },
	{ 7, 0b10000000 },
	{ 8, 0b00000001 },
	{ 9, 0b00000010 },
	{ 10, 0b00000100 },
	{ 11, 0b00001000 },
	{ 12, 0b00010000 },
	{ 13, 0b00100000 },
	{ 14, 0b01000000 },
	{ 15, 0b10000000 }
};