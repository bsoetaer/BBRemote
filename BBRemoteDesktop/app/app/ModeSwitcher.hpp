#pragma once
#include "DriverProxy.hpp"
#include "KeyboardProxy.hpp"
#include "MouseProxy.hpp"
#include "GamepadProxy.hpp"
#include "NoInputProxy.hpp"
#include "Modes.hpp"

class ModeSwitcher
{
public:
	virtual DriverProxy *switchMode(char *data, DriverProxy *currentProxy);
};