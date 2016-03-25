#pragma once
#include "DriverProxy.hpp"
#include "KeyboardProxy.hpp"
#include "MouseProxy.hpp"

class ModeSwitcher
{
public:
	static DriverProxy *switchMode(char *data);
};