#include "stdafx.h"
#include "CppUnitTest.h"
#include "../../app/app/ModeSwitcher.hpp"
#include "StubDriverProxy.hpp"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace BBModeSwitcherTests
{
	ModeSwitcher *modeSwitcher;

	TEST_CLASS(TestModeSwitcher)
	{
	public:

		void init()
		{
			modeSwitcher = new ModeSwitcher();
		}

		TEST_METHOD(TestCurrentProxyIsDeactivated)
		{
			init();
			char data[1];
			data[0] = 0;
			StubDriverProxy proxy = StubDriverProxy();
			modeSwitcher->switchMode(data, &proxy);
			Assert::IsTrue(proxy.deviceDeactivated);
		}

		TEST_METHOD(TestSwitchToKeyboard)
		{
			init();
			char data[1];
			data[0] = (char)Mode::KEYBOARD;
			DriverProxy *proxy = modeSwitcher->switchMode(data, NULL);
			Assert::AreEqual((int)Mode::KEYBOARD, (int)proxy->getModeId());
		}

		TEST_METHOD(TestSwitchToOptical)
		{
			init();
			char data[1];
			data[0] = (char)Mode::OPTICAL;
			DriverProxy *proxy = modeSwitcher->switchMode(data, NULL);
			Assert::AreEqual((int)Mode::OPTICAL, (int)proxy->getModeId());
		}

		TEST_METHOD(TestSwitchToTouchpad)
		{
			init();
			char data[1];
			data[0] = (char)Mode::TOUCHPAD;
			DriverProxy *proxy = modeSwitcher->switchMode(data, NULL);
			Assert::AreEqual((int)Mode::OPTICAL, (int)proxy->getModeId()); // there is only one mouse mode in the backend: optical
		}

		TEST_METHOD(TestSwitchToGamepad)
		{
			init();
			char data[1];
			data[0] = (char)Mode::GAMEPAD;
			DriverProxy *proxy = modeSwitcher->switchMode(data, NULL);
			Assert::AreEqual((int)Mode::GAMEPAD, (int)proxy->getModeId());
		}

	};
}