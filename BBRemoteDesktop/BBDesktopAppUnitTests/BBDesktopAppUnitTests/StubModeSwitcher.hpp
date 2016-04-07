#pragma once

#include "../../app/app/ModeSwitcher.hpp"

class StubModeSwitcher : public ModeSwitcher
{
public:
	bool switchModeCalled = false;
	DriverProxy *switchMode(char *data, DriverProxy *currentProxy) override;
};