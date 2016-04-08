/*
Requirements covered:
See included header files
*/

#include "stdafx.h"
#include "StubModeSwitcher.hpp"

DriverProxy* StubModeSwitcher::switchMode(char *data, DriverProxy *currentProxy)
{
	switchModeCalled = true;
	return new NoInputProxy();
}