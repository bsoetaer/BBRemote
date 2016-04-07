#include "../../app/app/DriverProxy.hpp"

class StubDriverProxy : public DriverProxy
{
private:
	int bytes;
public:
	bool deviceDeactivated;
	int handleData(char *data, int bytes);
	int getBytes();
	void deactivateDevice() override;
};