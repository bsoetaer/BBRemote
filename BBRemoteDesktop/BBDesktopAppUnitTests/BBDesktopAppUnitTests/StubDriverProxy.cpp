#include "stdafx.h"
#include "StubDriverProxy.hpp"

int StubDriverProxy::handleData(char *data, int bytes)
{
	this->bytes = bytes;
	this->deviceDeactivated = false;
	return SUCCESS;
}

int StubDriverProxy::getBytes()
{
	return bytes;
}

void StubDriverProxy::deactivateDevice()
{
	deviceDeactivated = true;
}