#include "ModeSwitcher.hpp"

DriverProxy* ModeSwitcher::switchMode(char *data)
{
	if (0 & *data)
		return new KeyboardProxy();
	else if (1 & *data)
		return new MouseProxy();
}