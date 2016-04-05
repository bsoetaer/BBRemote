#pragma once

#include "ModeSwitcher.hpp"

class StubModeSwitcher : public ModeSwitcher
{
public:
	bool switchModeCalled = false;
	DriverProxy *switchMode(char *data, DriverProxy *currentProxy) override;
};