/*
Requirements covered:
3.2.1.2.1. Connect Device Successfully
3.2.2.2.1. Mode Select with Valid Connection
*/

#pragma once
#include "DriverProxy.hpp"

class NoInputProxy : public DriverProxy
{
public:
	NoInputProxy();
	~NoInputProxy();
	int handleData(char *data, int bytes);
};