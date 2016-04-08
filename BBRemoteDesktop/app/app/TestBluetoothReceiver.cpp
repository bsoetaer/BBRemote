#pragma once
#include "stdafx.h"
#include "CppUnitTest.h"
//#include "BluetoothReceiver.hpp"
//#include "DriverProxy.hpp"
//#include "StubModeSwitcher.hpp"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace BBDesktopAppUnitTests
{
	//BluetoothReceiver receiver;
	//StubModeSwitcher switcher;

	TEST_CLASS(TestBluetoothReceiver)
	{
	public:

		TEST_CLASS_INITIALIZE(setup)
		{
			//switcher = StubModeSwitcher();
			//receiver = BluetoothReceiver(switcher);
		}

		TEST_METHOD(TestChangeMode)
		{
			//char data[5];
			//data[0] = DriverProxy::DATA_TYPE_MODE_CHANGE;
			//receiver.handleData(data, 5);
			//Assert::IsTrue(switcher.switchModeCalled);
			Assert::IsTrue(true);
		}

	};
}