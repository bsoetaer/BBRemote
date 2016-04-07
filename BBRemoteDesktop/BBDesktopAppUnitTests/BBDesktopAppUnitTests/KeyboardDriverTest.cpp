#include "stdafx.h"
#include "CppUnitTest.h"
#include <Windows.h>
#include "../../app/app/HandleDataErrorCodes.hpp"
#include "../../app/app/GlobalFile.hpp"
#include "../../app/app/Modes.hpp"
#include "FileWriter.hpp"
#include "FileLoader.hpp"

#define fileName L"bbRemoteBuffer"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

#define fileName L"bbRemoteBuffer"
#define KEYBOARD_BUFFER_SIZE 9
// the drivers poll every 5ms, so 10ms should be enough time for the driver to retrieve the data
#define WAIT_TIME 10

namespace BBKeyboardDriverTests
{

	char hidReport[KEYBOARD_BUFFER_SIZE];

	void clearBuffer()
	{
		hidReport[0] = (char)Mode::KEYBOARD;
		for (int i = 1; i < KEYBOARD_BUFFER_SIZE; i++)
			hidReport[i] = 0;
		FileWriter::writeData(hidReport, KEYBOARD_BUFFER_SIZE);
	}
	
	TEST_MODULE_INITIALIZE(Setup)
	{
		GlobalFile::createBufferFileHandle(fileName);
		clearBuffer();
	}

	TEST_MODULE_CLEANUP(Cleanup)
	{
		clearBuffer();
		GlobalFile::closeBufferFileHandle();
	}

	TEST_CLASS(TestKeyboardDriver)
	{
	public:

		void assertKeyDown(SHORT keyState)
		{
			Assert::IsTrue(0b10000000 & keyState); // if the key is down, then the first bit is 1
		}

		void assertKeyUp(SHORT keyState)
		{
			Assert::IsFalse(0b10000000 & keyState); // if the key is up, then the first bit is 0
		}

		void assertKeyToggled(SHORT keyState)
		{
			Assert::IsTrue(0b00000001 & keyState); // a togglable key is toggled if the last bit is 1
		}

		void assertKeyUntoggled(SHORT keyState)
		{
			Assert::IsFalse(0b00000001 & keyState); // a togglable key is toggled if the last bit is 0
		}

		TEST_METHOD(TestBufferStartsCleared)
		{
			char bufferData[KEYBOARD_BUFFER_SIZE];
			FileLoader::readData(bufferData, fileName, KEYBOARD_BUFFER_SIZE);
			Assert::AreEqual((char)Mode::KEYBOARD, bufferData[0]);
			for (int i = 1; i < KEYBOARD_BUFFER_SIZE; i++)
				Assert::AreEqual((char)0, bufferData[i]);
		}
		
		TEST_METHOD(TestAKey)
		{
			// press the key down
			hidReport[3] = 4; // the "A" key
			FileWriter::writeData(hidReport, KEYBOARD_BUFFER_SIZE);
			Sleep(WAIT_TIME);
			SHORT keyState = GetKeyState(65); // A
			assertKeyDown(keyState);

			// lift the key up
			hidReport[3] = 0; // the "A" key
			FileWriter::writeData(hidReport, KEYBOARD_BUFFER_SIZE);
			Sleep(WAIT_TIME);
			keyState = GetKeyState(65); // A
			assertKeyUp(keyState);
		}

		TEST_METHOD(TestCaps)
		{
			// press the key down
			hidReport[3] = 57; // the "CAPS" key
			FileWriter::writeData(hidReport, KEYBOARD_BUFFER_SIZE);
			Sleep(WAIT_TIME);
			
			// lift the key up
			hidReport[3] = 0;
			FileWriter::writeData(hidReport, KEYBOARD_BUFFER_SIZE);
			Sleep(WAIT_TIME);
			SHORT keyState = GetKeyState(VK_CAPITAL);
			assertKeyToggled(keyState);

			// reset it
			// press the key down
			hidReport[3] = 57; // the "CAPS" key
			FileWriter::writeData(hidReport, KEYBOARD_BUFFER_SIZE);
			Sleep(WAIT_TIME);

			// lift the key up
			hidReport[3] = 0;
			FileWriter::writeData(hidReport, KEYBOARD_BUFFER_SIZE);
			Sleep(WAIT_TIME);
			keyState = GetKeyState(VK_CAPITAL);
			assertKeyUntoggled(keyState);
		}

		TEST_METHOD(TestGui)
		{
			// toggle the key
			hidReport[1] = 0b00001000; // the "GUI" key
			FileWriter::writeData(hidReport, KEYBOARD_BUFFER_SIZE);
			Sleep(WAIT_TIME);
			SHORT keyState = GetKeyState(VK_LWIN);
			assertKeyToggled(keyState);
			

			// untoggle the key
			hidReport[1] = 0;
			FileWriter::writeData(hidReport, KEYBOARD_BUFFER_SIZE);
			Sleep(WAIT_TIME);
			keyState = GetKeyState(VK_LWIN);
			assertKeyUntoggled(keyState);
		}

		TEST_METHOD(TestSixDifferentKeysKey)
		{
			// press the key down
			hidReport[3] = 4; // A
			hidReport[4] = 5;
			hidReport[5] = 6;
			hidReport[6] = 7;
			hidReport[7] = 8;
			hidReport[8] = 9;
			FileWriter::writeData(hidReport, KEYBOARD_BUFFER_SIZE);
			Sleep(WAIT_TIME);
			SHORT keyState = GetKeyState(65); // A
			assertKeyDown(keyState);
			keyState = GetKeyState(66); // A
			assertKeyDown(keyState);
			keyState = GetKeyState(67); // A
			assertKeyDown(keyState);
			keyState = GetKeyState(68); // A
			assertKeyDown(keyState);
			keyState = GetKeyState(69); // A
			assertKeyDown(keyState);
			keyState = GetKeyState(70); // A
			assertKeyDown(keyState);
			
			// lift the key up
			hidReport[3] = 0;
			hidReport[4] = 0;
			hidReport[5] = 0;
			hidReport[6] = 0;
			hidReport[7] = 0;
			hidReport[8] = 0;
			FileWriter::writeData(hidReport, KEYBOARD_BUFFER_SIZE);
			Sleep(WAIT_TIME);
			keyState = GetKeyState(65); // A
			assertKeyUp(keyState);
			keyState = GetKeyState(66); // A
			assertKeyUp(keyState);
			keyState = GetKeyState(67); // A
			assertKeyUp(keyState);
			keyState = GetKeyState(68); // A
			assertKeyUp(keyState);
			keyState = GetKeyState(69); // A
			assertKeyUp(keyState);
			keyState = GetKeyState(70); // A
			assertKeyUp(keyState);
		}

	};
}