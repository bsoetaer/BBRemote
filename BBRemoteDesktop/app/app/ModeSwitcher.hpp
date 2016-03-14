#pragma once
#include "DriverProxy.hpp"
#include "KeyboardProxy.hpp"

class ModeSwitcher
{
public:
	static DriverProxy *switchMode(char *data);
};