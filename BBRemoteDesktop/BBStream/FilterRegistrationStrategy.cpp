/*
Requirements covered:
See following header file
*/

#pragma once

#include "FilterRegistrationStrategy.h"

HRESULT FilterRegistrationStrategy::registerFilter()
{
	HRESULT status;
	WCHAR *moduleName = getModuleName();
	IFilterMapper2 *fm = 0;
	status = initializeCOM(fm);
	if (SUCCEEDED(status))
		status = completeRegistration(moduleName, fm);
	cleanup(fm);
	return status;
}

WCHAR * FilterRegistrationStrategy::getModuleName()
{
	WCHAR moduleName[MAX_PATH];
	char charModuleName[MAX_PATH];

	GetModuleFileNameA(
		g_hInst,
		charModuleName,
		sizeof(charModuleName)
		);
	MultiByteToWideChar(
		CP_ACP,
		0,
		charModuleName,
		MAX_PATH + 1,
		moduleName,
		MAX_PATH + 1
		);

	return moduleName;
}

HRESULT FilterRegistrationStrategy::initializeCOM(IFilterMapper2 *fm)
{
	HRESULT success;
	success = CoInitialize(0);
	if (SUCCEEDED(success))
		success = CoCreateInstance(CLSID_FilterMapper2, NULL, CLSCTX_INPROC_SERVER, IID_IFilterMapper2, (void **)&fm);
	return success;
}

void FilterRegistrationStrategy::cleanup(IFilterMapper2 *fm)
{
	fm->Release();
	CoFreeUnusedLibraries();
	CoUninitialize();
}