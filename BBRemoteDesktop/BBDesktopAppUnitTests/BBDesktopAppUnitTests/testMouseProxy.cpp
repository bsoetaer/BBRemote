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

#include "stdafx.h"
#include "CppUnitTest.h"
#include "../../app/app/MouseProxy.hpp"
#include "../../app/app/HandleDataErrorCodes.hpp"
#include "../../app/app/GlobalFile.hpp"
#include "../../app/app/Modes.hpp"
#include "FileLoader.hpp"

#define MOUSE_PACKET_LENGTH 5
#define fileName L"bbRemoteBufferTest"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace BBMouseProxyTests
{
	MouseProxy *testProxy;

	TEST_CLASS(TestMouseProxy)
	{
	public:

		void init()
		{
			GlobalFile::createBufferFileHandle(fileName);
			testProxy = new MouseProxy();
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
			data[1] = 3; // axis 3 (doesn't exist)
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
			data[1] = 3; // button 3 (doesn't exist)
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

			char bufferData[MOUSE_PACKET_LENGTH];
			FileLoader::readData(bufferData, fileName, MOUSE_PACKET_LENGTH);
			Assert::AreEqual((char)Mode::OPTICAL, bufferData[0]);
			Assert::AreEqual((char)0, bufferData[1]);
			Assert::AreEqual((char)0, bufferData[2]);
			Assert::AreEqual((char)0, bufferData[3]);
			Assert::AreEqual((char)0, bufferData[4]);
		}

		TEST_METHOD(TestButtonWriteButton)
		{
			init();
			char data[3];
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 0; // button 0
			data[2] = -1; // button down
			testProxy->handleData(data, 3);

			char bufferData[MOUSE_PACKET_LENGTH];
			FileLoader::readData(bufferData, fileName, MOUSE_PACKET_LENGTH);
			Assert::AreEqual((char)Mode::OPTICAL, bufferData[0]);
			Assert::AreEqual((char)0b00000001, bufferData[1]);
			Assert::AreEqual((char)0, bufferData[2]);
			Assert::AreEqual((char)0, bufferData[3]);
			Assert::AreEqual((char)0, bufferData[4]);
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

			char bufferData[MOUSE_PACKET_LENGTH];
			FileLoader::readData(bufferData, fileName, MOUSE_PACKET_LENGTH);
			Assert::AreEqual((char)Mode::OPTICAL, bufferData[0]);
			Assert::AreEqual((char)0b00000011, bufferData[1]);
			Assert::AreEqual((char)0, bufferData[2]);
			Assert::AreEqual((char)0, bufferData[3]);
			Assert::AreEqual((char)0, bufferData[4]);
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
			data[1] = 0; // button 0
			data[2] = 0; // button up
			testProxy->handleData(data, 3);

			char bufferData[MOUSE_PACKET_LENGTH];
			FileLoader::readData(bufferData, fileName, MOUSE_PACKET_LENGTH);
			Assert::AreEqual((char)Mode::OPTICAL, bufferData[0]);
			Assert::AreEqual((char)0b00000010, bufferData[1]);
			Assert::AreEqual((char)0, bufferData[2]);
			Assert::AreEqual((char)0, bufferData[3]);
			Assert::AreEqual((char)0, bufferData[4]);
		}

		TEST_METHOD(TestAxisWrite)
		{
			init();
			char data[3];
			data[0] = DriverProxy::DATA_TYPE_AXIS;
			data[1] = 0; // axis 0
			data[2] = 7; // small movement
			testProxy->handleData(data, 3);

			char bufferData[MOUSE_PACKET_LENGTH];
			FileLoader::readData(bufferData, fileName, MOUSE_PACKET_LENGTH);
			Assert::AreEqual((char)Mode::OPTICAL, bufferData[0]);
			Assert::AreEqual((char)0, bufferData[1]);
			Assert::AreEqual((char)0b00000111, bufferData[2]);
			Assert::AreEqual((char)0, bufferData[3]);
			Assert::AreEqual((char)0, bufferData[4]);
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

			char bufferData[MOUSE_PACKET_LENGTH];
			FileLoader::readData(bufferData, fileName, MOUSE_PACKET_LENGTH);
			Assert::AreEqual((char)Mode::OPTICAL, bufferData[0]);
			Assert::AreEqual((char)0, bufferData[1]);
			Assert::AreEqual((char)0, bufferData[2]);
			Assert::AreEqual((char)0, bufferData[3]);
			Assert::AreEqual((char)0, bufferData[4]);
		}

	};
}