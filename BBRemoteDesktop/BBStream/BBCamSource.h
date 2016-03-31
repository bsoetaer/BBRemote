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