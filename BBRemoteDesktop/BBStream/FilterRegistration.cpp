#pragma once

#include "FilterRegistration.h"

HRESULT FilterRegistration::completeRegistration(WCHAR *moduleName, IFilterMapper2 *fm)
{
	HRESULT success;
	success = registerModuleWithFilter(moduleName);
	if (SUCCEEDED(success))
		success = registerFilterWithRegistry(fm);
	return success;
}

HRESULT FilterRegistration::registerModuleWithFilter(WCHAR *moduleName)
{
	return AMovieSetupRegisterServer(BBCam_GUID, DISPLAY_NAME, moduleName, NULL, NULL);
}

HRESULT FilterRegistration::registerFilterWithRegistry(IFilterMapper2 *fm)
{
	IMoniker *pMoniker = 0; // TODO: try changing to null
	REGFILTER2 rf2 = createFilterRegistrationData();

	// TODO: Change CLSID_VideoInputDeviceCategory to CLSID_AudioInputDeviceCategory for the mic
	return fm->RegisterFilter(BBCam_GUID, DISPLAY_NAME, &pMoniker, &CLSID_VideoInputDeviceCategory, NULL, &rf2);
}

REGFILTER2 FilterRegistration::createFilterRegistrationData()
{
	REGFILTER2 rf2;
	rf2.dwVersion = 1;
	rf2.dwMerit = MERIT_DO_NOT_USE;
	rf2.cPins = 1;
	rf2.rgPins = &pinSetup;
	return rf2;
}