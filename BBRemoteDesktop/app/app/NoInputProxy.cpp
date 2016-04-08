/*
Requirements Covered: see associated header file, included below
*/

#include "NoInputProxy.hpp"

NoInputProxy::NoInputProxy() : DriverProxy()
{
	modeId = Mode::NO_MODE;
}

NoInputProxy::~NoInputProxy() {}

int NoInputProxy::handleData(char *data, int bytes)
{
	return SUCCESS;
}