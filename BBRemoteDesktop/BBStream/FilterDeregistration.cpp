#pragma once

#include "FilterDeregistration.h"

HRESULT FilterDeregistration::completeRegistration(WCHAR *moduleName, IFilterMapper2 *fm)
{
	HRESULT success;
	success = registerFilterWithRegistry(fm);
	if (SUCCEEDED(success))
		success = registerModuleWithFilter(moduleName);
	return success;
}

HRESULT FilterDeregistration::registerModuleWithFilter(WCHAR *moduleName)
{
	return AMovieSetupUnregisterServer(BBCam_GUID);
}

HRESULT FilterDeregistration::registerFilterWithRegistry(IFilterMapper2 *fm)
{
	return fm->UnregisterFilter(&CLSID_VideoInputDeviceCategory, 0, BBCam_GUID);
}