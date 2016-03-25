#pragma once
#include <stdio.h>
#include "DriverProxy.hpp"

class KeyboardProxy : public DriverProxy
{
public:
	KeyboardProxy();
	~KeyboardProxy();
	void handleData(char *data, int bytes);
};