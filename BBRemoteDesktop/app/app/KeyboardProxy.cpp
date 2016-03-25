#include "KeyboardProxy.hpp"

KeyboardProxy::KeyboardProxy() : DriverProxy("bbKeyboardBuffer") {}

KeyboardProxy::~KeyboardProxy() {}

void KeyboardProxy::handleData(char *data, int bytes)
{
	printf("Hello world!"); //TODO: integrate this with the actual drivers
}