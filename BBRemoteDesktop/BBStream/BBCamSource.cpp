#pragma once

#include "BBCamSource.h"

CUnknown * WINAPI BBCamSource::CreateInstance(LPUNKNOWN unknown, HRESULT *result)
{
	return new BBCamSource(unknown, result);
}

BBCamSource::BBCamSource(LPUNKNOWN unknown, HRESULT *result) :
	CSource(NAME(CHAR_DISPLAY_NAME), unknown, BBCam_GUID)
{
	CAutoLock cAutoLock(&m_cStateLock);
	m_paStreams = (CSourceStream **) new BBCamPin*[1]; // member of CSource: the stream pins. Initialize it.
	m_paStreams[0] = new BBCamPin(result, this, DISPLAY_NAME); // include the name. TODO: make this a constant
}

/*HRESULT BBCamSource::QueryInterface(REFIID riid, void **ppv)
{
	// 

	//Forward request for IAMStreamConfig & IKsPropertySet to the pin
	if (riid == _uuidof(IAMStreamConfig) || riid == _uuidof(IKsPropertySet))
		return m_paStreams[0]->QueryInterface(riid, ppv);
	else
		return CSource::QueryInterface(riid, ppv);
}*/