/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Egg-03
 */
package io.github.eggy03.nautilus.windows.worker.typeresolver;

import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Utility class responsible for resolving numeric Windows Management
 * Instrumentation (WMI) property values into human-readable strings.
 *
 * <p>
 * Many WMI classes expose enumerated values as integers or longs.
 * This resolver translates those raw values into descriptive labels
 * based on Microsoft WMI documentation.
 * </p>
 *
 * <p>
 * Supported namespaces and classes include:
 * </p>
 *
 * <ul>
 *     <li>Win32_Processor</li>
 *     <li>Win32_CacheMemory</li>
 *     <li>Win32_PhysicalMemory</li>
 *     <li>Win32_PortConnector</li>
 *     <li>Win32_LogicalDisk</li>
 *     <li>Win32_UserAccount</li>
 *     <li>MSFT_NetAdapter</li>
 *     <li>MSFT_NetIPAddress</li>
 *     <li>MSFT_NetConnectionProfile</li>
 * </ul>
 *
 * <p>
 * If a value cannot be resolved, a descriptive fallback message is returned.
 * Null values are represented as {@code N/A}.
 * </p>
 *
 * <p>
 * This class is not intended to be instantiated.
 * </p>
 */
@SuppressWarnings("java:S1192")
@UtilityClass
public class WMIValueResolver {

    private static final @NonNull String NOT_AVAILABLE = "N/A";
    private static final @NonNull String NOT_RESOLVED = "Value Not Resolved For: ";

    //Win32_Processor
    @NonNull
    public static String resolveProcessorArchitecture(@Nullable Integer architecture) {

        if (architecture == null) return NOT_AVAILABLE;
        
        return switch (architecture) {
            case 0 -> "x86";
            case 1 -> "MIPS";
            case 2 -> "Alpha";
            case 3 -> "PowerPC";
            case 5 -> "ARM";
            case 6 -> "ia64";
            case 9 -> "x64";
            case 12 -> "ARM64";
            default -> NOT_RESOLVED + architecture;
        };
    }

    // Win32_CacheMemory
    @NonNull
    public static String resolveWMICacheMemoryType(@Nullable Integer cacheType) {

        if (cacheType == null) return NOT_AVAILABLE;

        return switch (cacheType) {
            case 1 -> "Other";
            case 2 -> "Unknown";
            case 3 -> "Instruction";
            case 4 -> "Data";
            case 5 -> "Unified";
            default -> NOT_RESOLVED + cacheType;
        };
    }

    @NonNull
    public static String resolveWMICacheMemoryLevel(@Nullable Integer cacheLevel) {

        if (cacheLevel == null) return NOT_AVAILABLE;

        return switch (cacheLevel) {
            case 1 -> "Other";
            case 2 -> "Unknown";
            case 3 -> "Primary (L1)";
            case 4 -> "Secondary (L2)";
            case 5 -> "Tertiary (L3)";
            case 6 -> "Not Applicable";
            default -> NOT_RESOLVED + cacheLevel;
        };
    }

    @NonNull
    public static String resolveWMICacheMemoryAssociativity(@Nullable Integer associativity) {

        if (associativity == null) return NOT_AVAILABLE;

        return switch (associativity) {
            case 1 -> "Other";
            case 2 -> "Unknown";
            case 3 -> "Direct Mapped";
            case 4 -> "2-way Set-Associative";
            case 5 -> "4-way Set-Associative";
            case 6 -> "Fully Associative";
            case 7 -> "8-way Set-Associative";
            case 8 -> "16-way Set-Associative";
            default -> NOT_RESOLVED + associativity;
        };
    }

    @NonNull
    public static String resolveWMICacheMemoryLocation(@Nullable Integer location) {

        if (location == null) return NOT_AVAILABLE;

        return switch (location) {
            case 0 -> "Internal";
            case 1 -> "External";
            case 2 -> "Reserved";
            case 3 -> "Unknown";
            default -> NOT_RESOLVED + location;
        };
    }

    @NonNull
    public static String resolveWMICacheErrorCorrectType(@Nullable Integer errorCorrectType) {

        if (errorCorrectType == null) return NOT_AVAILABLE;

        return switch (errorCorrectType) {
            case 0 -> "Reserved";
            case 1 -> "Other";
            case 2 -> "Unknown";
            case 3 -> "None";
            case 4 -> "Parity";
            case 5 -> "Single-bit ECC";
            case 6 -> "Multi-bit ECC";
            default -> NOT_RESOLVED + errorCorrectType;
        };
    }

    // Win32_PhysicalMemory
    @NonNull
    public static String resolveWMIPhysicalMemoryFormFactor(@Nullable Integer formFactor) {

        if (formFactor == null) return NOT_AVAILABLE;

        return switch (formFactor) {
            case 0 -> "Unknown";
            case 1 -> "Other";
            case 2 -> "SIP";
            case 3 -> "DIP";
            case 4 -> "ZIP";
            case 5 -> "SOJ";
            case 6 -> "Proprietary";
            case 7 -> "SIMM";
            case 8 -> "DIMM";
            case 9 -> "TSOP";
            case 10 -> "PGA";
            case 11 -> "RIMM";
            case 12 -> "SODIMM";
            case 13 -> "SRIMM";
            case 14 -> "SMD";
            case 15 -> "SSMP";
            case 16 -> "QFP";
            case 17 -> "TQFP";
            case 18 -> "SOIC";
            case 19 -> "LCC";
            case 20 -> "PLCC";
            case 21 -> "BGA";
            case 22 -> "FPBGA";
            case 23 -> "LGA";
            default -> NOT_RESOLVED + formFactor;
        };
    }

    // Win32_PortConnector
    @NonNull
    public static String resolveWMIPortType(@Nullable Integer portType) {

        if (portType == null) return NOT_AVAILABLE;

        return switch (portType) {
            case 0 -> "None";
            case 1 -> "Parallel Port XT/AT Compatible";
            case 2 -> "Parallel Port PS/2";
            case 3 -> "Parallel Port ECP";
            case 4 -> "Parallel Port EPP";
            case 5 -> "Parallel Port ECP/EPP";
            case 6 -> "Serial Port XT/AT Compatible";
            case 7 -> "Serial Port 16450 Compatible";
            case 8 -> "Serial Port 16550 Compatible";
            case 9 -> "Serial Port 16550A Compatible";
            case 10 -> "SCSI Port";
            case 11 -> "MIDI Port";
            case 12 -> "Joy Stick Port";
            case 13 -> "Keyboard Port";
            case 14 -> "Mouse Port";
            case 15 -> "SSA SCSI";
            case 16 -> "USB";
            case 17 -> "FireWire (IEEE 1394)";
            case 18 -> "PCMCIA Type I";
            case 19 -> "PCMCIA Type II";
            case 20 -> "PCMCIA Type III";
            case 21 -> "Cardbus";
            case 22 -> "Access Bus Port";
            case 23 -> "SCSI II";
            case 24 -> "SCSI Wide";
            case 25 -> "PC-98";
            case 26 -> "PC-98-Hireso";
            case 27 -> "PC-H98";
            case 28 -> "Video Port";
            case 29 -> "Audio Port";
            case 30 -> "Modem Port";
            case 31 -> "Network Port";
            case 32 -> "8251 Compatible";
            case 33 -> "8251 FIFO Compatible";
            default -> NOT_RESOLVED + portType;
        };
    }

    // MSFT_NetCommon
    @NonNull
    public static String resolveMsftIPvAddressFamily(@Nullable Number addressFamily) {

        if (addressFamily == null) {
            return NOT_AVAILABLE;
        }

        return switch (addressFamily.intValue()) {
            case 2 -> "IPv4";
            case 23 -> "IPv6";
            default -> "Unknown";
        };

    }

    // MSFT_NetAdapter
    @NonNull
    public static String resolveMsftNetAdapterMediaConnectState(@Nullable Long mediaConnectState) {

        if (mediaConnectState == null) {
            return NOT_AVAILABLE;
        }

        return switch (Math.toIntExact(mediaConnectState)) {
            case 0 -> "Unknown";
            case 1 -> "Connected";
            case 2 -> "Disconnected";
            default -> NOT_RESOLVED + mediaConnectState;
        };
    }

    // MSFT_NetIpAddress
    @NonNull
    public static String resolveMsftNetIpAddressType(@Nullable Integer type) {

        if (type == null) return NOT_AVAILABLE;

        return switch (type) {
            case 1 -> "Unicast";
            case 2 -> "Anycast";
            default -> NOT_RESOLVED + type;
        };
    }

    @NonNull
    public static String resolveMsftNetIpAddressPrefixOrigin(@Nullable Long prefixOrigin) {

        if (prefixOrigin == null) {
            return NOT_AVAILABLE;
        }

        return switch (prefixOrigin.intValue()) {
            case 0 -> "Other";
            case 1 -> "Manual";
            case 2 -> "Well Known";
            case 3 -> "DHCP";
            case 4 -> "Router Advertisement";
            default -> NOT_RESOLVED + prefixOrigin.intValue();
        };
    }

    @NonNull
    public static String resolveMsftNetIpAddressSuffixOrigin(@Nullable Long suffixOrigin) {

        if (suffixOrigin == null) {
            return NOT_AVAILABLE;
        }

        return switch (suffixOrigin.intValue()) {
            case 0 -> "Other";
            case 1 -> "Manual";
            case 2 -> "Well Known";
            case 3 -> "DHCP";
            case 4 -> "Link";
            case 5 -> "Random";
            default -> NOT_RESOLVED + suffixOrigin.intValue();
        };
    }

    // MSFT_NetConnectionProfile
    @NonNull
    public static String resolveMsftNetConnectionProfileNetworkCategory(@Nullable Long networkCategory) {

        if (networkCategory == null) {
            return NOT_AVAILABLE;
        }

        return switch (networkCategory.intValue()) {
            case 0 -> "Public";
            case 1 -> "Private";
            case 2 -> "Domain Authenticated";
            default -> NOT_RESOLVED + networkCategory.intValue();
        };
    }

    @NonNull
    public static String resolveMsftNetConnectionProfileDomainAuthenticationKind(
            @Nullable Long domainAuthenticationKind) {

        if (domainAuthenticationKind == null) {
            return NOT_AVAILABLE;
        }

        return switch (domainAuthenticationKind.intValue()) {
            case 0 -> "None";
            case 1 -> "LDAP";
            case 2 -> "TLS";
            default -> NOT_RESOLVED + domainAuthenticationKind.intValue();
        };
    }

    @NonNull
    public static String resolveMsftNetConnectionProfileConnectivity(@Nullable Long connectivity) {

        if (connectivity == null) {
            return NOT_AVAILABLE;
        }

        return switch (connectivity.intValue()) {
            case 0 -> "Disconnected";
            case 1 -> "No Traffic";
            case 2 -> "Subnet";
            case 3 -> "Local Network";
            case 4 -> "Internet";
            default -> NOT_RESOLVED + connectivity;
        };
    }

    // Win32_LogicalDisk
    @NonNull
    public static String resolveWMILogicalDiskDriveType(@Nullable Long driveType) {

        if (driveType == null) {
            return NOT_AVAILABLE;
        }

        return switch (driveType.intValue()) {
            case 0 -> "Unknown";
            case 1 -> "No Root Directory";
            case 2 -> "Removable Disk";
            case 3 -> "Local Disk";
            case 4 -> "Network Drive";
            case 5 -> "Compact Disc";
            case 6 -> "RAM Disk";
            default -> NOT_RESOLVED + driveType;
        };
    }

    @NonNull
    public static String resolveWMILogicalDiskMediaType(@Nullable Long mediaType) {

        if (mediaType == null) {
            return NOT_AVAILABLE;
        }

        return switch (mediaType.intValue()) {
            case 0 -> "Unknown format";
            case 1 -> "5.25 inch floppy (1.2 MB, 512 bytes/sector)";
            case 2 -> "3.5 inch floppy (1.44 MB, 512 bytes/sector)";
            case 3 -> "3.5 inch floppy (2.88 MB, 512 bytes/sector)";
            case 4 -> "3.5 inch floppy (20.8 MB, 512 bytes/sector)";
            case 5 -> "3.5 inch floppy (720 KB, 512 bytes/sector)";
            case 6 -> "5.25 inch floppy (360 KB, 512 bytes/sector)";
            case 7 -> "5.25 inch floppy (320 KB, 512 bytes/sector)";
            case 8 -> "5.25 inch floppy (320 KB, 1024 bytes/sector)";
            case 9 -> "5.25 inch floppy (180 KB, 512 bytes/sector)";
            case 10 -> "5.25 inch floppy (160 KB, 512 bytes/sector)";
            case 11 -> "Removable Media (non-floppy)";
            case 12 -> "Fixed Hard Disk Media";
            case 13 -> "3.5 inch floppy (120 MB, 512 bytes/sector)";
            case 14 -> "3.5 inch floppy (640 KB, 512 bytes/sector)";
            case 15 -> "5.25 inch floppy (640 KB, 512 bytes/sector)";
            case 16 -> "5.25 inch floppy (720 KB, 512 bytes/sector)";
            case 17 -> "3.5 inch floppy (1.2 MB, 512 bytes/sector)";
            case 18 -> "3.5 inch floppy (1.23 MB, 1024 bytes/sector)";
            case 19 -> "5.25 inch floppy (1.23 MB, 1024 bytes/sector)";
            case 20 -> "3.5 inch floppy (128 MB, 512 bytes/sector)";
            case 21 -> "3.5 inch floppy (230 MB, 512 bytes/sector)";
            case 22 -> "8 inch floppy (256 KB, 128 bytes/sector)";
            default -> NOT_RESOLVED + mediaType;
        };
    }

    // Win32_UserAccount
    @NonNull
    public static String resolveWMIUserAccountSidType(@Nullable Integer sidType) {

        if (sidType == null) return NOT_AVAILABLE;

        return switch (sidType) {
            case 1 -> "User";
            case 2 -> "Group";
            case 3 -> "Domain";
            case 4 -> "Alias";
            case 5 -> "Well-known Group";
            case 6 -> "Deleted Account";
            case 7 -> "Invalid";
            case 8 -> "Unknown";
            case 9 -> "Computer";
            default -> NOT_RESOLVED + sidType;
        };
    }

    @NonNull
    public static String resolveWMIUserAccountType(@Nullable Long accountType) {

        if (accountType == null) {
            return NOT_AVAILABLE;
        }

        return switch (accountType.intValue()) {
            case 256 -> "Temporary Duplicate Account";
            case 512 -> "Normal Account";
            case 2048 -> "Interdomain Trust Account";
            case 4096 -> "Workstation Trust Account";
            case 8192 -> "Server Trust Account";
            default -> NOT_RESOLVED + accountType.intValue();
        };
    }


    // GENERAL
    @NonNull
    public static String resolveWMIAvailability(@Nullable Integer availability) {

        if (availability == null) return NOT_AVAILABLE;

        return switch (availability) {
            case 1 -> "Other";
            case 2 -> "Unknown";
            case 3 -> "Running / Full Power";
            case 4 -> "Warning";
            case 5 -> "In Test";
            case 6 -> "Not Applicable";
            case 7 -> "Power Off";
            case 8 -> "Offline";
            case 9 -> "Off-duty";
            case 10 -> "Degraded";
            case 11 -> "Not Installed";
            case 12 -> "Install Error";
            case 13 -> "Power Save - Unknown";
            case 14 -> "Power Save - Low Power Mode";
            case 15 -> "Power Save - Standby";
            case 16 -> "Power Cycle";
            case 17 -> "Power Save - Warning";
            case 18 -> "Paused";
            case 19 -> "Not Ready";
            case 20 -> "Not Configured";
            case 21 -> "Quiesced";

            default -> NOT_RESOLVED + availability;
        };
    }
}
