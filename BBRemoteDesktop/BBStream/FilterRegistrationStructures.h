#pragma once

#ifndef FILTER_REGISTRATION_STRUCTURES_H_
#define FILTER_REGISTRATION_STRUCTURES_H_

#include <streams.h>

#include "Constants.h"
#include "BBCamSource.h"
#include "Filters.h"

const AMOVIESETUP_MEDIATYPE mediaType =
{
	&MEDIATYPE_Video, // major type; TODO: Change for audio
	&MEDIASUBTYPE_NULL // minor type
};

const AMOVIESETUP_PIN pinSetup =
{
	L"",                   // strName; blank since this is deprecated
	FALSE,                 // bRendered
	TRUE,                  // bOutput
	FALSE,                 // bZero; if true, we can have zero instances of this pin. Set true for audio?
	FALSE,                 // bMany
	&CLSID_NULL,           // clsConnectsToFilter; null, since this is deprecated
	NULL,                  // strConnectsToPin; null, since this is deprecated
	1,                     // nMediaTypes
	&mediaType             // lpMediaType
};

const AMOVIESETUP_FILTER cameraFilter =
{
	&BBCam_GUID,        // clsID
	DISPLAY_NAME,       // strName
	MERIT_DO_NOT_USE,   // dwMerit
	1,                  // nPins; todo: 2 for video (video+audio)
	&pinSetup           // lpPin
};

#endif