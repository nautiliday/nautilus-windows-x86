/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Egg-03
 */
package io.github.eggy03.nautilus.windows.worker;

import io.github.eggy03.cimari.entity.compounded.Win32ProcessorToCacheMemory;
import io.github.eggy03.cimari.entity.processor.Win32CacheMemory;
import io.github.eggy03.cimari.entity.processor.Win32Processor;
import io.github.eggy03.cimari.service.compounded.Win32ProcessorToCacheMemoryService;
import io.github.eggy03.nautilus.windows.constant.TerminalConstant;
import io.github.eggy03.nautilus.windows.worker.typeresolver.WMIValueResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static io.github.eggy03.nautilus.windows.worker.typeresolver.WMIValueResolver.resolveWMIAvailability;
import static io.github.eggy03.nautilus.windows.worker.typeresolver.WMIValueResolver.resolveWMICacheErrorCorrectType;
import static io.github.eggy03.nautilus.windows.worker.typeresolver.WMIValueResolver.resolveWMICacheMemoryAssociativity;
import static io.github.eggy03.nautilus.windows.worker.typeresolver.WMIValueResolver.resolveWMICacheMemoryLevel;
import static io.github.eggy03.nautilus.windows.worker.typeresolver.WMIValueResolver.resolveWMICacheMemoryLocation;
import static io.github.eggy03.nautilus.windows.worker.typeresolver.WMIValueResolver.resolveWMICacheMemoryType;

@RequiredArgsConstructor
@Slf4j
public class WMIProcessorPanelWorker extends SwingWorker<Map<String, Win32ProcessorToCacheMemory>, Void> {

    private final @NonNull JComboBox<String> cpuIdComboBox;
    private final @NonNull List<JTextField> cpuFields;
    private final @NonNull List<JTextArea> cpuTextAreas;

    @Override
    protected @NonNull Map<String, Win32ProcessorToCacheMemory> doInBackground() {
        List<Win32ProcessorToCacheMemory> cpuList = new Win32ProcessorToCacheMemoryService().get(TerminalConstant.TIMEOUT_SIXTY_SECONDS);
        log.info("Found {} Win32_Processor entry(s)", cpuList.size());

        return cpuList.stream()
                .filter(Objects::nonNull)
                .filter(cpu -> Objects.nonNull(cpu.deviceId()))
                .collect(Collectors.toUnmodifiableMap(
                        cpu -> Objects.requireNonNull(cpu.deviceId()),
                        cpu -> cpu
                ));

    }

    @Override
    protected void done() {
        try {
            Map<String, Win32ProcessorToCacheMemory> cpuMap = get();
            // populate the combo box with cpu device id
            cpuMap.keySet().stream().sorted().forEach(cpuIdComboBox::addItem);
            // populate fields for the first entry in the combo box
            populate(cpuMap);
            // add a listener to the combo box to re-populate fields on new selection
            cpuIdComboBox.addActionListener(selectEvent -> populate(cpuMap));
        } catch (ExecutionException e) {
            log.error("CPU Fetch Failed", e);
        } catch (InterruptedException e) {
            log.error("CPU Fetch Interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    // populate the fields based on the current cpu-id in the combo-box
    private void populate(@NonNull Map<String, Win32ProcessorToCacheMemory> cpuMap) {

        String deviceId = String.valueOf(cpuIdComboBox.getSelectedItem());

        Win32ProcessorToCacheMemory cpuObject = cpuMap.get(deviceId);
        if (cpuObject == null)
            return;

        Win32Processor currentCpu = cpuObject.processor();
        List<Win32CacheMemory> cacheList = cpuObject.cacheMemoryList();

        // populate cpu fields
        if (currentCpu != null) { // the order is maintained by the order in which the text fields have been passed to its constructor
            cpuFields.get(0).setText(String.valueOf(currentCpu.numberOfCores()));
            cpuFields.get(1).setText(String.valueOf(currentCpu.threadCount()));
            cpuFields.get(2).setText(currentCpu.maxClockSpeed() + " MHz");
            cpuFields.get(3).setText(currentCpu.name());
            cpuFields.get(4).setText(currentCpu.version());
            cpuFields.get(5).setText(String.valueOf(currentCpu.family()));
            cpuFields.get(6).setText(currentCpu.stepping());
            cpuFields.get(7).setText(currentCpu.manufacturer());
            cpuFields.get(8).setText(currentCpu.caption());
            cpuFields.get(9).setText(currentCpu.processorId());
            cpuFields.get(10).setText(String.valueOf(currentCpu.numberOfEnabledCores()));
            cpuFields.get(11).setText(String.valueOf(currentCpu.numberOfLogicalProcessors()));
            cpuFields.get(12).setText(WMIValueResolver.resolveProcessorArchitecture(currentCpu.architecture()));
            cpuFields.get(13).setText(currentCpu.addressWidth() + " bit");
            cpuFields.get(14).setText(String.valueOf(currentCpu.socketDesignation()));
            cpuFields.get(15).setText(currentCpu.extClock() + " MHz");

            // assign text to the concise cpu text area
            JTextArea conciseCpuTextArea = cpuTextAreas.get(0);
            String conciseText = "This CPU, " + String.valueOf(currentCpu.name()).trim() + " consists of "
                    + System.lineSeparator() +
                    currentCpu.numberOfCores() + " cores and " + currentCpu.threadCount() + " threads, out of which "
                    + System.lineSeparator() +
                    currentCpu.numberOfEnabledCores() + " cores and " + currentCpu.numberOfLogicalProcessors() + " threads are enabled."
                    + System.lineSeparator() +
                    "The factory reported base clock for this CPU is: " + currentCpu.maxClockSpeed() + " MHz."
                    + System.lineSeparator() +
                    "The reported socket for this CPU is: " + currentCpu.socketDesignation()
                    + System.lineSeparator() +
                    "The reported L2 Cache is: " + currentCpu.l2CacheSize() + " KB and the reported L3 Cache is: " + currentCpu.l3CacheSize() + " KB.";

            conciseCpuTextArea.setText(conciseText);
        }

        // populate cache size fields
        if (cacheList != null && !cacheList.isEmpty()) {

            // populate the text area with raw details
            JTextArea cacheTextArea = cpuTextAreas.get(1);
            cacheTextArea.setText(null); //before populating, clean the previous data if any
            cacheList.forEach(cache -> cacheTextArea.append(
                    "DeviceID: " + cache.deviceId() + System.lineSeparator() +
                            "Purpose: " + cache.purpose() + System.lineSeparator() +
                            "Type: " + resolveWMICacheMemoryType(cache.cacheType()) + System.lineSeparator() +
                            "Level: " + resolveWMICacheMemoryLevel(cache.level()) + System.lineSeparator() +
                            "Size: " + cache.installedSize() + " KB" + System.lineSeparator() +
                            "Associativity: " + resolveWMICacheMemoryAssociativity(cache.associativity()) + System.lineSeparator() +
                            "Location: " + resolveWMICacheMemoryLocation(cache.location()) + System.lineSeparator() +
                            "Error Correct Type: " + resolveWMICacheErrorCorrectType(cache.errorCorrectType()) + System.lineSeparator() +
                            "Availability: " + resolveWMIAvailability(cache.availability()) + System.lineSeparator() +
                            "Status: " + cache.status() + System.lineSeparator() + System.lineSeparator()
            ));
        }
    }
}
