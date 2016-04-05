#pragma once
#include "DriverProxy.hpp"

class NoInputProxy : public DriverProxy
{
public:
	NoInputProxy();
	~NoInputProxy();
	int handleData(char *data, int bytes);
};