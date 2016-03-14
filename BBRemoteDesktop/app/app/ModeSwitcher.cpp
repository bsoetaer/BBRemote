#include "ModeSwitcher.hpp"

DriverProxy* ModeSwitcher::switchMode(char *data)
{
	if (0 & *data) {
		return new KeyboardProxy();
	}
}