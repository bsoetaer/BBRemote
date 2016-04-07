#include "stdafx.h"
#include "CppUnitTest.h"
#include "../../app/app/BluetoothReceiver.hpp"
#include "../../app/app/DriverProxy.hpp"
#include "StubModeSwitcher.hpp"
#include "StubDriverProxy.hpp"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace BBDesktopAppUnitTests
{
	BluetoothReceiver receiver;
	StubModeSwitcher *switcher;
	StubDriverProxy *driverProxy;

	TEST_CLASS(TestBluetoothReceiver)
	{
	public:

		void init()
		{
			driverProxy = new StubDriverProxy();
			switcher = new StubModeSwitcher();
			receiver = BluetoothReceiver(switcher, driverProxy);
		}

		TEST_METHOD(TestDefaultProxyIsKeyboard)
		{
			receiver = BluetoothReceiver();
			Assert::AreEqual((int)Mode::KEYBOARD, (int)receiver.getCurrentProxy()->getModeId());
		}

		TEST_METHOD(TestChangeMode)
		{
			init();
			char data[5];
			data[0] = DriverProxy::DATA_TYPE_MODE_CHANGE;
			receiver.handleData(data, 5);
			Assert::IsTrue(switcher->switchModeCalled);
		}

		TEST_METHOD(TestHandleDataCallsSameNumberOfBytes)
		{
			init();
			char data[5];
			data[0] = DriverProxy::DATA_TYPE_BUTTON;
			receiver.handleData(data, 5);
			Assert::AreEqual(5, driverProxy->getBytes());
		}

	};
}