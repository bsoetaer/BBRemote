/*
Requirements covered:
3.2.1.2.1. Connect Device Successfully
3.2.1.2.2. Only Allow Connection Between Paired Devices
3.2.1.2.5. Send Data from Mobile Client to Desktop Client
3.2.1.2.7. Input Lost in Transit
3.2.1.2.8. Auto-connect to Last Device on Startup
3.2.3.2.1. Send Key Input
3.2.4.2.1. Send Mouse Movement
3.2.4.2.3. Left Click
3.2.4.2.4. Right Click
3.2.4.2.5. Scrolling
3.2.5.2.1. Send Mouse Movement
3.2.5.2.4. Left Click
3.2.5.2.5. Right Click
3.2.5.2.6. Scrolling
3.2.5.2.8. Middle Mouse Button
3.2.7.2.1. Press Gamepad Button
3.2.7.2.2. Adjust Gamepad Axis
3.2.7.2.3. Press Toggle Button
3.2.7.2.7. Multiple Input Press
3.2.7.2.9. Rotate Phone
3.2.9.2.1. Send Sound
3.2.10.2.1. Send Video
3.2.10.2.2. Send Audio
3.2.11.2.1. Use Mic and a touchscreen input together
*/

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