#pragma once
using namespace std;

class DriverProxy
{
public:
	virtual void handleData(char *data) = 0;
};