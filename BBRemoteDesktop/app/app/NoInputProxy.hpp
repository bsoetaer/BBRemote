#pragma once
#include "DriverProxy.hpp"

class NoInputProxy : public DriverProxy
{
public:
	NoInputProxy();
	~NoInputProxy();
	void handleData(char *data, int bytes);
};