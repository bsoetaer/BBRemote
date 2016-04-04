#include "NoInputProxy.hpp"

NoInputProxy::NoInputProxy() : DriverProxy()
{
	modeId = Mode::NO_MODE;
}

NoInputProxy::~NoInputProxy() {}

void NoInputProxy::handleData(char *data, int bytes)
{
	return;
}