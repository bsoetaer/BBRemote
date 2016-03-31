#pragma once

#ifndef BB_CAM_PIN_H_
#define BB_CAM_PIN_H_

#include <streams.h>
#include "BBCamSource.h"
#include "Constants.h"

class BBCamSource;
class BBCamPin : public CSourceStream, public IAMStreamConfig, public IKsPropertySet
{
public:
	BBCamPin(HRESULT *result, BBCamSource *parentSrc, LPWSTR pinName);
	~BBCamPin();

	// IUnknown: used to deal with multiple inheritance issues
	STDMETHODIMP QueryInterface(REFIID riid, void **ppv);
	STDMETHODIMP_(ULONG) AddRef() { return GetOwner()->AddRef(); } // effectively "super"
	STDMETHODIMP_(ULONG) Release() { return GetOwner()->Release(); } // effectively "super"

	// IAMStreamConfig
	HRESULT STDMETHODCALLTYPE SetFormat(AM_MEDIA_TYPE *pmt);
	HRESULT STDMETHODCALLTYPE GetFormat(AM_MEDIA_TYPE **ppmt);
	HRESULT STDMETHODCALLTYPE GetNumberOfCapabilities(int *piCount, int *piSize);
	HRESULT STDMETHODCALLTYPE GetStreamCaps(int iIndex, AM_MEDIA_TYPE **pmt, BYTE *pSCC);

	// IKsPropertySet
	HRESULT STDMETHODCALLTYPE Set(REFGUID guidPropSet, DWORD dwID, void *pInstanceData, DWORD cbInstanceData, void *pPropData, DWORD cbPropData);
	HRESULT STDMETHODCALLTYPE Get(REFGUID guidPropSet, DWORD dwPropID, void *pInstanceData, DWORD cbInstanceData, void *pPropData, DWORD cbPropData, DWORD *pcbReturned);
	HRESULT STDMETHODCALLTYPE QuerySupported(REFGUID guidPropSet, DWORD dwPropID, DWORD *pTypeSupport);

	// CSourceStream
	HRESULT FillBuffer(IMediaSample *pms);
	HRESULT DecideBufferSize(IMemAllocator *pIMemAlloc, ALLOCATOR_PROPERTIES *pProperties);
	HRESULT CheckMediaType(const CMediaType *pMediaType);
	HRESULT GetMediaType(int iPosition, CMediaType *pmt);
	HRESULT SetMediaType(const CMediaType *pmt);
	HRESULT OnThreadCreate(void);

private:
	BBCamSource *parentSource;
	REFERENCE_TIME lastTime;
	CCritSec criticalSection;
	IReferenceClock *clocks;
	HANDLE bufferFile;
	LONG bytesRead;

	void initializeMediaType();
	void initializeBufferFile();
};

#endif