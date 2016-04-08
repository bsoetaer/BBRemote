/*
Requirements covered:
3.2.9.2.2. Recognized as Device
3.2.10.2.3. Recognized as Device
*/

#pragma once

#ifndef FILTER_REGISTRATION_STRATEGY_H_
#define FILTER_REGISTRATION_STRATEGY_H_

#include <streams.h>
#include "Constants.h"
#include "FilterRegistrationStructures.h"

// These functions are defined in strmbasd.lib, but not in any header files.
STDAPI AMovieSetupRegisterServer(CLSID   clsServer, LPCWSTR szDescription, LPCWSTR szFileName, LPCWSTR szThreadingModel = L"Both", LPCWSTR szServerType = L"InprocServer32");
STDAPI AMovieSetupUnregisterServer(CLSID clsServer);

class FilterRegistrationStrategy
{
public:
	HRESULT registerFilter();
private:
	WCHAR * getModuleName();
	HRESULT initializeCOM(IFilterMapper2 *fm);
	void cleanup(IFilterMapper2 *fm);
protected:
	virtual HRESULT completeRegistration(WCHAR *moduleName, IFilterMapper2 *fm) = 0;
	virtual HRESULT registerModuleWithFilter(WCHAR *moduleName) = 0;
	virtual HRESULT registerFilterWithRegistry(IFilterMapper2 *fm) = 0;
};

#endif