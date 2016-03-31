#include "ModeSwitcher.hpp"

DriverProxy* ModeSwitcher::switchMode(char *data, DriverProxy *currentProxy)
{
	currentProxy->deactivateDevice();
	if ((int)Mode::KEYBOARD & *data)
		return new KeyboardProxy();
	else if ((int)Mode::TOUCHPAD & *data || (int)Mode::OPTICAL & *data)
		return new MouseProxy();
}