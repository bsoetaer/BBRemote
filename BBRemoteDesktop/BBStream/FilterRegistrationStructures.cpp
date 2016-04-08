/*
Requirements covered:
See following header file
*/

#pragma once

#include "FilterRegistrationStructures.h"

// externally defined
CFactoryTemplate g_Templates[] =
{
	{
		DISPLAY_NAME,					// m_Name
		&BBCam_GUID,					// m_ClsID
	CVCam::CreateInstance,	// m_IpfnNew:
	NULL,							// m_ipfnInit
	&cameraFilter					// m_pAMovieSetup_Filter
	},

};

int g_cTemplates = sizeof(g_Templates) / sizeof(g_Templates[0]);