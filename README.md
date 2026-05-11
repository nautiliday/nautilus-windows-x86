# Nautilus Windows

32-bit Windows build of Nautilus

# Download

## Pre-Built Binaries

Pre-built binaries are not signed, which means your antivirus or smart-screen may flag it. Make sure you download
it from trusted sources only.

Binaries can be found in [Releases](https://github.com/nautiliday/nautilus-windows-x86/releases) page.

## Building from source

### Pre-requisites

- JDK 17
- Build Tool: Maven (optional, maven wrapper will be provided)

### Steps

- Clone the repository
```shell
git clone https://github.com/nautiliday/nautilus-windows-x86.git
cd Nautilus
```
- Build the application 
```shell
./mvnw -Pdist package jpackage:jpackage@win
```

The generated binaries will be available in `{project.directory}/target/output`

> [!IMPORTANT]
> Note that each subsequent build requires manually clearing out the target folder because for some reason,
> generated binary stays in read-only mode and maven fails to replace or remove the read-only binary.

- The build uses `jpackage` to generate native app images
- Output format depends on the target OS
- A minimal runtime is generated using `jlink` which is then packaged into the target build
- This makes your build portable

# Screenshots

### Windows

A modified build of Nautilus with hidden Hardware ID and Processor ID, running on Windows 11 24H2

<img width="850" height="640" alt="Screenshot 2026-04-07 122534" src="https://github.com/user-attachments/assets/79bcb6bf-ea05-4c85-9e2f-5b27a5d57208" />

# License

[GPLv3](/LICENSE.md)
