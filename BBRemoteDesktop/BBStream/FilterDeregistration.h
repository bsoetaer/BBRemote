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