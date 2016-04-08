#include "StubModeSwitcher.hpp"

DriverProxy* StubModeSwitcher::switchMode(char *data, DriverProxy *currentProxy)
{
	switchModeCalled = true;
	return new NoInputProxy();
}