/*
Requirements covered:
See following header file
*/

#include "Dll.h"
/*
// These functions are defined in strmbasd.lib, but not in any header files.
STDAPI AMovieSetupRegisterServer(CLSID   clsServer, LPCWSTR szDescription, LPCWSTR szFileName, LPCWSTR szThreadingModel = L"Both", LPCWSTR szServerType = L"InprocServer32");
STDAPI AMovieSetupUnregisterServer(CLSID clsServer);




extern HINSTANCE g_hinst;




STDAPI registerFilters()
{

}

STDAPI unregisterFilters()
{

}













STDAPI DllRegisterServer()
{
	return registerFilters();
}

STDAPI DllUnregisterServer()
{
	return unregisterFilters();
}

extern "C" BOOL WINAPI DllEntryPoint(HINSTANCE, ULONG, LPVOID);

BOOL APIENTRY DllMain(HANDLE hModule, DWORD  dwReason, LPVOID lpReserved)
{
	return DllEntryPoint((HINSTANCE)(hModule), dwReason, lpReserved);
}






//////////////////////////////////////////////////////////////////////////
//  This file contains routines to register / Unregister the 
//  Directshow filter 'Virtual Cam'
//  We do not use the inbuilt BaseClasses routines as we need to register as
//  a capture source
//////////////////////////////////////////////////////////////////////////





/*

// todo: remove this macro, and just call the function
#define CreateComObject(clsid, iid, var) CoCreateInstance( clsid, NULL, CLSCTX_INPROC_SERVER, iid, (void **)&var);

extern HINSTANCE g_hinst;




// TODO: New GUID
// {8E14549A-DB61-4309-AFA1-3578E927E933}
DEFINE_GUID(CLSID_VirtualCam,
	0x8e14549a, 0xdb61, 0x4309, 0xaf, 0xa1, 0x35, 0x78, 0xe9, 0x27, 0xe9, 0x33);


const AMOVIESETUP_MEDIATYPE AMSMediaTypesVCam =
{
	&MEDIATYPE_Video, // major type; TODO: Change for audio
	&MEDIASUBTYPE_NULL // To minor type
};

// TODO: reword the comments
const AMOVIESETUP_PIN AMSPinVCam =
{
	L"Output",             // Pin string name
	FALSE,                 // Is it rendered
	TRUE,                  // Is it an output
	FALSE,                 // Can we have none
	FALSE,                 // Can we have many
	&CLSID_NULL,           // Connects to filter
	NULL,                  // Connects to pin
	1,                     // Number of types
	&AMSMediaTypesVCam      // Pin Media types
};

const AMOVIESETUP_FILTER AMSFilterVCam =
{
	&CLSID_VirtualCam,  // Filter CLSID
	L"Virtual Cam",     // String name
	MERIT_DO_NOT_USE,      // Filter merit
	1,                     // Number pins
	&AMSPinVCam             // Pin details
};

CFactoryTemplate g_Templates[] =
{
	{
		L"Virtual Cam", // m_Name
		&CLSID_VirtualCam, // m_ClsID
		CVCam::CreateInstance, // m_IpfnNew
		NULL, // m_ipfnInit
		&AMSFilterVCam // m_pAMovieSetup_Filter
	},

};

int g_cTemplates = sizeof(g_Templates) / sizeof(g_Templates[0]);

STDAPI RegisterFilters(BOOL bRegister)
{
	HRESULT hr = NOERROR;
	WCHAR achFileName[MAX_PATH];
	char achTemp[MAX_PATH];
	ASSERT(g_hInst != 0);

	// get the module name, and return the error if it doesn't work
	if (0 == GetModuleFileNameA(g_hInst, achTemp, sizeof(achTemp)))
		return AmHresultFromWin32(GetLastError());

	// convert the module name (achTemp) to a wide character (achFileName)
	MultiByteToWideChar(CP_ACP, 0L, achTemp, lstrlenA(achTemp) + 1,
		achFileName, NUMELMS(achFileName));

	hr = CoInitialize(0);
	if (bRegister)
	{
		// register the server for CLSID_VirtualCam
		hr = AMovieSetupRegisterServer(CLSID_VirtualCam, L"Virtual Cam", achFileName, NULL, NULL);
	}

	if (SUCCEEDED(hr))
	{
		// add the camera to the registry
		IFilterMapper2 *fm = 0;
		// create a filterMapper2 com object, and store it in fm
		hr = CreateComObject(CLSID_FilterMapper2, IID_IFilterMapper2, fm);
		if (SUCCEEDED(hr))
		{
			if (bRegister)
			{
				IMoniker *pMoniker = 0; // TODO: try changing to null
				REGFILTER2 rf2;
				rf2.dwVersion = 1;
				rf2.dwMerit = MERIT_DO_NOT_USE;
				rf2.cPins = 1;
				rf2.rgPins = &AMSPinVCam;
				// actually register the camera with the registry. Change CLSID_VideoInputDeviceCategory to CLSID_AudioInputDeviceCategory for the mic
				hr = fm->RegisterFilter(CLSID_VirtualCam, L"Virtual Cam", &pMoniker, &CLSID_VideoInputDeviceCategory, NULL, &rf2);
			}
			else
			{
				// unregister the filter from the registry
				hr = fm->UnregisterFilter(&CLSID_VideoInputDeviceCategory, 0, CLSID_VirtualCam);
			}
		}

		// release interface
		// free resourses
		if (fm)
			fm->Release();
	}

	// unregister the filter from CLSID_VirtualCam
	if (SUCCEEDED(hr) && !bRegister)
		hr = AMovieSetupUnregisterServer(CLSID_VirtualCam);

	// free any unused dlls
	CoFreeUnusedLibraries();
	// closes the com library
	CoUninitialize();
	return hr;
}*/










STDAPI DllRegisterServer()
{
	FilterRegistration registeration = FilterRegistration();
	return registeration.registerFilter();
}

STDAPI DllUnregisterServer()
{
	FilterDeregistration registeration = FilterDeregistration();
	return registeration.registerFilter();
}

extern "C" BOOL WINAPI DllEntryPoint(HINSTANCE, ULONG, LPVOID);

BOOL APIENTRY DllMain(HANDLE hModule, DWORD  dwReason, LPVOID lpReserved)
{
	return DllEntryPoint((HINSTANCE)(hModule), dwReason, lpReserved);
}
