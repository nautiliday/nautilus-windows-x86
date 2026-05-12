---
name: Bug report
about: Reporting bugs encountered while using Nautilus
title: ''
labels: bug
assignees: eggy03

---

## Describe the bug

A clear and concise description of what the bug is.

## To Reproduce

Steps to reproduce the behavior:
-

-
-

## Expected behavior

A clear and concise description of what you expected to happen.

## Screenshots

If applicable, add screenshots to help explain your problem.

## Logs

Provide INFO level logs. DEBUG level logs may be asked if required.

## Windows and PowerShell Version

Provide the Windows and PowerShell version of the target machine

```shell
winver
```

```shell
$PSVersionTable.PSVersion
```

## Optional Details you can include

```shell
Get-WinSystemLocale
```

```shell
$OutputEncoding
```

```shell
Get-ExecutionPolicy -List
```

## WMI Repository Integrity

```shell
winmgmt /verifyrepository
```

Also check if your AV or GroupPolicy changes prevent WMI from being called in the target machine**
