#include "stdafx.h"
#include "CppUnitTest.h"
#include "../../app/app/NoInputProxy.hpp"
#include "../../app/app/HandleDataErrorCodes.hpp"
#include "../../app/app/GlobalFile.hpp"
#include "../../app/app/Modes.hpp"
#include "FileLoader.hpp"

#define fileName L"bbRemoteBufferTest"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace BBNoInputProxyTests
{
	NoInputProxy *testProxy;

	TEST_CLASS(TestNoInputProxy)
	{
	public:

		void init()
		{
			testProxy = new NoInputProxy();
		}

		TEST_METHOD(TestModeIsNoInput)
		{
			init();
			Assert::AreEqual((int)Mode::NO_MODE, (int)testProxy->getModeId());
		}

		TEST_METHOD(TestHandleDataReturnsTrue)
		{
			init();
			Assert::AreEqual(SUCCESS, testProxy->handleData(NULL, 0));
		}

	};
}