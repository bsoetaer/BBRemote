#pragma once
#ifndef FILTER_REGISTRATION_H_
#define FILTER_REGISTRATION_H_

#include "FilterRegistrationStrategy.h"
#include <streams.h>

class FilterRegistration : public FilterRegistrationStrategy
{
protected:
	HRESULT completeRegistration(WCHAR *moduleName, IFilterMapper2 *fm);
	HRESULT registerModuleWithFilter(WCHAR *moduleName);
	HRESULT registerFilterWithRegistry(IFilterMapper2 *fm);
private:
	REGFILTER2 createFilterRegistrationData();
};

#endif