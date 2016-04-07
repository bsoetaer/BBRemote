#include "stdafx.h"
#include "CppUnitTest.h"
#include "../../app/app/KeyboardProxy.hpp"
#include "../../app/app/HandleDataErrorCodes.hpp"
#include "../../app/app/GlobalFile.hpp"
#include "../../app/app/Modes.hpp"
#include "FileLoader.hpp"

#define fileName L"bbRemoteBufferTest"
#define KEYBOARD_BUFFER_SIZE 9

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace BBKeyboardProxyTests
{
	KeyboardProxy *testProxy;

	TEST_CLASS(TestKeyboardProxy)
	{
	public:

		void init()
		{
			GlobalFile::createBufferFileHandle(fileName);
			testProxy = new KeyboardProxy();
		}

		bool arrayContains(char *arr, int bytes, char val)
		{
			for (int i = 0; i < bytes; i++)
				if (arr[i] == val)
					return true;
			return false;
		}

		TEST_METHOD(TestNotButton)
		{
			init();
			char data[3];
			data[0] = 5;
			int errorCode = testProxy->handleData(data, 3);
			Assert::AreEqual(ERROR_NOT_BUTTON, errorCode);
		}

		TEST_METHOD(TestButtonUnevenByteNumber)
		{
			init();
			char data[4]; // 3 bytes plus the data type
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			int errorCode = testProxy->handleData(data, 4);
			Assert::AreEqual(ERROR_UNEVEN_BYTE_COUNT, errorCode);
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

			char bufferData[KEYBOARD_BUFFER_SIZE];
			FileLoader::readData(bufferData, fileName, KEYBOARD_BUFFER_SIZE);
			Assert::AreEqual((char)Mode::KEYBOARD, bufferData[0]);
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
			data[1] = 4; // "A" key
			data[2] = -1; // button down
			testProxy->handleData(data, 3);

			char bufferData[KEYBOARD_BUFFER_SIZE];
			FileLoader::readData(bufferData, fileName, KEYBOARD_BUFFER_SIZE);
			Assert::AreEqual((char)Mode::KEYBOARD, bufferData[0]);
			Assert::AreEqual((char)0, bufferData[1]);
			Assert::AreEqual((char)0, bufferData[2]);
			Assert::AreEqual((char)4, bufferData[3]);
			Assert::AreEqual((char)0, bufferData[4]);
			Assert::AreEqual((char)0, bufferData[5]);
			Assert::AreEqual((char)0, bufferData[6]);
		}

		TEST_METHOD(TestButtonWriteButtonThenAnotherButton)
		{
			init();
			char data[3];
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 4; // "A" key
			data[2] = -1; // button down
			testProxy->handleData(data, 3);
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 5; // "B" key
			data[2] = -1; // button down
			testProxy->handleData(data, 3);

			char bufferData[KEYBOARD_BUFFER_SIZE];
			FileLoader::readData(bufferData, fileName, KEYBOARD_BUFFER_SIZE);
			Assert::AreEqual((char)Mode::KEYBOARD, bufferData[0]);
			Assert::AreEqual((char)0, bufferData[1]);
			Assert::AreEqual((char)0, bufferData[2]);
			// it doesn't matter what order the values come in
			if (bufferData[3] == 4)
				Assert::AreEqual((char)5, bufferData[4]);
			else if (bufferData[4] == 5)
				Assert::AreEqual((char)4, bufferData[3]);
			else
				Assert::Fail();
			Assert::AreEqual((char)0, bufferData[5]);
			Assert::AreEqual((char)0, bufferData[6]);
		}

		TEST_METHOD(TestButtonWriteButtonThenAnotherButtonThenReleaseFirst)
		{
			init();
			char data[3];
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 4; // "A" key
			data[2] = -1; // button down
			testProxy->handleData(data, 3);
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 5; // "B" key
			data[2] = -1; // button down
			testProxy->handleData(data, 3);
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 4; // "A" key
			data[2] = 0; // button up
			testProxy->handleData(data, 3);

			char bufferData[KEYBOARD_BUFFER_SIZE];
			FileLoader::readData(bufferData, fileName, KEYBOARD_BUFFER_SIZE);
			Assert::AreEqual((char)Mode::KEYBOARD, bufferData[0]);
			Assert::AreEqual((char)0, bufferData[1]);
			Assert::AreEqual((char)0, bufferData[2]);
			Assert::AreEqual((char)5, bufferData[3]);
			Assert::AreEqual((char)0, bufferData[4]);
			Assert::AreEqual((char)0, bufferData[5]);
			Assert::AreEqual((char)0, bufferData[6]);
		}

		TEST_METHOD(TestButtonWriteMaxInSequence)
		{
			init();
			char data[3];
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 4; // "A" key
			data[2] = -1; // button down
			testProxy->handleData(data, 3);
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 5; // "B" key
			data[2] = -1; // button down
			testProxy->handleData(data, 3);
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 6; // "C" key
			data[2] = -1; // button down
			testProxy->handleData(data, 3);
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 7; // "D" key
			data[2] = -1; // button down
			testProxy->handleData(data, 3);
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 8; // "E" key
			data[2] = -1; // button down
			testProxy->handleData(data, 3);
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 9; // "F" key
			data[2] = -1; // button down
			testProxy->handleData(data, 3);

			char bufferData[KEYBOARD_BUFFER_SIZE];
			FileLoader::readData(bufferData, fileName, KEYBOARD_BUFFER_SIZE);
			Assert::AreEqual((char)Mode::KEYBOARD, bufferData[0]);

			for (int i = 4; i < 10; i++)
				Assert::IsTrue(arrayContains(bufferData, KEYBOARD_BUFFER_SIZE, i));
		}

		TEST_METHOD(TestButtonWriteAllAtOnce)
		{
			init();
			char data[13];
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 4; // "A" key
			data[2] = -1; // button down
			data[3] = 5; // "B" key
			data[4] = -1; // button down
			data[5] = 6; // "C" key
			data[6] = -1; // button down
			data[7] = 7; // "D" key
			data[8] = -1; // button down
			data[9] = 8; // "E" key
			data[10] = -1; // button down
			data[11] = 9; // "F" key
			data[12] = -1; // button down
			testProxy->handleData(data, 13);


			char bufferData[KEYBOARD_BUFFER_SIZE];
			FileLoader::readData(bufferData, fileName, KEYBOARD_BUFFER_SIZE);
			Assert::AreEqual((char)Mode::KEYBOARD, bufferData[0]);

			for (int i = 4; i < 10; i++)
				Assert::IsTrue(arrayContains(bufferData, KEYBOARD_BUFFER_SIZE, i));
		}

		TEST_METHOD(TestButtonWriteLeftControl)
		{
			init();
			char data[3];
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 224; // "CTRL" key
			data[2] = -1; // button down
			testProxy->handleData(data, 3);

			char bufferData[KEYBOARD_BUFFER_SIZE];
			FileLoader::readData(bufferData, fileName, KEYBOARD_BUFFER_SIZE);
			Assert::AreEqual((char)Mode::KEYBOARD, bufferData[0]);
			Assert::AreEqual((char)0b00000001, bufferData[1]);
		}

		TEST_METHOD(TestButtonWriteLeftShift)
		{
			init();
			char data[3];
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 225; // "CTRL" key
			data[2] = -1; // button down
			testProxy->handleData(data, 3);

			char bufferData[KEYBOARD_BUFFER_SIZE];
			FileLoader::readData(bufferData, fileName, KEYBOARD_BUFFER_SIZE);
			Assert::AreEqual((char)Mode::KEYBOARD, bufferData[0]);
			Assert::AreEqual((char)0b00000010, bufferData[1]);
		}

		TEST_METHOD(TestButtonWriteLeftAlt)
		{
			init();
			char data[3];
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 226; // "CTRL" key
			data[2] = -1; // button down
			testProxy->handleData(data, 3);

			char bufferData[KEYBOARD_BUFFER_SIZE];
			FileLoader::readData(bufferData, fileName, KEYBOARD_BUFFER_SIZE);
			Assert::AreEqual((char)Mode::KEYBOARD, bufferData[0]);
			Assert::AreEqual((char)0b00000100, bufferData[1]);
		}

		TEST_METHOD(TestButtonWriteLeftGUI)
		{
			init();
			char data[3];
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			data[1] = 231; // "CTRL" key
			data[2] = -1; // button down
			testProxy->handleData(data, 3);

			char bufferData[KEYBOARD_BUFFER_SIZE];
			FileLoader::readData(bufferData, fileName, KEYBOARD_BUFFER_SIZE);
			Assert::AreEqual((char)Mode::KEYBOARD, bufferData[0]);
			Assert::AreEqual((char)0b00001000, bufferData[1]);
		}

	};
}