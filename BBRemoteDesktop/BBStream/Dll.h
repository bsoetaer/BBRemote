#pragma once

#ifndef DLL_H_
#define DLL_H_

#pragma comment(lib, "kernel32")
#pragma comment(lib, "user32")
#pragma comment(lib, "gdi32")
#pragma comment(lib, "advapi32")
#pragma comment(lib, "winmm")
#pragma comment(lib, "ole32")
#pragma comment(lib, "oleaut32")

#ifdef _DEBUG
#pragma comment(lib, "strmbasd")
#else
#pragma comment(lib, "strmbase")
#endif

#include <streams.h>
#include <olectl.h>
#include <initguid.h>
#include <dllsetup.h>
#include "filters.h"
#include "FilterDeregistration.h"
#include "FilterRegistration.h"

#endif