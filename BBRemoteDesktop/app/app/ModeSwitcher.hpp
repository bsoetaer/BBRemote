#pragma once
#include "DriverProxy.hpp"
#include "KeyboardProxy.hpp"
#include "MouseProxy.hpp"
#include "Modes.h"

class ModeSwitcher
{
public:
	static DriverProxy *switchMode(char *data, DriverProxy *currentProxy);
};