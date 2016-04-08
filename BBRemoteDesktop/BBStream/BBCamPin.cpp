/*
Requirements covered:
See following header file
*/

#pragma once

#include "BBCamPin.h"

BBCamPin::BBCamPin(HRESULT *result, BBCamSource *parentSrc, LPWSTR pinName) :
	CSourceStream(TEXT(CHAR_DISPLAY_NAME), result, parentSource, pinName)
{
	this->parentSource = parentSource;
	initializeMediaType();
	initializeBufferFile();
}

BBCamPin::~BBCamPin()
{
	CloseHandle(bufferFile);
}

void BBCamPin::initializeMediaType()
{
	GetMediaType(0, &m_mt);
}

void BBCamPin::initializeBufferFile()
{
	wchar_t text[BUFFER_FILE_NAME_LEN];
	mbstowcs(text, BUFFER_FILE_NAME, strlen(BUFFER_FILE_NAME) + 1);
	LPCWSTR filePath = text;

	bufferFile = CreateFile(
		text,
		GENERIC_READ,
		FILE_SHARE_WRITE | FILE_SHARE_READ,
		NULL,
		OPEN_ALWAYS,
		FILE_ATTRIBUTE_NORMAL,
		NULL
		);
}

HRESULT BBCamPin::QueryInterface(REFIID riid, void **ppv)
{
	if (riid == _uuidof(IAMStreamConfig))
		*ppv = (IAMStreamConfig*)this;
	else if (riid == _uuidof(IKsPropertySet))
		*ppv = (IKsPropertySet*)this;
	else
		return CSourceStream::QueryInterface(riid, ppv);

	//AddRef();
	return S_OK;
}

HRESULT STDMETHODCALLTYPE BBCamPin::SetFormat(AM_MEDIA_TYPE *newMediaType)
{
	VIDEOINFOHEADER *videoInfo = (VIDEOINFOHEADER*)m_mt.pbFormat;
	m_mt = *newMediaType;
	
	/*IPin* pin;
	ConnectedTo(&pin);
	// if we are currently connected to a pin...
	if (pin)
	{
		IFilterGraph *pGraph = m_pParent->GetGraph(); // get the graph of the parent (What does that do?)
		pGraph->Reconnect(this); // connect the graph
	}*/
	return S_OK;
}

HRESULT STDMETHODCALLTYPE BBCamPin::GetFormat(AM_MEDIA_TYPE **ppmt)
{
	*ppmt = CreateMediaType(&m_mt);
	return S_OK;
}

HRESULT STDMETHODCALLTYPE BBCamPin::GetNumberOfCapabilities(int *piCount, int *piSize)
{
	*piCount = 1;
	*piSize = sizeof(VIDEO_STREAM_CONFIG_CAPS); // TODO: Change for audio
	return S_OK;
}

// TODO: Not sure how this one works yet
HRESULT STDMETHODCALLTYPE BBCamPin::GetStreamCaps(
	int iIndex,
	AM_MEDIA_TYPE **pmt,
	BYTE *pSCC)
{
	*pmt = CreateMediaType(&m_mt);
	VIDEOINFOHEADER *pvi = (VIDEOINFOHEADER*)(*pmt)->pbFormat;

	//if (iIndex == 0) iIndex = 4;

	pvi->bmiHeader.biCompression = BI_RGB;
	pvi->bmiHeader.biBitCount = 24;
	pvi->bmiHeader.biSize = sizeof(BITMAPINFOHEADER);
	pvi->bmiHeader.biWidth = 80 * iIndex;
	pvi->bmiHeader.biHeight = 60 * iIndex;
	pvi->bmiHeader.biPlanes = 1;
	pvi->bmiHeader.biSizeImage = GetBitmapSize(&pvi->bmiHeader);
	pvi->bmiHeader.biClrImportant = 0;

	SetRectEmpty(&(pvi->rcSource)); // we want the whole image area rendered.
	SetRectEmpty(&(pvi->rcTarget)); // no particular destination rectangle

	(*pmt)->majortype = MEDIATYPE_Video;
	(*pmt)->subtype = MEDIASUBTYPE_H264;
	(*pmt)->formattype = FORMAT_VideoInfo;
	(*pmt)->bTemporalCompression = FALSE;
	(*pmt)->bFixedSizeSamples = FALSE;
	(*pmt)->lSampleSize = pvi->bmiHeader.biSizeImage;
	(*pmt)->cbFormat = sizeof(VIDEOINFOHEADER);

	VIDEO_STREAM_CONFIG_CAPS *pvscc = (VIDEO_STREAM_CONFIG_CAPS*)pSCC;


	pvscc->guid = FORMAT_VideoInfo;
	pvscc->VideoStandard = AnalogVideo_None;
	pvscc->InputSize.cx = 640;
	pvscc->InputSize.cy = 480;
	pvscc->MinCroppingSize.cx = 80;
	pvscc->MinCroppingSize.cy = 60;
	pvscc->MaxCroppingSize.cx = 640;
	pvscc->MaxCroppingSize.cy = 480;
	pvscc->CropGranularityX = 80;
	pvscc->CropGranularityY = 60;
	pvscc->CropAlignX = 0;
	pvscc->CropAlignY = 0;

	pvscc->MinOutputSize.cx = 80;
	pvscc->MinOutputSize.cy = 60;
	pvscc->MaxOutputSize.cx = 640;
	pvscc->MaxOutputSize.cy = 480;
	pvscc->OutputGranularityX = 0;
	pvscc->OutputGranularityY = 0;
	pvscc->StretchTapsX = 0;
	pvscc->StretchTapsY = 0;
	pvscc->ShrinkTapsX = 0;
	pvscc->ShrinkTapsY = 0;
	pvscc->MinFrameInterval = 200000;   //50 fps
	pvscc->MaxFrameInterval = 50000000; // 0.2 fps
	pvscc->MinBitsPerSecond = (80 * 60 * 3 * 8) / 5;
	pvscc->MaxBitsPerSecond = 640 * 480 * 3 * 8 * 50;

	return S_OK;
}

HRESULT BBCamPin::Set(
	REFGUID guidPropSet,
	DWORD dwID,
	void *pInstanceData,
	DWORD cbInstanceData,
	void *pPropData, 
DWORD cbPropData)
{
	return E_NOTIMPL;
}

HRESULT BBCamPin::Get(
	REFGUID guidPropSet,
	DWORD dwPropID,
	void *pInstanceData,
	DWORD cbInstanceData,
	void *pPropData,
	DWORD cbPropData,
	DWORD *pcbReturned
	)
{
	if (guidPropSet != AMPROPSETID_Pin)             return E_PROP_SET_UNSUPPORTED;
	if (dwPropID != AMPROPERTY_PIN_CATEGORY)        return E_PROP_ID_UNSUPPORTED;
	if (pPropData == NULL && pcbReturned == NULL)   return E_POINTER;

	if (pcbReturned) *pcbReturned = sizeof(GUID);
	if (pPropData == NULL)          return S_OK;
	if (cbPropData < sizeof(GUID))  return E_UNEXPECTED;

	*(GUID *)pPropData = PIN_CATEGORY_CAPTURE;
	return S_OK;
}

HRESULT BBCamPin::QuerySupported(
	REFGUID guidPropSet,
	DWORD dwPropID,
	DWORD *pTypeSupport)
{
	// you can only do things to pins
	if (guidPropSet != AMPROPSETID_Pin) 
		return E_PROP_SET_UNSUPPORTED;
	if (dwPropID != AMPROPERTY_PIN_CATEGORY)
		return E_PROP_ID_UNSUPPORTED;

	// We support getting this property, but not setting it.
	if (pTypeSupport)
		*pTypeSupport = KSPROPERTY_SUPPORT_GET;
	return S_OK;
}

HRESULT BBCamPin::FillBuffer(IMediaSample *pms)
{
	REFERENCE_TIME rtNow;

	REFERENCE_TIME avgFrameTime = ((VIDEOINFOHEADER*)m_mt.pbFormat)->AvgTimePerFrame;

	rtNow = lastTime;
	lastTime += avgFrameTime;
	pms->SetTime(&rtNow, &lastTime);
	pms->SetSyncPoint(TRUE);

	BYTE *pData;
	long lDataLen;
	pms->GetPointer(&pData);
	lDataLen = pms->GetSize();

	/*SetFilePointer(
	bufferFile,
	0,
	NULL,
	FILE_BEGIN
	);*/

	ReadFile(
		bufferFile,
		pData,
		lDataLen,
		NULL,
		NULL
		);

	return NOERROR;
}


// TODO: refactor this function
HRESULT BBCamPin::DecideBufferSize(IMemAllocator *pAlloc, ALLOCATOR_PROPERTIES *pProperties)
{
	// lock the buffer so that nothing changes during this state. The lock is removed when the current scope ends.
	CAutoLock cAutoLock(m_pFilter->pStateLock());
	HRESULT hr = NOERROR;

	// make only one buffer, of "image size"
	VIDEOINFOHEADER *pvi = (VIDEOINFOHEADER *)m_mt.Format();
	pProperties->cBuffers = 1;
	pProperties->cbBuffer = pvi->bmiHeader.biSizeImage;

	ALLOCATOR_PROPERTIES Actual;
	hr = pAlloc->SetProperties(pProperties, &Actual);

	// error handing: make sure the properties are okay
	if (FAILED(hr)) return hr;
	if (Actual.cbBuffer < pProperties->cbBuffer) return E_FAIL;

	return NOERROR;
}

HRESULT BBCamPin::CheckMediaType(const CMediaType *mediaType)
{
	//VIDEOINFOHEADER *pvi = (VIDEOINFOHEADER *)(mediaType->Format());
	if (*mediaType != m_mt)
		return E_INVALIDARG;
	return S_OK;
}

HRESULT BBCamPin::GetMediaType(int iPosition, CMediaType *pmt)
{
	if (iPosition < 0) return E_INVALIDARG;
	if (iPosition > 8) return VFW_S_NO_MORE_ITEMS;

	if (iPosition == 0)
	{
		*pmt = m_mt;
		return S_OK;
	}

	// TODO: the pvi is mostly duplicated from GetStreamCaps
	VIDEOINFOHEADER *pvi = (VIDEOINFOHEADER*) pmt->AllocFormatBuffer(sizeof(VIDEOINFOHEADER));
	ZeroMemory(pvi, sizeof(VIDEOINFOHEADER));

	pvi->bmiHeader.biCompression = BI_RGB;
	pvi->bmiHeader.biBitCount = 24;
	pvi->bmiHeader.biSize = sizeof(BITMAPINFOHEADER);
	pvi->bmiHeader.biWidth = 80 * iPosition;
	pvi->bmiHeader.biHeight = 60 * iPosition;
	pvi->bmiHeader.biPlanes = 1;
	pvi->bmiHeader.biSizeImage = GetBitmapSize(&pvi->bmiHeader);
	pvi->bmiHeader.biClrImportant = 0;

	pvi->AvgTimePerFrame = 1000000;

	SetRectEmpty(&(pvi->rcSource)); // we want the whole image area rendered.
	SetRectEmpty(&(pvi->rcTarget)); // no particular destination rectangle

	pmt->SetType(&MEDIATYPE_Video);
	pmt->SetFormatType(&FORMAT_VideoInfo);
	pmt->SetTemporalCompression(TRUE);

	pmt->SetSubtype(&MEDIASUBTYPE_H264);
	pmt->SetSampleSize(pvi->bmiHeader.biSizeImage);

	return NOERROR;

}

HRESULT BBCamPin::SetMediaType(const CMediaType *pmt)
{
	VIDEOINFOHEADER *pvi = (VIDEOINFOHEADER*) pmt->Format();
	return CSourceStream::SetMediaType(pmt);
}

HRESULT BBCamPin::OnThreadCreate()
{
	lastTime = 0;
	return NOERROR;
}