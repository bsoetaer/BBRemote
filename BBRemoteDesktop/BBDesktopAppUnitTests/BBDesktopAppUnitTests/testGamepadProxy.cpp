#include "stdafx.h"
#include "CppUnitTest.h"
#include "../../app/app/GamepadProxy.hpp"
#include "../../app/app/HandleDataErrorCodes.hpp"
#include "../../app/app/GlobalFile.hpp"
#include "../../app/app/Modes.hpp"
#include "FileLoader.hpp"

#define GAMEPAD_PACKET_LENGTH 7
#define fileName L"bbRemoteBuffer"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace BBGamepadProxyTests
{
	GamepadProxy *testProxy;

	TEST_CLASS(TestGamepadProxy)
	{
	public:

		void init()
		{
			GlobalFile::createBufferFileHandle(fileName);
			testProxy = new GamepadProxy();
		}

		TEST_METHOD(TestNotAxisOrButton)
		{
			init();
			char data[3];
			data[0] = 5;
			int errorCode = testProxy->handleData(data, 2);
			Assert::AreEqual(ERROR_NOT_AXIS_OR_BUTTON, errorCode);
		}

		TEST_METHOD(TestAxisUnevenByteNumber)
		{
			init();
			char data[4]; // 3 bytes plus the data type
			data[0] = DriverProxy::DATA_TYPE_AXIS;
			int errorCode = testProxy->handleData(data, 4);
			Assert::AreEqual(ERROR_UNEVEN_BYTE_COUNT, errorCode);
		}

		TEST_METHOD(TestAxisNoSuchAxis)
		{
			init();
			char data[3];
			data[0] = DriverProxy::DATA_TYPE_AXIS;
			data[1] = 4; // axis 4 (doesn't exist)
			data[2] = 0; // magnitude of 0
			int errorCode = testProxy->handleData(data, 3);
			Assert::AreEqual(ERROR_AXIS_DOES_NOT_EXIST, errorCode);
		}

		TEST_METHOD(TestButtonUnevenByteNumber)
		{
			init();
			char data[4]; // 3 bytes plus the data type
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			int errorCode = testProxy->handleData(data, 4);
			Assert::AreEqual(ERROR_UNEVEN_BYTE_COUNT, errorCode);
		}

		TEST_METHOD(TestButtonNoSuchButton)
		{
			init();
			char data[3];
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 16; // button 16 (doesn't exist)
			data[2] = 0; // value of 0
			int errorCode = testProxy->handleData(data, 3);
			Assert::AreEqual(ERROR_BUTTON_DOES_NOT_EXIST, errorCode);
		}

		TEST_METHOD(TestButtonValueWrong)
		{
			init();
			char data[3];
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 0; // button 0
			data[2] = 1; // should be 0 or -1
			int errorCode = testProxy->handleData(data, 3);
			Assert::AreEqual(ERROR_BUTTON_VALUE_INVALID, errorCode);
		}

		TEST_METHOD(TestButtonWriteNothing)
		{
			init();
			char data[3];
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 0; // button 0
			data[2] = 0; // blank data to set up the file
			testProxy->handleData(data, 3);

			char bufferData[GAMEPAD_PACKET_LENGTH];
			FileLoader::readData(bufferData, fileName, GAMEPAD_PACKET_LENGTH);
			Assert::AreEqual((char)Mode::GAMEPAD, bufferData[0]);
			Assert::AreEqual((char)0, bufferData[1]);
			Assert::AreEqual((char)0, bufferData[2]);
			Assert::AreEqual((char)0, bufferData[3]);
			Assert::AreEqual((char)0, bufferData[4]);
			Assert::AreEqual((char)0, bufferData[5]);
			Assert::AreEqual((char)0, bufferData[6]);
		}

		TEST_METHOD(TestButtonWriteButton)
		{
			init();
			char data[3];
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 0; // button 0
			data[2] = -1; // button down
			testProxy->handleData(data, 3);

			char bufferData[GAMEPAD_PACKET_LENGTH];
			FileLoader::readData(bufferData, fileName, GAMEPAD_PACKET_LENGTH);
			Assert::AreEqual((char)Mode::GAMEPAD, bufferData[0]);
			Assert::AreEqual((char)0b00000001, bufferData[1]);
			Assert::AreEqual((char)0, bufferData[2]);
			Assert::AreEqual((char)0, bufferData[3]);
			Assert::AreEqual((char)0, bufferData[4]);
			Assert::AreEqual((char)0, bufferData[5]);
			Assert::AreEqual((char)0, bufferData[6]);
		}

		TEST_METHOD(TestButtonWriteButtonThenAnotherButton)
		{
			init();
			char data[3];
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 0; // button 0
			data[2] = -1; // button down
			testProxy->handleData(data, 3);
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 1; // button 1
			data[2] = -1; // button down
			testProxy->handleData(data, 3);

			char bufferData[GAMEPAD_PACKET_LENGTH];
			FileLoader::readData(bufferData, fileName, GAMEPAD_PACKET_LENGTH);
			Assert::AreEqual((char)Mode::GAMEPAD, bufferData[0]);
			Assert::AreEqual((char)0b00000011, bufferData[1]);
			Assert::AreEqual((char)0, bufferData[2]);
			Assert::AreEqual((char)0, bufferData[3]);
			Assert::AreEqual((char)0, bufferData[4]);
			Assert::AreEqual((char)0, bufferData[5]);
			Assert::AreEqual((char)0, bufferData[6]);
		}

		TEST_METHOD(TestButtonWriteButtonThenAnotherButtonThenReleaseFirst)
		{
			init();
			char data[3];
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 0; // button 0
			data[2] = -1; // button down
			testProxy->handleData(data, 3);
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 1; // button 1
			data[2] = -1; // button down
			testProxy->handleData(data, 3);
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 0; // button 1
			data[2] = 0; // button down
			testProxy->handleData(data, 3);

			char bufferData[GAMEPAD_PACKET_LENGTH];
			FileLoader::readData(bufferData, fileName, GAMEPAD_PACKET_LENGTH);
			Assert::AreEqual((char)Mode::GAMEPAD, bufferData[0]);
			Assert::AreEqual((char)0b00000010, bufferData[1]);
			Assert::AreEqual((char)0, bufferData[2]);
			Assert::AreEqual((char)0, bufferData[3]);
			Assert::AreEqual((char)0, bufferData[4]);
			Assert::AreEqual((char)0, bufferData[5]);
			Assert::AreEqual((char)0, bufferData[6]);
		}

		TEST_METHOD(TestAxisWrite)
		{
			init();
			char data[3];
			data[0] = DriverProxy::DATA_TYPE_AXIS;
			data[1] = 0; // axis 0
			data[2] = 7; // small movement
			testProxy->handleData(data, 3);

			char bufferData[GAMEPAD_PACKET_LENGTH];
			FileLoader::readData(bufferData, fileName, GAMEPAD_PACKET_LENGTH);
			Assert::AreEqual((char)Mode::GAMEPAD, bufferData[0]);
			Assert::AreEqual((char)0, bufferData[1]);
			Assert::AreEqual((char)0, bufferData[2]);
			Assert::AreEqual((char)0b00000111, bufferData[3]);
			Assert::AreEqual((char)0, bufferData[4]);
			Assert::AreEqual((char)0, bufferData[5]);
			Assert::AreEqual((char)0, bufferData[6]);
		}

		TEST_METHOD(TestAxisLetGo)
		{
			init();
			char data[3];
			data[0] = DriverProxy::DATA_TYPE_AXIS;
			data[1] = 0; // axis 0
			data[2] = 7; // small movement
			testProxy->handleData(data, 3);
			data[1] = 0; // axis 0
			data[2] = 0; // no movement
			testProxy->handleData(data, 3);

			char bufferData[GAMEPAD_PACKET_LENGTH];
			FileLoader::readData(bufferData, fileName, GAMEPAD_PACKET_LENGTH);
			Assert::AreEqual((char)Mode::GAMEPAD, bufferData[0]);
			Assert::AreEqual((char)0, bufferData[1]);
			Assert::AreEqual((char)0, bufferData[2]);
			Assert::AreEqual((char)0, bufferData[3]);
			Assert::AreEqual((char)0, bufferData[4]);
			Assert::AreEqual((char)0, bufferData[5]);
			Assert::AreEqual((char)0, bufferData[6]);
		}

	};
}