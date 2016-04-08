/*
Requirements covered:
3.2.9.2.2. Recognized as Device
3.2.10.2.3. Recognized as Device
*/

#pragma once

#ifndef FILTER_DEREGISTRATION_H_
#define FILTER_DEREGISTRATION_H_

#include "FilterRegistrationStrategy.h"

class FilterDeregistration : public FilterRegistrationStrategy
{
protected:
	HRESULT completeRegistration(WCHAR *moduleName, IFilterMapper2 *fm);
	HRESULT registerModuleWithFilter(WCHAR *moduleName);
	HRESULT registerFilterWithRegistry(IFilterMapper2 *fm);
};

#endif