/*
Requirements covered:
3.2.9.2.1. Send Sound
3.2.9.2.2. Recognized as Device
3.2.10.2.1. Send Video
3.2.10.2.2. Send Audio
3.2.10.2.3. Recognized as Device
*/

#pragma once

#ifndef BB_CAM_SOURCE_H_
#define BB_CAM_SOURCE_H_

#include <streams.h>
#include "BBCamPin.h"
#include "Constants.h"

class BBCamSource : public CSource
{
public:
	static CUnknown * WINAPI CreateInstance(LPUNKNOWN lpunk, HRESULT *phr);

	// IUnknown
	//STDMETHODIMP QueryInterface(REFIID riid, void **ppv);

	IFilterGraph *GetGraph() { return m_pGraph; }

private:
	BBCamSource(LPUNKNOWN lpunk, HRESULT *phr); // We only want this accessable via CreateInstance
};

#endif