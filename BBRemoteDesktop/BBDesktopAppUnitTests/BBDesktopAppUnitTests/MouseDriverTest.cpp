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
#define MOUSE_BUFFER_SIZE 5
// it takes a while for the GetKeyState method to register mouse clicks
#define WAIT_TIME 1000

namespace BBMouseDriverTests
{

	char hidReport[MOUSE_BUFFER_SIZE];

	void clearBuffer()
	{
		hidReport[0] = (char)Mode::OPTICAL;
		for (int i = 1; i < MOUSE_BUFFER_SIZE; i++)
			hidReport[i] = 0;
		FileWriter::writeData(hidReport, MOUSE_BUFFER_SIZE);
	}

	TEST_MODULE_INITIALIZE(Setup)
	{
		Sleep(WAIT_TIME);
		GlobalFile::createBufferFileHandle(fileName);
		clearBuffer();
	}

	TEST_MODULE_CLEANUP(Cleanup)
	{
		clearBuffer();
		GlobalFile::closeBufferFileHandle();
	}

	TEST_CLASS(TestMouseDriver)
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

		TEST_METHOD(TestBufferStartsCleared)
		{
			char bufferData[MOUSE_BUFFER_SIZE];
			FileLoader::readData(bufferData, fileName, MOUSE_BUFFER_SIZE);
			Assert::AreEqual((char)Mode::OPTICAL, bufferData[0]);
			for (int i = 1; i < MOUSE_BUFFER_SIZE; i++)
				Assert::AreEqual((char)0, bufferData[i]);
		}

		TEST_METHOD(TestLeftButtonClick)
		{
			// press the key down
			hidReport[1] = 0b00000001; // left click
			FileWriter::writeData(hidReport, MOUSE_BUFFER_SIZE);
			Sleep(WAIT_TIME);
			SHORT keyState = GetKeyState(VK_LBUTTON);
			assertKeyDown(keyState);

			// lift the key up
			hidReport[1] = 0;
			FileWriter::writeData(hidReport, MOUSE_BUFFER_SIZE);
			Sleep(WAIT_TIME);
			keyState = GetKeyState(VK_LBUTTON);
			assertKeyUp(keyState);
		}

		TEST_METHOD(TestRightButtonClick)
		{
			// press the key down
			hidReport[1] = 0b00000010; // right click
			FileWriter::writeData(hidReport, MOUSE_BUFFER_SIZE);
			Sleep(WAIT_TIME);
			SHORT keyState = GetKeyState(VK_RBUTTON);
			assertKeyDown(keyState);

			// lift the key up
			hidReport[1] = 0;
			FileWriter::writeData(hidReport, MOUSE_BUFFER_SIZE);
			Sleep(WAIT_TIME);
			keyState = GetKeyState(VK_RBUTTON);
			assertKeyUp(keyState);
		}

		TEST_METHOD(TestMiddleButtonClick)
		{
			// press the key down
			hidReport[1] = 0b00000100; // middle click
			FileWriter::writeData(hidReport, MOUSE_BUFFER_SIZE);
			Sleep(WAIT_TIME);
			SHORT keyState = GetKeyState(VK_MBUTTON);
			assertKeyDown(keyState);

			// lift the key up
			hidReport[1] = 0;
			FileWriter::writeData(hidReport, MOUSE_BUFFER_SIZE);
			Sleep(WAIT_TIME);
			keyState = GetKeyState(VK_MBUTTON);
			assertKeyUp(keyState);
		}

		TEST_METHOD(TestMoveUp)
		{
			hidReport[3] = (char)-1; // move up
			
			POINT currentPoint;
			GetCursorPos(&currentPoint);

			FileWriter::writeData(hidReport, MOUSE_BUFFER_SIZE);
			Sleep(WAIT_TIME);

			POINT newPoint;
			GetCursorPos(&newPoint);

			Assert::IsTrue(newPoint.y < currentPoint.y);
		}

		TEST_METHOD(TestMoveDown)
		{
			hidReport[3] = (char)1; // move down

			POINT currentPoint;
			GetCursorPos(&currentPoint);

			FileWriter::writeData(hidReport, MOUSE_BUFFER_SIZE);
			Sleep(WAIT_TIME);

			POINT newPoint;
			GetCursorPos(&newPoint);

			Assert::IsTrue(newPoint.y > currentPoint.y);
		}

		TEST_METHOD(TestMoveRight)
		{
			hidReport[2] = (char)1; // move right

			POINT currentPoint;
			GetCursorPos(&currentPoint);

			FileWriter::writeData(hidReport, MOUSE_BUFFER_SIZE);
			Sleep(WAIT_TIME);

			POINT newPoint;
			GetCursorPos(&newPoint);

			Assert::IsTrue(newPoint.x > currentPoint.x);
		}

		TEST_METHOD(TestMoveLeft)
		{
			hidReport[2] = (char)-1; // move right

			POINT currentPoint;
			GetCursorPos(&currentPoint);

			FileWriter::writeData(hidReport, MOUSE_BUFFER_SIZE);
			Sleep(WAIT_TIME);

			POINT newPoint;
			GetCursorPos(&newPoint);

			Assert::IsTrue(newPoint.x < currentPoint.x);
		}

	};
}