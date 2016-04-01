#include <ntddk.h>
#include <wdf.h>
#include <Hidclass.h>
#include <vhf.h>
#include <initguid.h>

DRIVER_INITIALIZE DriverEntry;
EVT_WDF_DRIVER_DEVICE_ADD BBKbdEvtDeviceAdd;
EVT_WDF_DRIVER_UNLOAD  EvtDriverUnload;
EVT_WDF_TIMER                       EvtTimerFunc;
EVT_WDF_DEVICE_SELF_MANAGED_IO_CLEANUP EvtDeviceSelfManagedIoCleanup;

#define BUFFER_FILE_NAME L"\\SystemRoot\\Temp\\bbRemoteBuffer"

#define MAX_FILE_BUFFER_LENGTH 9
#define FILE_NAME_LEN 20
#define MAX_PATH 260

#define MOUSE_REPORT_ID 1
#define KEYBOARD_REPORT_ID 2
#define GAMEPAD_REPORT_ID 3

#define MOUSE_REPORT_SIZE 4
#define KEYBOARD_REPORT_SIZE 9
#define GAMEPAD_REPORT_SIZE 7

typedef UCHAR HID_REPORT_DESCRIPTOR, *PHID_REPORT_DESCRIPTOR;

typedef struct _DEVICE_CONTEXT
{
	VHFHANDLE				VhfHandle;
	WDFTIMER                Timer;
	HANDLE					ReadFileHandle;
} DEVICE_CONTEXT, *PDEVICE_CONTEXT;

WDF_DECLARE_CONTEXT_TYPE_WITH_NAME(DEVICE_CONTEXT, DeviceGetContext)

DEFINE_GUID(GUID_HID,
	0xd0f38147, 0x08dc, 0x47bc, 0xb7, 0x39, 0x44, 0xf1, 0x0e, 0x94, 0x11, 0x17);

UCHAR MouseReportDescriptor[] = {
	// mouse
	0x05, 0x01,                    // USAGE_PAGE (Generic Desktop)
	0x09, 0x02,                    // USAGE (Mouse)
	0xa1, 0x01,                    // COLLECTION (Application)
	0x09, 0x01,                    //   USAGE (Pointer)
	0xa1, 0x00,                    //   COLLECTION (Physical)
	0x85, MOUSE_REPORT_ID,         //     REPORT_ID (MOUSE_REPORT_ID)
	0x05, 0x09,                    //     USAGE_PAGE (Button)
	0x19, 0x01,                    //     USAGE_MINIMUM (Button 1)
	0x29, 0x03,                    //     USAGE_MAXIMUM (Button 3)
	0x15, 0x00,                    //     LOGICAL_MINIMUM (0)
	0x25, 0x01,                    //     LOGICAL_MAXIMUM (1)
	0x95, 0x03,                    //     REPORT_COUNT (3)
	0x75, 0x01,                    //     REPORT_SIZE (1)
	0x81, 0x02,                    //     INPUT (Data,Var,Abs)
	0x95, 0x01,                    //     REPORT_COUNT (1)
	0x75, 0x05,                    //     REPORT_SIZE (5)
	0x81, 0x03,                    //     INPUT (Cnst,Var,Abs)
	0x05, 0x01,                    //     USAGE_PAGE (Generic Desktop)
	0x09, 0x30,                    //     USAGE (X)
	0x09, 0x31,                    //     USAGE (Y)
	0x15, 0x81,                    //     LOGICAL_MINIMUM (-127)
	0x25, 0x7f,                    //     LOGICAL_MAXIMUM (127)
	0x75, 0x08,                    //     REPORT_SIZE (8)
	0x95, 0x02,                    //     REPORT_COUNT (2)
	0x81, 0x06,                    //     INPUT (Data,Var,Rel)
	0xc0,                          //   END_COLLECTION
	0xc0,                          // END_COLLECTION

								   // keyboard
	0x05, 0x01,                    // USAGE_PAGE (Generic Desktop)
	0x09, 0x06,                    // USAGE(Keyboard)
	0xA1, 0x01,                    // COLLECTION(Application)
	0x85, KEYBOARD_REPORT_ID,      //   REPORT_ID (KEYBOARD_REPORT_ID)
	0x75, 0x01,                    //   REPORT_SIZE(1)
	0x95, 0x08,                    //   REPORT_COUNT(8)
	0x05, 0x07,                    //   USAGE_PAGE(Keyboard)
	0x19, 0xE0,                    //   USAGE_MINIMUM(Keyboard LeftControl)
	0x29, 0xE7,                    //   USAGE_MAXIMUM(Keyboard Right GUI)
	0x15, 0x00,                    //   LOGICAL_MINIMUM(0)
	0x25, 0x01,                    //   LOGICAL_MAXIMUM(1)
	0x81, 0x02,                    //   INPUT(Data,Var,Abs)
	0x95, 0x01,                    //   REPORT_COUNT(1)
	0x75, 0x08,                    //   REPORT_SIZE(8)
	0x81, 0x01,                    //   INPUT(Cnst,Ary,Abs)
	0x95, 0x06,                    //   REPORT_COUNT(6)
	0x75, 0x08,                    //   REPORT_SIZE(8)
	0x15, 0x00,                    //   LOGICAL_MINIMUM(0)
	0x26, 0xE1, 0x00,              //   LOGICAL_MAXIMUM(255)
	0x05, 0x07,                    //   USAGE_PAGE(Keyboard)
	0x19, 0x00,                    //   USAGE_MINIMUM(Reserved(no event indicated))
	0x29, 0xE1,                    //   USAGE_MAXIMUM(Keyboard Application)
	0x81, 0x00,                    //   INPUT(Data,Ary,Abs)
	0xC0,                          // END_COLLECTION

								   // gamepad
	0x05, 0x01,                    // USAGE_PAGE(Generic Desktop)
	0x09, 0x05,                    // USAGE(Game Pad)	
	0xA1, 0x01,                    // COLLECTION(Application)	
	0xA1, 0x00,                    //   COLLECTION(Physical)	
	0x85, GAMEPAD_REPORT_ID,       //     REPORT_ID (GAMEPAD_REPORT_ID)
	0x05, 0x09,                    //     USAGE_PAGE(Button)
	0x19, 0x01,                    //     USAGE_MINIMUM(Button 1)	
	0x29, 0x10,                    //     USAGE_MAXIMUM(Button 16)	
	0x15, 0x00,                    //     LOGICAL_MINIMUM(0)	
	0x25, 0x01,                    //     LOGICAL_MAXIMUM(1)	
	0x95, 0x10,                    //     REPORT_COUNT(16)
	0x75, 0x01,                    //     REPORT_SIZE(1)
	0x81, 0x02,                    //     INPUT(Data,Var,Abs)	
	0x05, 0x01,                    //     USAGE_PAGE(Generic Desktop)	
	0x09, 0x30,                    //     USAGE(X)	
	0x09, 0x31,                    //     USAGE(Y)	
	0x09, 0x32,                    //     USAGE(Z)	
	0x09, 0x33,                    //     USAGE(Rx)	
	0x15, 0x81,                    //     LOGICAL_MINIMUM(-127)	
	0x25, 0x7F,                    //     LOGICAL_MAXIMUM(127)	
	0x75, 0x08,                    //     REPORT_SIZE(8)
	0x95, 0x04,                    //     REPORT_COUNT(4)	
	0x81, 0x02,                    //     INPUT(Data,Var,Abs)	
	0xC0,                          //     END_COLLECTION	
	0xC0                           // END_COLLECTION
};

VOID
VhfSourceDeviceCleanup(
	_In_ WDFOBJECT DeviceObject
	)
{
	PDEVICE_CONTEXT deviceContext;

	PAGED_CODE();

	deviceContext = DeviceGetContext(DeviceObject);

	if (deviceContext->VhfHandle != WDF_NO_HANDLE)
	{
		VhfDelete(deviceContext->VhfHandle, TRUE);
	}

}

NTSTATUS DriverEntry(_In_ PDRIVER_OBJECT  DriverObject, _In_ PUNICODE_STRING RegistryPath)
{

	NTSTATUS status;
	WDF_DRIVER_CONFIG config;

	PAGED_CODE();

	WDF_DRIVER_CONFIG_INIT(&config, BBKbdEvtDeviceAdd);
	status = WdfDriverCreate(DriverObject, RegistryPath, WDF_NO_OBJECT_ATTRIBUTES, &config, WDF_NO_HANDLE);

	return status;
}

NTSTATUS BBKbdEvtDeviceAdd(_In_ WDFDRIVER Driver, _Inout_ PWDFDEVICE_INIT DeviceInit)
{
	WDF_OBJECT_ATTRIBUTES   deviceAttributes;
	PDEVICE_CONTEXT deviceContext;
	VHF_CONFIG vhfConfig;
	WDFDEVICE device;
	NTSTATUS status;

	PAGED_CODE();

	WdfFdoInitSetFilter(DeviceInit);

	WDF_OBJECT_ATTRIBUTES_INIT_CONTEXT_TYPE(&deviceAttributes, DEVICE_CONTEXT);
	deviceAttributes.EvtCleanupCallback = VhfSourceDeviceCleanup;

	status = WdfDeviceCreate(&DeviceInit, &deviceAttributes, &device);

	// set up VHF
	if (NT_SUCCESS(status))
	{
		deviceContext = DeviceGetContext(device);

		VHF_CONFIG_INIT(&vhfConfig,
			WdfDeviceWdmGetDeviceObject(device),
			sizeof(MouseReportDescriptor),
			MouseReportDescriptor);

		status = VhfCreate(&vhfConfig, &(deviceContext->VhfHandle));

		if (NT_SUCCESS(status)) {

			status = VhfStart(deviceContext->VhfHandle);
		}

		// Open the buffer file
		HANDLE file;
		UNICODE_STRING fileNameUnicodeString;

		RtlInitUnicodeString(&fileNameUnicodeString,
			BUFFER_FILE_NAME);

		OBJECT_ATTRIBUTES attributes;

		InitializeObjectAttributes(
			&attributes,
			&fileNameUnicodeString,
			OBJ_CASE_INSENSITIVE | OBJ_KERNEL_HANDLE,
			NULL,
			NULL
			);

		IO_STATUS_BLOCK ioStatusBlock;

		status = ZwOpenFile(
			&file,
			GENERIC_READ,
			&attributes,
			&ioStatusBlock,
			FILE_SHARE_READ | FILE_SHARE_WRITE,
			FILE_SYNCHRONOUS_IO_NONALERT
			);

		deviceContext->ReadFileHandle = file;

		// start the polling thread
		WDF_OBJECT_ATTRIBUTES   timerAttributes;
		WDF_TIMER_CONFIG        timerConfig;
		WDF_TIMER_CONFIG_INIT_PERIODIC(
			&timerConfig,
			EvtTimerFunc,
			0);

		WDF_OBJECT_ATTRIBUTES_INIT(&timerAttributes);
		timerAttributes.ExecutionLevel = WdfExecutionLevelPassive; // we need to do read requests, which require a passive execution level.
		timerAttributes.ParentObject = device;
		status = WdfTimerCreate(&timerConfig,
			&timerAttributes,
			&deviceContext->Timer);

		if (!NT_SUCCESS(status)) {
			KdPrint(("WdfTimerCreate failed 0x%x\n", status));
			return status;
		}

		WdfTimerStart(deviceContext->Timer, WDF_REL_TIMEOUT_IN_MS(1));

	}

	return status;
}

int
getReportSize(
	_In_ BYTE reportId
	) {
	if (reportId == MOUSE_REPORT_ID)
		return MOUSE_REPORT_SIZE;
	if (reportId == KEYBOARD_REPORT_ID)
		return KEYBOARD_REPORT_SIZE;
	if (reportId == MOUSE_REPORT_ID)
		return MOUSE_REPORT_SIZE;
	return 0; //TODO: error handling
}

void
EvtTimerFunc(
	_In_  WDFTIMER          Timer
	) {

	WDFDEVICE device = WdfTimerGetParentObject(Timer);
	PDEVICE_CONTEXT deviceContext = DeviceGetContext(device);
	IO_STATUS_BLOCK ioStatusBlock;
	NTSTATUS status;

	UCHAR buffer[MAX_FILE_BUFFER_LENGTH] = { 0 };
	LARGE_INTEGER byteOffset;
	byteOffset.LowPart = byteOffset.HighPart = 0;

	status = ZwReadFile(
		deviceContext->ReadFileHandle,
		NULL,
		NULL,
		NULL,
		&ioStatusBlock,
		buffer,
		MAX_FILE_BUFFER_LENGTH,
		&byteOffset,
		NULL
		);

	// only read if bbRemote is active (when the fist byte is not 0)
	if (buffer[0])
	{
		HID_XFER_PACKET report;
		RtlZeroMemory(&report, sizeof(HID_XFER_PACKET));
		report.reportBuffer = buffer;
		report.reportBufferLen = getReportSize(buffer[0]);
		report.reportId = 0;

		status = VhfReadReportSubmit(deviceContext->VhfHandle, &report);

		// poll again
		WdfTimerStart(deviceContext->Timer, WDF_REL_TIMEOUT_IN_MS(5));
	}
	else // otherwise, we go a little bit lighter on the cpu
		WdfTimerStart(deviceContext->Timer, WDF_REL_TIMEOUT_IN_SEC(1));
}